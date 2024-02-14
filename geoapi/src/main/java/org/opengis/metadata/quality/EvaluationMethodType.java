/*
 *    GeoAPI - Java interfaces for OGC/ISO standards
 *    Copyright © 2004-2023 Open Geospatial Consortium, Inc.
 *    http://www.geoapi.org
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.opengis.metadata.quality;

import java.util.List;
import java.util.ArrayList;

import org.opengis.util.CodeList;
import org.opengis.annotation.UML;

import static org.opengis.annotation.Obligation.*;
import static org.opengis.annotation.Specification.*;


/**
 * Type of method for evaluating an identified data quality measure.
 *
 * @author  Martin Desruisseaux (IRD)
 * @version 3.1
 * @since   2.0
 */
@UML(identifier="DQ_EvaluationMethodTypeCode", specification=ISO_19157)
public final class EvaluationMethodType extends CodeList<EvaluationMethodType> {
    /**
     * Serial number for compatibility with different versions.
     */
    private static final long serialVersionUID = -2481257874205996202L;

    /**
     * List of all enumerations of this type.
     * Must be declared before any enum declaration.
     */
    private static final List<EvaluationMethodType> VALUES = new ArrayList<>(3);

    /**
     * Method of evaluating the quality of a data set based on inspection of items within
     * the data set, where all data required is internal to the data set being evaluated.
     */
    @UML(identifier="directInternal", obligation=CONDITIONAL, specification=ISO_19157)
    public static final EvaluationMethodType DIRECT_INTERNAL = new EvaluationMethodType("DIRECT_INTERNAL");

    /**
     * Method of evaluating the quality of a data set based on inspection of items within
     * the data set, where reference data external to the data set being evaluated is required.
     */
    @UML(identifier="directExternal", obligation=CONDITIONAL, specification=ISO_19157)
    public static final EvaluationMethodType DIRECT_EXTERNAL = new EvaluationMethodType("DIRECT_EXTERNAL");

    /**
     * Method of evaluating the quality of a data set based on external knowledge.
     */
    @UML(identifier="indirect", obligation=CONDITIONAL, specification=ISO_19157)
    public static final EvaluationMethodType INDIRECT = new EvaluationMethodType("INDIRECT");

    /**
     * Constructs an element of the given name. The new element is
     * automatically added to the list returned by {@link #values()}.
     *
     * @param name  the name of the new element. This name shall not be in use by another element of this type.
     */
    private EvaluationMethodType(final String name) {
        super(name, VALUES);
    }

    /**
     * Returns the list of {@code EvaluationMethodType}s.
     *
     * @return the list of codes declared in the current JVM.
     */
    public static EvaluationMethodType[] values() {
        synchronized (VALUES) {
            return VALUES.toArray(EvaluationMethodType[]::new);
        }
    }

    /**
     * Returns the list of codes of the same kind as this code list element.
     * Invoking this method is equivalent to invoking {@link #values()}, except that
     * this method can be invoked on an instance of the parent {@code CodeList} class.
     *
     * @return all code {@linkplain #values() values} for this code list.
     */
    @Override
    public EvaluationMethodType[] family() {
        return values();
    }

    /**
     * Returns the evaluation method type that matches the given string, or returns a
     * new one if none match it. More specifically, this methods returns the first instance for
     * which <code>{@linkplain #name() name()}.{@linkplain String#equals equals}(code)</code>
     * returns {@code true}. If no existing instance is found, then a new one is created for
     * the given name.
     *
     * @param  code  the name of the code to fetch or to create.
     * @return a code matching the given name.
     */
    public static EvaluationMethodType valueOf(String code) {
        return valueOf(EvaluationMethodType.class, code);
    }
}
