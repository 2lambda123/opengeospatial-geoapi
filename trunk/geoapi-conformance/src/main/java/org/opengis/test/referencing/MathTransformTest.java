/*
 *    GeoAPI - Java interfaces for OGC/ISO standards
 *    http://www.geoapi.org
 *
 *    Copyright (C) 2011 Open Geospatial Consortium, Inc.
 *    All Rights Reserved. http://www.opengeospatial.org/ogc/legal
 *
 *    Permission to use, copy, and modify this software and its documentation, with
 *    or without modification, for any purpose and without fee or royalty is hereby
 *    granted, provided that you include the following on ALL copies of the software
 *    and documentation or portions thereof, including modifications, that you make:
 *
 *    1. The full text of this NOTICE in a location viewable to users of the
 *       redistributed or derivative work.
 *    2. Notice of any changes or modifications to the OGC files, including the
 *       date changes were made.
 *
 *    THIS SOFTWARE AND DOCUMENTATION IS PROVIDED "AS IS," AND COPYRIGHT HOLDERS MAKE
 *    NO REPRESENTATIONS OR WARRANTIES, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 *    TO, WARRANTIES OF MERCHANTABILITY OR FITNESS FOR ANY PARTICULAR PURPOSE OR THAT
 *    THE USE OF THE SOFTWARE OR DOCUMENTATION WILL NOT INFRINGE ANY THIRD PARTY
 *    PATENTS, COPYRIGHTS, TRADEMARKS OR OTHER RIGHTS.
 *
 *    COPYRIGHT HOLDERS WILL NOT BE LIABLE FOR ANY DIRECT, INDIRECT, SPECIAL OR
 *    CONSEQUENTIAL DAMAGES ARISING OUT OF ANY USE OF THE SOFTWARE OR DOCUMENTATION.
 *
 *    The name and trademarks of copyright holders may NOT be used in advertising or
 *    publicity pertaining to the software without specific, written prior permission.
 *    Title to copyright in this software and any associated documentation will at all
 *    times remain with copyright holders.
 */
package org.opengis.test.referencing;

import java.util.List;
import java.util.Arrays;
import java.util.Random;
import java.awt.geom.Rectangle2D;

import org.opengis.util.Factory;
import org.opengis.util.FactoryException;
import org.opengis.geometry.DirectPosition;
import org.opengis.parameter.ParameterValueGroup;
import org.opengis.referencing.operation.CoordinateOperation;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;
import org.opengis.referencing.operation.MathTransformFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static java.lang.StrictMath.*;
import static org.junit.Assume.*;
import static org.opengis.test.Validators.*;


/**
 * Tests {@link MathTransform}s from the {@code org.opengis.referencing.operation} package.
 * Math transform instances are created using the factory given at construction time.
 *
 * In order to specify their factory and run the tests in a JUnit framework, implementors can
 * define a subclass as below:
 *
 * <blockquote><pre>import org.junit.runner.RunWith;
 *import org.junit.runners.JUnit4;
 *import org.opengis.test.referencing.MathTransformTest;
 *
 *&#64;RunWith(JUnit4.class)
 *public class MyTest extends MathTransformTest {
 *    public MyTest() {
 *        super(new MyMathTransformFactory());
 *    }
 *}</pre></blockquote>
 *
 * Alternatively this test class can also be used directly in the {@link org.opengis.test.TestSuite},
 * which combine every tests defined in the GeoAPI conformance module.
 *
 * @author  Martin Desruisseaux (Geomatys)
 * @version 3.1
 * @since   3.1
 */
