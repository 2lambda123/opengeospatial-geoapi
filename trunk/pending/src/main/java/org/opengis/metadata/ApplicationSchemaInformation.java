/*$************************************************************************************************
 **
 ** $Id$
 **
 ** $Source$
 **
 ** Copyright (C) 2004-2005 Open GIS Consortium, Inc.
 ** All Rights Reserved. http://www.opengis.org/legal/
 **
 *************************************************************************************************/
package org.opengis.metadata;

// J2SE direct dependencies
import java.net.URI;

// OpenGIS direct dependencies
import org.opengis.metadata.citation.Citation;

// Annotations
import org.opengis.annotation.UML;
import static org.opengis.annotation.Obligation.*;
import static org.opengis.annotation.Specification.*;


/**
 * Information about the application schema used to build the dataset.
 *
 * @version <A HREF="http://www.opengis.org/docs/01-111.pdf">Abstract specification 5.0</A>
 * @author Martin Desruisseaux (IRD)
 * @since GeoAPI 2.0
 */
@UML(identifier="MD_ApplicationSchemaInformation", specification=ISO_19115)
public interface ApplicationSchemaInformation {
    /**
     * Name of the application schema used.
     */
    @UML(identifier="name", obligation=MANDATORY, specification=ISO_19115)
    Citation getName();

    /**
     * Identification of the schema language used.
     */
    @UML(identifier="schemaLanguage", obligation=MANDATORY, specification=ISO_19115)
    String getSchemaLanguage();

    /**
     * Formal language used in Application Schema.
     */
    @UML(identifier="constraintLanguage", obligation=MANDATORY, specification=ISO_19115)
    String getConstraintLanguage();

    /**
     * Full application schema given as an ASCII file.
     *
     * @todo In UML, the type was {@code CharacterString}. It is not clear if
     *       it should be the file name or the file content.
     */
    @UML(identifier="schemaAscii", obligation=OPTIONAL, specification=ISO_19115)
    URI getSchemaAscii();

    /**
     * Full application schema given as a graphics file.
     */
    @UML(identifier="graphicsFile", obligation=OPTIONAL, specification=ISO_19115)
    URI getGraphicsFile();

    /**
     * Full application schema given as a software development file.
     *
     * @todo In UML, the type was {@code binary}. It is not clear if
     *       it was intented to be the file content.
     */
    @UML(identifier="softwareDevelopmentFile", obligation=OPTIONAL, specification=ISO_19115)
    URI getSoftwareDevelopmentFile();

    /**
     * Software dependent format used for the application schema software dependent file.
     */
    @UML(identifier="softwareDevelopmentFileFormat", obligation=OPTIONAL, specification=ISO_19115)
    String getSoftwareDevelopmentFileFormat();

    /**
     * Information about the spatial attributes in the application schema for the feature types.
     */
    @UML(identifier="featureCatalogueSupplement", obligation=OPTIONAL, specification=ISO_19115)
    SpatialAttributeSupplement getFeatureCatalogueSupplement();
}
