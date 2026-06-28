package com.sise.appmobil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {

    private EditText etCorreo;
    private EditText etContrasena;
    private Button btnIngresar;
    private TextView tvRegistrar;

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etCorreo = findViewById(R.id.et_correo);
        etContrasena = findViewById(R.id.et_contrasena);
        btnIngresar = findViewById(R.id.btn_ingresar);
        tvRegistrar = findViewById(R.id.tv_registrar);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarAcceso();
            }
        });

        tvRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
                startActivity(intent);
            }
        });
    }

    private void validarAcceso() {
        String email = etCorreo.getText().toString().trim();
        String password = etContrasena.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        btnIngresar.setEnabled(false);

        executorService.execute(() -> {
            try {
                URL url = new URL("https://api.toq.life/api/auth/login");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);

                JSONObject jsonParam = new JSONObject();
                jsonParam.put("email", email);
                jsonParam.put("password", password);

                try(OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonParam.toString().getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                int code = conn.getResponseCode();
                if (code == 200 || code == 201) {
                    InputStream is = conn.getInputStream();
                    Scanner scanner = new Scanner(is, "UTF-8").useDelimiter("\\A");
                    String response = scanner.hasNext() ? scanner.next() : "";
                    
                    JSONObject respJson = new JSONObject(response);
                    JSONObject dataJson = respJson.optJSONObject("data");
                    String token = "";
                    if (dataJson != null) {
                        token = dataJson.optString("accessToken");
                    } else {
                        token = respJson.optString("accessToken");
                    }
                    
                    if (!token.isEmpty()) {
                        SharedPreferences prefs = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
                        prefs.edit().putString("accessToken", token).apply();
                    }

                    runOnUiThread(() -> {
                        Toast.makeText(LoginActivity.this, "¡Bienvenido!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    });
                } else {
                    InputStream es = conn.getErrorStream();
                    String errorResponse = "";
                    if (es != null) {
                        Scanner scanner = new Scanner(es, "UTF-8").useDelimiter("\\A");
                        errorResponse = scanner.hasNext() ? scanner.next() : "";
                    }
                    final String errorMsg = errorResponse;
                    android.util.Log.e("LoginActivity", "Error Code: " + code + ", Response: " + errorMsg);
                    runOnUiThread(() -> {
                        Toast.makeText(LoginActivity.this, "Error " + code + ": Credenciales incorrectas", Toast.LENGTH_LONG).show();
                        btnIngresar.setEnabled(true);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    Toast.makeText(LoginActivity.this, "Error de conexión: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    btnIngresar.setEnabled(true);
                });
            }
        });
    }
}