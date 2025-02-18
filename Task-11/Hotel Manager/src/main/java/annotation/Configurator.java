package annotation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

public class Configurator {
    public static void configure(Object object) throws IllegalAccessException {
        Properties properties = loadProperties();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(ConfigProperty.class)) {
                ConfigProperty configProperty = field.getAnnotation(ConfigProperty.class);
                String propertyName = configProperty.propertyName().isEmpty()
                        ? object.getClass().getSimpleName() + "." + field.getName()
                        : configProperty.propertyName();
                String value = properties.getProperty(propertyName);
                field.setAccessible(true);
                if (field.getType() == int.class) {
                    field.setInt(object, Integer.parseInt(value));
                } else if (field.getType() == boolean.class) {
                    field.setBoolean(object, Boolean.parseBoolean(value));
                } else {
                    field.set(object, value);
                }
            }
        }
    }

    public static void configureStatic(Class<?> clazz) throws IllegalAccessException {
        Properties properties = loadProperties();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(ConfigProperty.class) && java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                ConfigProperty configProperty = field.getAnnotation(ConfigProperty.class);
                String propertyName = configProperty.propertyName().isEmpty()
                        ? clazz.getSimpleName() + "." + field.getName()
                        : configProperty.propertyName();
                String value = properties.getProperty(propertyName);
                field.setAccessible(true);
                if (field.getType() == int.class) {
                    field.setInt(null, Integer.parseInt(value));
                } else if (field.getType() == boolean.class) {
                    field.setBoolean(null, Boolean.parseBoolean(value));
                } else {
                    field.set(null, value);
                }
            }
        }
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = Configurator.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new FileNotFoundException("config.properties file not found in classpath");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error loading config.properties file", e);
        }
        return properties;
    }
}
