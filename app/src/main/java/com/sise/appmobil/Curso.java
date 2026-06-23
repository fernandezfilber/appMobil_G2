package com.sise.appmobil;

public class Curso {
    private String nombre;
    private String profesor;
    private double promedio;

    public Curso(String nombre, String profesor, double promedio) {
        this.nombre = nombre;
        this.profesor = profesor;
        this.promedio = promedio;
    }

    public String getNombre() {
        return nombre;
    }

    public String getProfesor() {
        return profesor;
    }

    public double getPromedio() {
        return promedio;
    }
}