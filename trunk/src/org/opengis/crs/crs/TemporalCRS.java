/*
 * Copyright (C) 2003 Open GIS Consortium, Inc. All Rights Reserved. http://www.opengis.org/Legal/
 */
package org.opengis.crs.crs;

// OpenGIS direct dependencies
import org.opengis.crs.cs.TemporalCS;
import org.opengis.crs.datum.TemporalDatum;


/**
 * A 1D coordinate reference system used for the recording of time.
 *
 * <TABLE CELLPADDING='6' BORDER='1'>
 * <TR BGCOLOR="#EEEEFF"><TH NOWRAP>Used with CS type(s)</TH></TR>
 * <TR><TD>
 *   {@link org.opengis.crs.cs.TemporalCS Temporal}
 * </TD></TR></TABLE>
 *
 * @UML abstract SC_TemporalCRS
 * @author ISO 19111
 * @author <A HREF="http://www.opengis.org">OpenGIS&reg; consortium</A>
 * @version 2.0
 */
public interface TemporalCRS extends CoordinateReferenceSystem {
    /**
     * Returns the coordinate system, which must be temporal.
     *
     * @return The coordinate system.
     * @UML association usesCS
     */
/// public TemporalCS getCoordinateSystem();

    /**
     * Returns the datum, which must be temporal.
     *
     * @return The datum.
     * @UML association usesDatum
     */
/// public TemporalDatum getDatum();
}
