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
import org.opengis.filter.Expression;


/**
 * The ColorMap element defines the mapping of palette-type raster colors or fixed-
 * numeric pixel values to colors using an Interpolate or Categorize SE function.
 * For example, a DEM raster giving elevations in meters above sea level can be
 * translated to a colored  image with a ColorMap.  The quantity attributes of
 * a color-map are used for translating between numeric  matrixes and color
 * rasters and the ColorMap entries should be in order of increasing numeric
 * quantity so  that intermediate numeric values can be matched to a color (or
 * be interpolated between two colors).   Labels may be used for legends or
 * may be used in the future to match character values.   Not all systems can
 * support opacity in colormaps.  The default opacity is 1.0 (fully opaque).
 * Defaults for quantity and label are system-dependent.
 *
 * @version <A HREF="http://www.opengeospatial.org/standards/symbol">Symbology Encoding Implementation Specification 1.1.0</A>
 * @author Open Geospatial Consortium
 * @author Johann Sorel (Geomatys)
 */
@XmlElement("ColorMap")
public interface ColorMap {
    /**
     *
     * @return Interpolate or Categorize function
     */
    Expression getFunction();

    /**
     * calls the visit method of a StyleVisitor
     *
     * @param visitor the style visitor
     */
    Object accept(StyleVisitor visitor, Object extraData);
}
