/*
 *    GeoAPI - Java interfaces for OGC/ISO standards
 *    http://www.geoapi.org
 *
 *    Copyright (C) 2004-2013 Open Geospatial Consortium, Inc.
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
package org.opengis.referencing.operation;

import java.util.Map;

import org.opengis.annotation.UML;
import org.opengis.util.FactoryException;
import org.opengis.util.NoSuchIdentifierException;
import org.opengis.referencing.ObjectFactory;
import org.opengis.referencing.cs.CartesianCS;
import org.opengis.referencing.cs.CoordinateSystem;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.crs.GeographicCRS;
import org.opengis.referencing.crs.CRSFactory;
import org.opengis.referencing.datum.GeodeticDatum;
import org.opengis.parameter.ParameterValueGroup;
import org.opengis.parameter.ParameterDescriptorGroup;

import static org.opengis.annotation.Specification.*;


/**
 * Creates {@linkplain CoordinateOperation coordinate operations} from
 * {@linkplain org.opengis.parameter.ParameterValueGroup parameter values},
 * or infers operations from source and target CRS.
 * This factory provides two groups of methods:
 * <p>
 * <ul>
 *   <li>Finding instances provided by the implementation:<ul>
 *     <li>{@link #getOperationMethod(String)}</li>
 *     <li>{@link #createOperation(CoordinateReferenceSystem, CoordinateReferenceSystem)}</li>
 *     <li>{@link #createOperation(CoordinateReferenceSystem, CoordinateReferenceSystem, OperationMethod)}</li>
 *   </ul></li>
 *   <li>Creating new instances from user-supplied parameters:<ul>
 *     <li>{@link #createOperationMethod(Map, Integer, Integer, ParameterDescriptorGroup)}</li>
 *     <li>{@link #createDefiningConversion(Map, OperationMethod, ParameterValueGroup)}</li>
 *     <li>{@link #createSingleOperation(Map, CoordinateReferenceSystem, CoordinateReferenceSystem, OperationMethod, ParameterValueGroup)}</li>
 *     <li>{@link #createConcatenatedOperation(Map, CoordinateOperation[])}</li>
 *   </ul></li>
 * </ul>
 *
 * @author  Martin Desruisseaux (IRD, Geomatys)
 * @version 3.1
 * @since   1.0
 */
@UML(identifier="CT_CoordinateTransformationFactory", specification=OGC_01009)
public interface CoordinateOperationFactory extends ObjectFactory {
    /**
     * Returns an operation for conversion or transformation between two coordinate reference systems.
     * <p>
     * <ul>
     *   <li>If an operation exists, it is returned.</li>
     *   <li>If more than one operation exists, the default is returned.</li>
     *   <li>If no operation exists, then the exception is thrown.</li>
     * </ul>
     * <p>
     * Implementations may try to
     * {@linkplain CoordinateOperationAuthorityFactory#createFromCoordinateReferenceSystemCodes
     * query an authority factory} first, and compute the operation next if no operation from
     * {@code source} to {@code target} code was explicitly defined by the authority.
     *
     * @param  sourceCRS Input coordinate reference system.
     * @param  targetCRS Output coordinate reference system.
     * @return A coordinate operation from {@code sourceCRS} to {@code targetCRS}.
     * @throws OperationNotFoundException if no operation path was found from {@code sourceCRS}
     *         to {@code targetCRS}.
     * @throws FactoryException if the operation creation failed for some other reason.
     */
    @UML(identifier="createFromCoordinateSystems", specification=OGC_01009)
    CoordinateOperation createOperation(CoordinateReferenceSystem sourceCRS,
                                        CoordinateReferenceSystem targetCRS)
            throws OperationNotFoundException, FactoryException;

