/*
 *    GeoAPI - Java interfaces for OGC/ISO standards
 *    Copyright © 2004-2011 Open Geospatial Consortium, Inc.
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
package org.opengis.metadata.citation;

import java.util.Collection;
import org.opengis.util.InternationalString;
import org.opengis.annotation.UML;

import static org.opengis.annotation.Obligation.*;
import static org.opengis.annotation.Specification.*;


/**
 * Location of the responsible individual or organization.
 *
 * @author  Martin Desruisseaux (IRD)
 * @version 3.0
 * @since   1.0
 */
@UML(identifier="CI_Address", specification=ISO_19115)
public interface Address {
    /**
     * Address line for the location (as described in ISO 11180, Annex A).
     * Returns an empty collection if none.
     *
     * @return Address line for the location.
     */
    @UML(identifier="deliveryPoint", obligation=OPTIONAL, specification=ISO_19115)
    Collection<String> getDeliveryPoints();

    /**
     * The city of the location.
     * Returns {@code null} if unspecified.
     *
     * @return The city of the location, or {@code null}.
     */
    @UML(identifier="city", obligation=OPTIONAL, specification=ISO_19115)
    InternationalString getCity();

    /**
     * State, province of the location.
     * Returns {@code null} if unspecified.
     *
     * @return State, province of the location, or {@code null}.
     */
    @UML(identifier="administrativeArea", obligation=OPTIONAL, specification=ISO_19115)
    InternationalString getAdministrativeArea();

    /**
     * ZIP or other postal code.
     * Returns {@code null} if unspecified.
     *
     * @return ZIP or other postal code, or {@code null}.
     */
    @UML(identifier="postalCode", obligation=OPTIONAL, specification=ISO_19115)
    String getPostalCode();

    /**
     * Country of the physical address.
     * Returns {@code null} if unspecified.
     *
     * @return Country of the physical address, or {@code null}.
     */
    @UML(identifier="country", obligation=OPTIONAL, specification=ISO_19115)
    InternationalString getCountry();

    /**
     * Address of the electronic mailbox of the responsible organization or individual.
     * Returns an empty collection if none.
     *
     * @return Address of the electronic mailbox of the responsible organization or individual.
     */
    @UML(identifier="electronicMailAddress", obligation=OPTIONAL, specification=ISO_19115)
    Collection<String> getElectronicMailAddresses();
}
