/*
 *    GeoAPI - Java interfaces for OGC/ISO standards
 *    http://www.geoapi.org
 *
 *    Copyright (C) 2004-2013 Open Geospatial Consortium, Inc.
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
package org.opengis.util;

import java.util.List;
import org.opengis.annotation.UML;

import static org.opengis.annotation.Obligation.*;
import static org.opengis.annotation.Specification.*;


/**
 * A composite of a {@linkplain LocalName local name} (as {@linkplain #head() head}) for locating
 * another {@linkplain NameSpace name space}, and a {@linkplain GenericName generic name} (as
 * {@linkplain #tail() tail}) valid in that name space. This definition allows for iteration. The
 * {@linkplain #tail() tail} may be either a {@linkplain LocalName local name} or a scoped name.
 * If it is a scoped name, then another another step towards a remote {@linkplain LocalName local
 * name} is taken. In this way, a scoped name may represent an arbitrarily distant
 * {@linkplain LocalName local name} simply by the number of times the {@link #tail()} method
 * evaluates to a {@code ScopedName} before finally terminating on a {@link LocalName}.
 *
 * <p>It may be seen that {@code ScopedName} is the means by which fully-qualified names are expressed.
 * However, a {@code ScopedName} is not, in itself, what is commonly thought of as a <cite>fully
 * qualified</cite> name. The {@code ScopedName} type is one link in the chain, not the entire chain.
 * A scoped name is a fully qualified name only if its {@linkplain #scope scope} is
 * {@linkplain NameSpace#isGlobal global}.</p>
 *
 * <p><b>Example</b>:
 * The illustration below shows the head, tail, path and name of {@code "org.opengis.util.Record"}.</p>
 * <blockquote><table border="1" cellpadding="15"><tr><td><table border="0">
 *   <tr>
 *     <th align="right">org</th>
 *     <th>.</th><th>opengis</th>
 *     <th>.</th><th>util</th>
 *     <th>.</th><th>Record</th>
 *   </tr>
 *   <tr align="center">
 *     <td style="background:palegoldenrod" colspan="1">{@linkplain #head() head}</td><td></td>
 *     <td style="background:palegoldenrod" colspan="5">{@linkplain #tail() tail}</td>
 *   </tr>
 *   <tr align="center">
 *     <td style="background:wheat" colspan="5">{@linkplain #path() path}</td><td></td>
 *     <td style="background:wheat" colspan="1">{@linkplain #tip() tip}</td>
 *   </tr>
 * </table></td></tr></table></blockquote>
 *
 * @author  Martin Desruisseaux (IRD)
 * @author  Bryce Nordgren (USDA)
 * @version 3.0
 * @since   2.0
 */
@UML(identifier="ScopedName", specification=ISO_19103)
public interface ScopedName extends GenericName {
    /**
     * Returns the first element in the sequence of {@linkplain #getParsedNames() parsed names}.
     * The head element must exists in the same {@linkplain NameSpace name space} than this
     * scoped name. In other words, the following relationship must holds:
     *
     * <ul>
     *   <li><code>head().scope()</code>
     *       &nbsp;&nbsp; {@linkplain Object#equals(Object) equals} &nbsp;&nbsp;
     *       <code>this.{@linkplain #scope() scope()}</code></li>
     * </ul>
     *
     * This method is similar in purpose to <code>{@linkplain javax.naming.Name#get(int) Name.get}(0)</code>
     * from the <cite>Java Naming and Directory Interface</cite>.
     *
     * <p><b>Example</b>:
     * If {@code this} name is {@code "org.opengis.util.Record"}, then this method
     * shall returns {@code "org"}.</p>
     *
     * @return The first element in the list of {@linkplain #getParsedNames parsed names}.
     *
     * @since 2.2
     */
    @Override
    @UML(identifier="head", obligation=MANDATORY, specification=ISO_19103)
    LocalName head();

    /**
     * Returns every elements of the {@linkplain #getParsedNames() parsed names list} except for
     * the {@linkplain #head() head}. In other words, the following relationship must holds:
     *
     * <ul>
     *   <li><code>tail().getParsedNames()</code>
     *   &nbsp;&nbsp; {@linkplain List#equals(Object) equals} &nbsp;&nbsp;
     *   <code>this.{@linkplain #getParsedNames getParsedNames()}.sublist(1, {@linkplain #depth depth})</code></li>
     * </ul>
     *
     * This method is similar in purpose to <code>{@link javax.naming.Name#getSuffix(int)
     * Name.getSuffix}(1)</code> from the <cite>Java Naming and Directory Interface</cite>.
     *
     * @return All elements except the first one in the in the list of
     *         {@linkplain #getParsedNames parsed names}.
     *
     * @since 2.1
     */
    @UML(identifier="tail", obligation=MANDATORY, specification=ISO_19103)
    GenericName tail();

    /**
     * Returns every elements of the {@linkplain #getParsedNames() parsed names list} except for
     * the {@linkplain #tip() tip}. In other words, the following relationship must holds:
     *
     * <ul>
     *   <li><code>tip().getParsedNames()</code>
     *   &nbsp;&nbsp; {@linkplain List#equals(Object) equals} &nbsp;&nbsp;
     *   <code>this.{@linkplain #getParsedNames() getParsedNames()}.sublist(0, {@linkplain #depth() depth}-1)</code></li>
     * </ul>
     *
     * This method is similar in purpose to <code>{@link javax.naming.Name#getPrefix(int)
     * Name.getPrefix}(size-1)</code> from the <cite>Java Naming and Directory Interface</cite>.
     *
     * @return All elements except the last one in the in the list of
     *         {@linkplain #getParsedNames parsed names}.
     *
     * @departure easeOfUse
     *   This method is not part of ISO specification. It has been added in GeoAPI as a
     *   complement of the ISO <code>tail()</code> method.
     *
     * @since 2.1
     */
    GenericName path();

    /**
     * Returns the last element in the sequence of {@linkplain #getParsedNames() parsed names}.
     *
     * <p>This method is similar in purpose to <code>{@linkplain javax.naming.Name#get(int)
     * Name.get}(size-1)</code> from the <cite>Java Naming and Directory Interface</cite>.</p>
     *
     * @return The last element in the list of {@linkplain #getParsedNames() parsed names}.
     *
     * @since 2.1
     */
    @Override
    LocalName tip();

    /**
     * Returns a locale-independent string representation of this scoped name.
     * This method encapsulates the domain logic which formats the {@linkplain #getParsedNames()
     * parsed names} into a legal string representation of the name. There will be variants on
     * this theme. XML aficionados may require URIs. For Java classes, a dotted notation is more
     * appropriate, for C/C++, a double-colon, for directories, a forward or reverse slash,
     * and for {@linkplain org.opengis.referencing.crs.CoordinateReferenceSystem CRS}, it
     * will depend on the mode of expression: URN or {@code Authority:Identifier} notation.
     */
    @Override
    @UML(identifier="scopedName", obligation=MANDATORY, specification=ISO_19103)
    String toString();
}
