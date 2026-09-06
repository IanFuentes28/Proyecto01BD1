<!DOCTYPE html>
<html>
    <head>
        <title>Insertar Empleado</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script>
            function validarFormulario() {
                //agarro los imputs, la unica validacion que falta es la de revisar que el nombre no este en la base de datos
                let nombre = document.getElementById("name").value.trim();
                let salario = document.getElementById("salario").value.trim();
                
                //revisar que ninguno este vacio
                if (nombre === "" || salario === "") {
                    alert("Error, los campos no pueden estar vacÌos.");
                    return false;
                }
                
                //validar el nombre con tildes y guion
                let regexNombre = /^[A-Za-z¡…Õ”⁄·ÈÌÛ˙—Ò\s-]+$/;
                if (!regexNombre.test(nombre)) {
                    alert("Error, el nombre solo debe contener letras o un guion.");
                    return false;
                }
                
                //validar el salario solo numeros y con dos decimales opcionalmente
                let regexSalario = /^\d+(\.\d{1,2})?$/;
                if (!regexSalario.test(salario)) {
                    alert("Error, el salario debe ser un valor monetario (650 o 650.50, max 2 decimales)");
                    return false;
                }
                
                return true; // Si todo est· bien, permite enviar al Servlet
            }
        </script>
    </head>
    <body>
        <h2>Registrar Nuevo Empleado</h2>
       
        <form action="EmpleadoServlet" method="get" onsubmit="return validarFormulario();">
            <label>Nombre:</label>
            <input type="text" id="name" name="name"/> <br><br>
            
            <label>Salario:</label>
            <input type="text" id="salario" name="salario"/> <br><br>
            
            <input type="submit" value="Ingresar empleado"/>
        </form>
        <br>
        <a href="index.jsp"><button type="button">Regresar</button></a>
    </body>
</html>