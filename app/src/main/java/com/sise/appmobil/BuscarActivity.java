package com.sise.appmobil;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class BuscarActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);
        
        TextView tvTitle = findViewById(R.id.tv_titulo_barra);
        if(tvTitle != null) tvTitle.setText("Buscar");
        
        ImageView btnBack = findViewById(R.id.boton_volver);
        if(btnBack != null) btnBack.setOnClickListener(v -> finish());
    }
}