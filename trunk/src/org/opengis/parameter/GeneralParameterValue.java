/*$************************************************************************************************
 **
 ** $Id$
 **
 ** $Source$
 **
 ** Copyright (C) 2003-2005 Open GIS Consortium, Inc.
 ** All Rights Reserved. http://www.opengis.org/legal/
 **
 *************************************************************************************************/
package org.opengis.parameter;

// OpenGIS direct dependencies
import org.opengis.util.Cloneable;

// Annotations
import org.opengis.annotation.UML;
import static org.opengis.annotation.Obligation.*;
import static org.opengis.annotation.Specification.*;


/**
 * Abstract parameter value or group of parameter values.
 *  
 * @author ISO 19111
 * @author <A HREF="http://www.opengis.org">OpenGIS&reg; consortium</A>
 * @version <A HREF="http://www.opengis.org/docs/03-073r1.zip">Abstract specification 2.0</A>
 *
 * @see GeneralParameterDescriptor
 */
@UML(identifier="CC_GeneralParameterValue", specification=ISO_19111)
public interface GeneralParameterValue extends Cloneable {
    /**
     * Returns the abstract definition of this parameter or group of parameters.
     *
     * @return The abstract definition of this parameter or group of parameters.
     */
    GeneralParameterDescriptor getDescriptor();

    /**
     * Returns a copy of this parameter value or group.
     *
     * @return A copy of this parameter value or group.
     */
    /*{GeneralParameterValue}*/ Object clone();
}
