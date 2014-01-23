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
package org.opengis.referencing;

import java.util.Set;
import java.util.Collection;
import org.opengis.util.GenericName;
import org.opengis.util.InternationalString;
import org.opengis.annotation.UML;
import org.opengis.annotation.Classifier;
import org.opengis.annotation.Stereotype;

import static org.opengis.annotation.Obligation.*;
import static org.opengis.annotation.Specification.*;


/**
 * Supplementary identification and remarks information for a CRS or CRS-related object.
 * Some typical {@code IdentifiedObject} sub-types are
 * {@linkplain org.opengis.referencing.datum.GeodeticDatum geodetic datum} (e.g. "<cite>World Geodetic System 1984</cite>"),
 * {@linkplain org.opengis.referencing.crs.CoordinateReferenceSystem Coordinate Reference System} (e.g. "<cite>WGS 84 / World Mercator</cite>") and
 * {@linkplain org.opengis.referencing.operation.Projection map projection} (e.g. "<cite>Mercator (variant A)</cite>").
 *
 * <p>When {@link org.opengis.referencing.crs.CRSAuthorityFactory} is used to create an object,
 * the {@linkplain ReferenceIdentifier#getAuthority() authority} and
 * {@linkplain ReferenceIdentifier#getCode() authority code} values shall be set to the
 * authority name of the factory object, and the authority code supplied by the client,
 * respectively. The other values may or may not be set. If the authority is EPSG, the
 * implementer may consider using the corresponding metadata values in the EPSG tables.</p>
 *
 * @departure harmonization
 *   ISO 19111 defines an <code>IdentifiedObjectBase</code> interface. The later is omitted in
 *   GeoAPI because the split between <code>IdentifiedObject</code> and <code>IdentifiedObjectBase</code>
 *   in the ISO/OGC specification was a workaround for introducing <code>IdentifiedObject</code>
 *   in ISO 19111 without changing the <code>ReferenceSystem</code> definition in ISO 19115 but
 *   GeoAPI does not need this workaround.
 *
 * @author  Martin Desruisseaux (IRD)
 * @version 3.1
 * @since   2.0
 *
 * @navassoc - - - GenericName
 * @navassoc - - - ReferenceIdentifier
 */
@Classifier(Stereotype.ABSTRACT)
@UML(identifier="IO_IdentifiedObject", specification=ISO_19111)
public interface IdentifiedObject {
    /**
     * Key for the <code>{@value}</code> property to be given to the
     * {@linkplain ObjectFactory object factory} <code>createFoo(…)</code> methods.
     * This is used for setting the value to be returned by {@link #getName()}.
     *
     * @see #getName()
     */
    String NAME_KEY = "name";

    /**
     * Key for the <code>{@value}</code> property to be given to the
     * {@linkplain ObjectFactory object factory} <code>createFoo(…)</code> methods.
     * This is used for setting the value to be returned by {@link #getAlias()}.
     *
     * @see #getAlias()
     */
    String ALIAS_KEY = "alias";

    /**
     * Key for the <code>{@value}</code> property to be given to the
     * {@linkplain ObjectFactory object factory} <code>createFoo(…)</code> methods.
     * This is used for setting the value to be returned by {@link #getIdentifiers()}.
     *
     * @see #getIdentifiers()
     */
    String IDENTIFIERS_KEY = "identifiers";

    /**
     * Key for the <code>{@value}</code> property to be given to the
     * {@linkplain ObjectFactory object factory} <code>createFoo(…)</code> methods.
     * This is used for setting the value to be returned by {@link #getRemarks()}.
     *
     * @see #getRemarks()
     */
    String REMARKS_KEY = "remarks";

    /**
     * The primary name by which this object is identified.
     *
     * @return The primary name.
     */
    @UML(identifier="name", obligation=MANDATORY, specification=ISO_19111)
    ReferenceIdentifier getName();

    /**
     * An alternative name by which this object is identified.
     *
     * @return The aliases, or an empty collection if there is none.
     */
    @UML(identifier="alias", obligation=OPTIONAL, specification=ISO_19111)
    Collection<GenericName> getAlias();

    /**
     * An identifier which references elsewhere the object's defining information.
     * Alternatively an identifier by which this object can be referenced.
     *
     * @return This object identifiers, or an empty collection if there is none.
     */
    @UML(identifier="identifier", obligation=OPTIONAL, specification=ISO_19111)
    Set<ReferenceIdentifier> getIdentifiers();

    /**
     * Comments on or information about this object, including data source information.
     *
     * @return The remarks, or {@code null} if none.
     */
    @UML(identifier="remarks", obligation=OPTIONAL, specification=ISO_19111)
    InternationalString getRemarks();

    /**
     * Returns a <cite>Well-Known Text</cite> (WKT) for this object.
     * Well-Known texts (WKT) may come in two formats:
     *
     * <ul>
     *   <li>The current standard — WKT 2 — is defined by ISO 19162.</li>
     *   <li>The legacy format — WKT 1 — was defined by <a href="http://www.opengeospatial.org/standards/ct">OGC 01-009</a>
     *       and is shown using Extended Backus Naur Form (EBNF) <a href="doc-files/WKT.html">here</a>.</li>
     * </ul>
     *
     * Implementations are encouraged, but not required, to format according the most recent standard.
     * This operation may fail if unsupported or if this instance contains elements that do not have
     * WKT representation.
     *
     * @return The Well-Know Text for this object.
     * @throws UnsupportedOperationException If this object can't be formatted as WKT.
     *
     * @departure extension
     *   This method is not part of the OGC specification. It has been added in order to provide
     *   the converse of the <code>CRSFactory.createFromWKT(String)</code> method, which is
     *   defined in OGC 01-009.
     *
     * @see org.opengis.referencing.crs.CRSFactory#createFromWKT(String)
     */
    String toWKT() throws UnsupportedOperationException;
}
