<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.tuempresa.backend.model.Empleado" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Lista de Empleados</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <h2>Lista de Empleados</h2>

        <%
            // Esta vista NO conoce EmpleadoService ni el backend: solo
            // lee los atributos que ListarEmpleadosServlet ya dejó
            // preparados en el request antes del forward. La vista se
            // limita a renderizar, sin lógica de acceso a datos.
            String errorConexion = (String) request.getAttribute("errorConexion");
            List<Empleado> empleados = (List<Empleado>) request.getAttribute("empleados");
        %>

        <% if (errorConexion != null) { %>
            <p style="color: red;"><%= errorConexion %></p>
        <% } %>

        <table border="1">
            <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Salario</th>
            </tr>
            <% if (empleados != null) {
                for (Empleado empleado : empleados) { %>
            <tr>
                <td><%= empleado.getId() %></td>
                <td><%= empleado.getNombre() %></td>
                <td><%= empleado.getSalario() %></td>
            </tr>
            <% }
            } %>
        </table>

        <br><br>

        <!-- boton la 'a' es lo que permite ir a otro lado-->
        <a href="Insertar.jsp">
            <button type="button">Insertar Empleado</button>
        </a>
    </body>
</html>
