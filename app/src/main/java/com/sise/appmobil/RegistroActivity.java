package com.sise.appmobil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegistroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);

        EditText etName = findViewById(R.id.etName);
        EditText etEmail = findViewById(R.id.etEmail);
        EditText etPassword = findViewById(R.id.etPassword);
        EditText etPhone = findViewById(R.id.etPhone);
        EditText etBio = findViewById(R.id.etBio);
        Button btnRegister = findViewById(R.id.btnRegister);
        TextView tvToLogin = findViewById(R.id.tvToLogin);

        btnRegister.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {

                Toast.makeText(this, "Cuenta creada para " + name, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Por favor complete los campos obligatorios", Toast.LENGTH_SHORT).show();
            }
        });

        tvToLogin.setOnClickListener(v -> {
            finish();
        });
    }
}