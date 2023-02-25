/*
 *    GeoAPI - Java interfaces for OGC/ISO standards
 *    http://www.geoapi.org
 *
 *    Copyright (C) 2006-2021 Open Geospatial Consortium, Inc.
 *    All Rights Reserved. http://www.opengeospatial.org/ogc/legal
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
package org.opengis.temporal;

import org.opengis.annotation.UML;

import static org.opengis.annotation.Obligation.*;
import static org.opengis.annotation.Specification.*;

/**
 * Provides an operation for determining the position of this {@linkplain TemporalPrimitive
 * temporal primitive} relative to another {@linkplain TemporalPrimitive temporal primitive}.
 *
 * @author Stephane Fellah (Image Matters)
 * @author Alexander Petkov
 * @since   2.3
 * @version 4.0
 */
@UML(identifier="TM_Order", specification=ISO_19108)
public interface TemporalOrder {
    /**
     * Determines the position of this <i>primitive</i> relative to another {@link TemporalPrimitive}.
     *
     * @param other the other primitive.
     * @return {@link RelativePosition} which represents the position of this <i>primitive</i> relative to another {@link TemporalPrimitive}.
     */
    @UML(identifier="relativePosition", obligation=MANDATORY, specification=ISO_19108)
    RelativePosition relativePosition(TemporalPrimitive other);
}
