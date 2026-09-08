package com.example.backend.exception;


  //Representa un rechazo de la operación por una regla de negocio,
  //ya sea detectada en la base de datos o detectada en la propia capa de servicio antes de llegar a la BD

public class EmpleadoValidationException extends RuntimeException {

    public EmpleadoValidationException(String message) {
        super(message);
    }

    public EmpleadoValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
