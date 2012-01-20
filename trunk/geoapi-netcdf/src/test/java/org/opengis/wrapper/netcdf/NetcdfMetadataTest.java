/*
 *    GeoAPI - Java interfaces for OGC/ISO standards
 *    http://www.geoapi.org
 *
 *    This file is hereby placed into the Public Domain.
 *    This means anyone is free to do whatever they wish with this file.
 *
 *    The NetCDF wrappers are provided as code examples, in the hope to facilitate
 *    GeoAPI implementations backed by other libraries. Implementors can take this
 *    source code and use it for any purpose, commercial or non-commercial, copyrighted
 *    or open-source, with no legal obligation to acknowledge the borrowing/copying
 *    in any way.
 */
package org.opengis.wrapper.netcdf;

import java.io.IOException;
import ucar.nc2.NetcdfFile;

import org.opengis.metadata.*;
import org.opengis.metadata.extent.*;
import org.opengis.metadata.spatial.*;
import org.opengis.metadata.citation.*;
import org.opengis.metadata.identification.*;
import org.opengis.test.metadata.MainValidator;

import org.junit.Test;

import static org.opengis.test.Assert.*;


/**
 * Tests the {@link NetcdfMetadata} class.
 * <p>
 * External projects can override the {@link #wrap(NetcdfFile)}
 * method in order to test their own NetCDF wrapper.
 *
 * @author  Martin Desruisseaux (Geomatys)
 * @version 3.1
 * @since   3.1
 */
