/*$************************************************************************************************
 **
 ** $Id$
 **
 ** $Source$
 **
 ** Copyright (C) 2003 Open GIS Consortium, Inc. All Rights Reserved. http://www.opengis.org/Legal/
 **
 *************************************************************************************************/
package org.opengis.filter.expression;

// Annotations
import org.opengis.annotation.XmlSchema;


/**
 * Encodes the operation of subtraction where the second argument is subtracted from the first.
 * Instances of this interface implement their {@link #evaluate evaluate} method by
 * computing the numeric difference between the {@linkplain #getExpression1 first} and
 * {@linkplain #getExpression2 second} operand.
 *
 * @author <A HREF="http://www.opengis.org">OpenGIS&reg; consortium</A>
 * @version <A HREF="http://www.opengis.org/docs/02-059.pdf">Filter encoding implementation specification 1.0</A>
 * @since 1.1
 */
@XmlSchema(URL="filter.xsd", element="Sub")
public interface Subtract extends BinaryExpression {
}
