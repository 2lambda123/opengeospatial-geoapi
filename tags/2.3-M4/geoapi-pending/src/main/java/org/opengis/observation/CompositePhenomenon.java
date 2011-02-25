/*$************************************************************************************************
 **
 ** $Id$
 **
 ** $URL$
 **
 ** Copyright (C) 2005 Open GIS Consortium, Inc.
 ** All Rights Reserved. http://www.opengis.org/legal/
 **
 *************************************************************************************************/
package org.opengis.observation;

import java.util.List;
import org.opengis.annotation.UML;

import static org.opengis.annotation.Specification.*;
import static org.opengis.annotation.Obligation.*;

/**
 * A CompositeProperty is composed of a set of component property. The
 * components may not be related to each other, though useful compound property
 * would usually have some semantic coherence. The optional base property allows
 * for the CompositeProperty to be generated by adding components to a base.
 *
 * @version <A HREF="http://www.opengeospatial.org/standards/om">Implementation specification 1.0</A>
 * @author Open Geospatial Consortium
 * @author Guilhem Legal (Geomatys)
 * @since GeoAPI 2.3
 */
@UML(identifier="CompositePhenomenon", specification=OGC_07022)
public interface CompositePhenomenon extends CompoundPhenomenon {

    /**
     * A set of component composing the phenomenon.
     */
    @UML(identifier="component", obligation=MANDATORY, specification=OGC_07022)
    List<? extends Phenomenon> getComponent();

    /**
     * Optional phenomenon that forms the basis for generating more specialized composite Phenomenon by adding more components.
     */
    @UML(identifier="base", obligation=OPTIONAL, specification=OGC_07022)
    Phenomenon getBase();
    
}