@RunWith(Parameterized.class)
public strictfp class MathTransformTest extends TransformTestCase {
    /**
     * The factory for creating {@link MathTransform} objects, or {@code null} if none.
     */
    protected final MathTransformFactory factory;

    /**
     * {@code true} if the {@linkplain #transform} being tested is a map projection
     * from a geographic CRS to a projected CRS. This flag shall be set together
     * with the {@link #tolerance} threshold before the {@code verify(...)} methods
     * are invoked.
     */
    private transient boolean isProjection;

    /**
     * Returns a default set of factories to use for running the tests. Those factories are given
     * in arguments to the constructor when this test class is instantiated directly by JUnit (for
     * example as a {@linkplain org.junit.runners.Suite.SuiteClasses suite} element), instead than
     * subclassed by the implementor. The factories are fetched as documented in the
     * {@link #factories(Class[])} javadoc.
     *
     * @return The default set of arguments to be given to the {@code MathTransformTest} constructor.
     */
    @Parameterized.Parameters
    public static List<Factory[]> factories() {
        return factories(MathTransformFactory.class);
    }

    /**
     * Creates a new test using the given factory. If the given factory is {@code null},
     * then the tests will be skipped.
     *
     * @param factory Factory for creating {@link MathTransform} instances.
     */
    public MathTransformTest(final MathTransformFactory factory) {
        this.factory = factory;
    }

    /**
     * Creates a math transform for the {@linkplain CoordinateOperation coordinate operation}
     * identified by the given EPSG code, and stores the result in the {@link #transform} field.
     * The set of allowed codes is documented in the {@link PseudoEpsgFactory#createParameters(int)}
     * method.
     * <p>
     * This method shall also set the {@link #tolerance} threshold in units of the target CRS
     * (typically metres), and the {@link #derivativeDeltas} in units of the source CRS
     * (typically degrees). The default implementation set the following values:
     * <p>
     * <ul>
     *   <li>{@link #tolerance} is sets to half the precision of the sample coordinate points
     *       given in the EPSG guidance document.</li>
     *   <li>{@link #derivativeDeltas} is set to a value in degrees corresponding to
     *       approximatively 1 metre on Earth (calculated using the standard nautical mile length).
     *       A finer value can lead to more accurate derivative approximation by the
     *       {@link #verifyDerivative(double[])} method, at the expense of more sensitivity
     *       to the accuracy of the {@link MathTransform#transform MathTransform.transform(...)}
     *       method being tested.</li>
     * </ul>
     * <p>
     * Subclasses can override this method if they want to customize the math transform creations,
     * or the tolerance and delta values.
     *
     * @param  code The EPSG code of the {@linkplain CoordinateOperation coordinate operation} to create.
     * @throws FactoryException If the math transform for the given projected CRS can not be created.
     */
    protected void createMathTransform(final int code) throws FactoryException {
        final ParameterValueGroup parameters = PseudoEpsgFactory.createParameters(factory, code);
        validate(parameters);
        transform = factory.createParameterizedTransform(parameters);
        tolerance = 0.005;
        derivativeDeltas = new double[transform.getSourceDimensions()];
        Arrays.fill(derivativeDeltas, toRadians(1.0 / 60) / 1852); // Approximatively one metre.
    }

    /**
     * Runs the test for the given EPSG Coordinate Reference System code. The set of allowed codes
     * is documented in the {@link PseudoEpsgFactory#createParameters(int)} method.
     *
     * @param  code The EPSG code of a target Coordinate Reference System.
     * @throws FactoryException If the math transform can not be created.
     * @throws TransformException If the example point can not be transformed.
     */
    private void run(int code) throws FactoryException, TransformException {
        assumeNotNull(factory);
        final SamplePoints sample = SamplePoints.getSamplePoints(code);
        createMathTransform(sample.operation);
        validate(transform);
        verifyTransform(sample.sourcePoints, sample.targetPoints);
        /*
         * At this point, we have been able to transform the sample points.
         * Now test the transform consistency using many random points inside
         * the area of validity.
         */
        final Rectangle2D areaOfValidity = sample.areaOfValidity;
        verifyInDomain(new double[] {
            areaOfValidity.getMinX(),
            areaOfValidity.getMinY()
        }, new double[] {
            areaOfValidity.getMaxX(),
            areaOfValidity.getMaxY()
        }, new int[] {
            50, 50
        }, new Random(code));
    }

    /**
     * Testes the "<cite>Mercator (variant A)</cite>" (EPSG:9804) projection.
     * First, this method transforms the point given in the <cite>Example</cite> section of the
     * EPSG guidance note and compares the {@link MathTransform} result with the expected result.
     * Next, this method transforms a random set of points in the projection area of validity
     * and ensures that the {@linkplain MathTransform#inverse() inverse transform} and the
     * {@linkplain MathTransform#derivative derivatives} are coherent.
     * <p>
     * The math transform parameters are:
     * <table border="1" cellspacing="0" cellpadding="2">
     * <tr><th>Parameter</th><th>Value</th></tr>
     * <tr><td>semi-major axis</td><td>6377397.155</td></tr>
     * <tr><td>semi-minor axis</td><td>6356078.962818189</td></tr>
     * <tr><td>Latitude of natural origin</td><td>0.0</td></tr>
     * <tr><td>Longitude of natural origin</td><td>110.0</td></tr>
     * <tr><td>Scale factor at natural origin</td><td>0.997</td></tr>
     * <tr><td>False easting</td><td>3900000.0</td></tr>
     * <tr><td>False northing</td><td>900000.0</td></tr>
     * </table>
     *
     * @throws FactoryException If the math transform can not be created.
     * @throws TransformException If the example point can not be transformed.
     */
    @Test
    public void testMercator1SP() throws FactoryException, TransformException {
        isProjection = true;
        run(3002);  // "Makassar / NEIEZ"
    }

    /**
     * Testes the "<cite>Mercator (variant B)</cite>" (EPSG:9805) projection.
     * First, this method transforms the point given in the <cite>Example</cite> section of the
     * EPSG guidance note and compares the {@link MathTransform} result with the expected result.
     * Next, this method transforms a random set of points in the projection area of validity
     * and ensures that the {@linkplain MathTransform#inverse() inverse transform} and the
     * {@linkplain MathTransform#derivative derivatives} are coherent.
     * <p>
     * The math transform parameters are:
     * <table border="1" cellspacing="0" cellpadding="2">
     * <tr><th>Parameter</th><th>Value</th></tr>
     * <tr><td>semi-major axis</td><td>6378245.0</td></tr>
     * <tr><td>semi-minor axis</td><td>6356863.018773047</td></tr>
     * <tr><td>Latitude of 1st standard parallel</td><td>42.0</td></tr>
     * <tr><td>Longitude of natural origin</td><td>51.0</td></tr>
     * <tr><td>False easting</td><td>0.0</td></tr>
     * <tr><td>False northing</td><td>0.0</td></tr>
     * </table>
     *
     * @throws FactoryException If the math transform can not be created.
     * @throws TransformException If the example point can not be transformed.
     */
    @Test
    public void testMercator2SP() throws FactoryException, TransformException {
        isProjection = true;
        run(3388);  // "Pulkovo 1942 / Caspian Sea Mercator"
    }

    /**
     * Testes the "<cite>Mercator Popular Visualisation Pseudo Mercator</cite>" (EPSG:1024) projection.
     * First, this method transforms the point given in the <cite>Example</cite> section of the
     * EPSG guidance note and compares the {@link MathTransform} result with the expected result.
     * Next, this method transforms a random set of points in the projection area of validity
     * and ensures that the {@linkplain MathTransform#inverse() inverse transform} and the
     * {@linkplain MathTransform#derivative derivatives} are coherent.
     * <p>
     * The math transform parameters are:
     * <table border="1" cellspacing="0" cellpadding="2">
     * <tr><th>Parameter</th><th>Value</th></tr>
     * <tr><td>semi-major axis</td><td>6378137.0</td></tr>
     * <tr><td>semi-minor axis</td><td>6356752.314245179</td></tr>
     * <tr><td>Latitude of natural origin</td><td>0.0</td></tr>
     * <tr><td>Longitude of natural origin</td><td>0.0</td></tr>
     * <tr><td>False easting</td><td>0.0</td></tr>
     * <tr><td>False northing</td><td>0.0</td></tr>
     * </table>
     *
     * @throws FactoryException If the math transform can not be created.
     * @throws TransformException If the example point can not be transformed.
     */
    @Test
    public void testPseudoMercator() throws FactoryException, TransformException {
        isProjection = true;
        run(3857);  // "WGS 84 / Pseudo-Mercator"
    }

    /**
     * Testes the "<cite>Lambert Conic Conformal (1SP)</cite>" (EPSG:9801) projection.
     * First, this method transforms the point given in the <cite>Example</cite> section of the
     * EPSG guidance note and compares the {@link MathTransform} result with the expected result.
     * Next, this method transforms a random set of points in the projection area of validity
     * and ensures that the {@linkplain MathTransform#inverse() inverse transform} and the
     * {@linkplain MathTransform#derivative derivatives} are coherent.
     * <p>
     * The math transform parameters are:
     * <table border="1" cellspacing="0" cellpadding="2">
     * <tr><th>Parameter</th><th>Value</th></tr>
     * <tr><td>semi-major axis</td><td>6378206.4</td></tr>
     * <tr><td>semi-minor axis</td><td>6356583.8</td></tr>
     * <tr><td>Latitude of natural origin</td><td>18.0</td></tr>
     * <tr><td>Longitude of natural origin</td><td>-77.0</td></tr>
     * <tr><td>Scale factor at natural origin</td><td>1.0</td></tr>
     * <tr><td>False easting</td><td>250000.0</td></tr>
     * <tr><td>False northing</td><td>150000.0</td></tr>
     * </table>
     *
     * @throws FactoryException If the math transform can not be created.
     * @throws TransformException If the example point can not be transformed.
     */
    @Test
    public void testLambertConicConformal1SP() throws FactoryException, TransformException {
        isProjection = true;
        run(24200);  // "JAD69 / Jamaica National Grid"
    }

    /**
     * Testes the "<cite>Lambert Conic Conformal (2SP)</cite>" (EPSG:9802) projection.
     * First, this method transforms the point given in the <cite>Example</cite> section of the
     * EPSG guidance note and compares the {@link MathTransform} result with the expected result.
     * Next, this method transforms a random set of points in the projection area of validity
     * and ensures that the {@linkplain MathTransform#inverse() inverse transform} and the
     * {@linkplain MathTransform#derivative derivatives} are coherent.
     * <p>
     * The math transform parameters are:
     * <table border="1" cellspacing="0" cellpadding="2">
     * <tr><th>Parameter</th><th>Value</th></tr>
     * <tr><td>semi-major axis</td><td>6378206.4</td></tr>
     * <tr><td>semi-minor axis</td><td>6356583.8</td></tr>
     * <tr><td>Latitude of false origin</td><td>27.833333333333333</td></tr>
     * <tr><td>Longitude of false origin</td><td>-99.0</td></tr>
     * <tr><td>Latitude of 1st standard parallel</td><td>28.383333333333333</td></tr>
     * <tr><td>Latitude of 2nd standard parallel</td><td>30.283333333333333</td></tr>
     * <tr><td>Easting at false origin</td><td>609601.2192024385</td></tr>
     * <tr><td>Northing at false origin</td><td>0.0</td></tr>
     * </table>
     *
     * @throws FactoryException If the math transform can not be created.
     * @throws TransformException If the example point can not be transformed.
     */
    @Test
    public void testLambertConicConformal2SP() throws FactoryException, TransformException {
        isProjection = true;
        run(32040);  // "NAD27 / Texas South Central"
    }

    /**
     * Testes the "<cite>Lambert Conic Conformal (2SP Belgium)</cite>" (EPSG:9803) projection.
     * First, this method transforms the point given in the <cite>Example</cite> section of the
     * EPSG guidance note and compares the {@link MathTransform} result with the expected result.
     * Next, this method transforms a random set of points in the projection area of validity
     * and ensures that the {@linkplain MathTransform#inverse() inverse transform} and the
     * {@linkplain MathTransform#derivative derivatives} are coherent.
     * <p>
     * The math transform parameters are:
     * <table border="1" cellspacing="0" cellpadding="2">
     * <tr><th>Parameter</th><th>Value</th></tr>
     * <tr><td>semi-major axis</td><td>6378388.0</td></tr>
     * <tr><td>semi-minor axis</td><td>6356911.9461279465</td></tr>
     * <tr><td>Latitude of false origin</td><td>90.0</td></tr>
     * <tr><td>Longitude of false origin</td><td>4.356939722222222</td></tr>
     * <tr><td>Latitude of 1st standard parallel</td><td>49.83333333333333</td></tr>
     * <tr><td>Latitude of 2nd standard parallel</td><td>51.16666666666667</td></tr>
     * <tr><td>Easting at false origin</td><td>150000.01256</td></tr>
     * <tr><td>Northing at false origin</td><td>5400088.4378</td></tr>
     * </table>
     *
     * @throws FactoryException If the math transform can not be created.
     * @throws TransformException If the example point can not be transformed.
     */
    @Test
    public void testLambertConicConformalBelgium() throws FactoryException, TransformException {
        isProjection = true;
        run(31300);  // "Belge 1972 / Belge Lambert 72"
    }

    /**
     * Testes the "<cite>IGNF:MILLER</cite>" (EPSG:310642901) projection.
     * First, this method transforms the point given by the
     * <a href="http://api.ign.fr/geoportail/api/doc/fr/developpeur/wmsc.html">IGN documentation</a>
     * and compares the {@link MathTransform} result with the expected result. Next, this method
     * transforms a random set of points in the projection area of validity and ensures that the
     * {@linkplain MathTransform#inverse() inverse transform} and the
     * {@linkplain MathTransform#derivative derivatives} are coherent.
     * <p>
     * The math transform parameters are:
     * <table border="1" cellspacing="0" cellpadding="2">
     * <tr><th>Parameter</th><th>Value</th></tr>
     * <tr><td>semi_major</td><td>6378137.0</td></tr>
     * <tr><td>semi_minor</td><td>6378137.0</td></tr>
     * <tr><td>latitude_of_center</td><td>0.0</td></tr>
     * <tr><td>longitude_of_center</td><td>0.0</td></tr>
     * <tr><td>false_easting</td><td>0.0</td></tr>
     * <tr><td>false_northing</td><td>0.0</td></tr>
     * </table>
     *
     * @throws FactoryException If the math transform can not be created.
     * @throws TransformException If the example point can not be transformed.
     */
    @Test
    public void testMiller() throws FactoryException, TransformException {
        isProjection = true;
        run(310642901);  // "IGNF:MILLER"
    }

    /**
     * Returns the tolerance threshold for comparing the given ordinate value. The default
     * implementation applies the following rules:
     * <p>
     * <ul>
     *   <li>For {@linkplain CalculationType#DIRECT_TRANSFORM direct transforms},
     *       return directly the {@linkplain #tolerance tolerance} value. When the transform to
     *       be tested is a map projection, this tolerance value is measured in metres.</li>
     *   <li>For {@linkplain CalculationType#INVERSE_TRANSFORM inverse transforms},
     *       if the transform being tested is a map projection, then the {@linkplain #tolerance
     *       tolerance} value is converted from metres to decimal degrees except for longitudes
     *       at a pole in which case the tolerance value is set to 360°.</li>
     *   <li>For {@linkplain CalculationType#DERIVATIVE derivatives}, returns a
     *       relative {@linkplain #tolerance tolerance} value instead than the absolute value.
     *       Relative tolerance values are required because derivative values close to a pole
     *       may tend toward infinity.</li>
     *   <li>For {@linkplain CalculationType#STRICT strict} comparisons,
     *       unconditionally returns 0.</li>
     * </ul>
     */
    @Override
    protected double tolerance(final DirectPosition coordinate, final int dimension, final CalculationType mode) {
        double tol = tolerance;
        switch (mode) {
            case STRICT: {
                tol = 0;
                break;
            }
            case DERIVATIVE: {
                tol = max(10*tol, tol * abs(coordinate.getOrdinate(dimension)));
                break;
            }
            case INVERSE_TRANSFORM: {
                if (isProjection) {
                    if (dimension == 0 && abs(coordinate.getOrdinate(1)) == 90) {
                        tol = 360;
                    } else {
                        tol /= (1852 * 60); // 1 nautical miles = 1852 metres in 1 minute of angle.
                    }
                }
                break;
            }
        }
        return tol;
    }
}
