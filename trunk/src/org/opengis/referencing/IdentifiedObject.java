/*$************************************************************************************************
 **
 ** $Id$
 **
 ** $Source$
 **
 ** Copyright (C) 2003-2005 Open GIS Consortium, Inc.
 ** All Rights Reserved. http://www.opengis.org/legal/
 **
 *************************************************************************************************/
package org.opengis.referencing;

// OpenGIS direct dependencies
import org.opengis.metadata.Identifier;
import org.opengis.util.GenericName;
import org.opengis.util.InternationalString;

// Annotations
import org.opengis.annotation.UML;
import org.opengis.annotation.Extension;
import static org.opengis.annotation.Obligation.*;
import static org.opengis.annotation.Specification.*;


/**
 * Supplementary identification and remarks information for a CRS or CRS-related object.
 * When {@link org.opengis.referencing.crs.CRSAuthorityFactory} is used to create an object,
 * the {@linkplain Identifier#getAuthority authority} and {@linkplain Identifier#getCode
 * authority code} values should be set to the authority name of the factory object,
 * and the authority code supplied by the client, respectively. The other values may
 * or may not be set. If the authority is EPSG, the implementer may consider using the
 * corresponding metadata values in the EPSG tables.
 *
 * @author <A HREF="http://www.opengis.org">OpenGIS&reg; consortium</A>
 * @version <A HREF="http://www.opengis.org/docs/03-073r1.zip">Abstract specification 2.0</A>
 * @since GeoAPI 1.1
 */
@UML(identifier="IdentifiedObject", specification=ISO_19111)
public interface IdentifiedObject {
    /**
     * Key for the <code>{@value}</code> property to be given to the
     * {@linkplain ObjectFactory object factory} <code>createFoo(&hellip;)</code> methods.
     * This is used for setting the value to be returned by {@link #getName}.
     *
     * @see #getName
     */
    String NAME_KEY = "name";

    /**
     * Key for the <code>{@value}</code> property to be given to the
     * {@linkplain ObjectFactory object factory} <code>createFoo(&hellip;)</code> methods.
     * This is used for setting the value to be returned by {@link #getAlias}.
     *
     * @see #getAlias
     */
    String ALIAS_KEY = "alias";

    /**
     * Key for the <code>{@value}</code> property to be given to the
     * {@linkplain ObjectFactory object factory} <code>createFoo(&hellip;)</code> methods.
     * This is used for setting the value to be returned by {@link #getIdentifiers}.
     *
     * @see #getIdentifiers
     */
    String IDENTIFIERS_KEY = "identifiers";

    /**
     * Key for the <code>{@value}</code> property to be given to the
     * {@linkplain ObjectFactory object factory} <code>createFoo(&hellip;)</code> methods.
     * This is used for setting the value to be returned by {@link #getRemarks}.
     *
     * @see #getRemarks
     */
    String REMARKS_KEY = "remarks";

    /**
     * The primary name by which this object is identified.
     */
    @UML(identifier="name", obligation=MANDATORY, specification=ISO_19111)
    Identifier getName();

    /**
     * An alternative name by which this object is identified.
     *
     * @return The aliases, or an empty array if there is none.
     */
    @UML(identifier="alias", obligation=OPTIONAL, specification=ISO_19111)
    GenericName[] getAlias();

    /**
     * An identifier which references elsewhere the object's defining information.
     * Alternatively an identifier by which this object can be referenced.
     *
     * @return This object identifiers, or an empty array if there is none.
     */
    @UML(identifier="identifier", obligation=OPTIONAL, specification=ISO_19111)
    Identifier[] getIdentifiers();

    /**
     * Comments on or information about this object, including data source information.
     */
    @UML(identifier="remarks", obligation=OPTIONAL, specification=ISO_19111)
    InternationalString getRemarks();

    /**
     * Returns a <A HREF="doc-files/WKT.html"><cite>Well Known Text</cite> (WKT)</A> for this object.
     * This operation may fails if an object is too complex for the WKT format capability (for
     * example an {@linkplain org.opengis.referencing.crs.EngineeringCRS engineering CRS} with
     * different unit for each axis).
     *
     * @return The Well Know Text for this object.
     * @throws UnsupportedOperationException If this object can't be formatted as WKT.
     */
    @Extension
    String toWKT() throws UnsupportedOperationException;
}
