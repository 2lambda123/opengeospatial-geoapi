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
package org.opengis.filter.expression;

// Annotations
import org.opengis.annotation.XmlElement;


/**
 * Encodes the operation of division where the first argument is divided by the second argument.
 * <p>
 * Instances of this interface implement their {@link #evaluate evaluate} method by
 * computing the numeric quotient resulting from dividing the {@linkplain #getExpression1 first}
 * operand by the {@linkplain #getExpression2 second}. The second argument or expression cannot
 * evaluate to zero.
 * </p>
 * @version <A HREF="http://www.opengis.org/docs/02-059.pdf">Implementation specification 1.0</A>
 * @author Chris Dillard (SYS Technologies)
 * @since GeoAPI 2.0
 */
@XmlElement("Div")
public interface Divide extends BinaryExpression {
	/** Operator name used to check FilterCapabilities */
	public static String NAME = "Div";
}
