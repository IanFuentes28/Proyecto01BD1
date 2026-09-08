package com.example.backend.dao.impl;

import com.example.backend.connection.ConnectionFactory;
import com.example.backend.dao.EmpleadoDAO;
import com.example.backend.exception.DataAccessException;
import com.example.backend.exception.EmpleadoValidationException;
import com.example.backend.model.Empleado;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


 //Implementación JDBC de EmpleadoDAO contra SQL Server.
 /**
 * esta clase se encarga de:
 *  -construir y ejecutar sentencias SQL / llamadas a stored procedures
 *  -mapear ResultSet a los objetos Empleado.
 *  -traducir SQLException a las excepciones de dominio
 *    (DataAccessException / EmpleadoValidationException), de forma que
 *    ninguna capa superior necesite conocer java.sql.SQLException.
 */

//tampoco valida cosas, las validaciones quedaron en ui y bd
public class EmpleadoDAOImpl implements EmpleadoDAO {

    /**
     * THROW 51000 es un codigo de error que sql asocia a este y que es
     * lanzado manualmente en sp_InsertarEmpleado cuando el nombre del
     * empleado ya esta. Se usa para distinguir errores de negocio
     * de errores técnicos.
     */
    private static final int SQL_ERROR_CODE_NOMBRE_DUPLICADO = 51000;

    private static final String SQL_LISTAR_EMPLEADOS =
            "SELECT id, Nombre, Salario FROM dbo.Empleado ORDER BY Nombre ASC";

    private static final String SQL_INSERTAR_EMPLEADO =
            "{call sp_InsertarEmpleado(?, ?)}";

    @Override
    public List<Empleado> listarTodosOrdenadosPorNombre() {
        List<Empleado> empleados = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_LISTAR_EMPLEADOS);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                empleados.add(mapearFila(resultSet));
            }

        } catch (SQLException e) {
            throw new DataAccessException(
                    "Error al obtener la lista de empleados desde la base de datos.", e
            );
        }

        return empleados;
    }

    @Override
    public void insertar(String nombre, BigDecimal salario) {
        try (Connection connection = ConnectionFactory.getConnection();
             CallableStatement statement = connection.prepareCall(SQL_INSERTAR_EMPLEADO)) {

            statement.setString(1, nombre);
            statement.setBigDecimal(2, salario);
            statement.execute();

        } catch (SQLException e) {
            if (e.getErrorCode() == SQL_ERROR_CODE_NOMBRE_DUPLICADO) {
                throw new EmpleadoValidationException(e.getMessage(), e);
            }
            throw new DataAccessException(
                    "Error al intentar insertar el empleado en la base de datos.", e
            );
        }
    }

    private Empleado mapearFila(ResultSet resultSet) throws SQLException {
        return new Empleado(
                resultSet.getInt("id"),
                resultSet.getString("Nombre"),
                resultSet.getBigDecimal("Salario")
        );
    }
}
