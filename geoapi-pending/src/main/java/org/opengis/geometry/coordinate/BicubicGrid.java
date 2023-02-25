/*
 *    GeoAPI - Java interfaces for OGC/ISO standards
 *    Copyright © 2005-2023 Open Geospatial Consortium, Inc.
 *    http://www.geoapi.org
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.opengis.geometry.coordinate;

import java.util.List;
import org.opengis.annotation.UML;

import static org.opengis.annotation.Obligation.*;
import static org.opengis.annotation.Specification.*;


/**
 * A {@linkplain GriddedSurface gridded surface} that uses cubic polynomial splines as the
 * horizontal and vertical curves. The initial tangents for the splines are often replaced
 * by an extra pair of rows (and columns) of control points.
 * <p>
 * The horizontal and vertical curves require initial and final tangent vectors for a complete
 * definition. These values are supplied by the four methods defined in this interface.
 *
 * @version <A HREF="http://www.opengeospatial.org/standards/as">ISO 19107</A>
 * @author Martin Desruisseaux (IRD)
 * @since GeoAPI 2.0
 */
@UML(identifier="GM_BicubicGrid", specification=ISO_19107)
public interface BicubicGrid extends GriddedSurface {
    /**
     * Returns the initial tangent vectors.
     */
    @UML(identifier="horiVectorAtStart", obligation=MANDATORY, specification=ISO_19107)
    List<double[]> getHorizontalVectorAtStart();

    /**
     * Returns the initial tangent vectors.
     */
    @UML(identifier="horiVectorAtEnd", obligation=MANDATORY, specification=ISO_19107)
    List<double[]> getHorizontalVectorAtEnd();

    /**
     * Returns the initial tangent vectors.
     */
    @UML(identifier="vertVectorAtStart", obligation=MANDATORY, specification=ISO_19107)
    List<double[]> getVerticalVectorAtStart();

    /**
     * Returns the initial tangent vectors.
     */
    @UML(identifier="vertVectorAtEnd", obligation=MANDATORY, specification=ISO_19107)
    List<double[]> getVerticalVectorAtEnd();
}
