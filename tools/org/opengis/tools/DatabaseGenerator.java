/*$************************************************************************************************
 **
 ** $Id$
 **
 ** $Source$
 **
 ** Copyright (C) 2003 Open GIS Consortium, Inc. All Rights Reserved. http://www.opengis.org/Legal/
 **
 *************************************************************************************************/
package org.opengis.tools;

// J2SE dependencies
import java.io.*;
import java.util.*;
import java.net.URI;
import java.net.URL;
import java.lang.reflect.*;
import java.util.logging.Logger;

// OpenGIS dependencies
import org.opengis.util.CodeList;
import org.opengis.util.GenericName;
import org.opengis.util.InternationalString;
import org.opengis.annotation.UML;
import org.opengis.annotation.Obligation;
import org.opengis.parameter.ParameterValueGroup;
import org.opengis.parameter.ParameterDescriptorGroup;
import org.opengis.spatialschema.geometry.geometry.PointGrid;
import org.opengis.spatialschema.geometry.geometry.PointArray;


/**
 * Generate a database from a set of GeoAPI interfaces.
 *
 * @author Martin Desruisseaux
 */
public class DatabaseGenerator {
    /**
     * The root package.
     */
    private static final String rootPackage = "org.opengis.metadata";

    /**
     * The owner for the database to create, or <code>null</code> for the default one.
     */
    private static final String owner = "latical";

    /**
     * The line separator.
     */
    private static final String lineSeparator = System.getProperty("line.separator", "\n");

    /**
     * The list of table created in inverse order. Used for the "DROP TABLE" statements.
     */
    private static final LinkedList<String> drop = new LinkedList<String>();

    /**
     * Do not allows instances of this class.
     */
    private DatabaseGenerator() {
    }

    /**
     * Scan all classes and members and write an SQL script.
     */
    public static void main(String[] args) throws Exception {
        Writer sql = new FileWriter("create.sql");
        final Set<Class> classes = ClassFinder.getClasses(CodeList.class, rootPackage);
        for (final Iterator<Class> it=classes.iterator(); it.hasNext();) {
            final Class classe = it.next();
            if (Throwable.class.isAssignableFrom(classe)) {
                it.remove();
                continue;
            }
            if (CodeList.class.isAssignableFrom(classe)) {
                it.remove();
                final String statement = sqlCodeList(classe);
                if (statement != null) {
                    sql.write(statement);
                }
            }
        }
        while (!classes.isEmpty()) {
            try {
                for (final Iterator<Class> it=classes.iterator(); it.hasNext();) {
                    final Class classe = it.next();
                    if (classe.isInterface()) {
                        it.remove(); // Most be invoked before 'sqlInterface'.
                        final String statement = sqlInterface(classe, classes);
                        if (statement != null) {
                            sql.write(statement);
                        }
                    }
                }
                break; // All interfaces were processed.
            } catch (ConcurrentModificationException ignore) {
                // The Set has been modified (in order to solve dependencies).
                // The iteration must be restarted.
            }
        }
        sql.close();
        sql = new BufferedWriter(new FileWriter("drop.sql"));
        for (final String name : drop) {
            sql.write("DROP TABLE \"");
            sql.write(name);
            sql.write("\";");
            sql.write(lineSeparator);
        }
        sql.close();
        if (!classes.isEmpty()) {
            Logger.getLogger("org.opengis.tools").warning("Unrecognized classes.");
        }
    }

