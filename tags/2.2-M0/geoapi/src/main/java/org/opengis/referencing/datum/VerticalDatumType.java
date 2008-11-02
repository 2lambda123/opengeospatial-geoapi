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
package org.opengis.referencing.datum;

import java.util.List;
import java.util.ArrayList;
import org.opengis.util.CodeList;
import org.opengis.annotation.UML;

import static org.opengis.annotation.Obligation.*;
import static org.opengis.annotation.Specification.*;


/**
 * Type of a vertical datum.
 *
 * @version <A HREF="http://portal.opengeospatial.org/files/?artifact_id=6716">Abstract specification 2.0</A>
 * @author ISO/DIS 19111
 * @author Martin Desruisseaux (IRD)
 * @since GeoAPI 1.0
 */
@UML(identifier="CD_VerticalDatumType", specification=ISO_19111)
public final class VerticalDatumType extends CodeList<VerticalDatumType> {
    /**
     * Serial number for compatibility with different versions.
     */
    private static final long serialVersionUID = -8161084528823937553L;

    /**
     * List of all enumerations of this type.
     * Must be declared before any enum declaration.
     */
    private static final List<VerticalDatumType> VALUES = new ArrayList<VerticalDatumType>(6);

    /**
     * In some cases, e.g. oil exploration and production, a geological feature, such as the top
     * or bottom of a geologically identifiable and meaningful subsurface layer, is used as a
     * vertical datum. Other variations to the above three vertical datum types may exist
     * and are all included in this type.
     */
    @UML(identifier="other surface", obligation=CONDITIONAL, specification=ISO_19111)
    public static final VerticalDatumType OTHER_SURFACE = new VerticalDatumType("OTHER_SURFACE");

    /**
     * The zero value of the associated vertical coordinate system axis is defined to approximate
     * a constant potential surface, usually the geoid. Such a reference surface is usually
     * determined by a national or scientific authority, and is then a well-known, named datum.
     */
    @UML(identifier="geoidal", obligation=CONDITIONAL, specification=ISO_19111)
    public static final VerticalDatumType GEOIDAL = new VerticalDatumType("GEOIDAL");

    /**
     * A vertical datum for ellipsoidal heights that are measured along the
     * normal to the ellipsoid used in the definition of horizontal datum.
     */
    @UML(identifier="CS_DatumType.CS_VD_Ellipsoidal", obligation=CONDITIONAL, specification=OGC_01009)
    public static final VerticalDatumType ELLIPSOIDAL = new VerticalDatumType("ELLIPSOIDAL");

    /**
     * The zero point of the vertical axis is defined by a surface that has meaning for the
     * purpose which the associated vertical measurements are used for. For hydrographic charts,
     * this is often a predicted nominal sea surface (i.e., without waves or other wind and current
     * effects) that occurs at low tide. Depths are measured in the direction perpendicular
     * (approximately) to the actual equipotential surfaces of the earth's gravity field,
     * using such procedures as echo-sounding.
     */
    @UML(identifier="depth", obligation=CONDITIONAL, specification=ISO_19111)
    public static final VerticalDatumType DEPTH = new VerticalDatumType("DEPTH");

    /**
     * Atmospheric pressure is the basis for the definition of the origin of the
     * associated vertical coordinate system axis. These are approximations of
     * orthometric heights obtained with the help of a barometer or a barometric
     * altimeter. These values are usually expressed in one of the following units:
     * meters, feet, millibars (used to measure pressure levels), or theta value
     * (units used to measure geopotential height).
     */
    @UML(identifier="barometric", obligation=CONDITIONAL, specification=ISO_19111)
    public static final VerticalDatumType BAROMETRIC = new VerticalDatumType("BAROMETRIC");

    /**
     * A vertical datum for orthometric heights that are measured along the plumb line.
     */
    @UML(identifier="CS_DatumType.CS_VD_Orthometric", obligation=CONDITIONAL, specification=OGC_01009)
    public static final VerticalDatumType ORTHOMETRIC = new VerticalDatumType("ORTHOMETRIC");

    /**
     * Constructs an enum with the given name. The new enum is
     * automatically added to the list returned by {@link #values}.
     *
     * @param name The enum name. This name must not be in use by an other enum of this type.
     */
    private VerticalDatumType(final String name) {
        super(name, VALUES);
    }

    /**
     * Returns the list of {@code VerticalDatumType}s.
     */
    public static VerticalDatumType[] values() {
        synchronized (VALUES) {
            return VALUES.toArray(new VerticalDatumType[VALUES.size()]);
        }
    }

    /**
     * Returns the list of enumerations of the same kind than this enum.
     */
    public VerticalDatumType[] family() {
        return values();
    }

    /**
     * Returns the vertical datum type that matches the given string, or returns a
     * new one if none match it.
     */
    public static VerticalDatumType valueOf(String code) {
        return valueOf(VerticalDatumType.class, code);
    }
}