    /**
     * Returns an operation using a particular method for conversion or transformation
     * between two coordinate reference systems.
     * <p>
     * <ul>
     *   <li>If the operation exists on the implementation, then it is returned.</li>
     *   <li>If the operation does not exist on the implementation, then the implementation
     *       has the option of inferring the operation from the argument objects.</li>
     *   <li>If for whatever reason the specified operation will not be returned, then
     *       the exception is thrown.</li>
     * </ul>
     * <p>
     * <b>Example:</b> A transformation between two {@linkplain GeographicCRS geographic CRS} using
     * different {@linkplain GeodeticDatum datum} requires a <cite>datum shift</cite>.
     * Many methods exist for this purpose, including interpolations in a grid,
     * a scale/rotation/translation in geocentric coordinates or the Molodenski approximation.
     * When invoking {@code createOperation(…)} without operation method,
     * this factory may select by default the transformation specified by the authority.
     * When invoking {@code createOperation(…)} with an operation method,
     * user can force usage of Molodenski approximation for instance.
     *
     * @param  sourceCRS Input coordinate reference system.
     * @param  targetCRS Output coordinate reference system.
     * @param  method The algorithmic method for conversion or transformation.
     * @return A coordinate operation from {@code sourceCRS} to {@code targetCRS}.
     * @throws OperationNotFoundException if no operation path was found from {@code sourceCRS}
     *         to {@code targetCRS}.
     * @throws FactoryException if the operation creation failed for some other reason.
     *
     * @departure extension
     *   This method has been added at user request, in order to specify the desired
     *   transformation path when many are available.
     */
    CoordinateOperation createOperation(CoordinateReferenceSystem sourceCRS,
                                        CoordinateReferenceSystem targetCRS,
                                        OperationMethod           method)
            throws OperationNotFoundException, FactoryException;

    /**
     * Creates a parameterized mathematical operation from the given parameter values.
     * Implementations are encouraged to infer the type ({@link TransformException},
     * {@link Conversion} or {@link PassThroughOperation}) from the operation method.
     * <p>
     * Some available properties are {@linkplain ObjectFactory listed there}.
     * Additionally, the following properties are understood by this constructor:
     * <p>
     * <table border="1" cellspacing="0" cellpadding="2">
     *   <tr bgcolor="#CCCCFF" class="TableHeadingColor">
     *     <th nowrap>Property name</th>
     *     <th nowrap>Value type</th>
     *     <th nowrap>Value given to</th>
     *   </tr>
     *   <tr>
     *     <td nowrap>&nbsp;{@value org.opengis.referencing.operation.CoordinateOperation#OPERATION_VERSION_KEY}&nbsp;</td>
     *     <td nowrap>&nbsp;{@link String}&nbsp;</td>
     *     <td nowrap>&nbsp;{@link CoordinateOperation#getOperationVersion()}</td>
     *   </tr>
     *   <tr>
     *     <td nowrap>&nbsp;{@value org.opengis.referencing.operation.CoordinateOperation#COORDINATE_OPERATION_ACCURACY_KEY}&nbsp;</td>
     *     <td nowrap>&nbsp;<code>{@linkplain org.opengis.metadata.quality.PositionalAccuracy}[]</code>&nbsp;</td>
     *     <td nowrap>&nbsp;{@link CoordinateOperation#getCoordinateOperationAccuracy()}</td>
     *   </tr>
     *   <tr>
     *     <td nowrap>&nbsp;{@value org.opengis.referencing.operation.CoordinateOperation#DOMAIN_OF_VALIDITY_KEY}&nbsp;</td>
     *     <td nowrap>&nbsp;{@link org.opengis.metadata.extent.Extent}&nbsp;</td>
     *     <td nowrap>&nbsp;{@link CoordinateOperation#getDomainOfValidity()}</td>
     *   </tr>
     *   <tr>
     *     <td nowrap>&nbsp;{@value org.opengis.referencing.operation.CoordinateOperation#SCOPE_KEY}&nbsp;</td>
     *     <td nowrap>&nbsp;{@link String} or {@link org.opengis.util.InternationalString}&nbsp;</td>
     *     <td nowrap>&nbsp;{@link CoordinateOperation#getScope()}</td>
     *   </tr>
     * </table>
     *
     * @param properties Set of properties. Shall contains at least {@code "name"}.
     * @param sourceCRS  The source coordinate reference system.
     * @param targetCRS  The destination coordinate reference system.
     * @param method     The operation method. A value can be obtained by {@link #getOperationMethod(String)}.
     * @param parameters The parameter values. A default set of parameters can be obtained by
     *         {@code method.getParameters().createValue()} and modified before to be given to
     *         this constructor.
     * @return The conversion, transformation or pass-through operation.
     * @throws FactoryException if the object creation failed.
     *
     * @departure extension
     *   This method has been added because OGC 01-009 does not define a factory
     *   method for creating such object.
     */
    SingleOperation createSingleOperation(Map<String, ?>            properties,
                                          CoordinateReferenceSystem sourceCRS,
                                          CoordinateReferenceSystem targetCRS,
                                          OperationMethod           method,
                                          ParameterValueGroup       parameters)
            throws FactoryException;

