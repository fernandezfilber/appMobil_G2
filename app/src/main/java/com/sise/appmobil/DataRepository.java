package com.sise.appmobil;

import java.util.ArrayList;

public class DataRepository {
    private static DataRepository instance;
    private ArrayList<Tarea> listaTareas;


    private ArrayList<Curso> listaCursos;

    private DataRepository() {
        listaTareas = new ArrayList<>();
        // Tus datos de prueba de tareas se quedan tal cual
        listaTareas.add(new Tarea("Examen Parcial", "Desarrollar el login dinámico en Android.", "24/06/2026", "Pendiente"));
        listaTareas.add(new Tarea("Laboratorio 02", "Subir el repositorio de la app a GitHub.", "28/06/2026", "Pendiente"));
        listaTareas.add(new Tarea("Avance de Proyecto", "Diseño de interfaces de usuario completas.", "02/07/2026", "Entregado"));

        // 2. Inicializamos la lista de cursos con datos de prueba académicos
        listaCursos = new ArrayList<>();
        listaCursos.add(new Curso("Desarrollo de Aplicaciones Móviles I", "Ing. J. Silva", 16.5));
        listaCursos.add(new Curso("Herramientas de Programación Web", "Ing. M. Ramos", 15.0));
        listaCursos.add(new Curso("Administración de Base de Datos", "Ing. C. Torres", 14.2));
    }

    public static synchronized DataRepository getInstance() {
        if (instance == null) {
            instance = new DataRepository();
        }
        return instance;
    }

    public ArrayList<Tarea> getListaTareas() {
        return listaTareas;
    }

    public void agregarTarea(Tarea tarea) {
        listaTareas.add(tarea);
    }


    public ArrayList<Curso> getListaCursos() {
        return listaCursos;
    }

    public void agregarCurso(Curso curso) {
        listaCursos.add(curso);
    }
}