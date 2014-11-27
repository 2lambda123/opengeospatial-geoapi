/*
 *    GeoAPI - Java interfaces for OGC/ISO standards
 *    http://www.geoapi.org
 *
 *    Copyright (C) 2009-2014 Open Geospatial Consortium, Inc.
 *    All Rights Reserved. http://www.opengeospatial.org/ogc/legal
 *
 *    Permission to use, copy, and modify this software and its documentation, with
 *    or without modification, for any purpose and without fee or royalty is hereby
 *    granted, provided that you include the following on ALL copies of the software
 *    and documentation or portions thereof, including modifications, that you make:
 *
 *    1. The full text of this NOTICE in a location viewable to users of the
 *       redistributed or derivative work.
 *    2. Notice of any changes or modifications to the OGC files, including the
 *       date changes were made.
 *
 *    THIS SOFTWARE AND DOCUMENTATION IS PROVIDED "AS IS," AND COPYRIGHT HOLDERS MAKE
 *    NO REPRESENTATIONS OR WARRANTIES, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 *    TO, WARRANTIES OF MERCHANTABILITY OR FITNESS FOR ANY PARTICULAR PURPOSE OR THAT
 *    THE USE OF THE SOFTWARE OR DOCUMENTATION WILL NOT INFRINGE ANY THIRD PARTY
 *    PATENTS, COPYRIGHTS, TRADEMARKS OR OTHER RIGHTS.
 *
 *    COPYRIGHT HOLDERS WILL NOT BE LIABLE FOR ANY DIRECT, INDIRECT, SPECIAL OR
 *    CONSEQUENTIAL DAMAGES ARISING OUT OF ANY USE OF THE SOFTWARE OR DOCUMENTATION.
 *
 *    The name and trademarks of copyright holders may NOT be used in advertising or
 *    publicity pertaining to the software without specific, written prior permission.
 *    Title to copyright in this software and any associated documentation will at all
 *    times remain with copyright holders.
 */
package org.opengis.temporal;

import java.util.Collection;
import java.util.Date;
import javax.measure.unit.Unit;
import org.opengis.metadata.Identifier;
import org.opengis.metadata.extent.Extent;
import org.opengis.util.InternationalString;

/**
 * Factory to create Temporal object.
 *
 * @author Open Geospatial Consortium
 * @author Guilhem Legal (Geomatys)
 * @author Johann Sorel (Geomatys)
 * @author Remi Marechal (Geomatys).
 * @since   2.3
 * @version 4.0
 */
public interface TemporalFactory {
    /**
     * Create {@link Calendar}
     *
     * @param name Name that uniquely identifies the temporal reference system.
     * @param domainOfValidity space and time within which the reference system is applicable.
     */
    Calendar createCalendar(Identifier name, Extent domainOfValidity);

    /**
     * Create {@link Calendar}
     *
     * @param name Name that uniquely identifies the temporal reference system.
     * @param domainOfValidity Space and time within which the reference system is applicable.
     * @param referenceFrame The {@linkplain CalendarEra calendar eras} associated with the calendar being described.
     * @param timeBasis The {@linkplain Clock time basis} that is use with this calendar to define temporal position within a calendar day.
     */
    Calendar createCalendar(Identifier name, Extent domainOfValidity, Collection<CalendarEra> referenceFrame, Clock timeBasis);

    /**
     * Create a {@link Clock} object.
     *
     * @param frame                   The TM_ReferenceSystem associated with this TM_TemporalPosition,
     *                                if not specified, it is assumed to be an association to the Gregorian calendar and UTC.
     * @param indeterminatePosition   Attribute provides the only value for
     *                                TM_TemporalPosition unless a subtype of TM_TemporalPosition is used as the data type.
     * @param calendarEraName         Name of the calendar era to which the date is referenced.
     * @param calendarDate            Sequence of positive integers in which the first
     *                                integer identifies a specific instance of the unit used at the highest level
     *                                of the calendar hierarchy, the second integer identifies a specific instance
     *                                of the unit used at the next lower level in the hierarchy, and so on.
     *                                The format defined in ISO 8601 for dates in the Gregorian calendar may be
     *                                used for any date that is composed of values for year, month and day.
     */
    CalendarDate createCalendarDate(TemporalReferenceSystem frame, IndeterminateValue indeterminatePosition,
            InternationalString calendarEraName, int[] calendarDate);

