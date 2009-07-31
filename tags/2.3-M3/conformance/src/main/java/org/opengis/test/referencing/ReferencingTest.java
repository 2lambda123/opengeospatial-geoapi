/*$************************************************************************************************
 **
 ** $Id$
 **
 ** $URL$
 **
 ** Copyright (C) 2009 Open GIS Consortium, Inc.
 ** All Rights Reserved. http://www.opengis.org/legal/
 **
 *************************************************************************************************/
package org.opengis.test.referencing;

import java.util.Map;
import java.util.Collections;
import javax.measure.unit.SI;
import javax.measure.unit.NonSI;

import org.opengis.referencing.cs.*;
import org.opengis.referencing.crs.*;
import org.opengis.referencing.datum.*;
import org.opengis.referencing.IdentifiedObject;
import org.opengis.referencing.FactoryException;

import org.junit.Test;
import org.opengis.test.TestCase;

import static org.junit.Assume.*;
import static org.opengis.test.Validators.*;


/**
 * Tests {@linkplain CoordinateReferenceSystem Coordinate Reference System} and related objects
 * from the {@code org.opengis.referencing.crs}, {@code cs} and {@code datum} packages. CRS
 * instances are created using the factories given at construction time.
 *
 * @author Cédric Briançon (Geomatys)
 * @since GeoAPI 2.3
 */
public abstract class ReferencingTest extends TestCase {
    /**
     * Factory to build a coordinate reference system, or {@code null} if none.
     */
    protected final CRSFactory crsFactory;

    /**
     * Factory to build a coordinate system, or {@code null} if none.
     */
    protected final CSFactory csFactory;

    /**
     * Factory to build a datum, or {@code null} if none.
     */
    protected final DatumFactory datumFactory;

    /**
     * Creates a new test using the given factories. If a given factory is {@code null},
     * then the tests which depend on it will be skipped.
     *
     * @param crsFactory   Factory for creating a {@link CoordinateReferenceSystem}.
     * @param csFactory    Factory for creating a {@link CoordinateSystem}.
     * @param datumFactory Factory for creating a {@link Datum}.
     */
    protected ReferencingTest(final CRSFactory crsFactory, final CSFactory csFactory, final DatumFactory datumFactory) {
        this.crsFactory   = crsFactory;
        this.csFactory    = csFactory;
        this.datumFactory = datumFactory;
    }

    /**
     * Builds a map containing only one value, composed by the {@link IdentifiedObject#NAME_KEY}
     * identifier and the value specified.
     *
     * @param value The value for the name key.
     * @return A map containing only the value specified for the name key.
     */
    private static Map<String,String> createMapWithName(final String value) {
        return Collections.singletonMap(IdentifiedObject.NAME_KEY, value);
    }

    /**
     * Tests the creation of the WGS84 {@linkplain CoordinateReferenceSystem CRS}, and verifies
     * that the axes are in the given (<var>longitude</var>, <var>latitude</var>) order.
     *
     * @throws FactoryException if a factory fails to create a referencing object.
     */
    @Test
    public void testWGS84() throws FactoryException {
        /*
         * Build a geodetic datum.
         */
        assumeNotNull(datumFactory);
        PrimeMeridian primeMeridian = datumFactory.createPrimeMeridian(
                createMapWithName("Greenwich"), 0.0, NonSI.DEGREE_ANGLE);
        validate(primeMeridian);
        Ellipsoid ellipsoid = datumFactory.createEllipsoid(
                createMapWithName("WGS 84"), 6378137.0, 298.257223563, SI.METRE);
        validate(ellipsoid);
        GeodeticDatum datum = datumFactory.createGeodeticDatum(
                createMapWithName("World Geodetic System 1984"), ellipsoid, primeMeridian);
        validate(datum);
        /*
         * Build an ellipsoidal coordinate system.
         */
        assumeNotNull(csFactory);
        CoordinateSystemAxis longitudeAxis = csFactory.createCoordinateSystemAxis(
                createMapWithName("Geodetic longitude"), "\u03BB", AxisDirection.EAST, NonSI.DEGREE_ANGLE);
        validate(longitudeAxis);
        CoordinateSystemAxis latitudeAxis  = csFactory.createCoordinateSystemAxis(
                createMapWithName("Geodetic latitude"),  "\u03C6", AxisDirection.NORTH, NonSI.DEGREE_ANGLE);
        validate(latitudeAxis);
        CoordinateSystemAxis heightAxis  = csFactory.createCoordinateSystemAxis(
                createMapWithName("height"), "h", AxisDirection.UP, SI.METRE);
        validate(heightAxis);
        EllipsoidalCS cs = csFactory.createEllipsoidalCS(
                createMapWithName("WGS 84"), latitudeAxis, longitudeAxis, heightAxis);
        validate(cs);
        /*
         * Finally build the geographic coordinate reference system.
         */
        assumeNotNull(crsFactory);
        GeographicCRS crs = crsFactory.createGeographicCRS(
                createMapWithName("WGS84(DD)"), datum, cs);
        validate(crs);
        /*
         * Validations. Fetch the new references for 'cs', 'longitudeAxis', etc.,
         * which may or may not be the instance that we created.
         */
        cs = crs.getCoordinateSystem();
        longitudeAxis = cs.getAxis(1);
        latitudeAxis  = cs.getAxis(0);
        heightAxis    = cs.getAxis(2);
        assertEquals("Geodetic latitude",  latitudeAxis .getName().toString());
        assertEquals(AxisDirection.NORTH,  latitudeAxis .getDirection());
        assertEquals(NonSI.DEGREE_ANGLE,   latitudeAxis .getUnit());
        assertEquals("Geodetic longitude", longitudeAxis.getName().toString());
        assertEquals(AxisDirection.EAST,   longitudeAxis.getDirection());
        assertEquals(NonSI.DEGREE_ANGLE,   longitudeAxis.getUnit());
        assertEquals("height",             heightAxis   .getName().toString());
        assertEquals(AxisDirection.UP,     heightAxis   .getDirection());
        assertEquals(SI.METRE,             heightAxis   .getUnit());

        datum = crs.getDatum();
        assertEquals("World Geodetic System 1984", datum.getName().toString());
        primeMeridian = datum.getPrimeMeridian();
        assertEquals(0.0, primeMeridian.getGreenwichLongitude(), 0.0);
        assertEquals(NonSI.DEGREE_ANGLE, primeMeridian.getAngularUnit());
    }
}
