/*
 *    GeoAPI - Java interfaces for OGC/ISO standards
 *    http://www.geoapi.org
 *
 *    This file is hereby placed into the Public Domain.
 *    This means anyone is free to do whatever they wish with this file.
 */
package org.opengis.example.referencing;

import javax.measure.unit.Unit;
import javax.measure.unit.SI;
import javax.measure.unit.NonSI;
import javax.measure.converter.ConversionException;

import org.opengis.metadata.citation.Citation;
import org.opengis.example.metadata.SimpleCitation;
import org.opengis.referencing.cs.AxisDirection;
import org.opengis.referencing.cs.CoordinateSystemAxis;
import org.opengis.referencing.cs.RangeMeaning;


/**
 * A {@link CoordinateSystemAxis}. This implementation infers some axis properties from the
 * {@linkplain #abbreviation}, as documented in the following table:
 *
 * <blockquote><table border="1" cellspacing="0" cellpadding="0">
 * <tr><th>Symbol</th>   <th>Common name</th>                  <th>Axis&nbsp;direction</th>                                         <th>Unit</th>                             <th>Range</th>                    <th>Meaning</th></tr>
 * <tr><th>&lambda;</th> <td>&nbsp;geodetic longitude</td>     <td>&nbsp;{@link AxisDirection#EAST            EAST}</td>            <td>&nbsp;{@link NonSI#DEGREE_ANGLE}</td> <td>&nbsp;&plusmn;90°</td>        <td>&nbsp;{@link RangeMeaning#EXACT      EXACT}</td></tr>
 * <tr><th>&phi;</th>    <td>&nbsp;geodetic latitude</td>      <td>&nbsp;{@link AxisDirection#NORTH           NORTH}</td>           <td>&nbsp;{@link NonSI#DEGREE_ANGLE}</td> <td>&nbsp;&plusmn;180°</td>       <td>&nbsp;{@link RangeMeaning#WRAPAROUND WRAPAROUND}</td></tr>
 * <tr><th>h</th>        <td>&nbsp;ellipsoidal height</td>     <td>&nbsp;{@link AxisDirection#UP              UP}</td>              <td>&nbsp;{@link SI#METRE}</td>           <td>&nbsp;</td>                   <td>&nbsp;</td></tr>
 * <tr><th>H</th>        <td>&nbsp;gravity-related height</td> <td>&nbsp;{@link AxisDirection#UP              UP}</td>              <td>&nbsp;{@link SI#METRE}</td>           <td>&nbsp;</td>                   <td>&nbsp;</td></tr>
 * <tr><th>d</th>        <td>&nbsp;depth</td>                  <td>&nbsp;{@link AxisDirection#DOWN            DOWN}</td>            <td>&nbsp;{@link SI#METRE}</td>           <td>&nbsp;</td>                   <td>&nbsp;</td></tr>
 * <tr><th>r</th>        <td>&nbsp;geocentric radius</td>      <td>&nbsp;{@link AxisDirection#UP              UP}</td>              <td>&nbsp;{@link SI#METRE}</td>           <td>&nbsp;[0&hellip;&infin;]</td> <td>&nbsp;</td></tr>
 * <tr><th>&Omega;</th>  <td>&nbsp;spherical longitude</td>    <td>&nbsp;{@link AxisDirection#EAST            EAST}</td>            <td>&nbsp;{@link NonSI#DEGREE_ANGLE}</td> <td>&nbsp;&plusmn;90°</td>        <td>&nbsp;{@link RangeMeaning#EXACT      EXACT}</td></tr>
 * <tr><th>&Theta;</th>  <td>&nbsp;spherical latitude</td>     <td>&nbsp;{@link AxisDirection#NORTH           NORTH}</td>           <td>&nbsp;{@link NonSI#DEGREE_ANGLE}</td> <td>&nbsp;&plusmn;180°</td>       <td>&nbsp;{@link RangeMeaning#WRAPAROUND WRAPAROUND}</td></tr>
 * <tr><th>X</th>        <td>&nbsp;geocentric X</td>           <td>&nbsp;{@link AxisDirection#GEOCENTRIC_X    GEOCENTRIC_X}</td>    <td>&nbsp;{@link SI#METRE}</td>           <td>&nbsp;</td>                   <td>&nbsp;</td></tr>
 * <tr><th>Y</th>        <td>&nbsp;geocentric Y</td>           <td>&nbsp;{@link AxisDirection#GEOCENTRIC_Y    GEOCENTRIC_Y}</td>    <td>&nbsp;{@link SI#METRE}</td>           <td>&nbsp;</td>                   <td>&nbsp;</td></tr>
 * <tr><th>Z</th>        <td>&nbsp;geocentric Z</td>           <td>&nbsp;{@link AxisDirection#GEOCENTRIC_Z    GEOCENTRIC_Z}</td>    <td>&nbsp;{@link SI#METRE}</td>           <td>&nbsp;</td>                   <td>&nbsp;</td></tr>
 * <tr><th>E</th>        <td>&nbsp;easting</td>                <td>&nbsp;{@link AxisDirection#EAST            EAST}</td>            <td>&nbsp;{@link SI#METRE}</td>           <td>&nbsp;</td>                   <td>&nbsp;</td></tr>
 * <tr><th>W</th>        <td>&nbsp;westing</td>                <td>&nbsp;{@link AxisDirection#WEST            WEST}</td>            <td>&nbsp;{@link SI#METRE}</td>           <td>&nbsp;</td>                   <td>&nbsp;</td></tr>
 * <tr><th>N</th>        <td>&nbsp;northing</td>               <td>&nbsp;{@link AxisDirection#NORTH           NORTH}</td>           <td>&nbsp;{@link SI#METRE}</td>           <td>&nbsp;</td>                   <td>&nbsp;</td></tr>
 * <tr><th>S</th>        <td>&nbsp;southing</td>               <td>&nbsp;{@link AxisDirection#SOUTH           SOUTH}</td>           <td>&nbsp;{@link SI#METRE}</td>           <td>&nbsp;</td>                   <td>&nbsp;</td></tr>
 * <tr><th>t</th>        <td>&nbsp;time</td>                   <td>&nbsp;{@link AxisDirection#FUTURE          FUTURE}</td>          <td>&nbsp;{@link SI#SECOND}</td>          <td>&nbsp;</td>                   <td>&nbsp;</td></tr>
 * <tr><th>i</th>        <td>&nbsp;column</td>                 <td>&nbsp;{@link AxisDirection#COLUMN_POSITIVE COLUMN_POSITIVE}</td> <td>&nbsp;{@link Unit#ONE}</td>           <td>&nbsp;</td>                   <td>&nbsp;</td></tr>
 * <tr><th>j</th>        <td>&nbsp;row</td>                    <td>&nbsp;{@link AxisDirection#ROW_POSITIVE    ROW_POSITIVE}</td>    <td>&nbsp;{@link Unit#ONE}</td>           <td>&nbsp;</td>                   <td>&nbsp;</td></tr>
 * </table></blockquote>
 *
 * @author  Martin Desruisseaux (Geomatys)
 * @version 3.1
 * @since   3.1
 */
