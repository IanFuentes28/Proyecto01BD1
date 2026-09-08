package com.example.backend.service;

import com.example.backend.dao.EmpleadoDAO;
import com.example.backend.dao.impl.EmpleadoDAOImpl;
import com.example.backend.exception.DataAccessException;
import com.example.backend.exception.EmpleadoValidationException;
import com.example.backend.model.Empleado;

import java.math.BigDecimal;
import java.util.List;


 //Punto de conexión entre backend y UI
 //Mae tienes que tomar en cuenta que la ui solo llame a esta clase
//empleado service llama las demás
 
public class EmpleadoService {

    private final EmpleadoDAO empleadoDAO;

    public EmpleadoService() {
        this.empleadoDAO = new EmpleadoDAOImpl();
    }

    //constructor auxiliar para pruebas
    public EmpleadoService(EmpleadoDAO empleadoDAO) {
        this.empleadoDAO = empleadoDAO;
    }

    //metodo para listar la BD
    public List<Empleado> obtenerEmpleados() {
        return empleadoDAO.listarTodosOrdenadosPorNombre();
    }

    //Intenta registrar un nuevo empleado
    /**
     *  Nota: validaciones de formato quedan en la UI
     * tipo nombre solo alfabético o guión
     * los salario con formato monetario como duplicados, en la base de datos
     * mediante sp_InsertarEmpleado
     * 
     * en resumen esta clase solo juega de puente
     * entran nombre y salario ya validados
     * 
     * EmpleadoValidationException si la BD rechaza por validación
     * DataAccessException si ocurre un error técnico
     */
    public void registrarEmpleado(String nombre, BigDecimal salario) {
        empleadoDAO.insertar(nombre, salario);
    }
}
