package com.example.backend.connection;

import com.example.backend.config.DatabaseConfig;
import com.example.backend.exception.DataAccessException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


 //abre conexiones JDBC hacia SQL Server.
 //para el manejo de errores

public final class ConnectionFactory {

    private ConnectionFactory() {
        // Clase de utilidad, no debe instanciarse
    }


    //Abre una nueva conexión a la base de datos configurada en database.properties
    //El caller es responsable de cerrar la conexión y ojala mediante try-with-resources
    //devuelve una nueva Connection lista para usarse
    //DataAccessException si la conexión no puede establecerse
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    DatabaseConfig.buildConnectionUrl(),
                    DatabaseConfig.getUser(),
                    DatabaseConfig.getPassword()
            );
        } catch (SQLException e) {
            throw new DataAccessException(
                    "No fue posible establecer conexión con la base de datos.", e
            );
        }
    }
}