    /**
     * Creates a concatenated operation from a sequence of operations.
     *
     * @param  properties Name and other properties to give to the new object.
     *         Available properties are {@linkplain ObjectFactory listed there}.
     * @param  operations The sequence of operations.
     * @return The concatenated operation.
     * @throws FactoryException if the object creation failed.
     *
     * @departure extension
     *   This method has been added because OGC 01-009 does not define a factory
     *   method for creating such object.
     */
    CoordinateOperation createConcatenatedOperation(Map<String, ?> properties,
                                                    CoordinateOperation... operations)
            throws FactoryException;

    /**
     * Creates a defining conversion from a set of properties. Defining conversions have no
     * {@linkplain Conversion#getSourceCRS() source} and {@linkplain Conversion#getTargetCRS() target CRS},
     * and do not need to have a {@linkplain Conversion#getMathTransform() math transform}.
     * Their sole purpose is to be given as an argument to {@linkplain CRSFactory#createDerivedCRS
     * derived CRS} and {@linkplain CRSFactory#createProjectedCRS projected CRS} constructors.
     * <p>
     * Some available properties are {@linkplain ObjectFactory listed there}.
     * Additionally, the following properties are understood by this constructor:
     * <p>
     * <table border="1" cellspacing="0" cellpadding="2">
     *   <tr bgcolor="#CCCCFF" class="TableHeadingColor">
     *     <th nowrap>Property name</th>
     *     <th nowrap>Value type</th>
     *     <th nowrap>Value given to</th>
     *   </tr>
     *   <tr>
     *     <td nowrap>&nbsp;{@value org.opengis.referencing.operation.CoordinateOperation#DOMAIN_OF_VALIDITY_KEY}&nbsp;</td>
     *     <td nowrap>&nbsp;{@link org.opengis.metadata.extent.Extent}&nbsp;</td>
     *     <td nowrap>&nbsp;{@link CoordinateOperation#getDomainOfValidity()}</td>
     *   </tr>
     *   <tr>
     *     <td nowrap>&nbsp;{@value org.opengis.referencing.operation.CoordinateOperation#SCOPE_KEY}&nbsp;</td>
     *     <td nowrap>&nbsp;{@link String} or {@link org.opengis.util.InternationalString}&nbsp;</td>
     *     <td nowrap>&nbsp;{@link CoordinateOperation#getScope()}</td>
     *   </tr>
     * </table>
     *
     * @param  properties Set of properties. Shall contains at least {@code "name"}.
     * @param  method The operation method. A value can be obtained by {@link #getOperationMethod(String)}.
     * @param  parameters The parameter values. A default set of parameters can be obtained by
     *         {@code method.getParameters().createValue()} and modified before to be given to
     *         this constructor.
     * @return The defining conversion.
     * @throws FactoryException if the object creation failed.
     *
     * @see CRSFactory#createProjectedCRS(Map, GeographicCRS, Conversion, CartesianCS)
     * @see CRSFactory#createDerivedCRS(Map, CoordinateReferenceSystem, Conversion, CoordinateSystem)
     *
     * @departure extension
     *   <cite>Defining conversions</cite> is a concept that appears in ISO 19111 textual
     *   specification without formalization in UML diagrams. This concept has been formalized
     *   in GeoAPI in order to allow the creation of <code>ProjectedCRS</code> instances.
     *
     * @since 2.1
     */
    Conversion createDefiningConversion(Map<String,?>       properties,
                                        OperationMethod     method,
                                        ParameterValueGroup parameters)
            throws FactoryException;

