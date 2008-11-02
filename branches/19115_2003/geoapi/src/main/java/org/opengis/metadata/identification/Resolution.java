/*$************************************************************************************************
 **
 ** $Id$
 **
 ** $Source: /cvsroot/geoapi/src/org/opengis/metadata/identification/Resolution.java,v $
 **
 ** Copyright (C) 2004-2005 Open GIS Consortium, Inc.
 ** All Rights Reserved. http://www.opengis.org/legal/
 **
 *************************************************************************************************/
package org.opengis.metadata.identification;

// Annotations
import static org.opengis.annotation.ComplianceLevel.CORE;
import static org.opengis.annotation.Obligation.CONDITIONAL;
import static org.opengis.annotation.Specification.ISO_19115;

import org.opengis.annotation.Profile;
import org.opengis.annotation.UML;
import org.opengis.metadata.MetadataEntity;


/**
 * Level of detail expressed as a scale factor or a ground distance.
 *
 * @version <A HREF="http://www.opengis.org/docs/01-111.pdf">Abstract specification 5.0</A>
 * @author Martin Desruisseaux (IRD)
 * @since GeoAPI 2.0
 */
@Profile (level=CORE)
@UML(identifier="MD_Resolution", specification=ISO_19115)
public interface Resolution extends MetadataEntity{
    /**
     * Level of detail expressed as the scale of a comparable hardcopy map or chart.
     * This value should be between 0 and 1.
     * Only one of {@linkplain #getEquivalentScale equivalent scale} and
     * {@linkplain #getDistance ground sample distance} may be provided.
     *
     * @unitof RepresentativeFraction
     */
    @Profile (level=CORE)
    @UML(identifier="equivalentScale", obligation=CONDITIONAL, specification=ISO_19115)
    double getEquivalentScale();

    /**
     * Ground sample distance.
     * Only one of {@linkplain #getEquivalentScale equivalent scale} and
     * {@linkplain #getDistance ground sample distance} may be provided.
     *
     * @unitof Distance
     */
    @Profile (level=CORE)
    @UML(identifier="distance", obligation=CONDITIONAL, specification=ISO_19115)
    double getDistance();
}