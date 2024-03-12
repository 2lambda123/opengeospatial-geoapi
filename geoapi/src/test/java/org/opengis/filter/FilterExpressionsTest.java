/*
 *    GeoAPI - Java interfaces for OGC/ISO standards
 *    Copyright © 2021-2024 Open Geospatial Consortium, Inc.
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
package org.opengis.filter;

import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Tests {@link FilterExpressions} bridge between {@link Filter} and {@link Expression} lists.
 *
 * @author  Martin Desruisseaux (Geomatys)
 * @version 3.1
 * @since   3.1
 */
public final class FilterExpressionsTest {
    /**
     * Creates a new test case.
     */
    public FilterExpressionsTest() {
    }

    /**
     * Tests with {@link FilterLiteral} values.
     */
    @Test
    public void testFilterLiterals() {
        final FilterExpressions<Filter<?>> exp = new FilterExpressions<>(List.of(Filter.include(), Filter.exclude()));
        assertFalse(exp.isEmpty());
        assertEquals(2, exp.size());
        assertEquals(Boolean.TRUE,  exp.get(0).apply(null));
        assertEquals(Boolean.FALSE, exp.get(1).apply(null));
        assertEquals("[Expression[Filter.INCLUDE], Expression[Filter.EXCLUDE]]", exp.toString());
        assertTrue(exp.get(0).getParameters().isEmpty());
        assertTrue(exp.get(1).getParameters().isEmpty());
    }
}
