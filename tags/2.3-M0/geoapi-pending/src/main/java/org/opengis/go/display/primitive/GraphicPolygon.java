/**************************************************************************************************
 **
 ** $Id$
 **
 ** $URL$
 **
 ** Copyright (C) 2003-2005 Open GIS Consortium, Inc.
 ** All Rights Reserved. http://www.opengis.org/legal/
 **
 *************************************************************************************************/
package org.opengis.go.display.primitive;

import org.opengis.go.display.style.PolygonSymbolizer;
import org.opengis.go.spatial.PathType;
import org.opengis.geometry.DirectPosition;


/**
 * Defines a common abstraction for graphic representation of polygons.
 * A <code>GraphicPolygon</code> consists of a an exterior ring of vertices
 * and a set of non-mutually-overlapping interior rings of vertices.
 * The exterior and interior rings of a polygon are defined by a list of
 * {@linkplain DirectPosition} objects.  Technically speaking, these rings are
 * required to be closed (i.e. the first and last points must coincide).
 * However, this interface allows the user to create lists where the first and
 * last points are not coincident.  In such a case, the implementation of this
 * interface will assume that there is an additional segment between the first
 * and last points, completing the ring.  (This will take place without
 * modifying the list of points.)
 *
 * @author <A HREF="http://www.opengis.org">OpenGIS&reg; consortium</A>
 * @since GO 1.0
 */
public interface GraphicPolygon extends Graphic {
    /**
     * Returns a live reference to the vertex at the given index.
     *
     * @param index Index of the point to retrieve a reference to.
     * @return Returns a reference to the index-th vertex.
     * @throws IndexOutOfBoundsException Throws this if the given index is less
     *   than zero or greater than or equal to the number points in the exterior
     *   ring.
     */
    DirectPosition getExteriorPoint(int index);

    /**
     * Adds a new point to the list of vertices in the exterior ring.
     *
     * @param position The new position to add to the list.
     */
    void addExteriorPoint(DirectPosition position);

    /**
     * Inserts a new point into the list of vertices for the exterior ring.
     *
     * @param index The index that the new position will occupy after the
     *   insert.  All other points have their index increased by one.
     * @param position The new position to add to the list.
     * @throws IndexOutOfBoundsException Throws this if the given index is less
     *   than zero or greater than or equal to the number points in the exterior
     *   ring.
     */
    void insertExteriorPoint(int index, DirectPosition position);

    /**
     * Replaces a position in the list of vertices for the exterior ring.
     *
     * @param index The index of the point to replace.
     * @param position The point that will take the place of the existing one.
     * @return Returns the position that was previously in the given position.
     * @throws IndexOutOfBoundsException Throws this if the given index is less
     *   than zero or greater than or equal to the number points in the exterior
     *   ring.
     */
    DirectPosition setExteriorPoint(int index, DirectPosition position);

    /**
     * Removes a position from the list of vertices for the exterior ring.
     *
     * @param index Index of the position to remove.  All positions after this
     *   index are moved forward in the list.
     * @return Returns the {@linkplain DirectPosition} previously at the given
     *   index.
     * @throws IndexOutOfBoundsException Throws this if the given index is less
     *   than zero or greater than or equal to the number points in the exterior
     *   ring.
     */
    DirectPosition removeExteriorPoint(int index);

    /**
     * Returns a new array containing references to all of the vertices in the
     * exterior ring.
     *
     * @return A newly allocated array containing references to the vertices.
     */
    DirectPosition [] getExteriorRing();

    /**
     * Clears the list of vertices in the exterior ring and adds all of the
     * vertices in the given array.  This polygon will keep references to the
     * {@linkplain DirectPosition}s in the array, but not the array object
     * itself.
     */
    void setExteriorRing(DirectPosition [] newVertices);

    /**
     * Returns the number of vertices in the exterior ring.
     */
    int getNumExteriorPoints();

    /**
     * Returns a live reference to the vertex at the given index.
     *
     * @param index Index of the point to retrieve a reference to.
     * @param interiorRingIndex Index of the interior ring whose vertex is
     *   desired.
     * @return Returns a reference to the index-th vertex.
     * @throws IndexOutOfBoundsException Throws this if the vertex index is
     *   less than zero or greater than or equal to the number of vertices in
     *   the given interior ring.  May also throw this if the interior ring
     *   index is less than zero or greater than or equal to the number of
     *   interior rings.
     */
    DirectPosition getInteriorPoint(int index, int interiorRingIndex);

    /**
     * Adds a new point to the list of vertices of an interior ring.
     *
     * @param position The new position to add to the list.
     * @param interiorRingIndex Index of the interior ring whose vertex list is
     *   to be modified.
     * @throws IndexOutOfBoundsException Throws this if the interior ring index
     *   is less than zero or greater than or equal to the number of interior
     *   rings.
     */
    void addInteriorPoint(int interiorRingIndex, DirectPosition position);

    /**
     * Inserts a new point into the list of vertices for an interior ring.
     *
     * @param index The index that the new position will occupy after the
     *   insert.  All other points have their index increased by one.
     * @param interiorRingIndex Index of the interior ring whose vertex list is
     *   to be modified.
     * @param position The new position to add to the list.
     * @throws IndexOutOfBoundsException Throws this if the vertex index is
     *   less than zero or greater than or equal to the number of vertices in
     *   the given interior ring.  May also throw this if the interior ring
     *   index is less than zero or greater than or equal to the number of
     *   interior rings.
     */
    void insertInteriorPoint(int index, int interiorRingIndex, DirectPosition position);

