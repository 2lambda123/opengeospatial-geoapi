/*$************************************************************************************************
 **
 ** $Id$
 **
 ** $Source$
 **
 ** Copyright (C) 2005 Open GIS Consortium, Inc.
 ** All Rights Reserved. http://www.opengis.org/legal/
 **
 *************************************************************************************************/
package org.opengis.filter;

// OpenGIS direct dependencies
import org.opengis.filter.spatial.BBOX;
import org.opengis.filter.spatial.Beyond;
import org.opengis.filter.spatial.Contains;
import org.opengis.filter.spatial.Crosses;
import org.opengis.filter.spatial.DWithin;
import org.opengis.filter.spatial.Disjoint;
import org.opengis.filter.spatial.Equals;
import org.opengis.filter.spatial.Intersects;
import org.opengis.filter.spatial.Overlaps;
import org.opengis.filter.spatial.Touches;
import org.opengis.filter.spatial.Within;

// Annotation
import org.opengis.annotation.Extension;


/**
 * Visitor with {@code visit} methods to be called by {@link Filter#accept Filter.accept(...)}.
 *
 * @version <A HREF="http://www.opengis.org/docs/02-059.pdf">Implementation specification 1.0</A>
 * @author Chris Dillard (SYS Technologies)
 * @since GeoAPI 2.0
 */
@Extension
public interface FilterVisitor {
    Object visit(And filter,                            Object extraData);
    Object visit(FeatureId filter,                      Object extraData);
    Object visit(Not filter,                            Object extraData);
    Object visit(Or filter,                             Object extraData);
    Object visit(PropertyIsBetween filter,              Object extraData);
    Object visit(PropertyIsEqualTo filter,              Object extraData);
    Object visit(PropertyIsGreaterThan filter,          Object extraData);
    Object visit(PropertyIsGreaterThanOrEqualTo filter, Object extraData);
    Object visit(PropertyIsLessThan filter,             Object extraData);
    Object visit(PropertyIsLessThanOrEqualTo filter,    Object extraData);
    Object visit(PropertyIsLike filter,                 Object extraData);
    Object visit(PropertyIsNull filter,                 Object extraData);

    Object visit(BBOX filter,       Object extraData);
    Object visit(Beyond filter,     Object extraData);
    Object visit(Contains filter,   Object extraData);
    Object visit(Crosses filter,    Object extraData);
    Object visit(Disjoint filter,   Object extraData);
    Object visit(DWithin filter,    Object extraData);
    Object visit(Equals filter,     Object extraData);
    Object visit(Intersects filter, Object extraData);
    Object visit(Overlaps filter,   Object extraData);
    Object visit(Touches filter,    Object extraData);
    Object visit(Within filter,     Object extraData);
}
