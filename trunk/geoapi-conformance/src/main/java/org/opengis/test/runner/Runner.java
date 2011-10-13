/*
 *    GeoAPI - Java interfaces for OGC/ISO standards
 *    http://www.geoapi.org
 *
 *    Copyright (C) 2011 Open Geospatial Consortium, Inc.
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
package org.opengis.test.runner;

import java.util.Set;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.logging.Logger;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.MalformedURLException;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.junit.runner.Result;
import org.junit.runner.JUnitCore;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import org.opengis.test.TestSuite;

import static org.opengis.test.runner.ReportEntry.Status.*;


/**
 * Provides methods for running the tests. This class does not depend on Swing widgets
 * or on console program.
 *
 * @author  Martin Desruisseaux (Geomatys)
 * @version 3.1
 * @since   3.1
 */
final class Runner extends RunListener {
    /**
     * The logger for this package.
     */
    static final Logger LOGGER = Logger.getLogger("org.opengis.test.runner");

    /**
     * The platform-specific line separator.
     */
    static final String LINE_SEPARATOR = System.getProperty("line.separator", "\n");

    /**
     * The result of each tests. All a access to this list must be synchronized.
     */
    private final Set<ReportEntry> entries;

    /**
     * The listeners to inform of any new entry.
     */
    private ChangeListener[] listeners;

    /**
     * The single change event to reuse every time an event is fired.
     */
    private final ChangeEvent event;

    /**
     * Creates a new, initially empty, runner.
     */
    Runner() {
        entries   = new LinkedHashSet<ReportEntry>();
        listeners = new ChangeListener[0];
        event     = new ChangeEvent(this);
    }

    /**
     * Sets the class loader to uses for running the tests.
     *
     * @param files The JAR files that contain the implementation to test.
     */
    static void setClassLoader(final File... files) throws MalformedURLException {
        final URL[] urls = new URL[files.length];
        for (int i=0; i<urls.length; i++) {
            urls[i] = files[i].toURI().toURL();
        }
        TestSuite.setClassLoader(new URLClassLoader(urls, TestSuite.class.getClassLoader()));
    }

    /**
     * Runs the JUnit tests.
     */
    void run() {
        final JUnitCore junit = new JUnitCore();
        junit.addListener(this);
        final Result result = junit.run(TestSuite.class);
    }

    /**
     * Returns all entries. This method returns a copy of the internal array.
     * Changes to this {@code ReportData} object will not be reflected in that array.
     */
    ReportEntry[] getEntries() {
        synchronized (entries) {
            return entries.toArray(new ReportEntry[entries.size()]);
        }
    }

    /**
     * Adds a new test result. If we already have an entry for the same test method,
     * silently discards the new entry. We do that because test failure cause two
     * entries to be emitted: first an entry for the test failure, then another
     * entry because the test finished.
     */
    private void addEntry(final Description description, final ReportEntry.Status status) {
        final ReportEntry entry = new ReportEntry(description, status);
        final ChangeListener[] list;
        synchronized (entries) {
            if (entries.contains(entry)) {
                return;
            }
            entries.add(entry);
            list = listeners;
        }
        for (final ChangeListener listener : list) {
            listener.stateChanged(event);
        }
    }

    /**
     * Called when an atomic test has finished, whether the test succeeds or fails.
     */
    @Override
    public void testFinished(final Description description) throws Exception {
        addEntry(description, SUCCESS);
        super.testFinished(description);
    }

    /**
     * Called when an atomic test fails.
     */
    @Override
    public void testFailure(final Failure failure) throws Exception {
        addEntry(failure.getDescription(), FAILURE);
        super.testFailure(failure);
    }

    /**
     * Called when an atomic test flags that it assumes a condition that is false.
     */
    @Override
    public void testAssumptionFailure(final Failure failure) {
        addEntry(failure.getDescription(), ASSUMPTION_NOT_MET);
        super.testAssumptionFailure(failure);
    }

    /**
     * Called when a test will not be run, generally because a test method is annotated with
     * {@link org.junit.Ignore}.
     */
    @Override
    public void testIgnored(final Description description) throws Exception {
        addEntry(description, IGNORED);
        super.testIgnored(description);
    }

    /**
     * Adds a change listener.
     */
    void addChangeListener(final ChangeListener listener) {
        synchronized (entries) {
            ChangeListener[] list = listeners;
            final int length = list.length;
            list = Arrays.copyOf(list, length + 1);
            list[length] = listener;
            listeners = list;
        }
    }
}
