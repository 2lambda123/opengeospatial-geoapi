/**************************************************************************************************
 **
 ** $Id$
 **
 ** $Source$
 **
 ** Copyright (C) 2003 Open GIS Consortium, Inc. All Rights Reserved. http://www.opengis.org/Legal/
 **
 *************************************************************************************************/
package org.opengis.crs.operation;

import org.opengis.crs.crs.CoordinateReferenceSystem;
import org.opengis.spatialschema.geometry.DirectPosition;

/**
 * <code>CoordinateTransformation</code> defines a common abstraction for classes
 * that convert from one <code>CoordinateReferenceSystem</code> to another.
 *
 * @see CoordinateReferenceSystem
 * @see DirectPosition
 *
 * @author  Open GIS Consortium, Inc.
 * @version $Revision$, $Date$
 */
public interface CoordinateTransformation {

    /**
     * Converts the <code>DirectPosition</code> passed in as <code>fromCoordinate</code>
     * to a different type.
     * @param fromCoordinate the <code>DirectPosition</code> to convert.
     * @param toCoordinateInterface the type of <code>DirectPosition</code> to convert to.
     * @return the <code>DirectPosition</code> that results from the conversion.
     */
    public DirectPosition convertDirectPosition(
        DirectPosition fromCoordinate,
        Class toCoordinateInterface,
        CoordinateReferenceSystem toCRS);
}
