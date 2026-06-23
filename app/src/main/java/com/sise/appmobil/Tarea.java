package com.sise.appmobil;

public class Tarea {
    private String titulo;
    private String descripcion;
    private String fechaEntrega;
    private String estado;

    public Tarea(String titulo, String descripcion, String fechaEntrega, String estado) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaEntrega = fechaEntrega;
        this.estado = estado;
    }

    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public String getFechaEntrega() { return fechaEntrega; }
    public String getEstado() { return estado; }
}