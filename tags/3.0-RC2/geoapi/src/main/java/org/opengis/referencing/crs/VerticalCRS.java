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
package org.opengis.referencing.crs;

import org.opengis.referencing.cs.VerticalCS;
import org.opengis.referencing.datum.VerticalDatum;
import org.opengis.annotation.UML;

import static org.opengis.annotation.Obligation.*;
import static org.opengis.annotation.Specification.*;


/**
 * A 1D coordinate reference system used for recording heights or depths. Vertical CRSs make use
 * of the direction of gravity to define the concept of height or depth, but the relationship with
 * gravity may not be straightforward.
 * <p>
 * By implication, ellipsoidal heights (<var>h</var>) cannot be captured in a vertical coordinate
 * reference system. Ellipsoidal heights cannot exist independently, but only as inseparable part
 * of a 3D coordinate tuple defined in a geographic 3D coordinate reference system. However GeoAPI
 * does not enforce this rule. Some applications may relax this rule and accept ellipsoidal heights
 * in the following context:
 *
 * <ul>
 *   <li><p>As a transient state while parsing <A HREF="../doc-files/WKT.html">Well Known Text</A>,
 *       or any other format based on legacy specifications where ellipsoidal heights were allowed
 *       as an independent axis.</p></li>
 *
 *   <li><p>As short-lived objects to be passed or returned by methods enforcing type safety, for
 *       example {@link org.opengis.metadata.extent.VerticalExtent#getVerticalCRS}.</p></li>
 *
 *   <li><p>Other cases at implementor convenience. However implementors are encouraged to
 *       assemble the full 3D CRS as soon as they can.</p></li>
 * </ul>
 *
 * <TABLE CELLPADDING='6' BORDER='1'>
 * <TR BGCOLOR="#EEEEFF"><TH NOWRAP>Used with CS type(s)</TH></TR>
 * <TR><TD>
 *   {@link org.opengis.referencing.cs.VerticalCS Vertical}
 * </TD></TR></TABLE>
 *
 * @author  Martin Desruisseaux (IRD)
 * @version 3.0
 * @since   1.0
 *
 * @navassoc 1 - - VerticalDatum
 * @navassoc 1 - - VerticalCS
 */
@UML(identifier="SC_VerticalCRS", specification=ISO_19111)
public interface VerticalCRS extends SingleCRS {
    /**
     * Returns the coordinate system, which must be vertical.
     */
    @UML(identifier="coordinateSystem", obligation=MANDATORY, specification=ISO_19111)
    VerticalCS getCoordinateSystem();

    /**
     * Returns the datum, which must be vertical.
     */
    @UML(identifier="datum", obligation=MANDATORY, specification=ISO_19111)
    VerticalDatum getDatum();
}