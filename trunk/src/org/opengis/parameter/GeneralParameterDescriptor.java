/*$************************************************************************************************
 **
 ** $Id$
 **
 ** $Source$
 **
 ** Copyright (C) 2003 Open GIS Consortium, Inc. All Rights Reserved. http://www.opengis.org/Legal/
 **
 *************************************************************************************************/
package org.opengis.parameter;

// OpenGIS direct dependencies
import org.opengis.referencing.IdentifiedObject;

// Annotations
///import org.opengis.annotation.UML;
///import static org.opengis.annotation.Obligation.*;


/**
 * Abstract definition of a parameter or group of parameters used by an operation method.
 *  
 * @author ISO 19111
 * @author <A HREF="http://www.opengis.org">OpenGIS&reg; consortium</A>
 * @version <A HREF="http://www.opengis.org/docs/03-073r1.zip">Abstract specification 2.0</A>
 *
 * @see GeneralParameterValue
 */
///@UML (identifier="CC_GeneralOperationParameter")
public interface GeneralParameterDescriptor extends IdentifiedObject {
    /**
     * Creates a new instance of {@linkplain GeneralParameterValue parameter value or group}
     * initialized with the {@linkplain ParameterDescriptor#getDefaultValue default value(s)}.
     * The {@linkplain GeneralParameterValue#getDescriptor parameter value descriptor} for
     * the created parameter value(s) will be <code>this</code> object.
     */
    GeneralParameterValue createValue();

    /**
     * The minimum number of times that values for this parameter group or
     * parameter are required. The default value is one. A value of 0 means
     * an optional parameter.
     *
     * @see #getMaximumOccurs
     */
/// @UML (identifier="minimumOccurs", obligation=OPTIONAL)
    int getMinimumOccurs();

    /**
     * The maximum number of times that values for this parameter group or
     * parameter can be included. For a {@linkplain ParameterDescriptor single parameter},
     * the value is always 1. For a {@linkplain ParameterDescriptorGroup parameter group},
     * it may vary. The default value is one.
     *
     * @see #getMinimumOccurs
     */
/// @UML (identifier="CC_OperationParameterGroup.maximumOccurs", obligation=OPTIONAL)
    int getMaximumOccurs();
}
