/*$************************************************************************************************
 **
 ** $Id$
 **
 ** $Source$
 **
 ** Copyright (C) 2003 Open GIS Consortium, Inc. All Rights Reserved. http://www.opengis.org/Legal/
 **
 *************************************************************************************************/
package org.opengis.feature;

import java.io.IOException;

public class DataStoreException extends IOException {
    public DataStoreException() {
        super();
    }

    public DataStoreException(final String message) {
        super(message);
    }

    public DataStoreException(final Throwable cause) {
        super();
        initCause(cause);
    }

    public DataStoreException(final String message, final Throwable cause) {
        super(message);
        initCause(cause);
    }
}
