/*
 *    GeoAPI - Java interfaces for OGC/ISO standards
 *    http://www.geoapi.org
 *
 *    This file is hereby placed into the Public Domain.
 *    This means anyone is free to do whatever they wish with this file.
 */

/**
 * Implementation of some interfaces from the {@link org.opengis.referencing} package.
 * In order to provide a simpler model, some classes in this package merge many distinct
 * GeoAPI concepts. For example, many existing projection libraries make no distinction between
 * <cite>Coordinate System</cite> (CS) and <cite>Coordinate Reference System</cite> (CRS).
 * This package follows this simplified model by providing a single class implementing both
 * the CS and CRS interfaces.
 * <p>
 * The following table lists the classes that implement more than one GeoAPI interface:
 * <p>
 * <blockquote><table><tr>
 * <th>Simple class</th>
 * <th colspan="2">Implements</th>
 * </tr><tr>
 * <td>{@link org.opengis.example.referencing.SimpleCRS}:&nbsp;</td>
 * <td>{@link org.opengis.referencing.crs.CoordinateReferenceSystem},</td>
 * <td>{@link org.opengis.referencing.cs.CoordinateSystem}</td>
 * </tr><tr>
 * <td>{@link org.opengis.example.referencing.SimpleDatum}:&nbsp;</td>
 * <td>{@link org.opengis.referencing.datum.GeodeticDatum},</td>
 * <td>{@link org.opengis.referencing.datum.Ellipsoid}</td>
 * </tr><tr>
 * <td>{@link org.opengis.example.referencing.SimpleTransform}:&nbsp;</td>
 * <td>{@link org.opengis.referencing.operation.CoordinateOperation},</td>
 * <td>{@link org.opengis.referencing.operation.MathTransform}</td>
 * </tr><tr>
 * <td>{@link org.opengis.example.referencing.SimpleIdentifiedObject}:&nbsp;</td>
 * <td>{@link org.opengis.referencing.IdentifiedObject},</td>
 * <td>{@link org.opengis.referencing.ReferenceIdentifier}</td>
 * </tr><tr>
 * <td>{@link org.opengis.example.metadata.SimpleCitation}:&nbsp;</td>
 * <td>{@link org.opengis.metadata.citation.Citation},</td>
 * <td>{@link org.opengis.util.InternationalString}</td>
 * </tr></table></blockquote>
 * <p>
 * Every classes in this package are hereby placed into the Public Domain.
 * This means anyone is free to do whatever they wish with those files.
 *
 * @author  Martin Desruisseaux (Geomatys)
 * @version 3.1
 * @since   3.1
 */
package org.opengis.example.referencing;