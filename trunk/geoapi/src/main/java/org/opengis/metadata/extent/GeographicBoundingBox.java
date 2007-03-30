/*$************************************************************************************************
 **
 ** $Id$
 **
 ** $URL$
 **
 ** Copyright (C) 2004-2005 Open GIS Consortium, Inc.
 ** All Rights Reserved. http://www.opengis.org/legal/
 **
 *************************************************************************************************/
package org.opengis.metadata.extent;

import org.opengis.annotation.UML;

import static org.opengis.annotation.Obligation.*;
import static org.opengis.annotation.Specification.*;


/**
 * Geographic position of the dataset. This is only an approximate
 * so specifying the co-ordinate reference system is unnecessary.
 *
 * @version <A HREF="http://www.opengeospatial.org/standards/as#01-111">ISO 19115</A>
 * @author Martin Desruisseaux (IRD)
 * @since GeoAPI 1.0
 */
@UML(identifier="EX_GeographicBoundingBox", specification=ISO_19115)
public interface GeographicBoundingBox extends GeographicExtent {
    /**
     * Returns the western-most coordinate of the limit of the
     * dataset extent. The value is expressed in longitude in
     * decimal degrees (positive east).
     *
     * @return The western-most longitude between -180 and +180�.
     * @unitof Angle
     * @returns Double mandatory for valid content, may be null for an invalid document 
     */
    @UML(identifier="westBoundLongitude", obligation=MANDATORY, specification=ISO_19115)
    Double getWestBoundLongitude();

    /**
     * Returns the eastern-most coordinate of the limit of the
     * dataset extent. The value is expressed in longitude in
     * decimal degrees (positive east).
     *
     * @return The eastern-most longitude between -180 and +180�.
     * @unitof Angle
     * @returns Double mandatory for valid content, may be null for an invalid document 
     */
    @UML(identifier="eastBoundLongitude", obligation=MANDATORY, specification=ISO_19115)
    Double getEastBoundLongitude();

    /**
     * Returns the southern-most coordinate of the limit of the
     * dataset extent. The value is expressed in latitude in
     * decimal degrees (positive north).
     *
     * @return The southern-most latitude between -90 and +90�.
     * @unitof Angle
     * @returns Double mandatory for valid content, may be null for an invalid document 
     */
    @UML(identifier="southBoundLatitude", obligation=MANDATORY, specification=ISO_19115)
    Double getSouthBoundLatitude();

    /**
     * Returns the northern-most, coordinate of the limit of the
     * dataset extent. The value is expressed in latitude in
     * decimal degrees (positive north).
     *
     * @return The northern-most latitude between -90 and +90�.
     * @unitof Angle
     * @returns Double mandatory for valid content, may be null for an invalid document 
     */
    @UML(identifier="northBoundLatitude", obligation=MANDATORY, specification=ISO_19115)
    Double getNorthBoundLatitude();
}
