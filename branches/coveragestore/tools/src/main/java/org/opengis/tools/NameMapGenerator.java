/*$************************************************************************************************
 **
 ** $Id$
 **
 ** $URL$
 **
 ** Copyright (C) 2003 Open GIS Consortium, Inc. All Rights Reserved. http://www.opengis.org/Legal/
 **
 *************************************************************************************************/
package org.opengis.tools;

// J2SE dependencies
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Collection;

// Annotation processing tools
import com.sun.mirror.apt.Filer;
import com.sun.mirror.type.TypeMirror;
import com.sun.mirror.type.DeclaredType;
import com.sun.mirror.declaration.Modifier;
import com.sun.mirror.declaration.TypeDeclaration;
import com.sun.mirror.declaration.MemberDeclaration;
import com.sun.mirror.declaration.MethodDeclaration;


/**
 * Generates properties files that map a GeoAPI name (interface, method, field) to the ISO name
 * it is derived from.
 *
 * The map generated by this class is mostly a workaround for J2SE 1.4 environments.
 * J2SE 1.5 environment are encouraged to inspect the annotations as runtime instead.
 * <p>
 * <b>How to use</b>
 * {@code chdir} to the root directory of source code. Then invoke the following command,
 * where {@code metadata.txt} is a file containing the path to all java classes to parse
 * in the {@value #ROOT_PACKAGE} package.
 *
 * <blockquote><pre>
 * apt -nocompile -factory org.opengis.tools.NameMapGenerator @metadata.txt
 * </pre></blockquote>
 *
 * The properties files will be stored in the {@value #ROOT_PACKAGE} package.
 *
 * @author Martin Desruisseaux
 */
public class NameMapGenerator extends UmlProcessor {
    /**
     * The root package.
     */
    public static final String ROOT_PACKAGE = "org.opengis.metadata";

    /**
     * For each GeoAPI name, its ISO name.
     */
    private final Properties nameMap = new Properties();

    /**
     * For each GeoAPI method returning a collection,
     * its generic type as a fully qualified class name.
     */
    private final Properties typeMap = new Properties();

    /**
     * Creates a default processor.
     */
    public NameMapGenerator() {
    }

    /**
     * Process all program elements supported by this annotation processor. This method scan
     * all interfaces and their methods (as well as code lists and their fields) and write
     * the map to a property file.
     */
    @Override
    public void process() {
        super.process();
        final Filer filer = environment.getFiler();
        try {
            OutputStream out = filer.createBinaryFile(Filer.Location.CLASS_TREE,
                               ROOT_PACKAGE, new File("GeoAPI_to_ISO.properties"));
            nameMap.store(out, "This is a temporary file for J2SE 1.4 implementations. " +
                               "Implementations for J2SE 5.0 should inspect annotations instead.");
            out.close();
            out = filer.createBinaryFile(Filer.Location.CLASS_TREE,
                               ROOT_PACKAGE, new File("CollectionTypes.properties"));
            typeMap.store(out, "This is a temporary file for J2SE 1.4 implementations. " +
                               "Implementations for J2SE 5.0 should inspect generic types instead.");
            out.close();
        } catch (IOException exception) {
            printError(exception);
        }
    }

    /**
     * Returns the name for the specified declaration.
     */
    private static String getName(final MemberDeclaration declaration) {
        String className = declaration.getSimpleName();
        String name = declaration.getSimpleName();
        final TypeDeclaration owner = declaration.getDeclaringType();
        if (owner != null) {
            name = owner.getSimpleName() + '.' + name;
        }
        return name;
    }

    /**
     * Adds the specified declaration to the map of "GeoAPI to ISO naming".
     */
    @Override
    public void visitMemberDeclaration(final MemberDeclaration declaration) {
        super.visitMemberDeclaration(declaration);
        if (!declaration.getModifiers().contains(Modifier.PUBLIC)) {
            return;
        }
        final String identifier = getUmlIdentifier(declaration);
        if (identifier == null) {
            return;
        }
        nameMap.put(getName(declaration), identifier);
    }

    /**
     * Adds the specified method to the map of collection types.
     */
    @Override
    public void visitMethodDeclaration(final MethodDeclaration declaration) {
        super.visitMethodDeclaration(declaration);
        final TypeMirror type = declaration.getReturnType();
        if (type instanceof DeclaredType) {
            final Collection<TypeMirror> genericTypes = ((DeclaredType) type).getActualTypeArguments();
            if (genericTypes.size() == 1) {
                final TypeMirror genericType = genericTypes.iterator().next();
                if (genericType instanceof DeclaredType) {
                    final String identifier = ((DeclaredType) genericType).getDeclaration().getQualifiedName();
                    typeMap.put(getName(declaration), identifier);
                }
            }
        }
    }
}
