/*$************************************************************************************************
 **
 ** $Id$
 **
 ** $Source$
 **
 ** Copyright (C) 2003 Open GIS Consortium, Inc. All Rights Reserved. http://www.opengis.org/Legal/
 **
 *************************************************************************************************/
package org.opengis.referencing.crs;

// OpenGIS direct dependencies
import org.opengis.referencing.cs.EllipsoidalCS;
import org.opengis.referencing.datum.GeodeticDatum;

// Annotations
import org.opengis.annotation.UML;
import static org.opengis.annotation.Obligation.*;
import static org.opengis.annotation.Specification.*;


/**
 * A coordinate reference system based on an ellipsoidal approximation of the geoid; this provides
 * an accurate representation of the geometry of geographic features for a large portion of the
 * earth's surface.
 *
 * <TABLE CELLPADDING='6' BORDER='1'>
 * <TR BGCOLOR="#EEEEFF"><TH NOWRAP>Used with CS type(s)</TH></TR>
 * <TR><TD>
 *   {@link org.opengis.referencing.cs.EllipsoidalCS Ellipsoidal}
 * </TD></TR></TABLE>
 *
 * @author ISO 19111
 * @author <A HREF="http://www.opengis.org">OpenGIS&reg; consortium</A>
 * @version <A HREF="http://www.opengis.org/docs/03-073r1.zip">Abstract specification 2.0</A>
 *
 * @revisit OGC document 01-009 defines a <CODE>getWGS84ConversionInfo()</CODE> method.
 *          A <CODE>getWGS84Parameters()</CODE> method was also defined in the datum.
 *          I see no equivalent in this ISO 19111 specification.
 */
@UML (identifier="SC_GeographicCRS", specification=ISO_19111)
public interface GeographicCRS extends SingleCRS {
    /**
     * Returns the coordinate system, which must be ellipsoidal.
     */
/// @UML (identifier="usesCS", obligation=MANDATORY, specification=ISO_19111)
/// EllipsoidalCS getCoordinateSystem();

    /**
     * Returns the datum, which must be geodetic.
     */
/// @UML (identifier="usesDatum", obligation=MANDATORY, specification=ISO_19111)
/// GeodeticDatum getDatum();
}
