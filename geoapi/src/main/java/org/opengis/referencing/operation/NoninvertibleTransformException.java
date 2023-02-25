/*
 *    GeoAPI - Java interfaces for OGC/ISO standards
 *    Copyright © 2004-2011 Open Geospatial Consortium, Inc.
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
package org.opengis.referencing.operation;


/**
 * Thrown when {@link MathTransform#inverse()} is
 * invoked but the transform can't be inverted.
 *
 * @departure extension
 *   This exception is not part of the OGC specification.
 *
 * @author  Martin Desruisseaux (IRD)
 * @version 3.0
 * @since   1.0
 *
 * @see org.opengis.referencing.operation.CoordinateOperationFactory
 */
public class NoninvertibleTransformException extends TransformException {
    /**
     * Serial number for inter-operability with different versions.
     */
    private static final long serialVersionUID = 9184806660368158575L;

    /**
     * Construct an exception with no detail message.
     */
    public NoninvertibleTransformException() {
    }

    /**
     * Construct an exception with the specified detail message.
     *
     * @param  message The detail message. The detail message is saved
     *         for later retrieval by the {@link #getMessage()} method.
     */
    public NoninvertibleTransformException(String message) {
        super(message);
    }

    /**
     * Construct an exception with the specified detail message and cause. The cause
     * is typically an other {@link java.lang.geom.NoninvertibleTransformException}
     * emitted by Java2D.
     *
     * @param  message The detail message. The detail message is saved
     *         for later retrieval by the {@link #getMessage()} method.
     * @param  cause The cause for this exception. The cause is saved
     *         for later retrieval by the {@link #getCause()} method.
     */
    public NoninvertibleTransformException(String message, Throwable cause) {
        super(message, cause);
    }
}
