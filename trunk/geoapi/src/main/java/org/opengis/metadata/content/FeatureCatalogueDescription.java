/*
 *    GeoAPI - Java interfaces for OGC/ISO standards
 *    http://www.geoapi.org
 *
 *    Copyright (C) 2004-2014 Open Geospatial Consortium, Inc.
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
package org.opengis.metadata.content;

import java.util.Collection;
import java.util.Locale;
import org.opengis.util.GenericName;
import org.opengis.metadata.citation.Citation;
import org.opengis.annotation.UML;

import static org.opengis.annotation.Obligation.*;
import static org.opengis.annotation.Specification.*;


/**
 * Information identifying the feature catalogue or the conceptual schema.
 *
 * @author  Martin Desruisseaux (IRD)
 * @version 3.1
 * @since   2.0
 */
@UML(identifier="MD_FeatureCatalogueDescription", specification=ISO_19115)
public interface FeatureCatalogueDescription extends ContentInformation {
    /**
     * Indication of whether or not the cited feature catalogue complies with ISO 19110.
     * This value is optional, and therefore may be null.
     *
     * @return Whether or not the cited feature catalogue complies with ISO 19110, or {@code null}.
     */
    @UML(identifier="complianceCode", obligation=OPTIONAL, specification=ISO_19115)
    Boolean isCompliant();

    /**
     * Language(s) used within the catalogue.
     *
     * @return Language(s) used within the catalogue.
     *
     * @departure historic
     *   GeoAPI has kept the <code>language</code> property as defined in ISO 19115:2003.
     *   See <code>Metadata.getLanguage()</code> for more information.
     *
     * @see org.opengis.metadata.Metadata#getLanguage()
     */
    @UML(identifier="language", obligation=OPTIONAL, specification=ISO_19115)
    Collection<Locale> getLanguages();

    /**
     * Indication of whether or not the feature catalogue is included with the resource.
     *
     * @return whether or not the feature catalogue is included with the resource.
     */
    @UML(identifier="includedWithDataset", obligation=MANDATORY, specification=ISO_19115)
    boolean isIncludedWithDataset();

    /**
     * Subset of feature types from cited feature catalogue occurring in resource
     * and count of feature instances.
     *
     * @return Subset of feature types occurring in resource.
     *
     * @departure rename
     *   Renamed from "<code>featureTypes</code>" to "<code>featureTypeInfo</code>" for the following reasons:
     *   <ol>
     *     <li>Avoid name collision with the ISO 19115:2003 definition of "<code>featureTypes</code>".</li>
     *     <li>Avoid confusion between <code>FeatureTypeInfo</code> and <code>org.opengis.feature.FeatureType</code>.
     *         A <code>getFeatureTypes()</code> method name would suggest that the collection contains the later.</li>
     *   </ol>
     *
     * @since 3.1
     */
    @UML(identifier="featureTypes", obligation=OPTIONAL, specification=ISO_19115)
    Collection<? extends FeatureTypeInfo> getFeatureTypeInfo();

    /**
     * Names of the {@linkplain #getFeatureTypes() feature types}.
     *
     * @return Names of the feature types.
     *
     * @deprecated As of ISO 19115:2014, replaced by {@link #getFeatureTypeInfo()}.
     */
    @Deprecated
    Collection<? extends GenericName> getFeatureTypes();

    /**
     * Complete bibliographic reference to one or more external feature catalogues.
     *
     * @return Bibliographic reference to one or more external feature catalogues.
     */
    @UML(identifier="featureCatalogueCitation", obligation=MANDATORY, specification=ISO_19115)
    Collection<? extends Citation> getFeatureCatalogueCitations();
}