    /**
     * Create a {@link Clock} object.
     *
     * @param name            Identify the calendar era within this calendar.
     * @param referenceEvent  Provide the name or description of a mythical or
     *                        historic event which fixes the position of the base scale of the calendar era.
     * @param referenceDate   Provide the date of the reference referenceEvent
     *                        expressed as a date in the given calendar. In most calendars, this date is
     *                        the origin (i.e the first day) of the scale, but this is not always true.
     * @param julianReference Provide the Julian date that corresponds to the reference date.
     * @param epochOfUse      Identify the TM_Period for which the calendar era was
     *                        used as a basis for dating, the datatype for TM_Period.begin and Tm_Period.end shall be JulianDate.
     */
    CalendarEra createCalendarEra(InternationalString name, InternationalString referenceEvent,
            CalendarDate referenceDate, JulianDate julianReference, Period epochOfUse);

    /**
     * Create a {@link Clock} object.
     *
     * @param name             Name that uniquely identifies the temporal reference system.
     * @param domainOfValidity The space and time within which the reference system is applicable.
     * @param referenceEvent   Provide the name or description of an event,
     *                         such as solar noon or sunrise.
     * @param referenceTime    Provide the time of day associated with the reference
     *                         event expressed as a time of day in the given clock, the reference time
     *                         is usually the origin of the clock scale.
     * @param utcReference     The 24-hour local or UTC time that corresponds to the reference time.
     */
    Clock createClock(Identifier name, Extent domainOfValidity, InternationalString referenceEvent,
            ClockTime referenceTime, ClockTime utcReference);

    /**
     * Create {@link ClockTime} object.
     *
     * @param frame                 The TM_ReferenceSystem associated with this TM_TemporalPosition,
     *                              if not specified, it is assumed to be an association to the Gregorian calendar and UTC.
     * @param indeterminatePosition The only value for
     *                              TM_TemporalPosition unless a subtype of TM_TemporalPosition is used as the data type.
     * @param clockTime             A sequence of positive numbers with a structure similar to a CalendarDate.
     */
    ClockTime createClockTime(TemporalReferenceSystem frame, IndeterminateValue indeterminatePosition,
            Number[] clockTime);

    /**
     * Create a {@link DateAndTime}.
     *
     * @param frame                 The TM_ReferenceSystem associated with this TM_TemporalPosition,
     *                              if not specified, it is assumed to be an association to the Gregorian calendar and UTC.
     * @param indeterminatePosition Attribute provides the only value for
     *                              TM_TemporalPosition unless a subtype of TM_TemporalPosition is used as the data type.
     * @param calendarEraName       Name of the calendar era to which the date is referenced.
     * @param calendarDate          A sequence of positive integers in which the first
     *                              integer identifies a specific instance of the unit used at the highest level of the calendar hierarchy,
     *                              the second integer identifies a specific instance of the unit used at the next
     *                              lower level in the hierarchy, and so on. The format defined in ISO 8601 for
     *                              dates in the Gregorian calendar may be used for any date that is composed
     *                              of values for year, month and day.
     * @param clockTime             A sequence of positive numbers with a structure similar to a CalendarDate.
     */
    DateAndTime createDateAndTime(TemporalReferenceSystem frame, IndeterminateValue indeterminatePosition,
            InternationalString calendarEraName, int[] calendarDate, Number[] clockTime);

    /**
     * Create an {@link Instant}.
     *
     * @param instant The position of this {@link Instant}, it shall be associated with a single temporal reference system.
     */
    Instant createInstant(Position instant);

    /**
     * Create an {@link IntervalLength}.
     *
     * @param unit   Name of the unit of measure used to express the length of the interval.
     * @param radix  Base of the multiplier of the unit.
     * @param factor Exponent of the base.
     * @param value  Length of the time interval as an integer multiple
     *               of one radix(exp -factor) of the specified unit.
     */
    IntervalLength createIntervalLenght(Unit unit, int radix, int factor, int value);

    /**
     * Create an {@link JulianDate}.
     *
     * @param frame                 The TM_ReferenceSystem associated with this TM_TemporalPosition,
     *                              if not specified, it is assumed to be an association to the Gregorian calendar and UTC.
     * @param indeterminatePosition Attribute provides the only value for
     *                              TM_TemporalPosition unless a subtype of TM_TemporalPosition is used as the data type.
     * @param coordinateValue       The distance from the scale origin expressed
     *                              as a multiple of the standard interval associated with the temporal coordinate system.
     */
    JulianDate createJulianDate(TemporalReferenceSystem frame, IndeterminateValue indeterminatePosition,
            Number coordinateValue);

    /**
     * Create an {@link OrdinalEra}.
     *
     * @param name      String that identifies the ordinal era within the TM_OrdinalReferenceSystem.
     * @param beginning Temporal position at which the ordinal era began, if it is known.
     * @param end       Temporal position at which the ordinal era ended.
     * @param member    {@link OrdinalEra} sequence that subdivide or compose this {@link OrdinalEra}.
     */
    OrdinalEra createOrdinalEra(InternationalString name, Date beginning, Date end,
            Collection<OrdinalEra> member);

