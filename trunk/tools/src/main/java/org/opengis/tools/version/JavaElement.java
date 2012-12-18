/*
 *    GeoAPI - Java interfaces for OGC/ISO standards
 *    http://www.geoapi.org
 *
 *    Copyright (C) 2004-2012 Open Geospatial Consortium, Inc.
 *    All Rights Reserved. http://www.opengeospatial.org/ogc/legal
 *
 *    Permission to use, copy, and modify this software and its documentation, with
 *    or without modification, for any purpose and without fee or royalty is hereby
 *    granted, provided that you include the following on ALL copies of the software
 *    and documentation or portions thereof, including modifications, that you make:
 *
 *    1. The full text of this NOTICE in a location viewable to users of the
 *       redistributed or derivative work.
 *    2. Notice of any changes or modifications to the OGC files, including the
 *       date changes were made.
 *
 *    THIS SOFTWARE AND DOCUMENTATION IS PROVIDED "AS IS," AND COPYRIGHT HOLDERS MAKE
 *    NO REPRESENTATIONS OR WARRANTIES, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 *    TO, WARRANTIES OF MERCHANTABILITY OR FITNESS FOR ANY PARTICULAR PURPOSE OR THAT
 *    THE USE OF THE SOFTWARE OR DOCUMENTATION WILL NOT INFRINGE ANY THIRD PARTY
 *    PATENTS, COPYRIGHTS, TRADEMARKS OR OTHER RIGHTS.
 *
 *    COPYRIGHT HOLDERS WILL NOT BE LIABLE FOR ANY DIRECT, INDIRECT, SPECIAL OR
 *    CONSEQUENTIAL DAMAGES ARISING OUT OF ANY USE OF THE SOFTWARE OR DOCUMENTATION.
 *
 *    The name and trademarks of copyright holders may NOT be used in advertising or
 *    publicity pertaining to the software without specific, written prior permission.
 *    Title to copyright in this software and any associated documentation will at all
 *    times remain with copyright holders.
 */
package org.opengis.tools.version;

import java.util.Arrays;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;


/**
 * Information about a type (class or interface) or a member (field or methods).
 *
 * @author  Martin Desruisseaux (Geomatys)
 * @version 3.1
 * @since   3.1
 */
final class JavaElement implements Comparable<JavaElement> {
    /**
     * The parent, or {@code null} if none.
     * <p>
     * <ul>
     *   <li>For fields and methods, the parent is the class or interface that define them.</li>
     *   <li>For classes and interfaces, the parent is the package that contains them.</li>
     *   <li>For packages, there is no parent.</li>
     * </ul>
     */
    final JavaElement parent;

    /**
     * The type of this element.
     */
    final JavaElementType type;

    /**
     * The name in the Java programming language.
     */
    final String javaName;

    /**
     * The name in OGC/ISO standards as inferred from the {@code @UML} annotation,
     * or {@code null} if none.
     */
    final String ogcName;

    /**
     * The obligation as inferred from the {@code @UML} annotation, or {@code null} if none.
     */
    final String obligation;

    /**
     * {@code true} if the element is public, or {@code false} if it is protected.
     */
    final boolean isPublic;

    /**
     * {@code true} if the element is deprecated. This usually apply to removed elements.
     */
    final boolean isDeprecated;

    /**
     * {@code true} if we have determined that this API element has been removed.
     * <p>
     * This attribute is ignored by {@link #equals(Object)} and {@link #hashCode()} methods,
     * since it is mutable (and actually modified after insertion in a hash map).
     */
    boolean isRemoved;

    /**
     * Creates a new element for a package name.
     * This is not mapped to any OGC/ISO standard.
     */
    JavaElement(final String packageName) {
        parent       = null;
        type         = JavaElementType.PACKAGE;
        javaName     = packageName;
        ogcName      = null;
        obligation   = null;
        isPublic     = true;
        isDeprecated = false;
    }

    /**
     * Creates a new element for the given annotated element, then adds itself to the collector
     * set of elements.
     *
     * @param collector Where to add the newly created element.
     * @param parent    The parent of the new element, or {@code null} if none.
     * @param type      The type of the new element.
     * @param element   The annotated element to add.
     * @param javaName  The simple (non-qualified) name of the element in the Java programming language.
     * @param isPublic  {@code true} if the element is public, or {@code false} if it is protected.
     */
    private JavaElement(final JavaElementCollector collector, final JavaElement parent, final JavaElementType type,
            final AnnotatedElement element, final String javaName, final boolean isPublic)
            throws IllegalAccessException, InvocationTargetException
    {
        this.parent       = parent;
        this.type         = type;
        this.javaName     = javaName;
        this.isPublic     = isPublic;
        this.isDeprecated = element.isAnnotationPresent(Deprecated.class);
        String ogcName    = null;
        String obligation = null;
        final Annotation uml = element.getAnnotation(collector.umlAnnotation);
        if (uml != null) {
            ogcName = (String) collector.umlIdentifier.invoke(uml, (Object[]) null);
            if (ogcName != null) {
                ogcName = ogcName.trim();
                if (ogcName.isEmpty()) {
                    ogcName = null;
                }
            }
            Enum<?> e = (Enum<?>) collector.umlObligation.invoke(uml, (Object[]) null);
            if (e != null) {
                obligation = e.name();
            }
        }
        this.ogcName    = ogcName;
        this.obligation = obligation;
        if (!collector.elements.add(this)) {
            throw new IllegalArgumentException("Duplicated API: " + this);
        }
    }

