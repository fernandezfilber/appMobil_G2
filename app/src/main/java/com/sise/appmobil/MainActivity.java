package com.sise.appmobil;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private LinearLayout contenedorDinamico;
    private TextView tvTituloBarra;
    private FloatingActionButton btnAgregarTarea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);

        drawerLayout = findViewById(R.id.layout_principal);
        contenedorDinamico = findViewById(R.id.contenedor_actividades_dinamico);
        tvTituloBarra = findViewById(R.id.tv_titulo_barra);
        btnAgregarTarea = findViewById(R.id.btnAgregarTarea);

        TextView botonMenu = findViewById(R.id.boton_menu);
        TextView botonCerrar = findViewById(R.id.boton_cerrar);

        if (botonMenu != null) {
            botonMenu.setOnClickListener(v -> {
                if (drawerLayout != null) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            });
        }

        if (botonCerrar != null) {
            botonCerrar.setOnClickListener(v -> {
                if (drawerLayout != null) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
            });
        }

        // Configuración única del botón flotante fijo con Formulario Dialog
        if (btnAgregarTarea != null) {
            btnAgregarTarea.setOnClickListener(v -> {
                // Crear un contenedor con márgenes limpios para los campos de texto
                LinearLayout layoutFormulario = new LinearLayout(MainActivity.this);
                layoutFormulario.setOrientation(LinearLayout.VERTICAL);
                layoutFormulario.setPadding(60, 30, 60, 20);

                // Campo 1: Título de la tarea
                final EditText inputTitulo = new EditText(MainActivity.this);
                inputTitulo.setHint("Título (Ej: Examen Parcial)");
                layoutFormulario.addView(inputTitulo);

                // Campo 2: Descripción de la tarea
                final EditText inputDescripcion = new EditText(MainActivity.this);
                inputDescripcion.setHint("Descripción (Ej: Desarrollar el login)");
                layoutFormulario.addView(inputDescripcion);

                // Campo 3: Fecha de entrega
                final EditText inputFecha = new EditText(MainActivity.this);
                inputFecha.setHint("Fecha de entrega (Ej: 24/06/2026)");
                layoutFormulario.addView(inputFecha);

                // Construir la ventana emergente interactiva
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Añadir Nueva Tarea")
                        .setMessage("Ingresa los datos de la actividad académica:")
                        .setView(layoutFormulario)
                        .setPositiveButton("Agregar", (dialog, which) -> {
                            String titulo = inputTitulo.getText().toString().trim();
                            String descripcion = inputDescripcion.getText().toString().trim();
                            String fecha = inputFecha.getText().toString().trim();

                            // Validamos que no metan campos en blanco
                            if (!titulo.isEmpty() && !descripcion.isEmpty() && !fecha.isEmpty()) {
                                // 1. Creamos el objeto Tarea real
                                Tarea nuevaTarea = new Tarea(titulo, descripcion, fecha, "Pendiente");

                                // 2. Lo agregamos al repositorio global indexado
                                DataRepository.getInstance().getListaTareas().add(nuevaTarea);

                                // 3. Forzamos el refresco visual instantáneo del tablero
                                cargarVistaTablero();

                                Toast.makeText(MainActivity.this, "¡Tarea agregada con éxito!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .show();
            });
        }

        // Al iniciar la app por primera vez, cargamos el tablero
        cargarVistaTablero();

        setupNavigation();
    }

    private void setupNavigation() {
        View navTablero = findViewById(R.id.nav_tablero);
        View navCuenta = findViewById(R.id.nav_cuenta);
        View navCursos = findViewById(R.id.nav_cursos);
        View navCalendario = findViewById(R.id.nav_calendario);
        View navBandeja = findViewById(R.id.nav_bandeja);
        View navHistorial = findViewById(R.id.nav_historial);
        View navGuia = findViewById(R.id.nav_guia);

        if (navTablero != null) navTablero.setOnClickListener(v -> {
            cargarVistaTablero();
            cerrarMenu();
        });

        if (navCursos != null) navCursos.setOnClickListener(v -> {
            cargarVistaCursos();
            cerrarMenu();
        });

        if (navCuenta != null) navCuenta.setOnClickListener(v -> navigate(CuentaActivity.class));
        if (navCalendario != null) navCalendario.setOnClickListener(v -> navigate(CalendarioActivity.class));
        if (navBandeja != null) navBandeja.setOnClickListener(v -> navigate(BandejaEntradaActivity.class));
        if (navHistorial != null) navHistorial.setOnClickListener(v -> navigate(HistorialActivity.class));
        if (navGuia != null) navGuia.setOnClickListener(v -> navigate(GuiaUsoActivity.class));
    }

    //  METODO 1 CARGAR EL TABLERO DE TAREAS EN LOS ESPACIOS DINAMICOS
    private void cargarVistaTablero() {
        if (contenedorDinamico == null) return;
        contenedorDinamico.removeAllViews();
        if (tvTituloBarra != null) tvTituloBarra.setText("Tablero");

        // MOSTRAR el banner, el encabezado y el botón flotante correspondientes al Tablero
        View banner = findViewById(R.id.banner_principal);
        View encabezado = findViewById(R.id.tv_encabezado_actividades);
        if (banner != null) banner.setVisibility(View.VISIBLE);
        if (encabezado != null) encabezado.setVisibility(View.VISIBLE);
        if (btnAgregarTarea != null) btnAgregarTarea.setVisibility(View.VISIBLE); // Hacer visible el botón "+"

        // Inflamos tu layout 'tablero.xml' limpio
        LayoutInflater inflater = LayoutInflater.from(this);
        View vistaTablero = inflater.inflate(R.layout.tablero, contenedorDinamico, false);
        contenedorDinamico.addView(vistaTablero);

        // Configuramos su RecyclerView interno
        RecyclerView rvTareas = vistaTablero.findViewById(R.id.rvTareas);
        if (rvTareas != null) {
            rvTareas.setLayoutManager(new LinearLayoutManager(this));
            rvTareas.setNestedScrollingEnabled(false);

            ArrayList<Tarea> listaTareas = DataRepository.getInstance().getListaTareas();
            TareaAdapter tareaAdapter = new TareaAdapter(listaTareas);
            rvTareas.setAdapter(tareaAdapter);
        }
    }

    // METODO 2 CARGAR LOS CURSOS EN LOS ESPACIOS DINAMICOS
    private void cargarVistaCursos() {
        if (contenedorDinamico == null) return;
        contenedorDinamico.removeAllViews();
        if (tvTituloBarra != null) tvTituloBarra.setText("Mis Cursos");


        View banner = findViewById(R.id.banner_principal);
        View encabezado = findViewById(R.id.tv_encabezado_actividades);
        if (banner != null) banner.setVisibility(View.GONE);
        if (encabezado != null) encabezado.setVisibility(View.GONE);
        if (btnAgregarTarea != null) btnAgregarTarea.setVisibility(View.GONE); // Ocultar el botón "+" en Cursos


        LayoutInflater inflater = LayoutInflater.from(this);
        View vistaCursos = inflater.inflate(R.layout.cursos, contenedorDinamico, false);
        contenedorDinamico.addView(vistaCursos);

        // Configuramos el RecyclerView interno de los cursos
        RecyclerView rvCursos = vistaCursos.findViewById(R.id.rvCursos);
        if (rvCursos != null) {
            rvCursos.setLayoutManager(new LinearLayoutManager(this));
            rvCursos.setNestedScrollingEnabled(false);

            ArrayList<Curso> listaCursos = DataRepository.getInstance().getListaCursos();
            CursoAdapter cursoAdapter = new CursoAdapter(listaCursos);
            rvCursos.setAdapter(cursoAdapter);
        }
    }

    private void cerrarMenu() {
        if (drawerLayout != null) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    private void navigate(Class<?> activityClass) {
        cerrarMenu();
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }
}