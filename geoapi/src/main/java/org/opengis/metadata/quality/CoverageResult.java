/*
 *    GeoAPI - Java interfaces for OGC/ISO standards
 *    http://www.geoapi.org
 *
 *    Copyright (C) 2009-2021 Open Geospatial Consortium, Inc.
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
package org.opengis.metadata.quality;

import java.util.Collection;
import java.util.Collections;
import org.opengis.annotation.UML;
import org.opengis.metadata.distribution.Format;
import org.opengis.metadata.distribution.DataFile;
import org.opengis.metadata.content.RangeDimension;
import org.opengis.metadata.content.CoverageDescription;
import org.opengis.metadata.spatial.SpatialRepresentation;
import org.opengis.metadata.spatial.SpatialRepresentationType;

import static org.opengis.annotation.Obligation.*;
import static org.opengis.annotation.Specification.*;


/**
 * Result of a data quality measure organizing the measured values as a coverage.
 *
 * @author  Cédric Briançon (Geomatys)
 * @author  Martin Desruisseaux (Geomatys)
 * @version 4.0
 * @since   2.3
 */
@UML(identifier="DQ_CoverageResult", specification=ISO_19157)
public interface CoverageResult extends Result {
    /**
     * Method used to spatially represent the coverage result.
     *
     * @return spatial representation of the coverage result.
     */
    @UML(identifier="spatialRepresentationType", obligation=MANDATORY, specification=ISO_19157)
    SpatialRepresentationType getSpatialRepresentationType();

    /**
     * Provides the digital representation of data quality measures composing the coverage result.
     *
     * @return digital representation of data quality measures composing the coverage result.
     */
    @UML(identifier="resultSpatialRepresentation", obligation=MANDATORY, specification=ISO_19157)
    SpatialRepresentation getResultSpatialRepresentation();

    /**
     * Provides the description of the content of the result coverage.
     * This is the semantic definition of the data quality measures.
     *
     * @return description of the content of the result coverage.
     *
     * @deprecated Replaced by {@link #getResultContent()}.
     */
    @Deprecated
    @UML(identifier="resultContentDescription", obligation=MANDATORY, specification=ISO_19115_2, version=2009)
    default CoverageDescription getResultContentDescription() {
        return null;
    }

    /**
     * Provides the description of the content of the result coverage.
     * This is the semantic definition of the data quality measures.
     *
     * @return description of the content of the result coverage.
     *
     * @condition Mandatory if {@linkplain #getResultFormat() result format}
     *            and {@link #getResultFile() result file} are not provided.
     */
    @UML(identifier="resultContent", obligation=CONDITIONAL, specification=ISO_19157)
    default Collection<? extends RangeDimension> getResultContent() {
        return Collections.emptyList();
    }

    /**
     * Provides information about the format of the result coverage data.
     *
     * @return format of the result coverage data.
     *
     * @condition Mandatory if {@linkplain #getResultContent() result content} is not provided.
     */
    @UML(identifier="resultFormat", obligation=CONDITIONAL, specification=ISO_19157)
    default Format getResultFormat() {
        return null;
    }

    /**
     * Provides information about the data file containing the result coverage data.
     *
     * @return data file containing the result coverage data.
     *
     * @condition Mandatory if {@linkplain #getResultContent() result content} is not provided.
     */
    @UML(identifier="resultFile", obligation=CONDITIONAL, specification=ISO_19157)
    default DataFile getResultFile() {
        return null;
    }
}