    /**
     * Creates a new element for the given type, then adds itself to the collector set of elements.
     *
     * @param collector Where to add the newly created element.
     * @param parent    The package of the new type, or {@code null} if none.
     * @param element   The type to add.
     * @param isPublic  {@code true} if the element is public, or {@code false} if it is protected.
     */
    JavaElement(final JavaElementCollector collector, final JavaElement parent, final Class<?> element, final boolean isPublic)
            throws IllegalAccessException, InvocationTargetException
    {
        this(collector, parent,
                Enum.class         .isAssignableFrom(element) ? JavaElementType.ENUM :
                collector.codeLists.isAssignableFrom(element) ? JavaElementType.CODE_LIST : JavaElementType.CLASS,
                element, getName(element), isPublic);
        addMembers(collector, element.getDeclaredFields(),  JavaElementType.FIELD);
        addMembers(collector, element.getDeclaredMethods(), JavaElementType.METHOD);
    }

    /**
     * Returns the simple name of the given class, including the name of the enclosing class
     * if the given class is an inner class.
     */
    private static String getName(final Class<?> type) {
        String name = type.getSimpleName();
        final Class<?> enclosing = type.getEnclosingClass();
        if (enclosing != null) {
            name = getName(enclosing) + '.' + name;
        }
        return name;
    }

    /**
     * Adds the given fields or members to the given collector set of elements.
     */
    private void addMembers(final JavaElementCollector collector, final Member[] members, final JavaElementType type)
            throws IllegalAccessException, InvocationTargetException
    {
        for (final Member member : members) {
            if (!member.isSynthetic()) {
                final int modifiers = member.getModifiers();
                final boolean isPublic = Modifier.isPublic(modifiers);
                if (isPublic || Modifier.isProtected(modifiers)) {
                    String name = member.getName();
                    if (member instanceof Method) {
                        boolean hasParameters = false;
                        final StringBuilder buffer = new StringBuilder(name).append('(');
                        for (final Class<?> param : ((Method) member).getParameterTypes()) {
                            if (hasParameters) {
                                buffer.append(", ");
                            }
                            buffer.append(param.getSimpleName());
                            hasParameters = true;
                        }
                        name = buffer.append(')').toString();
                    }
                    JavaElement child = new JavaElement(collector, this, type, (AnnotatedElement) member, name, isPublic);
                    assert collector.elements.contains(child);
                }
            }
        }
    }

    /**
     * Compares this element with the given element for order. This method is used for sorting
     * elements in the order to print them. Elements having the same parent (classes in the same
     * package, or methods in the same class) are grouped together.
     * <p>
     * This method is inconsistent with {@link #equals(Object)} since
     * it doesn't compare every possible values.
     *
     * @param  other The other element to compare with this method.
     * @return -1, 0, or +1 depending if this element shall be printed before or after the other element.
     */
    @Override
    public int compareTo(final JavaElement other) {
        int c = compare(parent, other.parent);
        if (c == 0) {
            c = compare(type, other.type);
            if (c == 0) {
                c = compare(javaName, other.javaName);
            }
        }
        return c;
    }

    /**
     * Compares this element with the given object for equality.
     */
    @Override
    public boolean equals(final Object other) {
        if (other instanceof JavaElement) {
            final JavaElement that = (JavaElement) other;
            return equals(parent,     that.parent)     &&
                   equals(type,       that.type)       &&
                   equals(javaName,   that.javaName)   &&
                   equals(ogcName,    that.ogcName)    &&
                   equals(obligation, that.obligation) &&
                   isPublic     ==    that.isPublic    &&
                   isDeprecated ==    that.isDeprecated;
        }
        return false;
    }

    /**
     * Returns a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(new Object[] {
            parent, type, javaName, ogcName, obligation, isPublic, isDeprecated
        });
    }

    /**
     * Returns a string representation of this element for debugging purposes.
     */
    @Override
    public String toString() {
        final StringBuilder buffer = new StringBuilder().append(type).append('[').append(javaName);
        if (ogcName != null) {
            buffer.append(", UML(“").append(ogcName).append("”)");
        }
        return buffer.append(']').toString();
    }

    /**
     * Null-safe comparison of the given objects for order.
     * Null values are sorted before non-null values.
     */
    private static <T extends Comparable<T>> int compare(final T a, final T b) {
        if (a == b)    return  0;
        if (a == null) return -1;
        if (b == null) return +1;
        return a.compareTo(b);
    }

    /**
     * Compares the given objects for equality.
     *
     * @todo To be removed with JDK7.
     */
    private static boolean equals(final Object a, final Object b) {
        return (a == b) || (a != null && a.equals(b));
    }
}
