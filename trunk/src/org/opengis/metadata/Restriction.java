/*$************************************************************************************************
 **
 ** $Id$
 **
 ** $Source$
 **
 ** Copyright (C) 2003 Open GIS Consortium, Inc. All Rights Reserved. http://www.opengis.org/Legal/
 **
 *************************************************************************************************/
package org.opengis.metadata;

// J2SE directdependencies
import java.util.List;
import java.util.ArrayList;

// OpenGIS direct dependencies
import org.opengis.util.CodeList;


/**
 * Limitation(s) placed upon the access or use of the data.
 *
 * @UML codelist MD_RestrictionCode
 * @author ISO 19115
 * @author <A HREF="http://www.opengis.org">OpenGIS&reg; consortium</A>
 * @version <A HREF="http://www.opengis.org/docs/01-111.pdf">Abstract specification 5.0</A>
 */
public final class Restriction extends CodeList {
    /**
     * Serial number for compatibility with different versions.
     */
    private static final long serialVersionUID = 7949159742645339894L;

    /**
     * List of all enumerations of this type.
     * Must be declared before any enum declaration.
     */
    private static final List VALUES = new ArrayList(8);

    /**
     * Exclusive right to the publication, production, or sale of the rights to a literary,
     * dramatic, musical, or artistic work, or to the use of a commercial print or label,
     * granted by law for a specified period of time to an author, composer, artist, distributor.
     *
     * @UML conditional copyright
     */
    public static final Restriction COPYRIGHT = new Restriction("COPYRIGHT");

    /**
     * Government has granted exclusive right to make, sell, use or license an invention
     * or discovery.
     *
     * @UML conditional patent
     */
    public static final Restriction PATENT = new Restriction("PATENT");

    /**
     * Produced or sold information awaiting a patent.
     *
     * @UML conditional patentPending
     */
    public static final Restriction PATENT_PENDING = new Restriction("PATENT_PENDING");

    /**
     * A name, symbol, or other device identifying a product, officially registered and
     * legally restricted to the use of the owner or manufacturer.
     *
     * @UML conditional trademark
     */
    public static final Restriction TRADEMARK = new Restriction("TRADEMARK");

    /**
     * Formal permission to do something.
     *
     * @UML conditional license
     */
    public static final Restriction LICENSE = new Restriction("LICENSE");

    /**
     * Rights to financial benefit from and control of distribution of non-tangible property
     * that is a result of creativity.
     *
     * @UML conditional intellectualPropertyRights
     */
    public static final Restriction INTELLECTUAL_PROPERTY_RIGHTS = new Restriction("INTELLECTUAL_PROPERTY_RIGHTS");

    /**
     * Withheld from general circulation or disclosure.
     *
     * @UML conditional restricted
     */
    public static final Restriction RESTRICTED = new Restriction("RESTRICTED");

    /**
     * Limitation not listed.
     *
     * @UML conditional otherRestictions
     */
    public static final Restriction OTHER_RESTRICTIONS = new Restriction("OTHER_RESTRICTIONS");

    /**
     * Constructs an enum with the given name. The new enum is
     * automatically added to the list returned by {@link #values}.
     *
     * @param name The enum name. This name must not be in use by an other enum of this type.
     */
    public Restriction(final String name) {
        super(name, VALUES);
    }

    /**
     * Returns the list of <code>Restriction</code>s.
     */
    public static Restriction[] values() {
        synchronized (VALUES) {
            return (Restriction[]) VALUES.toArray(new Restriction[VALUES.size()]);
        }
    }

    /**
     * Returns the list of enumerations of the same kind than this enum.
     */
    public /*{Restriction}*/ CodeList[] family() {
        return values();
    }
}
