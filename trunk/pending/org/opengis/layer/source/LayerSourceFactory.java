/*
 * $ Id $
 * $ Source $
 * Created on Apr 4, 2005
 */
package org.opengis.layer.source;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URL;
import java.util.Map;

import org.opengis.feature.FeatureStore;
import org.opengis.feature.Query;
import org.opengis.filter.Filter;
import org.opengis.go.display.primitive.store.GraphicStore;
import org.opengis.sld.FeatureStyle;
import org.opengis.util.InternationalString;


/**
 * The <code>LayerSourceFactory</code> class/interface...
 * 
 * @author SYS Technologies
 * @author crossley
 * @version $Revision $
 */
public interface LayerSourceFactory {

    /** 
     * Well-known param key indicating service requires a username for authentication.
     */
    public static final Param USERNAME = 
        new Param( "user", String.class, "Username for authentication" );
    
    /** 
     * Well-known param key indicating service requires a username for authentication
     */ 
    public static final Param PASSWORD = 
        new Param( "password", String.class, "Password for authentication" );
    
    public static final Param FEATURE_STORES = 
        new Param("featureStores", FeatureStore[].class, "Array of FeatureStores to use");
    
    public static final Param GRAPHIC_STORES = 
        new Param("graphicStores", GraphicStore[].class, "Array of GraphicStores to use");
    
    public static final Param QUERIES = 
        new Param("queries", Query[].class, "Array of Queries to use");
    
    public static final Param FILTERS = 
        new Param("filters", Filter[].class, "Array of Filters to use");
    
    public static final Param FEATURE_STYLES = 
        new Param("featureStyles", FeatureStyle[].class, "Array of FeatureStyles to use");
    
    LayerSource createLayerSource(URI provider, Map params) throws IOException;

    LayerSource createNewLayerSource(URI provider, Map params) throws IOException;
    
    /**
     * Icon representing this category of datastores.
     * <p>
     * Assumed to point to a 16x16 icon?
     * </p>
     * @return the icon.
     */
    URL getIcon();

    /**
     *  Display name used to communicate this type of FeatureStore to end users.
     * @return
     */
    InternationalString getDisplayName();
    
    /** 
     * Descrption of this type of FeatureStore.
     * @return
     */
    InternationalString getDescription();

    /**
     * DOCUMENT ME.
     * @return
     */
    Param[] getParametersInfo();

    /**
     * Indicates this FeatureStoreFactory communicate with the indicated provider or service.
     * <p>
     * This method should not fail, if a connection needs to be made
     * to parse a GetCapabilities file or negotiate WMS versions any
     * IO problems simply indicate the inabiity to process.
     * </p>
     * <p>
     * This method may be considered the same as: canProcess( provider, hints )
     * where hints was generated by using all the default values specified by the
     * getParameterInfo method.
     * </p>
     * @param provider Provider or Server of spatial information. 
     */
    boolean canProcess(URI provider);
    
    /**
     * Indicates this FeatureStoreFactory communicate with the indicated provider or service.
     * <p>
     * This method differs from canProcess in that additional configuration
     * information may be supplied. 
     * </p>
     * @param provider
     * @param params
     * @return <code>true</code> if this factory can communicate with the provider.
     */
    boolean canProcess(URI provider, Map params);

    /**
     * Allows a FeatureStoreFactory to ensure all its preconditions are met,
     * such as the presense of required libraries.
     */
    boolean isAvailable();

    /**
     * Simple service metadata - should be replaced by ISO19119 interfaces
     * as they are made available.
     * <p>
     * Differences from geotools - no param is required. Sensible
     * defaults should always be available.
     * </p>
     * @author Jody Garnett
     */
    class Param {
        /** True if Param is required. */
        final public boolean required;

        /** Key used in Parameter map. */
        final public String key;

        /** Type of information required. */
        final public Class type;

        /** Short description (less then 40 characters). */
        final public String description;

        /** Default value, please provide one users will be so much happier */
        final public Object sample;

        public Param(final String key) {
            this(key, String.class, null);
        }

        public Param(final String key, final Class type) {
            this(key, type, null);
        }

        public Param(final String key, final Class type, final String description) {
            this(key, type, description, true);
        }

        public Param(
                final String key, 
                final Class type, 
                final String description, 
                final boolean required) {
            this(key, type, description, required, null);
        }

        public Param(
                final String key, 
                final Class type, 
                final String description, 
                final boolean required, 
                final Object sample) {
            this.key = key;
            this.type = type;
            this.description = description;
            this.required = required;
            this.sample = sample;
        }

        /**
         * Utility method for implementors of canProcess.
         * <p>
         * Willing to check all known constraints and parse Strings to requred value
         * if needed.
         * </p>
         * 
         * @param map Map of parameter values
         * @return Value of the correct type from map
         * @throws IOException if parameter was of the wrong type, or a required parameter is not present
         */
        public Object lookUp(final Map map) throws IOException {
            if (!map.containsKey(key)) {
                if (required) {
                    throw new IOException("Parameter " + key + " is required:" + description);
                } else {
                    return null;
                }
            }
            Object value = map.get(key);
            if (value == null) {
                return null;
            }
            if (value instanceof String && (type != String.class)) {
                value = handle((String) value);
            }
            if (value == null) {
                return null;
            }
            if (!type.isInstance(value)) {
                throw new IOException(type.getName() + " required for parameter " + key + ": not "
                        + value.getClass().getName());
            }
            return value;
        }

        public String text(final Object value) {
            return value.toString();
        }

        public Object handle(final String text) throws IOException {
            if (text == null) {
                return null;
            }
            if (type == String.class) {
                return text;
            }
            if (text.length() == 0) {
                return null;
            }
            try {
                return parse(text);
            } catch (IOException ioException) {
                throw ioException;
            } catch (Throwable throwable) {
                throw new LayerSourceException("Problem creating " + type.getName() + " from '"
                        + text + "'", throwable);
            }
        }

        public Object parse(final String text) throws Throwable {
            Constructor constructor;

            try {
                constructor = type.getConstructor(new Class[] {
                    String.class
                });
            } catch (SecurityException e) {
                //  type( String ) constructor is not public
                throw new IOException("Could not create " + type.getName() + " from text");
            } catch (NoSuchMethodException e) {
                // No type( String ) constructor
                throw new IOException("Could not create " + type.getName() + " from text");
            }

            try {
                return constructor.newInstance(new Object[] {
                    text,
                });
            } catch (IllegalArgumentException illegalArgumentException) {
                throw new LayerSourceException("Could not create " + type.getName() + ": from '"
                        + text + "'", illegalArgumentException);
            } catch (InstantiationException instantiaionException) {
                throw new LayerSourceException("Could not create " + type.getName() + ": from '"
                        + text + "'", instantiaionException);
            } catch (IllegalAccessException illegalAccessException) {
                throw new LayerSourceException("Could not create " + type.getName() + ": from '"
                        + text + "'", illegalAccessException);
            } catch (InvocationTargetException targetException) {
                throw targetException.getCause();
            }
        }
    }
}