    /**
     * Replaces a position in the list of vertices for an interior ring.
     *
     * @param index The index of the point to replace.
     * @param interiorRingIndex Index of the interior ring whose vertex list is
     *   to be modified.
     * @param position The point that will take the place of the existing one.
     * @return Returns the position that was previously in the given position.
     * @throws IndexOutOfBoundsException Throws this if the vertex index is
     *   less than zero or greater than or equal to the number of vertices in
     *   the given interior ring.  May also throw this if the interior ring
     *   index is less than zero or greater than or equal to the number of
     *   interior rings.
     */
    DirectPosition setInteriorPoint(int index, int interiorRingIndex, DirectPosition position);

    /**
     * Removes a position from the list of vertices for an interior ring.
     *
     * @param index Index of the position to remove.  All positions after this
     *   index are moved forward in the list.
     * @param interiorRingIndex Index of the interior ring whose vertex list is
     *   to be modified.
     * @return Returns the {@linkplain DirectPosition} previously at the given
     *   index.
     * @throws IndexOutOfBoundsException Throws this if the vertex index is
     *   less than zero or greater than or equal to the number of vertices in
     *   the given interior ring.  May also throw this if the interior ring
     *   index is less than zero or greater than or equal to the number of
     *   interior rings.
     */
    DirectPosition removeInteriorPoint(int index, int interiorRingIndex);

    /**
     * Returns a new array containing references to all of the vertices in the
     * exterior ring.
     *
     * @param interiorRingIndex Index of the interior ring whose vertex list is
     *   being requested.
     * @return A newly allocated array containing references to the vertices.
     * @throws IndexOutOfBoundsException Throws this if the interior ring index
     *   is less than zero or greater than or equal to the number of interior
     *   rings.
     */
    DirectPosition[] getInteriorRing(int interiorRingIndex);

    /**
     * Creates a new interior ring (hole) for this polygon.
     *
     * @return Returns the index of the newly created ring.
     */
    int addInteriorRing();

    /**
     * Creates a new interior ring for this polygon and immediately sets its
     * points to those in the given array.
     *
     * @return Returns the index of the newly created ring.
     */
    int addInteriorRing(DirectPosition [] vertices);

    /**
     * Clears the list of vertices in an exterior ring and adds all of the
     * vertices in the given array.  This polygon will keep references to the
     * {@linkplain DirectPosition}s in the array, but not the array object
     * itself.
     *
     * @param interiorRingIndex Index of the interior ring whose vertex list is
     *   to be modified.
     * @param newVertices The list of vertices to replace the existing ring
     *   with.
     * @throws IndexOutOfBoundsException Throws this if the interior ring index
     *   is less than zero or greater than or equal to the number of interior
     *   rings.
     */
    void setInteriorRing(int interiorRingIndex, DirectPosition [] newVertices);

    /**
     * Returns the number of vertices in an interior ring.
     *
     * @param interiorRingIndex Index of the interior ring to get a vertex count
     *   from.
     * @throws IndexOutOfBoundsException Throws this if the interior ring index
     *   is less than zero or greater than or equal to the number of interior
     *   rings.
     */
    int getNumInteriorPoints(int interiorRingIndex);

    /**
     * Removes an interior ring.
     *
     * @param interiorRingIndex Index of the ring to remove.
     */
    void removeInteriorRing(int interiorRingIndex);

    /**
     * Returns the number of interior rings currently in this polygon.
     */
    int getNumInteriorRings();

    /**
     * Returns a newly allocated two-dimensional array of points.  The first
     * index of the returned array chooses an interior ring.  The second index
     * runs over the vertices of that ring.  The {@linkplain DirectPosition}s in
     * the list are references to the same objects held by this polygon, but the
     * array object is newly allocated.
     */
    DirectPosition[][] getInteriorRings();

    /**
     * Clears the lists of vertices of all the interior rings and adds all of the
     * vertices in the given arrays.  This polygon will keep references to the
     * {@linkplain DirectPosition}s in the arrays, but not the array objecst
     * themselves.
     *
     */
    void setInteriorRings(DirectPosition[][] interiorRingPoints);

    /**
     * Returns the <code>GraphicStyle</code> for this <code>GraphicPolygon</code>,
     * which is required to be a <code>PolygonSymbolizer</code>.
     * @return the GraphicPolygon's <code>GraphicStyle</code>.
     */
    PolygonSymbolizer getPolygonSymbolizer();

    /**
     * Sets the parameter that indicates how the "in-between" points between
     * vertices should be drawn.
     *
     * @param pathType One of the static constants indicating the method to
     *   use.
     */
    void setPathType(PathType pathType);

    /**
     * Returns the parameter that indicates how the "in-between" points between
     * vertices are to be drawn.
     */
    PathType getPathType();

    /**
     * Indicates whether clicking on an edge of this graphic linestring should
     * insert a new vertex at that location when the object is in edit mode.
     */
    boolean isAllowingNewVertices();

    /**
     * Sets the boolean that indicates whether clicking on an edge of this
     * graphic linestring should insert a new vertex at that location.
     */
    void setAllowingNewVertices(boolean newValue);
}