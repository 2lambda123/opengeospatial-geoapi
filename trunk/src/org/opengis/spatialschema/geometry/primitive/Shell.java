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
package org.opengis.spatialschema.geometry.primitive;

// OpenGIS direct dependencies
import org.opengis.spatialschema.geometry.complex.CompositeSurface;

// Annotations
import org.opengis.annotation.UML;
import static org.opengis.annotation.Obligation.*;
import static org.opengis.annotation.Specification.*;


/**
 * Represents a single connected component of a {@linkplain SolidBoundary solid boundary}.
 * A shell consists of a number of references to {@linkplain OrientableSurface orientable
 * surfaces} connected in a topological cycle (an object whose boundary is empty). Unlike a
 * {@linkplain Ring ring}, a <code>Shell</code>'s elements have no natural sort order. Like
 * {@linkplain Ring rings}, <code>Shell</code>s are simple.
 *
 * @version <A HREF="http://www.opengis.org/docs/01-101.pdf">Abstract specification 5</A>
 * @author Martin Desruisseaux (IRD)
 * @since GeoAPI 1.0
 *
 * @see SolidBoundary
 * @see Ring
 */
@UML(identifier="GM_Shell", specification=ISO_19107)
public interface Shell extends CompositeSurface {
    /**
     * Always returns <code>true</code> since shell objects are simples.
     *
     * @return Always <code>true</code>.
     */
    public boolean isSimple();
}
