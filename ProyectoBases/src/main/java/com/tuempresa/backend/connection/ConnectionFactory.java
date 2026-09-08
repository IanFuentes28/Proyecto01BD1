package com.tuempresa.backend.connection;

import com.tuempresa.backend.config.DatabaseConfig;
import com.tuempresa.backend.exception.DataAccessException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Única responsable de abrir conexiones JDBC hacia SQL Server.
 *
 * Se aísla esta lógica en una clase propia (en vez de que cada DAO
 * llame a DriverManager.getConnection directamente) por dos motivos:
 *
 * 1. Si en el futuro se necesita introducir un connection pool
 *    (ej. HikariCP) por crecimiento de la app, solo esta clase cambia;
 *    ningún DAO necesita modificarse.
 * 2. Centraliza el manejo de errores de conexión en un solo lugar,
 *    traduciendo SQLException a DataAccessException.
 */
public final class ConnectionFactory {

    private ConnectionFactory() {
        // Clase de utilidad: no debe instanciarse.
    }

    /**
     * Abre una nueva conexión a la base de datos configurada en
     * database.properties.
     *
     * El llamador es responsable de cerrar la conexión (idealmente
     * mediante try-with-resources).
     *
     * @return una nueva Connection lista para usarse
     * @throws DataAccessException si la conexión no puede establecerse
     */
public static Connection getConnection() {
        try {
            // Carga explícita del driver de SQL Server para Tomcat
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            return DriverManager.getConnection(
                    DatabaseConfig.buildConnectionUrl(),
                    DatabaseConfig.getUser(),
                    DatabaseConfig.getPassword()
            );
        } catch (ClassNotFoundException e) {
            throw new DataAccessException(
                    "No se encontró el driver JDBC de SQL Server en el classpath.", e
            );
        } catch (SQLException e) {
            throw new DataAccessException(
                    "No fue posible establecer conexión con la base de datos.", e
            );
        }
    }
}
