package com.sise.appmobil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    // 1. Declaración de las variables de la vista
    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvRegister;

    // Credenciales de prueba en memoria
    private final String CORREO_CORRECTO = "admin@gmail.com";
    private final String PASSWORD_CORRECTO = "123456";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    // 2. Vinculación de IDs del XML
         etEmail = findViewById(R.id.etEmail);
         etPassword = findViewById(R.id.etPassword);
         btnLogin = findViewById(R.id.btnLogin);
         tvRegister = findViewById(R.id.tvRegister);

        // 3. Acción para el botón de Ingresar
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarAcceso();
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abre la pantalla de registro de forma dinámica
                Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
                startActivity(intent);
            }
        });
    }
    private void validarAcceso() {
        String emailIngresado = etEmail.getText().toString().trim();
        String passwordIngresado = etPassword.getText().toString().trim();

        // Validamos que los campos no se queden vacíos
        if (emailIngresado.isEmpty() || passwordIngresado.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }
        if (emailIngresado.equalsIgnoreCase(CORREO_CORRECTO) && passwordIngresado.equals(PASSWORD_CORRECTO)) {
            Toast.makeText(this, "¡Bienvenido!", Toast.LENGTH_SHORT).show();

            // Redirige al contenedor principal (activity_main)
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);

            // Finaliza el Login para que no regrese al presionar "Atrás"
            finish();
        } else {
            Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
        }
    }


}