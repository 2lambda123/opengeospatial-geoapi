/*
 *    GeoAPI - Java interfaces for OGC/ISO standards
 *    Copyright © 2008-2023 Open Geospatial Consortium, Inc.
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
package org.opengis.style;

import org.opengis.annotation.XmlElement;


/**
 * Behavior when multiple raster images in a layer overlap each other.
 * Overlaps happen for example with satellite-image scenes.
 * The default behavior is system-dependent.
 *
 * @version <A HREF="http://www.opengeospatial.org/standards/symbol">Symbology Encoding Implementation Specification 1.1.0</A>
 * @author Open Geospatial Consortium
 * @author Johann Sorel (Geomatys)
 */
@XmlElement("OverlapBehavior")
public enum OverlapBehavior {
    /**
     * Most recently captured scene on top.
     */
    LATEST_ON_TOP,

    /**
     * Less recently captures scene on top.
     */
    EARLIEST_ON_TOP,

    /**
     * Average multiple scenes together.
     * This can produce blurry results if the source images
     * are not perfectly aligned in their georeferencing.
     */
    AVERAGE,

    /**
     * Select an image (or piece thereof) randomly and place it on top.
     * This can produce crisper results than {@link #AVERAGE} but is potentially
     * more efficiently than {@link #LATEST_ON_TOP} or {@link #EARLIEST_ON_TOP}.
     */
    RANDOM
}