    /**
     * Create an SQL "CREATE TABLE" statement for the specified code list.
     * Returns <code>null</code> if the specified interface doesn't have @UML tags.
     *
     * @param  classe The class to process.
     * @return The SQL string to add to the 'create.sql' file.
     */
    private static String sqlCodeList(final Class classe) throws IllegalAccessException {
        final String className = getIdentifier(classe);
        if (className == null) {
            return null;
        }
        final StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE \"");
        sql.append(className);
        sql.append('"');
        sql.append(lineSeparator);
        sql.append('(');
        sql.append(lineSeparator);
        sql.append("  \"code\" INT2 NOT NULL,");
        sql.append(lineSeparator);
        sql.append("  \"name\" CHARACTER VARYING(32) NOT NULL,");
        sql.append(lineSeparator);
        sql.append("  CONSTRAINT \"");
        sql.append(className);
        sql.append(".primaryKey\" PRIMARY KEY (\"code\"),");
        sql.append(lineSeparator);
        sql.append("  CONSTRAINT \"");
        sql.append(className);
        sql.append(".uniqueName\" UNIQUE (\"name\")");
        sql.append(lineSeparator);
        sql.append(") WITHOUT OIDS;");
        sql.append(lineSeparator);
        if (owner != null) {
            sql.append("ALTER TABLE \"");
            sql.append(className);
            sql.append("\" OWNER TO ");
            sql.append(owner);
            sql.append(';');
            sql.append(lineSeparator);
        }        
        final Field[] attributes = classe.getDeclaredFields();
        for (final Field attribute : attributes) {
            if (!Modifier.isPublic(attribute.getModifiers())) {
                continue;
            }
            final String attributeName = getIdentifier(attribute);
            if (attributeName == null) {
                continue;
            }
            final CodeList code = (CodeList) attribute.get(null);
            sql.append("INSERT INTO \"");
            sql.append(className);
            sql.append("\" VALUES (");
            sql.append(code.ordinal() + 1);
            sql.append(", '");
            sql.append(attributeName.replace("'", "''"));
            sql.append("');");
            sql.append(lineSeparator);
        }
        sql.append(lineSeparator);
        sql.append(lineSeparator);
        drop.addFirst(className);
        return sql.toString();
    }

    /**
     * Create an SQL "CREATE TABLE" statement for the specified interface.
     * Returns <code>null</code> if the specified interface doesn't have @UML tags.
     *
     * @param  classe The class to process.
     * @param  classes The remaining classes to process. Element in this set will be
     *         removed if this method determine that it need to create some other classes
     *         before this one, in order to resolve dependencies.
     * @return The SQL string to add to the 'create.sql' file.
     */
    private static String sqlInterface(final Class classe, final Set<Class> classes) {
        final String className = getIdentifier(classe);
        if (className == null) {
            return null;
        }
        final StringBuilder constraints = new StringBuilder();
        final StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE \"");
        sql.append(className);
        sql.append('"');
        sql.append(lineSeparator);
        sql.append('(');
        sql.append(lineSeparator);
        final Method[] attributes = classe.getDeclaredMethods();
