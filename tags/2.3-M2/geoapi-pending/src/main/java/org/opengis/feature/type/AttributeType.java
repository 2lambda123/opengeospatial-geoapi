/*$************************************************************************************************
 **
 ** $Id$
 **
 ** $URL$
 **
 ** Copyright (C) 2004-2007 Open GIS Consortium, Inc.
 ** All Rights Reserved. http://www.opengis.org/legal/
 **
 *************************************************************************************************/
package org.opengis.feature.type;

import org.opengis.feature.Attribute;

/**
 * The type of an attribute.
 * <p>
 * An attribute is similar to the notion of a UML attribute, or a field of a java
 * object. See the javadoc of {@link Attribute} for more info on the semantics
 * of attributes.
 * </p>
 * <p>
 * <h3>Identifiablily</h3>
 * An attribute may be "identifiable". When this is the case the attribute has a
 * unique identifier associated with it. See {@link Attribute#getID()}. The type
 * of the attribute specifies wether it is identifiable or not ({@link #isIdentified()}.
 * </p>
 *
 * @author Jody Garnett, Refractions Research
 * @author Justin Deoliveira, The Open Planning Project
 */
public interface AttributeType extends PropertyType {

    /**
     * Indicates if the type is identified or not.
     * <p>
     * If this method returns <code>true</code>, then the corresponding
     * attribute must have a unique identifier, ie, {@link Attribute#getID()}
     * must return a value, and cannot be <code>null</code>.
     * </p>
     *
     * @return <code>true</code> if the attribute is identified, otherwise <code>false</code>.
     *
     * @see Attribute#getID()
     */
    boolean isIdentified();

    /**
     * Override of {@link PropertyType#getSuper()} which type narrows to
     * {@link AttributeType}.
     *
     * @see PropertyType#getSuper()
     */
    AttributeType getSuper();
}