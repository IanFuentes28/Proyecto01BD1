package com.tuempresa.backend.dao.impl;

import com.tuempresa.backend.connection.ConnectionFactory;
import com.tuempresa.backend.dao.EmpleadoDAO;
import com.tuempresa.backend.exception.DataAccessException;
import com.tuempresa.backend.exception.EmpleadoValidationException;
import com.tuempresa.backend.model.Empleado;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación JDBC de EmpleadoDAO contra SQL Server.
 *
 * Responsabilidades de esta clase:
 *  - Construir y ejecutar sentencias SQL / llamadas a stored procedures.
 *  - Mapear ResultSet -> objetos Empleado.
 *  - Traducir SQLException a las excepciones de dominio del proyecto
 *    (DataAccessException / EmpleadoValidationException), de forma que
 *    ninguna capa superior necesite conocer java.sql.SQLException.
 *
 * Esta clase NO valida reglas de negocio (eso ya lo hace el stored
 * procedure sp_InsertarEmpleado en la base de datos, según lo acordado
 * en el diseño del proyecto). Su única labor es la mecánica de
 * comunicación con la BD.
 */
public class EmpleadoDAOImpl implements EmpleadoDAO {

    /**
     * Código de error nativo que SQL Server asocia al THROW 51000
     * lanzado manualmente en sp_InsertarEmpleado cuando el nombre del
     * empleado ya existe. Se usa para distinguir errores de negocio
     * de errores técnicos genéricos de SQL.
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
