/*
 *    GeoAPI - Java interfaces for OGC/ISO standards
 *    http://www.geoapi.org
 *
 *    Copyright (C) 2004-2019 Open Geospatial Consortium, Inc.
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
package org.opengis.metadata.maintenance;

import java.util.Date;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import org.opengis.metadata.citation.DateType;
import org.opengis.metadata.citation.CitationDate;
import org.opengis.metadata.citation.Responsibility;
import org.opengis.metadata.citation.ResponsibleParty;
import org.opengis.metadata.maintenance.Scope;
import org.opengis.temporal.PeriodDuration;
import org.opengis.util.InternationalString;
import org.opengis.annotation.UML;

import static org.opengis.annotation.Obligation.*;
import static org.opengis.annotation.Specification.*;


/**
 * Information about the scope and frequency of updating.
 *
 * @author  Martin Desruisseaux (IRD)
 * @author  Cory Horner (Refractions Research)
 * @author  Rémi Maréchal (Geomatys)
 * @version 3.1
 * @since   2.0
 */
@UML(identifier="MD_MaintenanceInformation", specification=ISO_19115)
public interface MaintenanceInformation {
    /**
     * Frequency with which changes and additions are made to the resource after the
     * initial resource is completed.
     *
     * @return frequency with which changes and additions are made to the resource.
     */
    @UML(identifier="maintenanceAndUpdateFrequency", obligation=OPTIONAL, specification=ISO_19115)
    default MaintenanceFrequency getMaintenanceAndUpdateFrequency() {
        return null;
    }

    /**
     * Date information associated with maintenance of resource.
     * Returns an empty collection if none.
     *
     * @return date information associated with maintenance of resource.
     *
     * @since 3.1
     */
    @UML(identifier="maintenanceDate", obligation=OPTIONAL, specification=ISO_19115)
    default Collection<? extends CitationDate> getMaintenanceDates() {
        return Collections.emptyList();
    }

    /**
     * Scheduled revision date for resource.
     *
     * @return scheduled revision date, or {@code null}.
     *
     * @deprecated As of ISO 19115:2014, replaced by {@link #getMaintenanceDates()} in order to enable inclusion
     *             of a {@link org.opengis.metadata.citation.DateType} to describe the type of the date.
     *             Note that {@link org.opengis.metadata.citation.DateType#NEXT_UPDATE} was added to that code list.
     */
    @Deprecated
    @UML(identifier="dateOfNextUpdate", obligation=OPTIONAL, specification=ISO_19115, version=2003)
    default Date getDateOfNextUpdate() {
        Date fallback = null;
        for (CitationDate info : getMaintenanceDates()) {
            Date date = info.getDate();
            DateType type = info.getDateType();
            if (DateType.NEXT_UPDATE.equals(type)) {
                return date;
            } else if (fallback == null) {
                fallback = date;
            }
        }
        return fallback;
    }

    /**
     * Maintenance period other than those defined.
     *
     * @return the maintenance period, or {@code null}.
     */
    @UML(identifier="userDefinedMaintenanceFrequency", obligation=OPTIONAL, specification=ISO_19115)
    default PeriodDuration getUserDefinedMaintenanceFrequency() {
        return null;
    }

    /**
     * Type of resource and / or extent to which the maintenance information applies.
     *
     * @return type of resource and / or extent to which the maintenance information applies, or {@code null} if none.
     *
     * @since 3.1
     */
    @UML(identifier="maintenanceScope", obligation=OPTIONAL, specification=ISO_19115)
    default Collection<? extends Scope> getMaintenanceScopes() {
        return Collections.emptyList();
    }

    /**
     * Scope of data to which maintenance is applied.
     *
     * @return scope of data to which maintenance is applied.
     *
     * @deprecated As of ISO 19115:2014, {@code getUpdateScopes()} and {@link #getUpdateScopeDescriptions()}
     *             were combined into {@link #getMaintenanceScopes()} in order to allow specifying a scope
     *             that includes a spatial and temporal extent.
     */
    @Deprecated
    @UML(identifier="updateScope", obligation=OPTIONAL, specification=ISO_19115, version=2003)
    default Collection<ScopeCode> getUpdateScopes() {
        LinkedHashSet<ScopeCode> codes = new LinkedHashSet<>();
        getMaintenanceScopes().forEach((scope) -> {
            codes.add(scope.getLevel());
        });
        return codes;
    }

    /**
     * Additional information about the range or extent of the resource.
     *
     * @return additional information about the range or extent of the resource.
     *
     * @deprecated As of ISO 19115:2014, {@link #getUpdateScopes()} and {@code getUpdateScopeDescriptions()}
     *             were combined into {@link #getMaintenanceScopes()} in order to allow specifying a scope
     *             that includes a spatial and temporal extent.
     */
    @Deprecated
    @UML(identifier="updateScopeDescription", obligation=OPTIONAL, specification=ISO_19115, version=2003)
    default Collection<? extends ScopeDescription> getUpdateScopeDescriptions() {
        for (Scope scope : getMaintenanceScopes()) {
            Collection<? extends ScopeDescription> desc = scope.getLevelDescription();
            if (desc != null) return desc;
        }
        return Collections.emptyList();
    }

    /**
     * Information regarding specific requirements for maintaining the resource.
     *
     * @return information regarding specific requirements for maintaining the resource.
     */
    @UML(identifier="maintenanceNote", obligation=OPTIONAL, specification=ISO_19115)
    default Collection<? extends InternationalString> getMaintenanceNotes() {
        return Collections.emptyList();
    }

    /**
     * Identification of, and means of communicating with,
     * person(s) and organization(s) with responsibility for maintaining the resource.
     *
     * <div class="warning"><b>Upcoming API change — generalization</b><br>
     * As of ISO 19115:2014, {@code ResponsibleParty} is replaced by the {@link Responsibility} parent interface.
     * This change may be applied in GeoAPI 4.0.
     * </div>
     *
     * @return means of communicating with person(s) and organization(s) with responsibility
     *         for maintaining the resource.
     */
    @UML(identifier="contact", obligation=OPTIONAL, specification=ISO_19115, version=2003)
    default Collection<? extends ResponsibleParty> getContacts() {
        return Collections.emptyList();
    }
}
