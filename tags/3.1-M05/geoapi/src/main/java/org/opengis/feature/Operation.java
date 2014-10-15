/*
 *    GeoAPI - Java interfaces for OGC/ISO standards
 *    http://www.geoapi.org
 *
 *    Copyright (C) 2006-2014 Open Geospatial Consortium, Inc.
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
package org.opengis.feature;

import org.opengis.annotation.UML;
import org.opengis.annotation.Classifier;
import org.opengis.annotation.Stereotype;
import org.opengis.parameter.ParameterDescriptorGroup;

import static org.opengis.annotation.Obligation.*;
import static org.opengis.annotation.Specification.ISO_19109;


/**
 * Describes the behaviour of a feature type as a function or a method.
 * Operations can:
 *
 * <ul>
 *   <li>Compute values from the attributes.</li>
 *   <li>Perform actions that change the attribute values.</li>
 * </ul>
 *
 * <blockquote><font size="-1"><b>Example:</b> a mutator operation may raise the height of a dam. This changes
 * may affect other properties like the watercourse and the reservoir associated with the dam.</font></blockquote>
 *
 * This {@code Operation} type is used for defining the required parameters and expected result.
 *
 * @author  Jody Garnett (Refractions Research, Inc.)
 * @author  Martin Desruisseaux (Geomatys)
 * @version 3.1
 * @since   3.1
 */
@Classifier(Stereotype.METACLASS)
@UML(identifier="Operation", specification=ISO_19109)
public interface Operation extends PropertyType {
    /**
     * Returns a description of the input parameters.
     *
     * @return Description of the input parameters.
     */
    @UML(identifier="signature", obligation=MANDATORY, specification=ISO_19109)
    ParameterDescriptorGroup getParameters();

    /**
     * Returns the expected result type, or {@code null} if none.
     *
     * @return The type of the result, or {@code null} if none.
     */
    @UML(identifier="signature", obligation=MANDATORY, specification=ISO_19109)
    IdentifiedType getResult();

    /*
     * TODO: missing invoke method. A possibility is:
     *
     * Object invoke(ParameterValueGroup).
     */
}
