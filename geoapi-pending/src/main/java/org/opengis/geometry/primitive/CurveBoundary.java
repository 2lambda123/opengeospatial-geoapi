/*
 *    GeoAPI - Java interfaces for OGC/ISO standards
 *    Copyright © 2004-2023 Open Geospatial Consortium, Inc.
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
package org.opengis.geometry.primitive;

import org.opengis.annotation.UML;

import static org.opengis.annotation.Obligation.*;
import static org.opengis.annotation.Specification.*;


/**
 * The boundary of {@linkplain Curve curves}.
 * A {@code CurveBoundary} contains two {@linkplain Point point} references
 * ({@linkplain #getStartPoint start point} and {@linkplain #getEndPoint end point}).
 *
 * @version <A HREF="http://www.opengeospatial.org/standards/as">ISO 19107</A>
 * @author Martin Desruisseaux (IRD)
 * @since GeoAPI 1.0
 */
@UML(identifier="GM_CurveBoundary", specification=ISO_19107)
public interface CurveBoundary extends PrimitiveBoundary {
    /**
     * Returns the start point.
     *
     * @see #getEndPoint
     */
    @UML(identifier="startPoint", obligation=MANDATORY, specification=ISO_19107)
    Point getStartPoint();

    /**
     * Returns the end point.
     *
     * @see #getStartPoint
     */
    @UML(identifier="endPoint", obligation=MANDATORY, specification=ISO_19107)
    Point getEndPoint();
}
