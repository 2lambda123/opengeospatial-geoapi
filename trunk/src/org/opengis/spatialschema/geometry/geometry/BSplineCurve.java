/*$************************************************************************************************
 **
 ** $Id$
 **
 ** $Source$
 **
 ** Copyright (C) 2003 Open GIS Consortium, Inc. All Rights Reserved. http://www.opengis.org/Legal/
 **
 *************************************************************************************************/
package org.opengis.spatialschema.geometry.geometry;

// Annotations
///import org.opengis.annotation.UML;
///import static org.opengis.annotation.Obligation.*;


/**
 * A piecewise parametric polynomial or rational curve described
 * in terms of control points and basis functions. If the weights in the knots are equal
 * then it is a polynomial spline. If not, then it is a rational function spline. If
 * the boolean {@link #isPolynomial} is set to {@code true} then the weights shall all be set to 1.
 * A B-spline curve is a piecewise B�zier curve if it is quasi-uniform except that the
 * interior knots have multiplicity "degree" rather than having multiplicity one. In
 * this subtype the knot spacing shall be 1.0, starting at 0.0. A piecewise B�zier curve
 * that has only two knots, 0.0, and 1.0, each of multiplicity (degree+1), is equivalent
 * to a simple B�zier curve. 
 *
 * @author ISO/DIS 19107
 * @author <A HREF="http://www.opengis.org">OpenGIS&reg; consortium</A>
 * @version 2.0
 *
 * @see GeometryFactory#createBSplineCurve
 */
/// @UML (identifier="GM_BSplineCurve")
public interface BSplineCurve extends SplineCurve {
    /**
     * The algebraic degree of the basis functions.
     */
/// @UML (identifier="degree", obligation=MANDATORY)
    int getDegree();

    /**
     * Identifies particular types of curve which this spline is being used to
     * approximate. It is for information only, used to capture the original intention.
     * If no such approximation is intended, then the value of this attribute is {@code null}.
     */
/// @UML (identifier="curveForm", obligation=OPTIONAL)
    SplineCurveForm getCurveForm();

    /**
     * Gives the type of knot distribution used in defining this spline.
     * This is for information only and is set according to the different construction-functions.
     */
/// @UML (identifier="knotSpec", obligation=OPTIONAL)
    KnotType getKnotSpec();

    /**
     * {@code true} if this is a polynomial spline.
     */
/// @UML (identifier="isPolynomial", obligation=MANDATORY)
    boolean isPolynomial();
}
