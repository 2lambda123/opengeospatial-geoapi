/*
 * Copyright (C) 2003 Open GIS Consortium, Inc. All Rights Reserved. http://www.opengis.org/Legal/
 */
package org.opengis.gm.primitive;

// J2SE direct dependencies
import java.util.List;

// OpenGIS direct dependencies
import org.opengis.gm.Envelope;
import org.opengis.gm.DirectPosition;
import org.opengis.gm.geometry.Position;


/**
 * A factory of {@linkplain Primitive primitive} geometric objects.
 *
 * @author ISO/DIS 19107
 * @author <A HREF="http://www.opengis.org">OpenGIS&reg; consortium</A>
 * @version 2.0
 *
 * @revisit Should we extend {@link org.opengis.crs.Factory}?
 */
public interface PrimitiveFactory {
    /**
     * Returns an envelope as a primitive. An {@linkplain Envelope envelope} will often be
     * used in query operations, and therefore must have a cast operation that returns a
     * {@linkplain org.opengis.gm.Geometry geometry}. The actual return of the operation depends
     * upon the dimension of the {@linkplain org.opengis.crs.crs.CoordinateReferenceSystem coordinate
     * reference system} and the extent of the {@linkplain Envelope envelope}. In a 2D system,
     * the primitive returned will be a {@linkplain Surface surface} (if the envelope does not
     * collapse to a point or line). In 3D systems, the usual return is a {@linkplain Solid solid}.
     * <br><br>
     * <strong>EXAMPLE:</strong> In the case where the {@linkplain Envelope envelope} is totally
     * contained in the domain of validity of its {@linkplain org.opengis.crs.crs.CRS} (coordinate
     * reference system) object, its associated {@linkplain Primitive primitive} is the convex
     * hull of the various permutations of the coordinates in the corners. For example, suppose
     * that a particular envelope in 2D is defined as:
     *
     * <blockquote><pre>
     * lowerCorner = (x1, y1)
     * upperCorner = (x2, y2)</pre></blockquote>
     *
     * (we ignore the CRS below, assuming that it is a global variable), then we can take the
     * various permutations of the ordinate values to create a list of polygon corners:
     *
     * <blockquote><pre>
     * {@link org.opengis.gm.aggregate.MultiPoint} = { (x1, y1), (x1, y2), (x2, y1), (x2, y2) }</pre></blockquote>
     *
     * If we then apply the {@linkplain org.opengis.gm.Geometry#getConvexHull convex hull}
     * function to the multi point, we get a polygon as a {@linkplain Surface surface}.
     * The extent of a polygon in 2D is totally defined by its
     * {@linkplain org.opengis.gm.Geometry#getBoundary boundary} (internal surface
     * patches are planar and do not need interior control points) which gives
     * us a data type to represent {@linkplain Surface surface} in 2D:
     *
     * <blockquote><pre>
     * {@link org.opengis.gm.primitive.Ring} = {
     *     {@link org.opengis.gm.geometry.LineString} = { (x1, y1), (x1, y2), (x2, y2), (x2, y1), (x1, y1)}
     * }</pre></blockquote>
     *
     * So that the {@linkplain SurfaceBoundary surface boundary} record contains the above-cited
     * exterior ring, and an empty set of interior rings (convex sets have no "interior" holes).
     *
     * @UML constructor GM_Primitive(GM_Envelope)
     */
    public Primitive createPrimitive(Envelope envelope);

    /**
     * Creates a point at the specified position.
     *
     * @UML constructor GM_Point(GM_Position)
     */
    public Point createPoint(Position position);

    /**
     * Takes a list of {@linkplain CurveSegment curve segments} with the appropriate
     * end-to-start relationships and creates a {@linkplain Curve curve}.
     *
     * @UML constructor GM_Curve(GM_CurveSegment[1..n])
     */
    public Curve createCurve(List<CurveSegment> segments);

    /**
     * Takes a list of {@linkplain SurfacePatch surface patches} with the appropriate
     * side-toside relationships and creates a {@linkplain Surface surface}.
     *
     * @UML constructor GM_Surface(GM_SurfacePatch[1..n])
     */
    public Surface createSurface(List<SurfacePatch> surfaces);

    /**
     * Constructs a {@linkplain Surface surface} by indicating its boundary as a collection
     * of {@linkplain Curve curves} organized into the specified {@linkplain SurfaceBoundary
     * surface boundary}. This method is guaranteed to work always in 2D coordinate spaces,
     * In 3D coordinate spaces, this method shall require all of the defining boundary
     * {@linkplain Curve curve} instances to be coplanar (lie in a single plane) which will
     * define the surface interior.
     *
     * @UML constructor GM_Surface(GM_SurfaceBoundary)
     */
    public Surface createSurface(SurfaceBoundary boundary);

    /**
     * Constructs a {@linkplain Solid solid} by indicating its boundary as a collection of
     * {@linkplain Shell shells} organized into a {@linkplain SolidBoundary solid boundary}.
     * Since this specification is limited to 3-dimensional coordinate reference systems,
     * any solid is definable by its boundary.
     *
     * @UML constructor GM_Solid(GM_SolidBoundary)
     */
    public Solid createSolid(SolidBoundary boundary);
}
