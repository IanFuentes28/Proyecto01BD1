<!DOCTYPE html>
<html>
    <head>
        <title>Lista de Empleados</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <h2>Lista de Empleados</h2>
        
         <!-- tabla ya seria que la tabla saque info de la base de datos -->
        <table border="1">
            <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Salario</th>
            </tr>
            <!--ejemplo de un empleado -->
            <tr>
                <td>1</td>
                <td>Juan Pérez</td>
                <td>450000</td>
            </tr>
        </table>
        
        <br><br>
        
        <!-- boton la 'a' es lo que permite ir a otro lado-->
        <a href="Insertar.jsp">
            <button type="button">Insertar Empleado</button>
        </a>
    </body>
</html>