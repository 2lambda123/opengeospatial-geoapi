/*$************************************************************************************************
 **
 ** $Id$
 **
 ** $Source$
 **
 ** Copyright (C) 2003 Open GIS Consortium, Inc. All Rights Reserved. http://www.opengis.org/Legal/
 **
 *************************************************************************************************/
package org.opengis.referencing;

// J2SE direct dependencies
import java.util.Set;

// OpenGIS direct dependencies
import org.opengis.metadata.citation.Citation;
import org.opengis.util.InternationalString;


/**
 * Base interface for all authority factories. An <cite>authority</cite> is an
 * organization that maintains definitions of authority codes. An <cite>authority
 * code</cite> is a compact string defined by an authority to reference a particular
 * spatial reference object. For example the
 * <A HREF="http://www.epsg.org">European Petroleum Survey Group (EPSG)</A> maintains
 * a database of coordinate systems, and other spatial referencing objects, where each
 * object has a code number ID. For example, the EPSG code for a WGS84 Lat/Lon coordinate
 * system is '4326'.
 *
 * @author <A HREF="http://www.opengis.org">OpenGIS&reg; consortium</A>
 * @version <A HREF="http://www.opengis.org/docs/01-009.pdf">Implementation specification 1.0</A>
 */
public interface AuthorityFactory extends Factory {
    /**
     * Returns the organization or party responsible for definition and maintenance of the
     * database.
     */
    Citation getAuthority();

    /**
     * Returns the set of authority codes of the given type. The <code>type</code>
     * argument specify the base class. For example if this factory is an instance
     * of {@link org.opengis.referencing.crs.CRSAuthorityFactory}, then:
     * <ul>
     *   <li><strong><code>{@linkplain org.opengis.referencing.crs.CoordinateReferenceSystem}.class&nbsp;</code></strong>
     *       asks for all authority codes accepted by one of
     *       {@link org.opengis.referencing.crs.CRSAuthorityFactory#createGeographicCRS createGeographicCRS},
     *       {@link org.opengis.referencing.crs.CRSAuthorityFactory#createProjectedCRS createProjectedCRS},
     *       {@link org.opengis.referencing.crs.CRSAuthorityFactory#createVerticalCRS createVerticalCRS},
     *       {@link org.opengis.referencing.crs.CRSAuthorityFactory#createTemporalCRS createTemporalCRS}
     *       and their friends.</li>
     *   <li><strong><code>{@linkplain org.opengis.referencing.crs.ProjectedCRS}.class&nbsp;</code></strong>
     *       asks only for authority codes accepted by
     *       {@link org.opengis.referencing.crs.CRSAuthorityFactory#createProjectedCRS createProjectedCRS}.</li>
     * </ul>
     *
     * @param  type The spatial reference objects type (may be <code>Object.class</code>).
     * @return The set of authority codes for spatial reference objects of the given type.
     *         If this factory doesn't contains any object of the given type, then this method
     *         returns an {@linkplain java.util.Collections#EMPTY_SET empty set}.
     * @throws FactoryException if access to the underlying database failed.
     */
    Set/*<String>*/ getAuthorityCodes(Class type) throws FactoryException;

    /**
     * Gets a description of the object corresponding to a code.
     *
     * @param  code Value allocated by authority.
     * @return A description of the object, or <code>null</code> if the object
     *         corresponding to the specified <code>code</code> has no description.
     * @throws NoSuchAuthorityCodeException if the specified <code>code</code> was not found.
     * @throws FactoryException if the query failed for some other reason.
     */
    InternationalString getDescriptionText(String code) throws FactoryException;

    /**
     * Returns an arbitrary object from a code. The returned object will typically be an
     * instance of {@link org.opengis.referencing.datum.Datum}, {@link org.opengis.referencing.cs.CoordinateSystem},
     * {@link org.opengis.referencing.ReferenceSystem} or {@link org.opengis.referencing.operation.CoordinateOperation}.
     * If the type of the object is know at compile time, it is recommended to invoke the
     * most precise method instead of this one (for example
     * <code>&nbsp;{@linkplain org.opengis.referencing.crs.CRSAuthorityFactory#createCoordinateReferenceSystem
     * createCoordinateReferenceSystem}(code)&nbsp;</code> instead of <code>&nbsp;createObject(code)&nbsp;</code>
     * if the caller know he is asking for a {@linkplain org.opengis.referencing.crs.CoordinateReferenceSystem coordinate
     * reference system}).
     *
     * @param  code Value allocated by authority.
     * @throws NoSuchAuthorityCodeException if the specified <code>code</code> was not found.
     * @throws FactoryException if the object creation failed for some other reason.
     *
     * @see org.opengis.referencing.datum.DatumAuthorityFactory#createDatum
     * @see org.opengis.referencing.crs.CRSAuthorityFactory#createCoordinateReferenceSystem
     */
    IdentifiedObject createObject(String code) throws FactoryException;
}
