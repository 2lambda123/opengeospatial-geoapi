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

// J2SE dependencies
import java.util.EventListener;


/**
 * The listener interface for receiving feature events.
 *
 * @since GeoAPI 1.1
 */
public interface FeatureListener extends EventListener {
    /**
     * Invoked if features were added to or became members of a collection we
     * are listening to.
     */
    void featuresAdded(FeatureEvent e);

    /**
     * Invoked if features were removed from or no longer members of a
     * collection we are listening to.
     */
    void featuresRemoved(FeatureEvent e);

    /**
     * Invoked if the values of the attributes of features changed.
     */
    void featuresChanged(FeatureEvent e);
}
