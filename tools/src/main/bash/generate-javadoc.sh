#!/bin/sh
#
# Generate the Javadoc in the "target/apidocs" directory.
#

set -o errexit

#
# Create links to source code for all "org.opengis" modules to document,
# excluding the "module-info.java" of examples and conformance modules
# because we need to replace the "pending" module requirement.
#
mkdir --parents target/src/org.opengis.geoapi
mkdir           target/src/org.opengis.geoapi.example
mkdir           target/src/org.opengis.geoapi.conformance

cp --link --recursive geoapi/src/main/java/*               target/src/org.opengis.geoapi/
cp --link --recursive geoapi/src/pending/java/*            target/src/org.opengis.geoapi/
cp --link --recursive geoapi-examples/src/main/java/org    target/src/org.opengis.geoapi.example/
cp --link --recursive geoapi/src/shared/java/org           target/src/org.opengis.geoapi.conformance/
cp --link --recursive geoapi-conformance/src/main/java/org target/src/org.opengis.geoapi.conformance/

#
# Remove the classes that depend on geoapi-pending.
# Then copy the "module-info" without the dependency replaced.
#
rm target/src/org.opengis.geoapi.example/org/opengis/example/coverage/*.java
rm target/src/org.opengis.geoapi.example/org/opengis/example/geometry/SimpleEnvelope.java
rm target/src/org.opengis.geoapi.conformance/org/opengis/test/coverage/image/ImageReaderTestCase.java
sed "s/geoapi\.pending/geoapi/g" geoapi-examples/src/main/java/module-info.java    > target/src/org.opengis.geoapi.example/module-info.java
sed "s/geoapi\.pending/geoapi/g" geoapi-conformance/src/main/java/module-info.java > target/src/org.opengis.geoapi.conformance/module-info.java

#
# Two pending classes that are used by `ReferencingTestCase`.
#
cp --link geoapi-pending/src/main/java/org/opengis/temporal/Instant.java \
          geoapi-pending/src/main/java/org/opengis/temporal/Period.java  \
          geoapi-pending/src/main/java/org/opengis/temporal/Separation.java  \
          geoapi-pending/src/main/java/org/opengis/temporal/TemporalGeometricPrimitive.java  \
          target/src/org.opengis.geoapi/org/opengis/temporal/

find target/src/ -name "*.java" > target/sources.txt

#
# Build the list of dependencies for all documented modules.
# The standard GeoAPI module uses only "unit-api".
#
M2R=~/.m2/repository
DEPS=$M2R/javax/measure/unit-api/2.1.3/unit-api-2.1.3.jar
DEPS=$DEPS:$M2R/tech/uom/seshat/1.3/seshat-1.3.jar
DEPS=$DEPS:$M2R/javax/vecmath/vecmath/1.5.2/vecmath-1.5.2.jar
DEPS=$DEPS:$M2R/org/junit/jupiter/junit-jupiter-api/5.10.2/junit-jupiter-api-5.10.2.jar
DEPS=$DEPS:$M2R/org/opentest4j/opentest4j/1.3.0/opentest4j-1.3.0.jar
DEPS=$DEPS:$M2R/org/junit/platform/junit-platform-commons/1.10.2/junit-platform-commons-1.10.2.jar
DEPS=$DEPS:$M2R/org/apiguardian/apiguardian-api/1.1.2/apiguardian-api-1.1.2.jar
DOCLET=tools/target/tools-*.jar

javadoc -doctitle "GeoAPI SNAPSHOT" \
 -top '<div class="snapshot-warning"><b>This specification is not final and is subject to change.</b><br/>For OGC standard, see <a href="http://www.geoapi.org/3.0/javadoc/index.html">GeoAPI 3.0.2</a>.</div>' \
 --add-stylesheet src/main/javadoc/geoapi.css -overview src/main/javadoc/overview.html \
 -doclet org.opengis.tools.doclet.FlushableDoclet -docletpath $DOCLET \
 -taglet org.opengis.tools.doclet.Departure -tagletpath $DOCLET \
 --since 3.1 \
 -tag category:X:Category: \
 -tag condition:tfmc:Condition: \
 -tag todo:tfmc:TODO: \
 -tag unitof:fm:Unit: \
 -charset UTF-8 -docencoding UTF-8 -breakiterator -locale en -noqualifier all -use -Xdoclint:syntax \
 -link https://download.java.net/media/java3d/javadoc/1.5.2/ \
 -link https://download.java.net/media/jai/javadoc/1.1.3/jai-apidocs/ \
 -link https://junit.org/junit5/docs/current/api/ \
 -d target/apidocs --module-source-path target/src --module-path $DEPS @target/sources.txt

# Copy some resources.
cp --interactive src/main/javadoc/content.* src/main/javadoc/departure.* src/main/javadoc/*.png target/apidocs/