/*
 *    GeoAPI - Java interfaces for OGC/ISO standards
 *    http://www.geoapi.org
 *
 *    Copyright (C) 2006-2014 Open Geospatial Consortium, Inc.
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
package org.opengis.feature;

import java.util.Set;
import java.util.Collection;
import org.opengis.annotation.UML;
import org.opengis.annotation.Classifier;
import org.opengis.annotation.Stereotype;
import org.opengis.util.GenericName;

import static org.opengis.annotation.Obligation.*;
import static org.opengis.annotation.Specification.ISO_19109;


/**
 * Abstraction of a real-world phenomena.
 * A {@code FeatureType} instance describes the class of all {@link Feature} instances of that type.
 *
 * <blockquote><font size="-1"><b>Analogy:</b>
 * compared to the Java language, {@code FeatureType} is equivalent to {@link Class} while
 * {@code Feature} instances are equivalent to {@link Object} instances of that class.</font></blockquote>
 *
 * <h3>Naming</h3>
 * The feature type {@linkplain #getName() name} is mandatory and should be unique.
 * Names can be {@linkplain org.opengis.util.ScopedName} for avoiding name collision.
 *
 * <h3>Properties and inheritance</h3>
 * Each feature type can provide descriptions for the following {@linkplain #getProperties(boolean) properties}:
 *
 * <ul>
 *   <li>{@linkplain AttributeType           Attributes}</li>
 *   <li>{@linkplain FeatureAssociationRole  Associations to other features}</li>
 *   <li>{@linkplain Operation               Operations}</li>
 * </ul>
 *
 * In addition, a feature type can inherit the properties of one or more other feature types.
 * Properties defined in the sub-type can override properties of the same name defined in the
 * {@linkplain #getSuperTypes() super-types}, provided that values of the sub-type property are
 * assignable to the super-type property.
 *
 * <blockquote><font size="-1"><b>Analogy:</b>
 * compared to the Java language, the above rule is similar to overriding a method with a more specific return
 * type (a.k.a. <cite>covariant return type</cite>). This is also similar to Java arrays, which are implicitly
 * <cite>covariant</cite> (i.e. {@code String[]} can be casted to {@code CharSequence[]}, which is safe for read
 * operations but not for write operations — the later may throw {@link ArrayStoreException}).</font></blockquote>
 *
 * @author  Jody Garnett (Refractions Research)
 * @author  Justin Deoliveira (The Open Planning Project)
 * @author  Martin Desruisseaux (Geomatys)
 * @version 3.1
 * @since   3.1
 */
@Classifier(Stereotype.METACLASS)
@UML(identifier="FeatureType", specification=ISO_19109)
public interface FeatureType extends IdentifiedType {
    /**
     * Returns the name of this feature type.
     * For {@code FeatureType}, the name is mandatory.
     * The feature name is often an instance of {@link org.opengis.util.TypeName}, but this is not mandatory.
     *
     * @return The feature type name.
     */
    @Override
    @UML(identifier="name", obligation=MANDATORY, specification=ISO_19109)
    GenericName getName();

    /**
     * Returns {@code true} if the feature type acts as an abstract super-type.
     * Abstract types can not be {@linkplain #newInstance() instantiated}.
     *
     * @return {@code true} if the feature type acts as an abstract super-type.
     */
    @UML(identifier="isAbstract", obligation=MANDATORY, specification=ISO_19109)
    boolean isAbstract();

    /**
     * Returns {@code true} if this feature type contains only attributes constrained to the [1 … 1] cardinality,
     * or operations (no feature association).
     * Such feature types can be handled as a {@link org.opengis.util.RecordType} instances.
     *
     * @return {@code true} if this feature type contains only simple attributes or operations.
     */
    boolean isSimple();

    /**
     * Returns the attribute, operation or association role for the given name.
     *
     * @param  name The name of the property to search.
     * @return The property for the given name, or {@code null} if none.
     * @throws IllegalArgumentException If the given argument is not a property name of this feature.
     */
    PropertyType getProperty(String name) throws IllegalArgumentException;

    /**
     * Returns any feature operation, any feature attribute type and any feature association role that
     * carries characteristics of a feature type. The returned collection will include the properties
     * inherited from the {@linkplain #getSuperTypes() super-types} only if {@code includeSuperTypes}
     * is {@code true}.
     *
     * @param  includeSuperTypes {@code true} for including the properties inherited from the super-types,
     *         or {@code false} for returning only the properties defined explicitely in this type.
     * @return Feature operation, attribute type and association role that carries characteristics of this
     *         feature type (not including parent types).
     */
    @UML(identifier="carrierOfCharacteristics", obligation=OPTIONAL, specification=ISO_19109)
    Collection<? extends PropertyType> getProperties(boolean includeSuperTypes);

    /**
     * Returns the direct parents of this feature type.
     *
     * <blockquote><font size="-1"><b>Analogy:</b>
     * if we compare {@code FeatureType} to {@link Class} in the Java language, then this method is equivalent
     * to {@link Class#getSuperclass()} except that feature types allow multi-inheritance.</font></blockquote>
     *
     * @return The parents of this feature type, or an empty set if none.
     */
    @UML(identifier="superType", obligation=OPTIONAL, specification=ISO_19109)
    Set<? extends FeatureType> getSuperTypes();

    /**
     * Returns {@code true} if this type is same or a super-type of the given type.
     *
     * <blockquote><font size="-1"><b>Analogy:</b>
     * if we compare {@code FeatureType} to {@link Class} in the Java language, then this method is equivalent
     * to {@link Class#isAssignableFrom(Class)}.</font></blockquote>
     *
     * @param  type The type to be checked.
     * @return {@code true} if instances of the given type can be assigned to association of this type.
     */
    boolean isAssignableFrom(FeatureType type);
}