scan:   for (final Method attribute : attributes) {
            if (!Modifier.isPublic(attribute.getModifiers())) {
                continue;
            }
            final String attributeName = getIdentifier(attribute);
            if (attributeName == null) {
                continue;
            }
            for (final Class parent : classe.getInterfaces()) {
                try {
                    parent.getMethod(attributeName, attribute.getParameterTypes());
                    continue scan; // Found the method in superclass: don't add it again.
                } catch (NoSuchMethodException ignore) {
                    // No such method in superclass. Checks other interfaces.
                }
            }
            final Class attributeType = attribute.getReturnType();
            final String sqlType = toSQLType(attributeType, attribute.getGenericReturnType());
            if (sqlType == null) {
                continue;
            }
            sql.append("  \"");
            sql.append(attributeName);
            sql.append("\" ");
            sql.append(sqlType);
            if (Obligation.MANDATORY.equals(attribute.getAnnotation(UML.class).obligation())) {
                sql.append(" NOT NULL");
            }
            sql.append(',');
            sql.append(lineSeparator);
            final String foreignKey;
            if (CodeList.class.isAssignableFrom(attributeType)) {
                foreignKey = "code";
            } else if (attributeType.getName().startsWith(rootPackage)) {
                foreignKey = "oid";
            } else {
                continue;
            }
            constraints.append("  CONSTRAINT \"");
            constraints.append(className);
            constraints.append('.');
            constraints.append(attributeName);
            constraints.append("\" FOREIGN KEY (\"");
            constraints.append(attributeName);
            constraints.append("\") REFERENCES \"");
            constraints.append(getIdentifier(attributeType));
            constraints.append("\" (");
            constraints.append(foreignKey);
            constraints.append(") ON UPDATE RESTRICT ON DELETE RESTRICT,");
            constraints.append(lineSeparator);
            if (classes.remove(attributeType)) {
                // Resolve dependencies first.
                sql.insert(0, sqlInterface(attributeType, classes));
            }
        }
        sql.append(constraints);
        sql.append("  CONSTRAINT \"");
        sql.append(className);
        sql.append(".primaryKey\" PRIMARY KEY (oid)");
        sql.append(lineSeparator);
        sql.append(')');
        final Class[] parents = classe.getInterfaces();
        if (parents != null) {
            for (int i=0; i<parents.length; i++) {
                final Class parent = parents[i];
                if (classes.remove(parent)) {
                    // Resolve dependencies first.
                    sql.insert(0, sqlInterface(parent, classes));
                }
                sql.append(i==0 ? " INHERITS (\"" : ", \"");
                sql.append(getIdentifier(parent));
                sql.append('"');
            }
            if (parents.length != 0) {
                sql.append(')');
            }
        }
        sql.append(" WITH OIDS;");
        sql.append(lineSeparator);
        if (owner != null) {
            sql.append("ALTER TABLE \"");
            sql.append(className);
            sql.append("\" OWNER TO ");
            sql.append(owner);
            sql.append(';');
            sql.append(lineSeparator);
        }        
        sql.append(lineSeparator);
        sql.append(lineSeparator);
        drop.addFirst(className);
        return sql.toString();
    }

    /**
     * Returns the SQL type for the specified Java classe.
     */
    private static String toSQLType(final Class classe, final Type type) {
        if (       CharSequence.class.isAssignableFrom(classe) ||
            InternationalString.class.isAssignableFrom(classe))
        {
            return "TEXT";
        }
        if (URL.class.isAssignableFrom(classe) ||
            URI.class.isAssignableFrom(classe))
        {
            return "TEXT";
        }
        if (GenericName.class.isAssignableFrom(classe))
        {
            return "TEXT";
        }
        if (Boolean.class.isAssignableFrom(classe) ||
            Boolean.TYPE .isAssignableFrom(classe))
        {
            return "BOOLEAN";
        }
        if (Short.class.isAssignableFrom(classe) ||
            Short.TYPE .isAssignableFrom(classe))
        {
            return "SMALLINT";
        }
        if (Integer.class.isAssignableFrom(classe) ||
            Integer.TYPE .isAssignableFrom(classe))
        {
            return "INTEGER";
        }
        if (Long.class.isAssignableFrom(classe) ||
            Long.TYPE .isAssignableFrom(classe))
        {
            return "BIGINT";
        }
        if (Float.class.isAssignableFrom(classe) ||
            Float.TYPE .isAssignableFrom(classe))
        {
            return "REAL";
        }
        if (Double.class.isAssignableFrom(classe) ||
            Double.TYPE .isAssignableFrom(classe))
        {
            return "DOUBLE PRECISION";
        }
        if (Number.class.isAssignableFrom(classe))
        {
            return "DOUBLE PRECISION";
        }
        if (Date.class.isAssignableFrom(classe))
        {
            return "TIMESTAMP WITH TIME ZONE";
        }
        if (CodeList.class.isAssignableFrom(classe))
        {
            return "INT2";
        }
        if (classe.getName().startsWith(rootPackage)) {
            return "INT4";
        }
        if (classe.isArray()) {
            final String sql = toSQLType(classe.getComponentType(), null);
            return (sql != null) ? sql+"[]" : null;
        }
        if (Collection.class.isAssignableFrom(classe)) {
            if (type instanceof ParameterizedType) {
                final Type[] types = ((ParameterizedType) type).getActualTypeArguments();
                if (types.length == 1) {
                    final String sql = toSQLType((Class) types[0], null);
                    return (sql != null) ? sql+"[]" : null;
                }
            }
        }
        Logger.getLogger("org.opengis.tools").warning("No mapping to SQL type: "+classe.getName());
        return null;
    }

    /**
     * Returns the UML identifier for the specified element, or <code>null</code>
     * if the specified element is not part of the UML model.
     */
    private static String getIdentifier(final AnnotatedElement element) {
        final UML uml = element.getAnnotation(UML.class);
        if (uml != null) {
            String identifier = uml.identifier();
            /*
             * If there is two or more UML identifier collapsed in only one
             * Java method, keep only the first identifier (which is usually
             * the main attribute).
             */
            final int split = identifier.indexOf(',');
            if (split >= 0) {
                identifier = identifier.substring(0, split);
            }
            return identifier;
        }
        return null;
    }
}
