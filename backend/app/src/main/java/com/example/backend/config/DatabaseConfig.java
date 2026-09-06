package com.example.backend.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


 //Responsable exclusivamente de leer y exponer los parámetros de la conexión a la base de datos.
 
 //valores se leen de un archivo database.properties ubicado en el classpath (src/main/resources).
 //hay que mantener las credenciales fuera del código fuente permite cambiarlas sin recompilar y evita subirlas por accidnete
 //IMPORTANTE el .properties se excluye vía .gitignore.
    //no abre conexiones, solo lee configuración.
 
public final class DatabaseConfig {

    private static final String CONFIG_FILE = "database.properties";

    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream input = DatabaseConfig.class
                .getClassLoader()
                .getResourceAsStream(CONFIG_FILE)) {

            if (input == null) {
                throw new IllegalStateException(
                        "No se encontró el archivo de configuración '" + CONFIG_FILE
                        + "' en el classpath (se esperaba en src/main/resources)."
                );
            }
            PROPERTIES.load(input);

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Error al leer el archivo de configuración '" + CONFIG_FILE + "'.", e
            );
        }
    }

    private DatabaseConfig() {
        // Clase de utilidad: no debe instanciarse.
    }

    public static String getHost() {
        return getRequiredProperty("db.host");
    }

    public static String getPort() {
        return getRequiredProperty("db.port");
    }

    public static String getDatabaseName() {
        return getRequiredProperty("db.name");
    }

    public static String getUser() {
        return getRequiredProperty("db.user");
    }

    public static String getPassword() {
        return getRequiredProperty("db.password");
    }

    /**
     * Construye la URL JDBC completa para SQL Server a partir de las
     * propiedades individuales. Centralizar esto aquí evita construir
     * la URL manualmente en varios lugares del código.
     */
    public static String buildConnectionUrl() {
        return "jdbc:sqlserver://" + getHost() + ":" + getPort()
                + ";databaseName=" + getDatabaseName()
                + ";encrypt=true;trustServerCertificate=true";
    }

    private static String getRequiredProperty(String key) {
        String value = PROPERTIES.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "La propiedad requerida '" + key + "' no está definida en " + CONFIG_FILE
            );
        }
        return value;
    }
}
