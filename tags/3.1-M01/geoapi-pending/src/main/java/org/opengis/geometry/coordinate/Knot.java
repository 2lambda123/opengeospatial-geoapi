/*
 *    GeoAPI - Java interfaces for OGC/ISO standards
 *    http://www.geoapi.org
 *
 *    Copyright (C) 2004-2011 Open Geospatial Consortium, Inc.
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
package org.opengis.geometry.coordinate;

import org.opengis.annotation.UML;

import static org.opengis.annotation.Obligation.*;
import static org.opengis.annotation.Specification.*;


/**
 * Controls the constructive parameter space for spline curves and surfaces. Each knot sequence
 * is used for a dimension of the spline's parameter space. Thus, in a surface spline, there will
 * be two knot sequences, one for each parameter (<var>u</var>, <var>v</var>).
 * The <var>i</var><sup>th</sup>, <var>j</var><sup>th</sup> would be (<var>u<sub>i</sub></var>,
 * <var>v<sub>j</sub></var>), where the original knot sequences were (<var>u<sub>i</sub></var>)
 * and (<var>v<sub>j</sub></var>). Each knot of a spline curve or surface is described using a
 * {@code Knot}.
 *
 * @version <A HREF="http://www.opengeospatial.org/standards/as">ISO 19107</A>
 * @author Martin Desruisseaux (IRD)
 * @since GeoAPI 2.0
 */
@UML(identifier="GM_Knot", specification=ISO_19107)
public interface Knot {
    /**
     * The value of the parameter at the knot of the spline. The sequence of knots shall be a
     * nondecreasing sequence. That is, each knot's value in the sequence shall be equal to or
     * greater than the previous knot's value. The use of equal consecutive knots is normally
     * handled using the multiplicity.
     */
    @UML(identifier="value", obligation=MANDATORY, specification=ISO_19107)
    double getValue();

    /**
     * The multiplicity of this knot used in the definition of the spline (with the same weight).
     */
    @UML(identifier="multiplicity", obligation=MANDATORY, specification=ISO_19107)
    int getMultiplicity();

    /**
     * The value of the averaging weight used for this knot of the spline.
     */
    @UML(identifier="weight", obligation=MANDATORY, specification=ISO_19107)
    double getWeight();
}