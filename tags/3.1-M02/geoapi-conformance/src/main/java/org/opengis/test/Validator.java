/*
 *    GeoAPI - Java interfaces for OGC/ISO standards
 *    http://www.geoapi.org
 *
 *    Copyright (C) 2008-2011 Open Geospatial Consortium, Inc.
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
package org.opengis.test;

import java.util.BitSet;
import java.util.Collection;
import java.util.logging.Logger;
import static org.opengis.test.Assert.*;


/**
 * Base class of all GeoAPI validators. Validators can be configured on a case-by-case basis by
 * changing the values of non-final public fields. If the same configuration needs to be applied
 * on all validators, then {@link ValidatorContainer#all} provides a convenient way to make such
 * change in a loop.
 *
 * <p>Once the configuration is finished, all validators provided in GeoAPI are thread-safe
 * provided that their configuration is not modified.</p>
 *
 * @author  Martin Desruisseaux (Geomatys)
 * @version 3.1
 * @since   2.2
 */
public abstract class Validator {
    /**
     * The default tolerance value for comparisons of floating point numbers.
     * The current value is {@value}.
     */
    public static final double DEFAULT_TOLERANCE = 1E-6;

    /**
     * The container of this validator.
     */
    protected final ValidatorContainer container;

    /**
     * The logger for reporting non-fatal warnings.
     */
    protected final Logger logger;

    /**
     * {@code true} if mandatory attributes are required to be non-null, or {@code false}
     * for tolerating null values. ISO specifications flags some attributes as mandatory,
     * while some other are optional. Optional attributes are allowed to be null at any time,
     * but mandatory attributes shall never be null - in theory. However implementors may
     * choose to returns {@code null} on a temporary basis while they are developing their
     * library. If this field is set to {@code false}, then missing mandatory attributes
     * will be logged as warnings instead than causing a failure.
     *
     * <p>The default value is {@code true}.</p>
     *
     * @see #mandatory(String, Object)
     */
    public boolean requireMandatoryAttributes = true;

    /**
     * {@code true} if forbidden attributes are required to be null, or {@code false} for
     * tolerating non-null values. In ISO specifications, some attributes are declared as
     * optional in parent class and specialized in subclasses, either as mandatory or as
     * forbidden. If this field is set to {@code false}, then forbidden attributes will
     * be logged as warnings instead than causing a failure.
     *
     * <p>The default value is {@code true}.</p>
     *
     * @see #forbidden(String, Object)
     */
    public boolean enforceForbiddenAttributes = true;

    /**
     * Creates a new validator instance.
     *
     * @param container   The container of this validator.
     * @param packageName The name of the package containing the classes to be validated.
     */
    protected Validator(final ValidatorContainer container, final String packageName) {
        this.container = container;
        this.logger = Logger.getLogger(packageName);
    }

    /**
     * Invoked when the existence of a mandatory attribute needs to be verified.
     * If the given value is {@code null}, then there is a choice:
     *
     * <ul>
     *   <li>If {@link #requireMandatoryAttributes} is {@code true} (which is the default),
     *       then the test fails with the given message.</li>
     *   <li>Otherwise, the message is logged as a warning and the test continues.</li>
     * </ul>
     *
     * <p>Subclasses can override this method if they want more control.</p>
     *
     * @param message The message to send in case of failure.
     * @param value   The value to test for non-nullity.
     *
     * @see #requireMandatoryAttributes
     */
    protected void mandatory(final String message, final Object value) {
        if (requireMandatoryAttributes) {
            assertNotNull(message, value);
        } else if (value == null) {
            logger.warning(message);
        }
    }

    /**
     * Invoked when the existence of a forbidden attribute needs to be checked.
     * If the given value is non-null, then there is a choice:
     *
     * <ul>
     *   <li>If {@link #enforceForbiddenAttributes} is {@code true} (which is the default),
     *       then the test fails with the given message.</li>
     *   <li>Otherwise, the message is logged as a warning and the test continues.</li>
     * </ul>
     *
     * <p>Subclasses can override this method if they want more control.</p>
     *
     * @param message The message to send in case of failure.
     * @param value   The value to test for nullity.
     *
     * @see #enforceForbiddenAttributes
     */
    protected void forbidden(final String message, final Object value) {
        if (enforceForbiddenAttributes) {
            assertNull(message, value);
        } else if (value != null) {
            logger.warning(message);
        }
    }

    /**
     * Ensures that the elements in the given collection are compliant with the {@link Object}
     * {@code equals(Object)} and {@code hashCode()} contract. This method ensures that the
     * {@code equals(Object)} methods implement <cite>reflexive</cite>, <cite>symmetric</cite>
     * and <cite>transitive</cite> relations. It also ensures that if {@code A.equals(B)}, then
     * {@code A.hashCode() == B.hashCode()}.
     * <p>
     * If the given collection is null, then this method does nothing.
     * If the given collection contains null elements, then those elements are ignored.
     * <p>
     * This method does not invoke any other {@code validate} method on collection elements.
     * It is caller responsibility to validates elements according their types.
     *
     * @param collection The collection of elements to validate, or {@code null}.
     *
     * @since 3.1
     */
    protected void validate(final Collection<?> collection) {
        if (collection == null) {
            return;
        }
        // Get an array with null elements omitted.
        int count = 0;
        final Object[] elements = collection.toArray();
        for (int i=0; i<elements.length; i++) {
            final Object element = elements[i];
            if (element != null) {
                elements[count++] = element;
            }
        }
        // Store the hash code before to do any comparison,
        // in order to detect unexpected changes.
        final int[] hashCodes = new int[count];
        for (int i=0; i<count; i++) {
            hashCodes[i] = elements[i].hashCode();
        }
        // Marks every objects that are equal.
        final BitSet[] equalMasks = new BitSet[count];
        for (int i=0; i<count; i++) {
            final Object toCompare = elements  [i];
            final int    hashCode  = hashCodes [i];
            final BitSet equalMask = equalMasks[i] = new BitSet(count);
            for (int j=0; j<count; j++) {
                final Object candidate = elements[j];
                if (toCompare.equals(candidate)) {
                    assertEquals("Inconsistent hash codes.", hashCode, candidate.hashCode());
                    equalMask.set(j);
                }
            }
            assertFalse("equals(null):", toCompare.equals(null));
        }
        // Now compare the sets of objects marked as equal.
        for (int i=0; i<count; i++) {
            final BitSet equalMask = equalMasks[i];
            assertTrue("equals(this) shall be reflexive.", equalMask.get(i));
            for (int j=0; (j=equalMask.nextSetBit(j)) >= 0; j++) {
                assertEquals("A.equals(B) shall be symmetric and transitive.", equalMask, equalMasks[j]);
            }
            assertEquals("The hash code value has changed.", hashCodes[i], elements[i].hashCode());
        }
    }
}