/**************************************************************************************************
 **
 ** $Id$
 **
 ** $Source$
 **
 ** Copyright (C) 2003 Open GIS Consortium, Inc. All Rights Reserved. http://www.opengis.org/Legal/
 **
 *************************************************************************************************/
package org.opengis.util;

// J2SE direct dependencies
import java.awt.Component;
import java.awt.Graphics;
import java.net.URL;
import java.util.Collection;
import javax.swing.Icon;
import javax.swing.ImageIcon;


/**
 * Class that implements simple, type safe enumerations in Java.
 * Two enumeration values are equal if <code>e1 == e2</code> or if
 * <code>e1.getValue() == e2.getValue()</code>.
 *
 * @version 0.2
 * @author <A HREF="http://www.opengis.org">OpenGIS&reg; consortium</A>
 *
 * @revisit Should we rename this as <code>GraphicalCodeList</code> or something similar?
 */
public abstract class SimpleEnumerationType extends CodeList implements Comparable {
    
    //*************************************************************************
    //  Static Fields
    //*************************************************************************

    /** A null icon singleton */
    private final static Icon NULL_ICON = new NullIcon();

    //*************************************************************************
    //  Fields
    //*************************************************************************

    /** The description */
    private String description;
    
    /** The icon */
    private final Icon icon;

    //*************************************************************************
    //  Constructor
    //*************************************************************************
    
    /**
     * Add a new <code>SimpleEnumerationType</code> to the given list
     * using the given <code>name</code> and <code>description</code>.
     * The icon will be set to the Null Icon.
     *
     * @param values the list of values to add to.
     * @param name the short name for the enum.
     * @param description the description for the enum.
     */
    protected SimpleEnumerationType(Collection values, String name, String description) {
        super(values, name);
        this.description = description;
        this.icon = getNullIcon();
    }
    
    /**
     * Add a new <code>SimpleEnumerationType</code> to the given list
     * using the given <code>name</code> and <code>description</code>.
     *
     * @param values the list of values to add to.
     * @param name the short name for the enum.
     * @param description the description for the enum.
     * @param icon the icon for the enum.
     */
    protected SimpleEnumerationType(Collection values, String name, String description, Icon icon) {
        super(values, name);
        this.description = description;
        this.icon = icon;
    }
    
    //*************************************************************************
    //  override the Java toString method
    //*************************************************************************
    
    /**
     * Gets the string representation of this object. This just calls {@link #name()}.
     *
     * @return the string representation.
     */
    public String toString() {
        return name();
    }

    //*************************************************************************
    //  Accessors (no mutators)
    //*************************************************************************

    /**
     * Gets the description for this enumeration value.
     * @return the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the icon for this enumeration value.
     * @return the icon.
     */
    public Icon getIcon() {
        return icon;
    }
    
    //*************************************************************************
    //  comparison method
    //*************************************************************************

    /**
     * Compares this object to another of the same type.
     *
     * @param obj the object to compare to.
     * @return a value that corresponds to the comparison.
     */
    public int compareTo(Object obj) {
        int rv = 0;
        if (this.getClass() != obj.getClass()) {
            throw new ClassCastException("Can't compare " +
                                            this.getClass().getName() +
                                            " to " +
                                            obj.getClass().getName());
        }
        if (this != obj) {
            rv = this.ordinal() - ((SimpleEnumerationType)obj).ordinal();
        }
        return rv;
    }
    
    //*************************************************************************
    //  static methods
    //*************************************************************************
    
    /**
     * Load an icon for this
     * enumeration. <code>loadIconResource</code> loads the icon found
     * by <code>a_class.getResource(name)</code>.
     *
     * @param a_class class to use to find icon resource.
     * @param name name of icon resource, for example, "SOLID.gif"
     * @return a loaded icon for this enumeration. When no icon can be
     *         found the null icon is returned.
     * @see #getNullIcon
     */
    protected static Icon loadIconResource(Class a_class, String name) {
        URL iconURL = a_class.getResource(name);
        if (iconURL == null) {
            return getNullIcon();
        }
        return new ImageIcon(iconURL);
    }
    
    /**
     * Gets the null icon.
     *
     * @return a singleton icon that can be used when no icon was
     *         found in <code>loadIconResource</code>. The null icon is a
     *         fixed size, 16&times;64, and its paint method simply returns.
     *
     * @revisit Why a size of 16&times;64? Standard icons are
     *          16&times;16 or 24&times;24. See for example
     *          <A HREF="http://java.sun.com/developer/techDocs/hi/repository/TBG_General.html">http://java.sun.com/developer/techDocs/hi/repository/TBG_General.html</A>.
     */
    public static Icon getNullIcon() {
        return NULL_ICON;
    }
    
    //*************************************************************************
    //  the private, static NullIcon inner class
    //*************************************************************************

    /**
     * A icon that can be used when no icon was found in load
     * icon. The null icon is a fix 16x64 and its paint method simply
     * returns.
     */
    private static class NullIcon implements Icon {
        public int getIconHeight() {
            return 16;
        }
        public int getIconWidth() {
            return 64;
        }
        public void paintIcon(Component c, Graphics g, int x, int y) {
        }
    }
}