public class SimpleAxis extends SimpleIdentifiedObject implements CoordinateSystemAxis {
    /**
     * For cross-version compatibility.
     */
    private static final long serialVersionUID = 3541182320484949668L;

    /**
     * The <cite>geodetic latitude</cite> axis.
     * Values are increasing toward East, in decimal degrees.
     */
    public static final CoordinateSystemAxis LATITUDE = new SimpleAxis(SimpleCitation.EPSG, "geodetic latitude", 'φ');

    /**
     * The <cite>geodetic longitude</cite> axis.
     * Values are increasing toward North, in decimal degrees.
     */
    public static final CoordinateSystemAxis LONGITUDE = new SimpleAxis(SimpleCitation.EPSG, "geodetic longitude", 'λ');

    /**
     * The abbreviation used for this coordinate system axes. This abbreviation is also
     * used to identify the ordinates in coordinate tuple. Examples are "<var>X</var>"
     * and "<var>Y</var>".
     *
     * @see #getAbbreviation()
     */
    protected final char abbreviation;

    /**
     * Direction of this coordinate system axis.
     *
     * @see #getDirection()
     */
    protected final AxisDirection direction;

    /**
     * The unit of measure used for this coordinate system axis.
     *
     * @see #getUnit()
     */
    protected final Unit<?> unit;

