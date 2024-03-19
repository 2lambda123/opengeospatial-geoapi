/*
 *    GeoAPI - Java interfaces for OGC/ISO standards
 *    Copyright © 2006-2023 Open Geospatial Consortium, Inc.
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
package org.opengis.referencing.crs;

import org.opengis.referencing.datum.GeodeticDatum;
import org.opengis.annotation.UML;

import static org.opengis.annotation.Obligation.*;
import static org.opengis.annotation.Specification.*;


/**
 * A 2- or 3-dimensional <abbr>CRS</abbr> used over the whole planet or substantial parts of it.
 * This type of CRS can be used with coordinate systems of type
 * {@link org.opengis.referencing.cs.CartesianCS},
 * {@link org.opengis.referencing.cs.SphericalCS} or
 * {@link org.opengis.referencing.cs.EllipsoidalCS}.
 * In the latter case, the {@link GeographicCRS} specialization should be used.
 *
 * @author  OGC Topic 2 (for abstract model and documentation)
 * @author  Martin Desruisseaux (IRD, Geomatys)
 * @version 3.1
 * @since   2.1
 */
@UML(identifier="SC_GeodeticCRS", specification=ISO_19111, version=2007)
public interface GeodeticCRS extends SingleCRS {
    /**
     * Returns the datum, which shall be geodetic.
     */
    @Override
    @UML(identifier="datum", obligation=MANDATORY, specification=ISO_19111)
    GeodeticDatum getDatum();
}
