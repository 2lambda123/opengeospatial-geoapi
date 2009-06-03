/*$************************************************************************************************
 **
 ** $Id$
 **
 ** $Source: /cvsroot/geoapi/src/org/opengis/metadata/distribution/Medium.java,v $
 **
 ** Copyright (C) 2004-2005 Open GIS Consortium, Inc.
 ** All Rights Reserved. http://www.opengis.org/legal/
 **
 *************************************************************************************************/
package org.opengis.metadata.distribution;

// J2SE direct dependencies and extensions
import java.util.Collection;
import javax.units.Unit;

// OpenGIS direct dependencies
import org.opengis.metadata.MetadataEntity;
import org.opengis.util.InternationalString;

// Annotations
import org.opengis.annotation.UML;
import static org.opengis.annotation.Obligation.*;
import static org.opengis.annotation.Specification.*;


/**
 * Information about the media on which the resource can be distributed.
 *
 * @version <A HREF="http://www.opengis.org/docs/01-111.pdf">Abstract specification 5.0</A>
 * @author Martin Desruisseaux (IRD)
 * @since GeoAPI 2.0
 */
@UML(identifier="MD_Medium", specification=ISO_19115)
public interface Medium extends MetadataEntity{
    /**
     * Name of the medium on which the resource can be received.
     */
    @UML(identifier="name", obligation=OPTIONAL, specification=ISO_19115)
    MediumName getName();

    /**
     * Density at which the data is recorded.
     * The numbers should be greater than zero.
     */
    @UML(identifier="density", obligation=OPTIONAL, specification=ISO_19115)
    Collection<Number> getDensities();

    /**
     * Units of measure for the recording density.
     */
    @UML(identifier="densityUnits", obligation=CONDITIONAL, specification=ISO_19115)
    Unit getDensityUnits();

    /**
     * Number of items in the media identified.
     * Returns {@code null} if unknown.
     */
    @UML(identifier="volumes", obligation=OPTIONAL, specification=ISO_19115)
    Integer getVolumes();

    /**
     * Method used to write to the medium.
     */
    @UML(identifier="mediumFormat", obligation=OPTIONAL, specification=ISO_19115)
    Collection<MediumFormat> getMediumFormats();

    /**
     * Description of other limitations or requirements for using the medium.
     */
    @UML(identifier="mediumNote", obligation=OPTIONAL, specification=ISO_19115)
    InternationalString getMediumNote();
}