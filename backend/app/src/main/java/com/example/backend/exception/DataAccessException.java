package com.example.backend.exception;


  //Representa un fallo técnico al comunicarse con la base de datos:
  //problemas de conexión, timeouts, errores de red, credenciales y así

 
 //RuntimeException para no obligar a la UI a envolver cada llamada en try/catch obligatoriamente
 // igual es distingubile por EmpleadoValidationException
 //DAO debe capturar SQLException genérica y relanzarla como DataAccessException
 
public class DataAccessException extends RuntimeException {

    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
