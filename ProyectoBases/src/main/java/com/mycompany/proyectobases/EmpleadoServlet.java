package com.mycompany.proyectobases;

import com.example.backend.exception.DataAccessException;
import com.example.backend.exception.EmpleadoValidationException;
import com.example.backend.service.EmpleadoService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

/**
 * Servlet responsable de procesar la inserción de un nuevo empleado.
 *
 * Responsabilidad única: recibir los parámetros del formulario
 * (Insertar.jsp), delegar la inserción a EmpleadoService, y responder
 * al navegador con un resultado (éxito o el mensaje de error
 * correspondiente), redirigiendo de vuelta a index.jsp.
 *
 * Este servlet NO valida reglas de negocio ni formato: esa
 * responsabilidad ya está cubierta en dos lugares, según lo acordado
 * en el diseño del proyecto:
 *   - Formato (nombre alfabético/guión, salario bien formado): en el
 *     JavaScript de Insertar.jsp, antes de que el formulario se envíe.
 *   - Duplicados: en el stored procedure sp_InsertarEmpleado, en la
 *     base de datos.
 *
 * El servlet solo hace de "puente": toma la petición HTTP, la traduce
 * a una llamada de servicio, y traduce el resultado de vuelta a HTTP.
 */
@WebServlet(name = "EmpleadoServlet", urlPatterns = {"/EmpleadoServlet"})
public class EmpleadoServlet extends HttpServlet {

    private final EmpleadoService empleadoService = new EmpleadoService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombre = request.getParameter("name");
        String salarioTexto = request.getParameter("salario");

        response.setContentType("text/html;charset=UTF-8");

        try {
            BigDecimal salario = new BigDecimal(salarioTexto);
            empleadoService.registrarEmpleado(nombre, salario);
            // Se redirige a "empleados" (ListarEmpleadosServlet) y no
            // directo a index.jsp: el JSP es una vista pura que espera
            // recibir la lista ya cargada en el request.
            responderYRedirigir(response, "Inserción exitosa", "empleados");

        } catch (NumberFormatException e) {
            // Defensa adicional por si el formulario se envía sin pasar
            // por la validación de JavaScript (ej. JS deshabilitado).
            responderYRedirigir(response, "Error: el salario no tiene un formato válido.", "Insertar.jsp");

        } catch (EmpleadoValidationException e) {
            // Regla de negocio rechazada por la base de datos
            // (ej. nombre duplicado, vía el THROW del stored procedure).
            responderYRedirigir(response, "Error: " + e.getMessage(), "Insertar.jsp");

        } catch (DataAccessException e) {
            // Error técnico de conexión/BD. No se expone el detalle
            // interno (e.getMessage() de la excepción original) al
            // usuario final; se registra en el log del servidor para
            // que el equipo de desarrollo lo pueda diagnosticar.
            getServletContext().log("Error de acceso a datos al insertar empleado", e);
            responderYRedirigir(response, "Error: no fue posible conectar con la base de datos. Intente más tarde.", "Insertar.jsp");
        }
    }

    /**
     * Escribe un pequeño script que muestra una alerta con el mensaje
     * dado y luego redirige al destino indicado. Se conserva el mismo
     * patrón que ya usaba el equipo (alert + window.location.href) para
     * no romper la convención existente en el proyecto.
     */
    private void responderYRedirigir(HttpServletResponse response, String mensaje, String destino)
            throws IOException {
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html><head><meta charset=\"UTF-8\"></head><body>");
            out.println("<script>");
            out.println("alert('" + escaparParaJavaScript(mensaje) + "');");
            out.println("window.location.href = '" + destino + "';");
            out.println("</script>");
            out.println("</body></html>");
        }
    }

    /**
     * Escapa comillas simples y saltos de línea para evitar romper el
     * literal de JavaScript generado dinámicamente. No es un mecanismo
     * de seguridad contra XSS de propósito general; el mensaje aquí
     * siempre proviene de nuestras propias excepciones controladas,
     * nunca de entrada del usuario sin pasar antes por el mensaje fijo
     * del stored procedure.
     */
    private String escaparParaJavaScript(String texto) {
        return texto.replace("\\", "\\\\")
                .replace("'", "\\'")
                .replace("\n", " ")
                .replace("\r", " ");
    }
}
