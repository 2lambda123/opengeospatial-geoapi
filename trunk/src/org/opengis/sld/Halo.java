/*$************************************************************************************************
 **
 ** $Id$
 **
 ** $Source$
 **
 ** Copyright (C) 2003 Open GIS Consortium, Inc. All Rights Reserved. http://www.opengis.org/Legal/
 **
 *************************************************************************************************/
package org.opengis.sld;

// OpenGIS direct dependencies
import org.opengis.filter.expression.Expression;

// Annotations
import org.opengis.annotation.XmlElement;


/**
 * Describes the settings of a "halo" effect as it is applied to text.
 *
 * @author <A HREF="http://www.opengis.org">OpenGIS&reg; consortium</A>
 * @version <A HREF="http://www.opengis.org/docs/02-070.pdf">Implementation specification 1.0</A>
 * @since GeoAPI 1.1
 */
@XmlElement("Halo")
public interface Halo {
    /**
     * Returns the object that indicates how the halo area around the text
     * should be filled.
     */
    @XmlElement("Fill")
    Fill getFill();

    /**
     * Sets the object that indicates how the halo area around the text should
     * be filled.
     */
    @XmlElement("Fill")
    void setFill(Fill f);

    /**
     * Returns the expression that will be evaluated to get the pixel radius of
     * the halo around the text.
     */
    @XmlElement("Radius")
    Expression getRadius();

    /**
     * Sets the expression that will be evaluates to get the pixel radius of the
     * halo around the text.
     */
    @XmlElement("Radius")
    void setRadius(Expression expression);
}
