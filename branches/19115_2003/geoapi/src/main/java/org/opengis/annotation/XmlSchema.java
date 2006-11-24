/*$************************************************************************************************
 **
 ** $Id$
 **
 ** $URL$
 **
 ** Copyright (C) 2003-2005 Open GIS Consortium, Inc.
 ** All Rights Reserved. http://www.opengis.org/legal/
 **
 *************************************************************************************************/
package org.opengis.annotation;

// J2SE dependencies
import java.lang.annotation.Target;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;


/**
 * An annotation mapping a package to the XML schema from which it was derived.
 *
 * @author Martin Desruisseaux (IRD)
 * @since GeoAPI 2.0
 */
@Documented
@Target(PACKAGE)
@Retention(RUNTIME)
public @interface XmlSchema {
    /**
     * The URL to the schema.
     */
    String URL();

    /**
     * The specification where this XML schema come from.
     */
    Specification specification();
}
