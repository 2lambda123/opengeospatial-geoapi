/*$************************************************************************************************
 **
 ** $Id$
 **
 ** $URL$
 **
 ** Copyright (C) 2005 Open GIS Consortium, Inc.
 ** All Rights Reserved. http://www.opengis.org/legal/
 **
 *************************************************************************************************/
package org.opengis.coverage.grid;

import org.opengis.coverage.GeometryValuePair;
import org.opengis.util.Record;
import org.opengis.annotation.UML;

import static org.opengis.annotation.Obligation.*;
import static org.opengis.annotation.Specification.*;


/**
 * A {@linkplain GeometryValuePair geometry value pair} that has a {@linkplain GridPoint grid point}
 * as the value of its geometry attribute.
 *
 * @version ISO 19123:2004
 * @author  Wim Koolhoven
 * @author  Martin Desruisseaux (IRD)
 * @since   GeoAPI 2.1
 */
@UML(identifier="CV_GridPointValuePair", specification=ISO_19123)
public interface GridPointValuePair extends GeometryValuePair {
    /**
     * The grid point that is a member of this <var>grid point</var>-<var>value</var> pair.
     * It is one of the {@linkplain GridPoint grid points} linked to the
     * {@linkplain GridValuesMatrix grid value matrix} through {@link Grid#getIntersections}.
     *
     * @return The geometry member of the pair.
     */
    @UML(identifier="geometry", obligation=MANDATORY, specification=ISO_19123)
    GridPoint getGeometry();

    /**
     * Holds the record of feature attribute values associated with the grid point.
     *
     * @return The value member of the pair.
     */
    @UML(identifier="value", obligation=MANDATORY, specification=ISO_19123)
    Record getValue();
}
