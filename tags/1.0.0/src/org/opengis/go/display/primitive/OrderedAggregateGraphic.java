/**************************************************************************************************
 **
 ** $Id$
 **
 ** $URL$
 **
 ** Copyright (C) 2003 Open GIS Consortium, Inc. All Rights Reserved. http://www.opengis.org/Legal/
 **
 *************************************************************************************************/
package org.opengis.go.display.primitive;

/**
 * Extends the <code>AggregateGraphic</code> interface to add the ability for the user
 * to specify a stacking order or Z-order.  When the objects contained in this aggregate
 * are drawn, they should be drawn in the order they appear in the list of children,
 * starting with index 0.
 * 
 * @author <A HREF="http://www.opengis.org">OpenGIS&reg; consortium</A>
 * @version $Revision: 659 $, $Date: 2006-02-23 14:07:07 +1100 (jeu., 23 févr. 2006) $
 */
public interface OrderedAggregateGraphic extends AggregateGraphic {

    /**
     * Adds a child into this aggregate, inserting it before the child
     * at the given index.  The existing child at the given index and
     * all those with a larger index have their index increased by one.
     *
     * @param index Index where the new child will be added.
     * @param child New child Graphic to add.
     * @return Returns the child just added.
     */
    public Graphic insertChild(int index, Graphic child);

    /**
     * Retrieves the child Graphic at the given index.
     */
    public Graphic getChild(int index);

    /**
     * Removes the child at the given index.
     *
     * @return Returns the child just removed.
     */
    public Graphic removeChild(int index);
}

