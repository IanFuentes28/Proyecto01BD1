package com.tuempresa.backend.model;

import java.math.BigDecimal;

/**
 * Representa un registro de la tabla dbo.Empleado.
 *
 * Es un objeto plano (POJO): no contiene lógica de negocio ni de acceso
 * a datos. Su única responsabilidad es transportar los datos de un
 * empleado entre las distintas capas de la aplicación (DAO -> Service -> UI).
 *
 * Se usa BigDecimal para el salario (y no double/float) porque el tipo
 * SQL Server MONEY exige precisión monetaria exacta; los tipos de punto
 * flotante binario pueden introducir errores de redondeo inaceptables
 * en contextos monetarios.
 */
public class Empleado {

    private Integer id;
    private String nombre;
    private BigDecimal salario;

    public Empleado() {
    }

    public Empleado(String nombre, BigDecimal salario) {
        this.nombre = nombre;
        this.salario = salario;
    }

    public Empleado(Integer id, String nombre, BigDecimal salario) {
        this.id = id;
        this.nombre = nombre;
        this.salario = salario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    @Override
    public String toString() {
        return "Empleado{id=" + id + ", nombre='" + nombre + "', salario=" + salario + "}";
    }
}
