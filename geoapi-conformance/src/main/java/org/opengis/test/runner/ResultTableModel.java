/*
 *    GeoAPI - Java interfaces for OGC/ISO standards
 *    Copyright © 2011-2023 Open Geospatial Consortium, Inc.
 *    http://www.geoapi.org
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.opengis.test.runner;

import java.awt.EventQueue;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;


/**
 * The table model for the {@link ResultEntry} instances to be displayed.
 *
 * @author  Martin Desruisseaux (Geomatys)
 * @version 3.1
 * @since   3.1
 */
@SuppressWarnings("serial")
final class ResultTableModel extends AbstractTableModel implements ChangeListener, Runnable {
    /**
     * Index of columns handled by this model.
     */
    static final int CLASS_COLUMN   = 0,
                     METHOD_COLUMN  = 1,
                     RESULT_COLUMN  = 2,
                     MESSAGE_COLUMN = 3;

    /**
     * The titles of all columns.
     */
    private static final String[] COLUMN_TITLES;
    static {
        COLUMN_TITLES = new String[4];
        COLUMN_TITLES[CLASS_COLUMN]   = "Class";
        COLUMN_TITLES[METHOD_COLUMN]  = "Method";
        COLUMN_TITLES[RESULT_COLUMN]  = "Result";
        COLUMN_TITLES[MESSAGE_COLUMN] = "Message";
    };

    /**
     * The object which is receiving the result of each tests.
     */
    private final Runner data;

    /**
     * The result of each tests.
     */
    private ResultEntry[] entries;

    /**
     * Creates a table model for the given data.
     */
    @SuppressWarnings("ThisEscapedInObjectConstruction")
    ResultTableModel(final Runner data) {
        this.data = data;
        entries = data.getEntries();
        data.addChangeListener(this);
    }

    /**
     * Returns the number of columns in this table.
     */
    @Override
    public int getColumnCount() {
        return COLUMN_TITLES.length;
    }

    /**
     * Returns the name of the given column.
     */
    @Override
    public String getColumnName(final int column) {
        return COLUMN_TITLES[column];
    }

    /**
     * Returns the type of values in the given column.
     */
    @Override
    public Class<?> getColumnClass(final int column) {
        return String.class;
    }

    /**
     * Returns the number of rows in this table.
     */
    @Override
    public int getRowCount() {
        return entries.length;
    }

    /**
     * Returns the values in the given row.
     */
    final ResultEntry getValueAt(final int row) {
        return entries[row];
    }

    /**
     * Returns the value in the given cell.
     */
    @Override
    public String getValueAt(final int row, final int column) {
        final ResultEntry entry = entries[row];
        switch (column) {
            case CLASS_COLUMN:  return entry.simpleClassName;
            case METHOD_COLUMN: return entry.simpleMethodName;
            case RESULT_COLUMN: switch (entry.status) {
                case SUCCESS: return "success";
                case FAILURE: return "failure";
                default:      return null;
            }
            case MESSAGE_COLUMN: {
                if (entry.status != ResultEntry.Status.ASSUMPTION_NOT_MET) {
                    final Throwable exception = entry.exception;
                    if (exception != null) {
                        String message = exception.getLocalizedMessage();
                        if (message != null) {
                            message = message.trim();
                            final int stop = message.indexOf('\n');
                            if (stop >= 0) {
                                message = message.substring(0, stop).trim();
                            }
                            if (!message.isEmpty()) {
                                return message;
                            }
                        }
                    }
                }
                return null;
            }
            default: throw new IndexOutOfBoundsException(String.valueOf(column));
        }
    }

    /**
     * Invoked every time a new entry has been added in {@link ReportData}.
     * This method may be invoked from any thread, so we just report the event
     * in the Swing thread.
     */
    @Override
    public void stateChanged(final ChangeEvent event) {
        EventQueue.invokeLater(this);
    }

    /**
     * Invoked in the Swing thread after a new entry has been added in {@link ReportData}.
     * This method fires a table event with the range of row index for the new entries.
     * We presume that the previous entries has not been modified.
     */
    @Override
    public void run() {
        final int lower = entries.length;
        entries = data.getEntries();
        final int upper = entries.length;
        if (lower != upper) {
            fireTableRowsInserted(lower, upper-1);
        }
    }
}