public strictfp class NetcdfMetadataTest extends IOTestCase {
    /**
     * Tolerance factor for floating point comparison.
     * We actually require an exact match.
     */
    private static final double EPS = 0;

    /**
     * The validator to use for validating the {@link Metadata} instance.
     * This validator is specified at construction time.
     */
    protected final MainValidator validator;

    /**
     * Creates a new test case using the default validator.
     * This constructor sets the {@link MainValidator#requireMandatoryAttributes} field
     * to {@code false}, since NetCDF metadata are sometime incomplete.
     */
    public NetcdfMetadataTest() {
        this(new MainValidator(org.opengis.test.Validators.DEFAULT));
        validator.requireMandatoryAttributes = false;
    }

    /**
     * Creates a new test case using the given validator. This constructor is provided for
     * subclasses wanting to use different validation methods. It is caller responsibility
     * to configure the given validator (for example whatever to
     * {@linkplain MainValidator#requireMandatoryAttributes require mandatory attributes}).
     *
     * @param validator The validator to use for validating the {@link Metadata} instance.
     */
    protected NetcdfMetadataTest(final MainValidator validator) {
        this.validator = validator;
    }

    /**
     * Wraps the given NetCDF file into a GeoAPI Metadata object. The default implementation
     * creates a {@link NetcdfMetadata} instance. Subclasses can override this method for
     * creating their own instance.
     *
     * @param  file The NetCDF file to wrap.
     * @return A metadata implementation created from the attributes found in the given file.
     * @throws IOException If an error occurred while wrapping the given NetCDF file.
     */
    protected Metadata wrap(final NetcdfFile file) throws IOException {
        return new NetcdfMetadata(file);
    }

    /**
     * Tests the {@value org.opengis.wrapper.netcdf.IOTestCase#THREDDS} file (XML format).
     * The most relevant parts of this file are as below:
     *
     *<blockquote><pre>&lt;netcdf&gt;
     *  &lt;!-- Metadata from the NetCDF or NcML file global attributes --&gt;
     *  &lt;attribute name="Conventions" value="CF-1.4"/&gt;
     *  &lt;attribute name="title" value="crm_v1.grd"/&gt;
     *  &lt;attribute name="history" value="xyz2grd -R-80/-64/40/48 -I3c -Gcrm_v1.grd"/&gt;
     *  &lt;attribute name="GMT_version" value="4.5.1 [64-bit]"/&gt;
     *  &lt;attribute name="creator_name" value="David Neufeld"/&gt;
     *  &lt;attribute name="creator_email" value="xxxxx.xxxxxxx@noaa.gov"/&gt;
     *  &lt;attribute name="geospatial_lon_units" value="degrees_east"/&gt;
     *  &lt;attribute name="geospatial_lat_units" value="degrees_north"/&gt;
     *  &lt;attribute name="geospatial_lon_min" type="float" value="-80.0"/&gt;
     *  &lt;attribute name="geospatial_lon_max" type="float" value="-64.0"/&gt;
     *  &lt;attribute name="geospatial_lat_max" type="float" value="48.0"/&gt;
     *  &lt;attribute name="geospatial_lat_min" type="float" value="40.0"/&gt;
     *  &lt;attribute name="geospatial_lon_resolution" type="double" value="8.33E-4"/&gt;
     *  &lt;attribute name="geospatial_lat_resolution" type="double" value="8.33E-4"/&gt;
     *
     *  &lt;dimension name="x" length="19201"/&gt;
     *  &lt;dimension name="y" length="9601"/&gt;
     *
     *  &lt;variable name="z" shape="y x" type="float"&gt;
     *    &lt;attribute name="long_name" value="z"/&gt;
     *    &lt;attribute name="_FillValue" type="float" value="NaN"/&gt;
     *    &lt;attribute name="actual_range" type="double" value="-2754.39990234375 1903.0"/&gt;
     *    &lt;attribute name="units" value="meters"/&gt;
     *    &lt;attribute name="positive" value="up"/&gt;
     *  &lt;/variable&gt;
     *  &lt;variable name="x" shape="x" type="double"&gt;
     *    &lt;attribute name="long_name" value="x"/&gt;
     *    &lt;attribute name="actual_range" type="double" value="-80.0 -64.0"/&gt;
     *    &lt;attribute name="units" value="degrees_east"/&gt;
     *    &lt;attribute name="_CoordinateAxisType" value="Lon"/&gt;
     *  &lt;/variable&gt;
     *  &lt;variable name="y" shape="y" type="double"&gt;
     *    &lt;attribute name="long_name" value="y"/&gt;
     *    &lt;attribute name="actual_range" type="double" value="40.0 48.0"/&gt;
     *    &lt;attribute name="units" value="degrees_north"/&gt;
     *    &lt;attribute name="_CoordinateAxisType" value="Lat"/&gt;
     *  &lt;/variable&gt;
     *&lt;/netcdf&gt;</pre></blockquote>
     *
     * Some additional THREDDS attributes are defined, but are not the subject of this test.
     * The full file can be seen from the <a href="{@svnurl netcdf}/thredds.ncml">source code
     * repository</a>.
     *
     * @throws IOException If the test file can not be read.
     */
    @Test
    public void testThredds() throws IOException {
        final NetcdfFile file = open(THREDDS);
        try {
            final Metadata metadata = wrap(file);
            validator.validate(metadata);
            /*
            * Responsibly party.
            */
            final ResponsibleParty party = getSingleton(metadata.getContacts());
            assertEquals("David Neufeld", party.getIndividualName());
            assertEquals("xxxxx.xxxxxxx@noaa.gov", getSingleton(party.getContactInfo().getAddress().getElectronicMailAddresses()));
            /*
            * Metadata / Data Identification / Geographic Bounding Box.
            */
            final DataIdentification identification = (DataIdentification) getSingleton(metadata.getIdentificationInfo());
            final GeographicBoundingBox bbox = (GeographicBoundingBox) getSingleton(getSingleton(identification.getExtents()).getGeographicElements());
            assertEquals("West Bound Longitude", -80, bbox.getWestBoundLongitude(), EPS);
            assertEquals("East Bound Longitude", -64, bbox.getEastBoundLongitude(), EPS);
            assertEquals("South Bound Latitude",  40, bbox.getSouthBoundLatitude(), EPS);
            assertEquals("North Bound Latitude",  48, bbox.getNorthBoundLatitude(), EPS);
        } finally {
            file.close();
        }
    }

    /**
     * Tests the {@value org.opengis.wrapper.netcdf.IOTestCase#GEOTIME_NC} file (binary format).
     * The global attributes are:
     *
     * <blockquote><pre>:record = "reftime, valtime" ;
     *:history = "2003-04-07 12:12:50 - created by gribtocdl 2005-09-26T21:50:00 - edavis - add attributes for dataset discovery" ;
     *:title = "Sea Surface Temperature Analysis Model" ;
     *:Conventions = "NUWG, _Coordinates" ;
     *:GRIB_reference = "Office Note 388 GRIB" ;
     *:GRIB_URL = "http://www.nco.ncep.noaa.gov/pmb/docs/on388/" ;
     *:version = 1. ;
     *:Metadata_Conventions = "Unidata Dataset Discovery v1.0" ;
     *:summary = "NCEP SST Global 5.0 x 2.5 degree model data" ;
     *:keywords = "EARTH SCIENCE > Oceans > Ocean Temperature > Sea Surface Temperature" ;
     *:keywords_vocabulary = "GCMD Science Keywords" ;
     *:id = "NCEP/SST/Global_5x2p5deg/SST_Global_5x2p5deg_20050922_0000.nc" ;
     *:naming_authority = "edu.ucar.unidata" ;
     *:cdm_data_type = "Grid" ;
     *:date_created = "2005-09-22T00:00" ;
     *:creator_name = "NOAA/NWS/NCEP" ;
     *:creator_url = "" ;
     *:creator_email = "" ;
     *:geospatial_lat_min = "-90.0" ;
     *:geospatial_lat_max = "90.0" ;
     *:geospatial_lon_min = "-180.0" ;
     *:geospatial_lon_max = "180.0" ;
     *:geospatial_vertical_min = "0.0" ;
     *:geospatial_vertical_max = "0.0" ;
     *:time_coverage_start = "2005-09-22T00:00" ;
     *:time_coverage_duration = "0.0" ;
     *:license = "Freely available" ;</pre></blockquote>
     *
     * @throws IOException If the test file can not be read.
     */
    @Test
    public void testGeographicWithTime() throws IOException {
        final NetcdfFile file = open(GEOTIME_NC);
        try {
            final Metadata metadata = wrap(file);
            validator.validate(metadata);
            /*
            * Metadata / Data Identification.
            */
            assertEquals("edu.ucar.unidata:NCEP/SST/Global_5x2p5deg/SST_Global_5x2p5deg_20050922_0000.nc", metadata.getFileIdentifier());
            final DataIdentification identification = (DataIdentification) getSingleton(metadata.getIdentificationInfo());
            assertSame(SpatialRepresentationType.GRID, getSingleton(identification.getSpatialRepresentationTypes()));
            assertEquals("NCEP SST Global 5.0 x 2.5 degree model data", identification.getAbstract().toString());
            /*
            * Metadata / Responsibly party.
            */
            final ResponsibleParty party = getSingleton(metadata.getContacts());
            assertEquals("NOAA/NWS/NCEP", party.getIndividualName());
            assertEquals(Role.ORIGINATOR, party.getRole());
            /*
            * Metadata / Data Identification / Citation.
            */
            final Citation citation = identification.getCitation();
            final Identifier identifier = getSingleton(citation.getIdentifiers());
            final CitationDate date = getSingleton(citation.getDates());
            assertEquals("Sea Surface Temperature Analysis Model", citation.getTitle().toString());
            assertEquals("edu.ucar.unidata", identifier.getAuthority().getTitle().toString());
            assertEquals("NCEP/SST/Global_5x2p5deg/SST_Global_5x2p5deg_20050922_0000.nc", identifier.getCode());
            assertEquals("Expected 2005-09-22T00:00", parseDate("2005-09-22T00:00"), date.getDate());
            assertSame(DateType.CREATION, date.getDateType());
            /*
            * Metadata / Data Identification / Geographic Bounding Box.
            */
            final Extent extent = getSingleton(identification.getExtents());
            final GeographicBoundingBox bbox = (GeographicBoundingBox) getSingleton(extent.getGeographicElements());
            assertEquals("West Bound Longitude", -180, bbox.getWestBoundLongitude(), 0);
            assertEquals("East Bound Longitude", +180, bbox.getEastBoundLongitude(), 0);
            assertEquals("South Bound Latitude",  -90, bbox.getSouthBoundLatitude(), 0);
            assertEquals("North Bound Latitude",  +90, bbox.getNorthBoundLatitude(), 0);
        } finally {
            file.close();
        }
    }
}
