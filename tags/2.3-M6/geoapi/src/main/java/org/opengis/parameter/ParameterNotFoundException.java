/*$************************************************************************************************
 **
 ** $Id$
 **
 ** $URL$
 **
 ** Copyright (C) 2003-2005 Open GIS Consortium, Inc.
 ** All Rights Reserved. http://www.opengis.org/legal/
 **
 *************************************************************************************************/
package org.opengis.parameter;


/**
 * Thrown by {@link ParameterValueGroup} and {@link ParameterDescriptorGroup}
 * when a parameter is requested but not found in that group.
 *
 * @departure extension
 *   This exception is not part of OGC specification.
 *
 * @author  Martin Desruisseaux (IRD)
 * @since   GeoAPI 1.0
 *
 * @see ParameterValueGroup#parameter(String)
 * @see ParameterDescriptorGroup#descriptor(String)
 */
public class ParameterNotFoundException extends IllegalArgumentException {
    /**
     * Serial number for interoperability with different versions.
     */
    private static final long serialVersionUID = -8074834945993975175L;

    /**
     * The invalid parameter name.
     */
    private final String parameterName;

    /**
     * Creates an exception with the specified message and parameter name.
     *
     * @param message The detail message. The detail message is saved for
     *        later retrieval by the {@link #getMessage()} method.
     * @param parameterName The name of the parameter which was required but not found.
     */
    public ParameterNotFoundException(String message, String parameterName) {
        super(message);
        this.parameterName = parameterName;
    }

    /**
     * Returns the name of the parameter which was required but not found.
     *
     * @return The required parameter name.
     */
    public String getParameterName() {
        return parameterName;
    }
}
