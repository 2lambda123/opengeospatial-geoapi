/*
 * Copyright (C) 2003 Open GIS Consortium, Inc. All Rights Reserved. http://www.opengis.org/Legal/
 */
package org.opengis.sc;

// OpenGIS direct dependencies
import org.opengis.cc.Conversion;


/**
 * A coordinate reference system that is defined by its coordinate
 * {@linkplain Conversion conversion} from another coordinate reference system
 * (not by a {@linkplain org.opengis.cd.Datum datum}).
 *
 * @UML abstract SC_GeneralDerivedCRS
 * @author ISO 19111
 * @author <A HREF="http://www.opengis.org">OpenGIS&reg; consortium</A>
 * @version 2.0
 */
public interface GeneralDerivedCRS extends CoordinateReferenceSystem {
    /**
     * Returns the base coordinate reference system.
     *
     * @return The base coordinate reference system.
     * @UML association baseCRS
     */
    public CoordinateReferenceSystem getBaseCRS();

    /**
     * Returns the conversion from the {@linkplain #getBaseCRS base CRS} to
     * this CRS.
     *
     * @return The conversion to this CRS.
     * @UML association definedByConversion
     *
     * @revisit <code>definedByConversion</code> may be a precise description of the association,
     *          but is confusing as a method name. OGC document 01-009 used <code>toBase()</code>
     *          name. For this specification, waybe we should use <code>fromBase()</code> method
     *          name.
     */
    public Conversion getDefinedByConversion();
}
