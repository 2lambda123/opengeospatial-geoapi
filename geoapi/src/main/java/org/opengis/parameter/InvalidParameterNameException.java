/*
 *    GeoAPI - Java interfaces for OGC/ISO standards
 *    http://www.geoapi.org
 *
 *    Copyright (C) 2004-2021 Open Geospatial Consortium, Inc.
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
package org.opengis.parameter;

import org.opengis.annotation.UML;
import static org.opengis.annotation.Specification.*;


/**
 * Thrown when an unexpected parameter was found in a {@linkplain ParameterDescriptorGroup parameter group}.
 * For example, this exception may be thrown at construction time if the same parameter name is used twice.
 *
 * @author  Martin Desruisseaux (IRD)
 * @version 3.0
 * @since   1.0
 *
 * @see ParameterNotFoundException
 */
@UML(identifier="GC_InvalidParameterName", specification=OGC_01004)
public class InvalidParameterNameException extends IllegalArgumentException {
    /**
     * Serial number for inter-operability with different versions.
     */
    private static final long serialVersionUID = -8473266898408204803L;

    /**
     * The invalid parameter name.
     */
    private final String parameterName;

    /**
     * Creates an exception with the specified message and parameter name.
     *
     * @param message        the detail message, saved for later retrieval by the {@link #getMessage()} method.
     * @param parameterName  the invalid parameter name.
     */
    public InvalidParameterNameException(String message, String parameterName) {
        super(message);
        this.parameterName = parameterName;
    }

    /**
     * Creates an exception with the specified message, cause and parameter name.
     *
     * @param message        the detail message, saved for later retrieval by the {@link #getMessage()} method.
     * @param cause          the cause, saved for later retrieval by the {@link #getCause()} method.
     * @param parameterName  the invalid parameter name.
     *
     * @since 3.1
     */
    public InvalidParameterNameException(String message, Throwable cause, String parameterName) {
        super(message, cause);
        this.parameterName = parameterName;
    }

    /**
     * Returns the invalid parameter name.
     *
     * @return the name of the invalid parameter.
     */
    public String getParameterName() {
        return parameterName;
    }
}
