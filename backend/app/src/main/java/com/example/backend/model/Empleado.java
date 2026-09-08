package com.example.backend.model;

import java.math.BigDecimal;


 // es un registro de la tabla dbo.Empleado.
 
 //Es un objeto plano sin lógica de negocio ni de acceso a datos.
 //hace DAO a Service a UI
 
 //Se usa BigDecimal para el salario porque el SQL Server MONEY pide precisión monetaria exacta

public class Empleado {

    private Integer id;
    private String nombre;
    private BigDecimal salario;

    public Empleado() {
    }
    //constructor
    public Empleado(String nombre, BigDecimal salario) {
        this.nombre = nombre;
        this.salario = salario;
    }
    //constructor para la BD
    public Empleado(Integer id, String nombre, BigDecimal salario) {
        this.id = id;
        this.nombre = nombre;
        this.salario = salario;
    }
    //gets y sets
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