    /**
     * Creates an operation method from a set of properties and a descriptor group.
     * This factory method allows the creation of arbitrary {@code OperationMethod}
     * instances. However some implementations may have a collection of build-in
     * operation methods. For obtaining such build-in instance, see
     * {@link #getOperationMethod(String)} instead.
     * <p>
     * Some available properties are {@linkplain ObjectFactory listed there}.
     * Additionally, the following properties are understood by this constructor:
     * <p>
     * <table border="1" cellspacing="0" cellpadding="2">
     *   <tr bgcolor="#CCCCFF" class="TableHeadingColor">
     *     <th nowrap>Property name</th>
     *     <th nowrap>Value type</th>
     *     <th nowrap>Value given to</th>
     *   </tr>
     *   <tr>
     *     <td nowrap>&nbsp;{@value org.opengis.referencing.operation.OperationMethod#FORMULA_KEY}&nbsp;</td>
     *     <td nowrap>&nbsp;{@link Formula}&nbsp;</td>
     *     <td nowrap>&nbsp;{@link OperationMethod#getFormula()}</td>
     *   </tr>
     * </table>
     *
     * @param  properties Set of properties. Shall contains at least {@code "name"}.
     * @param  sourceDimension Number of dimensions in the source CRS of the operation method, or {@code null}.
     * @param  targetDimension Number of dimensions in the target CRS of the operation method, or {@code null}.
     * @param  parameters A description of the parameters for the operation method.
     * @return The operation method.
     * @throws FactoryException if the object creation failed.
     *
     * @departure extension
     *   This method has been added because OGC 01-009 does not define a factory
     *   method for creating such object.
     *
     * @since 3.1
     */
    OperationMethod createOperationMethod(Map<String,?> properties,
                                          Integer  sourceDimension,
                                          Integer  targetDimension,
                                          ParameterDescriptorGroup parameters) throws FactoryException;

    /**
     * Returns the build-in operation method of the given name.
     * This is a helper method for usage of the following methods:
     * <p>
     * <ul>
     *   <li>{@link #createOperation(CoordinateReferenceSystem, CoordinateReferenceSystem, OperationMethod)}</li>
     *   <li>{@link #createDefiningConversion(Map, OperationMethod, ParameterValueGroup)}</li>
     * </ul>
     * <p>
     * Examples of typical operation method names are:
     * <p>
     * <table border="1" cellspacing="0" cellpadding="2">
     *   <tr bgcolor="#CCCCFF" class="TableHeadingColor">
     *     <th nowrap>OGC name</th>
     *     <th nowrap>EPSG name</th>
     *   </tr>
     *   <tr><td>Mercator_1SP</td>                  <td>Mercator (variant A)</td></tr>
     *   <tr><td>Mercator_2SP</td>                  <td>Mercator (variant B)</td></tr>
     *   <tr><td>Transverse_Mercator</td>           <td>Transverse Mercator</td></tr>
     *   <tr><td>Lambert_Conformal_Conic_1SP</td>   <td>Lambert Conic Conformal (1SP)</td></tr>
     *   <tr><td>Lambert_Conformal_Conic_2SP</td>   <td>Lambert Conic Conformal (2SP)</td></tr>
     *   <tr><td>Lambert_Azimuthal_Equal_Area</td>  <td>Lambert Azimuthal Equal Area</td></tr>
     *   <tr><td>Albers_Conic_Equal_Area</td>       <td>Albers Equal Area</td></tr>
     *   <tr><td>Cassini_Soldner</td>               <td>Cassini-Soldner</td></tr>
     *   <tr><td>Orthographic</td>                  <td>Orthographic</td></tr>
     * </table>
     * <p>
     * Implementations may delegate to their {@link MathTransformFactory}, or delegate to their
     * {@link CoordinateOperationAuthorityFactory}, or get the operation method in some other way
     * at implementor choice.
     *
     * @param  name The name of the operation method to fetch.
     * @return The operation method of the given name.
     * @throws NoSuchIdentifierException if no operation method of the given name is known to this factory.
     * @throws FactoryException if the method failed for some other reason.
     *
     * @departure easeOfUse
     *   This method has been added in order to free the user from choosing whether he should
     *   get the operation method from <code>CoordinateOperationAuthorityFactory</code>, or from
     *   <code>MathTransformFactory</code>, or creating it himself.
     *
     * @see MathTransformFactory#getAvailableMethods(Class)
     * @see CoordinateOperationAuthorityFactory#createOperationMethod(String)
     *
     * @since 3.1
     */
    OperationMethod getOperationMethod(String name) throws NoSuchIdentifierException, FactoryException;
}
