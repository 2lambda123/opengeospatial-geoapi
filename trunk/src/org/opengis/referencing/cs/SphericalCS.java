/*$************************************************************************************************
 **
 ** $Id$
 **
 ** $Source$
 **
 ** Copyright (C) 2003-2005 Open GIS Consortium, Inc.
 ** All Rights Reserved. http://www.opengis.org/legal/
 **
 *************************************************************************************************/
package org.opengis.referencing.cs;

// Annotations
import org.opengis.annotation.UML;
import static org.opengis.annotation.Obligation.*;
import static org.opengis.annotation.Specification.*;


/**
 * A three-dimensional coordinate system with one distance measured from the origin and two
 * angular coordinates. Not to be confused with an {@linkplain EllipsoidalCS ellipsoidal
 * coordinate system} based on an ellipsoid "degenerated" into a sphere.
 * A <code>SphericalCS</code> shall have three {@linkplain #getAxis axis associations}.
 *
 * <TABLE CELLPADDING='6' BORDER='1'>
 * <TR BGCOLOR="#EEEEFF"><TH NOWRAP>Used with CRS type(s)</TH></TR>
 * <TR><TD>
 *   {@link org.opengis.referencing.crs.GeocentricCRS  Geocentric},
 *   {@link org.opengis.referencing.crs.EngineeringCRS Engineering}
 * </TD></TR></TABLE>
 *
 * @author ISO 19111
 * @author <A HREF="http://www.opengis.org">OpenGIS&reg; consortium</A>
 * @version <A HREF="http://www.opengis.org/docs/03-073r1.zip">Abstract specification 2.0</A>
 * @since GeoAPI 1.0
 */
@UML(identifier="CS_SphericalCS", specification=ISO_19111)
public interface SphericalCS extends CoordinateSystem {
}
