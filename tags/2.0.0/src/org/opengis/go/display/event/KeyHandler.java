/**************************************************************************************************
 **
 ** $Id$
 **
 ** $URL$
 **
 ** Copyright (C) 2003-2005 Open GIS Consortium, Inc.
 ** All Rights Reserved. http://www.opengis.org/legal/
 **
 *************************************************************************************************/

package org.opengis.go.display.event;

// J2SE direct dependencies
import java.awt.event.KeyListener;

/**
 * Defines a common abstraction whose implementations can associate different
 * key handlers with a Component to switch key handling states easily.
 *
 * @version $Revision: 660 $, $Date: 2006-02-23 14:35:47 +1100 (jeu., 23 févr. 2006) $
 * @author <A HREF="http://www.opengis.org">OpenGIS&reg; consortium</A>
 */

public interface KeyHandler extends EventHandler, KeyListener {

}

