/*$************************************************************************************************
 **
 ** $Id$
 **
 ** $URL$
 **
 ** Copyright (C) 2004-2005 Open GIS Consortium, Inc.
 ** All Rights Reserved. http://www.opengis.org/legal/
 **
 *************************************************************************************************/
package org.opengis.metadata.constraint;

import java.util.Collection;
import org.opengis.util.InternationalString;
import org.opengis.annotation.UML;

import static org.opengis.annotation.Obligation.*;
import static org.opengis.annotation.Specification.*;


/**
 * Restrictions and legal prerequisites for accessing and using the resource.
 *
 * @version <A HREF="http://www.opengeospatial.org/standards/as#01-111">ISO 19115</A>
 * @author  Martin Desruisseaux (IRD)
 * @author  Cory Horner (Refractions Research)
 * @since   GeoAPI 2.0
 */
@UML(identifier="MD_LegalConstraints", specification=ISO_19115)
public interface LegalConstraints extends Constraints {
    /**
     * Access constraints applied to assure the protection of privacy or intellectual property,
     * and any special restrictions or limitations on obtaining the resource.
     *
     * @return Access constraints applied to assure the protection of privacy or intellectual property.
     */
    @UML(identifier="accessConstraints", obligation=OPTIONAL, specification=ISO_19115)
    Collection<Restriction> getAccessConstraints();

    /**
     * Constraints applied to assure the protection of privacy or intellectual property, and any
     * special restrictions or limitations or warnings on using the resource.
     *
     * @return Constraints applied to assure the protection of privacy or intellectual property.
     */
    @UML(identifier="useConstraints", obligation=OPTIONAL, specification=ISO_19115)
    Collection<Restriction> getUseConstraints();

    /**
     * Other restrictions and legal prerequisites for accessing and using the resource.
     * This method should returns a non-empty value only if {@linkplain #getAccessConstraints
     * access constraints} or {@linkplain #getUseConstraints use constraints} declares
     * {@linkplain Restriction#OTHER_RESTRICTIONS other restrictions}.
     *
     * @return Other restrictions and legal prerequisites for accessing and using the resource.
     *
     * @condition {@linkplain #getAccessConstraints() Access constraints} or
     *            {@linkplain #getUseConstraints() use constraints} equal
     *            {@link Restriction#OTHER_RESTRICTIONS}.
     */
    @UML(identifier="otherConstraints", obligation=CONDITIONAL, specification=ISO_19115)
    Collection<? extends InternationalString> getOtherConstraints();
}