    /**
     * Creates a new axis for the given authority, name and abbreviation. The axis direction
     * and units are inferred from the abbreviation using the table documented in the class
     * javadoc.
     *
     * @param  authority    Organization responsible for definition of the name, or {@code null}.
     * @param  name         The name of the new axis.
     * @param  abbreviation The abbreviation used for this coordinate system axes.
     * @throws IllegalArgumentException If the abbreviation is not one of the documented ones.
     */
    public SimpleAxis(final Citation authority, final String name, final char abbreviation)
            throws IllegalArgumentException
    {
        super(authority, name);
        this.abbreviation = abbreviation;
        switch (abbreviation) {
            case 'X':                               direction = AxisDirection.GEOCENTRIC_X;    break;
            case 'Y':                               direction = AxisDirection.GEOCENTRIC_Y;    break;
            case 'Z':                               direction = AxisDirection.GEOCENTRIC_Z;    break;
            case 'E': case 'x': case 'λ': case 'Ω': direction = AxisDirection.EAST;            break;
            case 'N': case 'y': case 'φ': case 'Θ': direction = AxisDirection.NORTH;           break;
            case 'H': case 'z': case 'h': case 'r': direction = AxisDirection.UP;              break;
            case 'W':                               direction = AxisDirection.WEST;            break;
            case 'S':                               direction = AxisDirection.SOUTH;           break;
            case 'd':                               direction = AxisDirection.DOWN;            break;
            case 't':                               direction = AxisDirection.FUTURE;          break;
            case 'i':                               direction = AxisDirection.COLUMN_POSITIVE; break;
            case 'j':                               direction = AxisDirection.ROW_POSITIVE;    break;
            default: throw new IllegalArgumentException("Unknown abbreviation: " + abbreviation);
        }
        switch (abbreviation) {
            case 'λ': case 'Ω':
            case 'φ': case 'Θ': unit = NonSI.DEGREE_ANGLE; break;
            case 'i': case 'j': unit =  Unit.ONE;          break;
            case 't':           unit =    SI.SECOND;       break;
            default:            unit =    SI.METRE;        break;
        }
    }

    /**
     * Creates a new axis for the given authority, name, units and direction.
     * The following abbreviation are handled specially by this class:
     * <p>
     * <ul>
     *   <li>&lambda; for geodetic  longitude</li>
     *   <li>&phi;    for geodetic  latitude</li>
     *   <li>&Omega;  for spherical longitude</li>
     *   <li>&Theta;  for spherical latitude</li>
     *   <li><var>r</var> for geocentric radius</li>
     * </ul>
     *
     * @param authority    Organization responsible for definition of the name, or {@code null}.
     * @param name         The name of the new axis.
     * @param abbreviation The abbreviation used for this coordinate system axes.
     * @param direction    Direction of this coordinate system axis.
     * @param unit         The unit of measure used for this coordinate system axis.
     */
    public SimpleAxis(final Citation authority, final String name, final char abbreviation,
            final AxisDirection direction, final Unit<?> unit)
    {
        super(authority, name);
        Objects.requireNonNull(direction);
        Objects.requireNonNull(unit);
        this.abbreviation = abbreviation;
        this.direction    = direction;
        this.unit         = unit;
    }

