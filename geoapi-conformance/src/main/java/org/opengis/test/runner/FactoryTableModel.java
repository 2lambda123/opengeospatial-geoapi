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

import java.util.List;
import java.util.Collections;
import javax.swing.table.AbstractTableModel;


/**
 * The table model for the list of factories.
 *
 * @author  Martin Desruisseaux (Geomatys)
 * @version 3.1
 * @since   3.1
 */
@SuppressWarnings("serial")
final class FactoryTableModel extends AbstractTableModel {
    /**
     * Index of columns handled by this model.
     * Must matches the columns documented in {@link ReportEntry#factories}.
     */
    static final int CATEGORY_COLUMN       = 0,
                     IMPLEMENTATION_COLUMN = 1,
                     VENDOR_COLUMN         = 2,
                     AUTHORITY_COLUMN      = 3;

    /**
     * The titles of all columns.
     */
    private static final String[] COLUMN_TITLES;
    static {
        COLUMN_TITLES = new String[4];
        COLUMN_TITLES[CATEGORY_COLUMN]        = "Category";
        COLUMN_TITLES[IMPLEMENTATION_COLUMN]  = "Implementation";
        COLUMN_TITLES[VENDOR_COLUMN]          = "Vendor";
        COLUMN_TITLES[AUTHORITY_COLUMN]       = "Authority";
    };

    /**
     * The factories.
     */
    List<String[]> entries;

    /**
     * Creates an initially empty table model.
     */
    FactoryTableModel() {
        entries = Collections.emptyList();
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
        return entries.size();
    }

    /**
     * Returns the value in the given cell.
     */
    @Override
    public String getValueAt(final int row, final int column) {
        return entries.get(row)[column];
    }
}
