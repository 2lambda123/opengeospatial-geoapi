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

import java.util.Set;
import javax.measure.unit.Unit;
import org.opengis.util.CodeList;
import org.opengis.annotation.UML;

import static org.opengis.annotation.Obligation.*;
import static org.opengis.annotation.Specification.*;


/**
 * The definition of a parameter used by an operation method. Most parameter values are
 * numeric, but other types of parameter values are possible.
 *
 * @param <T> The type of parameter values.
 *
 * @departure rename
 *   GeoAPI uses a name which contains the "<code>Descriptor</code>" word for consistency with other
 *   libraries in Java (e.g. <code>ParameterListDescriptor</code> in Java Advanced Imaging).
 *
 * @version <A HREF="http://portal.opengeospatial.org/files/?artifact_id=6716">Abstract specification 2.0</A>
 * @author  Martin Desruisseaux (IRD)
 * @author  Jody Garnett (Refractions Research)
 * @since   GeoAPI 2.0
 *
 * @see ParameterValue
 * @see ParameterDescriptorGroup
 */
@UML(identifier="CC_OperationParameter", specification=ISO_19111)
public interface ParameterDescriptor<T> extends GeneralParameterDescriptor {
    /**
     * Creates a new instance of {@linkplain ParameterValue parameter value} initialized with the
     * {@linkplain #getDefaultValue default value}. The {@linkplain ParameterValue#getDescriptor
     * parameter value descriptor} for the created parameter value will be {@code this} object.
     *
     * @departure extension
     *   This method is not part of the ISO specification. It is provided in GeoAPI as a kind of
     *   factory method.
     */
    ParameterValue<T> createValue();

    /**
     * Returns the class that describe the type of the parameter.
     *
     * @return The type of parameter values.
     */
    @UML(identifier="GC_ParameterInfo.type", obligation=MANDATORY, specification=ISO_19111)
    Class<T> getValueClass();

    /**
     * Returns the set of allowed values when these are restricted to some finite set or returns
     * {@code null} otherwise. The returned set usually contains {@linkplain CodeList code list}
     * or enumeration elements.
     *
     * @return A finite set of valid values (usually from a {@linkplain CodeList code list}),
     *         or {@code null} if it doesn't apply.
     *
     * @departure extension
     *   This method is not part of ISO specification. It is provided as a complement of information.
     */
    Set<T> getValidValues();

    /**
     * Returns the default value for the parameter. The return type can be any type
     * including a {@link Number} or a {@link String}. If there is no default value,
     * then this method returns {@code null}.
     *
     * @return The default value, or {@code null} in none.
     */
    @UML(identifier="GC_ParameterInfo.defaultValue", obligation=OPTIONAL, specification=ISO_19111)
    T getDefaultValue();

    /**
     * Returns the minimum parameter value.
     *
     * If there is no minimum value, or if minimum
     * value is inappropriate for the {@linkplain #getValueClass parameter type}, then
     * this method returns {@code null}.
     * <p>
     * When the getValueClass() is an array or Collection getMinimumValue
     * may be used to constrain the contained elements.
     * </p>
     * @return The minimum parameter value (often an instance of {@link Double}), or {@code null}.
     */
    @UML(identifier="GC_ParameterInfo.minimumValue", obligation=OPTIONAL, specification=ISO_19111)
    Comparable<T> getMinimumValue();

    /**
     * Returns the maximum parameter value.
     *
     * If there is no maximum value, or if maximum
     * value is inappropriate for the {@linkplain #getValueClass parameter type}, then
     * this method returns {@code null}.
     * <p>
     * When the getValueClass() is an array or Collection getMaximumValue
     * may be used to constratin the contained elements.
     *
     * @return The minimum parameter value (often an instance of {@link Double}), or {@code null}.
     */
    @UML(identifier="GC_ParameterInfo.maximumValue", obligation=OPTIONAL, specification=ISO_19111)
    Comparable<T> getMaximumValue();

    /**
     * Returns the unit for
     * {@linkplain #getDefaultValue default},
     * {@linkplain #getMinimumValue minimum} and
     * {@linkplain #getMaximumValue maximum} values.
     * This attribute apply only if the values is of numeric type (usually an instance
     * of {@link Double}).
     *
     * @return The unit for numeric value, or {@code null} if it doesn't apply to the value type.
     *
     * @departure extension
     *   This method is not part of ISO specification. It is provided as a complement of information.
     */
    Unit<?> getUnit();
}