    /**
     * The abbreviation used for this coordinate system axes. This abbreviation is also
     * used to identify the ordinates in coordinate tuple. Examples are "<var>X</var>"
     * and "<var>Y</var>".
     */
    @Override
    public String getAbbreviation() {
        return String.valueOf(abbreviation);
    }

    /**
     * Direction of this coordinate system axis.
     */
    @Override
    public AxisDirection getDirection() {
        return direction;
    }

    /**
     * Returns the minimum value normally allowed for this axis, in the {@linkplain #getUnit unit
     * of measure for the axis}. The default implementation infers the value from the abbreviation
     * symbol:
     * <p>
     * <ul>
     *   <li>0 for <var>r</var></li>
     *   <li>-90 for &phi; and &Theta;</li>
     *   <li>-180 for &lambda; and &Omega;</li>
     *   <li>{@link Double#NEGATIVE_INFINITY NEGATIVE_INFINITY} for all other abbreviations</li>
     * </ul>
     */
    @Override
    public double getMinimumValue() {
        switch (abbreviation) {
            case 'r':           return 0;
            case 'φ': case 'Θ': return toAngularUnit( -90);
            case 'λ': case 'Ω': return toAngularUnit(-180);
        }
        return Double.NEGATIVE_INFINITY;
    }

    /**
     * Returns the maximum value normally allowed for this axis, in the {@linkplain #getUnit unit
     * of measure for the axis}. The default implementation infers the value from the abbreviation
     * symbol:
     * <p>
     * <ul>
     *   <li>90 for &phi; and &Theta;</li>
     *   <li>180 for &lambda; and &Omega;</li>
     *   <li>{@link Double#POSITIVE_INFINITY POSITIVE_INFINITY} for all other abbreviations</li>
     * </ul>
     */
    @Override
    public double getMaximumValue() {
        switch (abbreviation) {
            case 'φ': case 'Θ': return toAngularUnit( 90);
            case 'λ': case 'Ω': return toAngularUnit(180);
        }
        return Double.POSITIVE_INFINITY;
    }

    /**
     * Returns the meaning of axis value range specified by the {@linkplain #getMinimumValue
     * minimum} and {@linkplain #getMaximumValue maximum} values. The default implementation
     * infers the value from the abbreviation symbol:
     * <p>
     * <ul>
     *   <li>{@link RangeMeaning#EXACT EXACT} for &phi; and &Theta;</li>
     *   <li>{@link RangeMeaning#WRAPAROUND WRAPAROUND} for &lambda; and &Omega;</li>
     *   <li>{@code null} for all other abbreviations</li>
     * </ul>
     */
    @Override
    public RangeMeaning getRangeMeaning() {
        switch (abbreviation) {
            case 'φ': case 'Θ': return RangeMeaning.EXACT;
            case 'λ': case 'Ω': return RangeMeaning.WRAPAROUND;
        }
        return null;
    }

    /**
     * The unit of measure used for this coordinate system axis.
     */
    @Override
    public Unit<?> getUnit() {
        return unit;
    }

    /**
     * Converts the given angle from degrees to the angular units used by this axis.
     *
     * @param  angle The angle to convert.
     * @return The converted angle.
     * @throws IllegalStateException If this axis doesn't use angular units.
     */
    private double toAngularUnit(final double angle) throws IllegalStateException {
        try {
            return NonSI.DEGREE_ANGLE.getConverterToAny(unit).convert(angle);
        } catch (ConversionException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Compares this axis with the given object for equality.
     *
     * @param  object The object to compare with this {@code SimpleCRS}.
     * @return {@code true} if the given object is equals to this object.
     */
    @Override
    public boolean equals(final Object object) {
        if (super.equals(object)) {
            final SimpleAxis other = (SimpleAxis) object;
            return abbreviation == other.abbreviation &&
                   direction.equals(other.direction) &&
                   unit.equals(other.unit);
        }
        return false;
    }
}