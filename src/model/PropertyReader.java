package model;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by Matus Cuper on 25.2.2017.
 *
 * Reader for application properties stored in application.properties file
 */
class PropertyReader {

    private static final Logger LOG = Logger.getLogger(PropertyReader.class.getName());

    private static Properties properties = null;


    private PropertyReader() {}

    /**
     * Reads specific property from application.properties file
     * @param propertyName to read
     * @return property value placed in etc/application.properties
     */
    static String readProperty(String propertyName) {
        readPropertyFile();
        return properties.getProperty(propertyName);
    }

    /**
     * Initial localization of property file and read it
     */
    private static void readPropertyFile() {
        if (properties == null) {
            properties = new Properties();
            try {
                properties.load(new FileInputStream("etc/application.properties"));
            } catch (IOException e) {
                LOG.severe("Could not find application.properties file.");
            }
        }
    }
}
