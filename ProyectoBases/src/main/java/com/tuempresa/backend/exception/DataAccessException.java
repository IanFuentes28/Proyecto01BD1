package com.tuempresa.backend.exception;

/**
 * Representa un fallo técnico al comunicarse con la base de datos:
 * problemas de conexión, timeouts, errores de red, credenciales
 * inválidas, el servidor no disponible, etc.
 *
 * Es una RuntimeException (no checked) para no obligar a la UI a
 * envolver cada llamada en try/catch obligatoriamente, pero sigue
 * siendo específica y capturable quien lo necesite distinguir de
 * errores de validación de negocio (ver EmpleadoValidationException).
 *
 * La capa DAO debe capturar SQLException genérica y relanzarla como
 * DataAccessException, para que capas superiores (Service, UI) nunca
 * dependan directamente de java.sql.SQLException.
 */
public class DataAccessException extends RuntimeException {

    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
