package com.sise.appmobil;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.sise.appmobil.network.ApiService;
import com.sise.appmobil.network.ApiClient;
import com.sise.appmobil.network.BaseResponse;
import com.sise.appmobil.models.User;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private LinearLayout contenedorDinamico;
    private TextView tvTituloBarra;
    private FloatingActionButton btnAgregarTarea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.layout_principal);
        contenedorDinamico = findViewById(R.id.contenedor_actividades_dinamico);
        tvTituloBarra = findViewById(R.id.tv_titulo_barra);
        btnAgregarTarea = findViewById(R.id.btnAgregarTarea);

        TextView botonCerrar = findViewById(R.id.boton_cerrar);
        if (botonCerrar != null) {
            botonCerrar.setOnClickListener(v -> {
                if (drawerLayout != null) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
            });
        }

        // Configuración para Cerrar Sesión con los 3 puntos
        ImageView botonOpciones = findViewById(R.id.boton_opciones);
        if (botonOpciones != null) {
            botonOpciones.setOnClickListener(v -> {
                android.content.SharedPreferences prefs = getSharedPreferences("AppMobilPrefs", MODE_PRIVATE);
                prefs.edit().clear().apply();
                android.content.Intent intent = new android.content.Intent(MainActivity.this, LoginActivity.class);
                intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            });
        }

        // Configurar Barra Inferior (Bottom Navigation)
        TextView navAjustes = findViewById(R.id.nav_ajustes);
        TextView navHorario = findViewById(R.id.nav_horario);
        TextView navAlertas = findViewById(R.id.nav_alertas);
        TextView navBuscar = findViewById(R.id.nav_buscar);

        if (navAjustes != null) navAjustes.setOnClickListener(v -> navigate(AjustesActivity.class));
        if (navHorario != null) navHorario.setOnClickListener(v -> navigate(HorarioActivity.class));
        if (navAlertas != null) navAlertas.setOnClickListener(v -> navigate(AnunciosActivity.class));
        if (navBuscar != null) navBuscar.setOnClickListener(v -> navigate(BuscarActivity.class));
        // El botón flotante de agregar tarea ya no se usa aquí en el tablero principal 
        
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

        if (navCursos != null) navCursos.setOnClickListener(v -> navigate(CursosActivity.class));


    }

    //  METODO 1 CARGAR EL TABLERO DE TAREAS EN LOS ESPACIOS DINAMICOS
    private void cargarVistaTablero() {
        if (contenedorDinamico == null) return;
        contenedorDinamico.removeAllViews();
        if (tvTituloBarra != null) tvTituloBarra.setText("SISE»");

        // Ocultar elementos antiguos
        View banner = findViewById(R.id.banner_principal);
        View encabezado = findViewById(R.id.tv_encabezado_actividades);
        if (banner != null) banner.setVisibility(View.GONE);
        if (encabezado != null) encabezado.setVisibility(View.GONE);

        // Inflamos el nuevo layout 'layout_tablero.xml'
        LayoutInflater inflater = LayoutInflater.from(this);
        View vistaTablero = inflater.inflate(R.layout.layout_tablero, contenedorDinamico, false);
        contenedorDinamico.addView(vistaTablero);

        TextView tvHolaNombre = vistaTablero.findViewById(R.id.tv_hola_nombre);

        // Enlazar clics de las tarjetas del tablero
        View cardHorario = vistaTablero.findViewById(R.id.card_horario);
        if (cardHorario != null) cardHorario.setOnClickListener(v -> navigate(HorarioActivity.class));

        View cardMatricula = vistaTablero.findViewById(R.id.card_matricula);
        if (cardMatricula != null) cardMatricula.setOnClickListener(v -> navigate(MatriculaActivity.class));

        View cardNotas = vistaTablero.findViewById(R.id.card_notas);
        if (cardNotas != null) cardNotas.setOnClickListener(v -> navigate(NotasActivity.class));

        View cardCursos = vistaTablero.findViewById(R.id.card_cursos);
        if (cardCursos != null) cardCursos.setOnClickListener(v -> navigate(CursosActivity.class));

        View cardAsistencias = vistaTablero.findViewById(R.id.card_asistencias);
        if (cardAsistencias != null) cardAsistencias.setOnClickListener(v -> navigate(AsistenciasActivity.class));

        View cardTareas = vistaTablero.findViewById(R.id.card_tareas);
        if (cardTareas != null) cardTareas.setOnClickListener(v -> navigate(TareasActivity.class));

        View cardAnuncios = vistaTablero.findViewById(R.id.card_anuncios);
        if (cardAnuncios != null) cardAnuncios.setOnClickListener(v -> navigate(AnunciosActivity.class));

        // Llamar a Retrofit para obtener datos del usuario
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);
        
        apiService.getMe().enqueue(new Callback<BaseResponse<User>>() {
            @Override
            public void onResponse(Call<BaseResponse<User>> call, Response<BaseResponse<User>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    String fullName = response.body().getData().getName();
                    String firstName = fullName.split(" ")[0]; // Solo el primer nombre
                    if (tvHolaNombre != null) {
                        tvHolaNombre.setText("Hola,\n" + firstName);
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<User>> call, Throwable t) {
                t.printStackTrace();
            }
        });
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