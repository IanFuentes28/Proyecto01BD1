package com.example.backend.service;

import com.example.backend.dao.EmpleadoDAO;
import com.example.backend.dao.impl.EmpleadoDAOImpl;
import com.example.backend.exception.DataAccessException;
import com.example.backend.exception.EmpleadoValidationException;
import com.example.backend.model.Empleado;

import java.math.BigDecimal;
import java.util.List;

/**
 * Punto de entrada de la capa de backend hacia la UI.
 *
 * Esta es la única clase que la interfaz de usuario (tu compañero)
 * necesita conocer e invocar directamente. La UI NO debe hablar con
 * EmpleadoDAO, ConnectionFactory, ni ninguna otra clase interna: eso
 * mantiene un límite claro entre capas y permite que el backend
 * cambie internamente (ej. agregar caché, cambiar de JDBC a un pool,
 * etc.) sin que el código de la UI se vea afectado.
 *
 * Uso esperado desde la UI:
 *
 *   EmpleadoService service = new EmpleadoService();
 *   List<Empleado> empleados = service.obtenerEmpleados();
 *
 *   try {
 *       service.registrarEmpleado(nombre, salario);
 *       // refrescar grid, mostrar éxito
 *   } catch (EmpleadoValidationException ex) {
 *       // mostrar ex.getMessage() al usuario (ej. "nombre ya registrado")
 *   } catch (DataAccessException ex) {
 *       // mostrar mensaje genérico de error técnico/conexión
 *   }
 */
public class EmpleadoService {

    private final EmpleadoDAO empleadoDAO;

    public EmpleadoService() {
        this.empleadoDAO = new EmpleadoDAOImpl();
    }

    /**
     * Constructor pensado para pruebas unitarias o para inyectar una
     * implementación distinta de EmpleadoDAO sin modificar esta clase.
     */
    public EmpleadoService(EmpleadoDAO empleadoDAO) {
        this.empleadoDAO = empleadoDAO;
    }

    /**
     * Devuelve la lista de empleados ordenada alfabéticamente por
     * nombre, lista para pintarse directamente en el grid de la UI.
     */
    public List<Empleado> obtenerEmpleados() {
        return empleadoDAO.listarTodosOrdenadosPorNombre();
    }

    /**
     * Intenta registrar un nuevo empleado.
     *
     * Nota de diseño: por acuerdo del equipo, las validaciones de
     * formato (nombre solo alfabético/guión, salario con formato
     * monetario válido) se realizan en la capa de UI y, para las
     * reglas de negocio como duplicados, en la base de datos vía
     * sp_InsertarEmpleado. Esta clase no revalida esos formatos; se
     * limita a delegar la operación y traducir el resultado.
     *
     * @param nombre  nombre ya validado por la UI
     * @param salario salario ya validado por la UI
     * @throws EmpleadoValidationException si la BD rechaza la
     *         operación por una regla de negocio (ej. nombre duplicado)
     * @throws DataAccessException si ocurre un error técnico de
     *         conexión o de la base de datos
     */
    public void registrarEmpleado(String nombre, BigDecimal salario) {
        empleadoDAO.insertar(nombre, salario);
    }
}
