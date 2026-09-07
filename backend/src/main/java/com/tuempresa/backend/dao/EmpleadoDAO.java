package com.tuempresa.backend.dao;

import com.tuempresa.backend.model.Empleado;

import java.math.BigDecimal;
import java.util.List;

/**
 * Contrato de acceso a datos para la entidad Empleado.
 *
 * Se define como interfaz (y no directamente como clase concreta)
 * para que la capa de servicio dependa de una abstracción y no de una
 * implementación concreta de JDBC. Esto facilita, por ejemplo, crear
 * una implementación "falsa" (mock) en pruebas unitarias sin tocar
 * una base de datos real.
 */
public interface EmpleadoDAO {

    /**
     * Obtiene todos los empleados ordenados alfabéticamente de forma
     * ascendente por nombre. El orden se resuelve en la consulta SQL
     * (ORDER BY), no en memoria en Java, para mantener esa
     * responsabilidad en la capa de datos.
     */
    List<Empleado> listarTodosOrdenadosPorNombre();

    /**
     * Invoca el stored procedure sp_InsertarEmpleado para intentar
     * insertar un nuevo empleado.
     *
     * @param nombre  nombre del empleado a insertar
     * @param salario salario del empleado a insertar
     * @throws com.tuempresa.backend.exception.EmpleadoValidationException
     *         si la base de datos rechaza la inserción por una regla
     *         de negocio (ej. nombre duplicado, vía THROW 51000)
     * @throws com.tuempresa.backend.exception.DataAccessException
     *         si ocurre un error técnico de conexión/BD
     */
    void insertar(String nombre, BigDecimal salario);
}
