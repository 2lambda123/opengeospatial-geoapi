/*$************************************************************************************************
 **
 ** $Id$
 **
 ** $Source$
 **
 ** Copyright (C) 2003 Open GIS Consortium, Inc. All Rights Reserved. http://www.opengis.org/Legal/
 **
 *************************************************************************************************/
package org.opengis.crs.crs;

// OpenGIS direct dependencies
import org.opengis.crs.cs.CartesianCS;
import org.opengis.crs.crs.projection.Projection;


/**
 * A 2D coordinate reference system used to approximate the shape of the earth on a planar surface.
 * It is done in such a way that the distortion that is inherent to the approximation is carefully
 * controlled and known. Distortion correction is commonly applied to calculated bearings and
 * distances to produce values that are a close match to actual field values.
 *
 * <TABLE CELLPADDING='6' BORDER='1'>
 * <TR BGCOLOR="#EEEEFF"><TH NOWRAP>Used with CS type(s)</TH></TR>
 * <TR><TD>
 *   {@link org.opengis.crs.cs.CartesianCS Cartesian}
 * </TD></TR></TABLE>
 *
 * @UML abstract SC_ProjectedCRS
 * @author ISO 19111
 * @author <A HREF="http://www.opengis.org">OpenGIS&reg; consortium</A>
 * @version <A HREF="http://www.opengis.org/docs/03-073r1.zip">Abstract specification 2.0</A>
 */
public interface ProjectedCRS extends GeneralDerivedCRS {
    /**
     * Returns the map projection.
     *
     * @return The member Projection.
     */
    public Projection getProjection( );

    /**
     * Sets the map projection.
     *
     * @param projection The new Projection.
     */
    public void setProjection(Projection projection);

    /**
     * Returns the coordinate system, which must be cartesian.
     *
     * @return The coordinate system.
     * @UML association usesCS
     */
/// CartesianCS getCoordinateSystem();
}
