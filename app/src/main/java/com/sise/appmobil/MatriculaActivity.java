package com.sise.appmobil;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MatriculaActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matricula);
        
        TextView tvTitle = findViewById(R.id.tv_titulo_barra);
        if(tvTitle != null) tvTitle.setText("MatrÃ­cula");
        
        ImageView btnBack = findViewById(R.id.boton_volver);
        if(btnBack != null) btnBack.setOnClickListener(v -> finish());
    }
}
