package com.mycompany.proyectobases;

import com.tuempresa.backend.exception.DataAccessException;
import com.tuempresa.backend.model.Empleado;
import com.tuempresa.backend.service.EmpleadoService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Servlet responsable de cargar la lista de empleados (ordenada
 * alfabéticamente por nombre, resuelto en la capa de datos) y
 * exponerla a index.jsp mediante un forward.
 *
 * Se mantiene esta carga fuera del JSP a propósito: un JSP debe
 * limitarse a renderizar datos que ya se le entregan (vista), sin
 * conocer EmpleadoService ni ninguna clase del backend. Esto evita
 * mezclar lógica de acceso a datos con marcado HTML.
 *
 * Se mapea a "/empleados" (y NO a "/"): mapear un servlet a "/" lo
 * convierte en el "default servlet" de la aplicación, lo cual
 * interceptaría también recursos estáticos y el propio index.jsp de
 * formas difíciles de depurar. En su lugar, "/empleados" se configura
 * como welcome-file en web.xml, logrando el mismo efecto (la raíz de
 * la app carga los datos primero) sin ese riesgo.
 */
@WebServlet(name = "ListarEmpleadosServlet", urlPatterns = {"/empleados"})
public class ListarEmpleadosServlet extends HttpServlet {

    private static final String ATRIBUTO_EMPLEADOS = "empleados";
    private static final String ATRIBUTO_ERROR = "errorConexion";
    private static final String VISTA_INDEX = "/index.jsp";

    private final EmpleadoService empleadoService = new EmpleadoService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            List<Empleado> empleados = empleadoService.obtenerEmpleados();
            request.setAttribute(ATRIBUTO_EMPLEADOS, empleados);

        } catch (DataAccessException e) {
            // Si la BD no responde, no se rompe la página: se muestra
            // un grid vacío junto con un mensaje de error legible.
            getServletContext().log("Error de acceso a datos al listar empleados", e);
            request.setAttribute(ATRIBUTO_EMPLEADOS, Collections.emptyList());
            request.setAttribute(ATRIBUTO_ERROR, "No fue posible cargar la lista de empleados. Verifique la conexión con la base de datos.");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(VISTA_INDEX);
        dispatcher.forward(request, response);
    }
}
