/*
 *    GeoAPI - Java interfaces for OGC/ISO standards
 *    http://www.geoapi.org
 *
 *    Copyright (C) 2014 Open Geospatial Consortium, Inc.
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
package org.opengis.metadata.service;

import java.util.Set;
import java.util.List;
import java.util.Collection;
import org.opengis.annotation.UML;
import org.opengis.util.InternationalString;
import org.opengis.metadata.citation.OnlineResource;

import static org.opengis.annotation.Obligation.*;
import static org.opengis.annotation.Specification.ISO_19115;


/**
 * Describe the signature of one and only one method provided by the service.
 *
 * @author  Rémi Maréchal (Geomatys)
 * @version 3.1
 * @since   3.1
 */
@UML(identifier="SV_OperationMetadata", specification=ISO_19115)
public interface OperationMetadata {
    /**
     * An unique identifier for this interface.
     *
     * @return An unique identifier for this interface.
     */
    @UML(identifier="operationName", obligation=MANDATORY, specification=ISO_19115)
    InternationalString getOperationName();

    /**
     * Distributed computing platforms (DCPs) on which the operation has been implemented.
     *
     * @return Distributed computing platforms on which the operation has been implemented.
     */
    @UML(identifier="distributedComputingPlatform", obligation=MANDATORY, specification=ISO_19115)
    Collection<DistributedComputingPlatform> getDistributedComputingPlatform();

    /**
     * Free text description of the intent of the operation and the results of the operation.
     *
     * @return Free text description of the intent of the operation and the results of the operation,
     *         or {@code null} if none.
     */
    @UML(identifier="operationDescription", obligation=OPTIONAL, specification=ISO_19115)
    InternationalString getOperationDescription();

    /**
     * The name use to invoke this interface within the context of the DCP.
     * The name is identical for all Distributed computing platforms (DCPs).
     *
     * @return The name use to invoke this interface within the context of the DCP, or {@code null} if none.
     */
    @UML(identifier="invocationName", obligation=OPTIONAL, specification=ISO_19115)
    InternationalString getInvocationName();

    /**
     * Handle for accessing the service interface.
     *
     * @return Handle for accessing the service interface.
     */
    @UML(identifier="connectPoint", obligation=MANDATORY, specification=ISO_19115)
    Collection<? extends OnlineResource> getConnectPoint();

    /**
     * The parameters that are required for this interface.
     * Returns an empty collection if none.
     *
     * @return The parameters that are required for this interface, or an empty collection if none.
     */
    @UML(identifier="parameters", obligation=OPTIONAL, specification=ISO_19115)
    Set<? extends Parameter> getParameters();

    /**
     * List of operation that must be completed immediately before current operation is invoked.
     * The return value is structured as a list for capturing alternate predecessor paths
     * and sets for capturing parallel predecessor paths.
     *
     * @return List of operation that must be completed immediately, or an empty list if none.
     */
    @UML(identifier="dependsOn", obligation=OPTIONAL, specification=ISO_19115)
    List<? extends OperationMetadata> getDependsOn();
}
