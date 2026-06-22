package com.sise.appmobil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);

        drawerLayout = findViewById(R.id.layout_principal);
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

        if (navTablero != null) navTablero.setOnClickListener(v -> navigate(TableroActivity.class));
        if (navCuenta != null) navCuenta.setOnClickListener(v -> navigate(CuentaActivity.class));
        if (navCursos != null) navCursos.setOnClickListener(v -> navigate(CursosActivity.class));
        if (navCalendario != null) navCalendario.setOnClickListener(v -> navigate(CalendarioActivity.class));
        if (navBandeja != null) navBandeja.setOnClickListener(v -> navigate(BandejaEntradaActivity.class));
        if (navHistorial != null) navHistorial.setOnClickListener(v -> navigate(HistorialActivity.class));
        if (navGuia != null) navGuia.setOnClickListener(v -> navigate(GuiaUsoActivity.class));
    }

    private void navigate(Class<?> activityClass) {
        if (drawerLayout != null) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }
}