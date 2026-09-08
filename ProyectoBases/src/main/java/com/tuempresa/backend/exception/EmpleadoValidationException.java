package com.tuempresa.backend.exception;

/**
 * Representa un rechazo de la operación por una regla de negocio,
 * ya sea detectada en la base de datos (por ejemplo, el THROW 51000
 * del stored procedure sp_InsertarEmpleado cuando el nombre ya existe)
 * o detectada en la propia capa de servicio antes de llegar a la BD.
 *
 * Se distingue deliberadamente de DataAccessException: esta excepción
 * es "esperable" (el usuario cometió un error de negocio, ej. nombre
 * duplicado) y su mensaje está pensado para mostrarse directamente al
 * usuario final en la UI. DataAccessException, en cambio, indica un
 * problema técnico que normalmente NO se debe mostrar tal cual al
 * usuario.
 */
public class EmpleadoValidationException extends RuntimeException {

    public EmpleadoValidationException(String message) {
        super(message);
    }

    public EmpleadoValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