    /**
     * Create an {@link OrdinalPosition}.
     *
     * @param frame                 The TM_ReferenceSystem associated with this TM_TemporalPosition,
     *                              if not specified, it is assumed to be an association to the Gregorian calendar and UTC.
     * @param indeterminatePosition Provides the only value for
     *                              TM_TemporalPosition unless a subtype of TM_TemporalPosition is used as the data type.
     * @param ordinalPosition       A reference to the ordinal era in which the instant occurs.
     */
    OrdinalPosition createOrdinalPosition(TemporalReferenceSystem frame,
            IndeterminateValue indeterminatePosition, OrdinalEra ordinalPosition);

    /**
     * Create an {@link OrdinalReferenceSystem}.
     *
     * @param name               Name that uniquely identifies the temporal reference system.
     * @param domainOfValidity   Space and time within which the reference system is applicable.
     * @param ordinalEraSequence An ordinal temporal reference system  consists of a set of ordinal eras.
     */
    OrdinalReferenceSystem createOrdinalReferenceSystem(Identifier name,
            Extent domainOfValidity, Collection<OrdinalEra> ordinalEraSequence);

    /**
     * Create an {@link Period}.
     *
     * @param begin The {@link Instant} at which this Period starts.
     * @param end   The {@link Instant} at which this Period ends.
     */
    Period createPeriod(Instant begin, Instant end);

    /**
     * Create an {@link PeriodDuration}.
     *
     * @param years   Number of years in the period.
     * @param months  Number of months in the period.
     * @param week    Number of week in the period.
     * @param days    Number of days in the period.
     * @param hours   number of hours in the period.
     * @param minutes number of minutes in the period.
     * @param seconds number of seconds in the period.
     */
    PeriodDuration createPeriodDuration(InternationalString years, InternationalString months,
            InternationalString week, InternationalString days, InternationalString hours,
            InternationalString minutes, InternationalString seconds);

    /**
     * Create an {@link Position}.
     *
     * @param position One of the data types listed as:
     *                 Date, Time, DateTime, and TemporalPosition with its subtypes
     */
    Position createPosition(Date position);

    /**
     * Create an {@link TemporalCoordinate}.
     *
     * @param frame                 The {@link TemporalReferenceSystem} associated with this {@link TemporalCoordinate},
     *                              if not specified, it is assumed to be an association to the Gregorian calendar and UTC.
     * @param indeterminatePosition Provides the only value for
     *                              {@link TemporalCoordinate} unless a subtype of {@link TemporalCoordinate} is used as the data type.
     * @param coordinateValue       Distance from the scale origin expressed
     *                              as a multiple of the standard interval associated with the temporal coordinate system.
     */
    TemporalCoordinate createTemporalCoordinate(TemporalReferenceSystem frame,
            IndeterminateValue indeterminatePosition, Number coordinateValue);

    /**
     * Create a {@link TemporalCoordinateSystem}.
     *
     * @param name             Name that uniquely identifies the temporal reference system.
     * @param domainOfValidity The space and time within which the reference coordinate system is applicable.
     * @param origin           The origin of the scale, it must be specified in the Gregorian
     *                         calendar with time of day in UTC.
     * @param interval         The name of a single unit of measure used as the base interval for the scale.
     *                         it shall be one of those units of measure for time specified by ISO 31-1,
     *                         or a multiple of one of those units, as specified by ISO 1000.
     */
    TemporalCoordinateSystem createTemporalCoordinateSystem(Identifier name,
            Extent domainOfValidity, Date origin, Unit<javax.measure.quantity.Duration> interval);

    /**
     * Create a {@link TemporalPosition}.
     *
     * @param frame                 The TM_ReferenceSystem associated with this TM_TemporalPosition,
     *                              if not specified, it is assumed to be an association to the Gregorian calendar and UTC.
     * @param indeterminatePosition The only value for
     *                              TM_TemporalPosition unless a subtype of TM_TemporalPosition is used as the data type.
     */
    TemporalPosition createTemporalPosition(TemporalReferenceSystem frame,
            IndeterminateValue indeterminatePosition);

    /**
     * Create a {@link TemporalReferenceSystem}.
     *
     * @param name             Name that uniquely identifies the temporal reference system.
     * @param domainOfValidity The space and time within which the reference system is applicable.
     */
    TemporalReferenceSystem createTemporalReferenceSystem(Identifier name,
            Extent domainOfValidity);
}