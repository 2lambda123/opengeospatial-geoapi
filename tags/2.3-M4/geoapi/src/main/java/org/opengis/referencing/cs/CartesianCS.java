/*$************************************************************************************************
 **
 ** $Id$
 **
 ** $URL$
 **
 ** Copyright (C) 2003-2005 Open GIS Consortium, Inc.
 ** All Rights Reserved. http://www.opengis.org/legal/
 **
 *************************************************************************************************/
package org.opengis.referencing.cs;

import org.opengis.annotation.UML;
import static org.opengis.annotation.Specification.*;


/**
 * A 1-, 2-, or 3-dimensional coordinate system. Gives the position of points relative to
 * orthogonal straight axes in the 2- and 3-dimensional cases. In the 1-dimensional case,
 * it contains a single straight coordinate axis. In the multi-dimensional case, all axes
 * shall have the same length unit of measure. A {@code CartesianCS} shall have one,
 * two, or three {@linkplain #getAxis axis associations}.
 *
 * <TABLE CELLPADDING='6' BORDER='1'>
 * <TR BGCOLOR="#EEEEFF"><TH NOWRAP>Used with CRS type(s)</TH></TR>
 * <TR><TD>
 *   {@link org.opengis.referencing.crs.GeocentricCRS  Geocentric},
 *   {@link org.opengis.referencing.crs.ProjectedCRS   Projected},
 *   {@link org.opengis.referencing.crs.EngineeringCRS Engineering},
 *   {@link org.opengis.referencing.crs.ImageCRS       Image}
 * </TD></TR></TABLE>
 *
 * @departure constraint
 *   ISO 19111 defines <code>CartesianCS</code> as a direct sub-type of <code>CoordinateSystem</code>.
 *   ISO also defines <code>ImageCS</code> as the union of <code>AffineCS</code> and <code>CartesianCS</code>,
 *   for use by <code>ImageCRS</code>. Because the <code>union</code> construct found in some languages like
 *   C/C++ doesn't exist in Java, GeoAPI defines <code>CartesianCS</code> as a sub-type of <code>AffineCS</code>
 *   in order to achieve the same type safety. In this hierarchy, <code>CartesianCS</code> is considered
 *   a special case of <code>AffineCS</code> where all axes are perpendicular to each other.
 *
 * @version <A HREF="http://portal.opengeospatial.org/files/?artifact_id=6716">Abstract specification 2.0</A>
 * @author  Martin Desruisseaux (IRD)
 * @since   GeoAPI 1.0
 *
 * @see AffineCS
 */
@UML(identifier="CS_CartesianCS", specification=ISO_19111)
public interface CartesianCS extends AffineCS {
}