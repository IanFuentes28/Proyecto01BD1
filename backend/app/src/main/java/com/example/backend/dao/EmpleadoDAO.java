package com.example.backend.dao;

import com.example.backend.model.Empleado;

import java.math.BigDecimal;
import java.util.List;


 //Contrato de acceso a datos para la entidad Empleado.

public interface EmpleadoDAO {

     // Obtiene todos los empleados ordenados alfabéticamente de forma ascendente por nombre. El orden se resuelve en la consulta SQL
     //ORDER BY
    List<Empleado> listarTodosOrdenadosPorNombre();

    
     //Invoca el stored procedure sp_InsertarEmpleado para intentar insertar un nuevo empleado.
     //@param nombre  nombre del empleado a insertar
     //@param salario salario del empleado a insertar
     
    void insertar(String nombre, BigDecimal salario);
}
