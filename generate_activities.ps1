$activities = @(
    @{ name="SiseCard"; title="SISE Card"; xml="activity_sise_card" },
    @{ name="AulaVirtual"; title="Aula Virtual"; xml="activity_aula_virtual" },
    @{ name="Horario"; title="Horario"; xml="activity_horario" },
    @{ name="Matricula"; title="Matrícula"; xml="activity_matricula" },
    @{ name="Notas"; title="Notas"; xml="activity_notas" },
    @{ name="Cursos"; title="Cursos"; xml="activity_cursos" },
    @{ name="Pagos"; title="Pagos"; xml="activity_pagos" },
    @{ name="Trabajo"; title="Bolsa de Trabajo"; xml="activity_trabajo" },
    @{ name="Tramites"; title="Trámites"; xml="activity_tramites" },
    @{ name="Ubicate"; title="Ubícate"; xml="activity_ubicate" },
    @{ name="Contactanos"; title="Contáctanos"; xml="activity_contactanos" }
)

foreach ($act in $activities) {
    $javaCode = @"
package com.sise.appmobil;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class $($act.name)Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.$($act.xml));
        
        TextView tvTitle = findViewById(R.id.tv_titulo_barra);
        if(tvTitle != null) tvTitle.setText("$($act.title)");
        
        ImageView btnBack = findViewById(R.id.boton_volver);
        if(btnBack != null) btnBack.setOnClickListener(v -> finish());
    }
}
"@

    $xmlCode = @"
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:background="#F2F2F2">

    <!-- Barra Superior -->
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="60dp"
        android:background="#FFFFFF"
        android:gravity="center_vertical"
        android:orientation="horizontal"
        android:paddingHorizontal="16dp"
        android:elevation="4dp">

        <ImageView
            android:id="@+id/boton_volver"
            android:layout_width="32dp"
            android:layout_height="32dp"
            android:padding="4dp"
            android:src="@android:drawable/ic_menu_revert" />

        <TextView
            android:id="@+id/tv_titulo_barra"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_marginStart="16dp"
            android:layout_weight="1"
            android:text="$($act.title)"
            android:textColor="#333333"
            android:textSize="20sp"
            android:textStyle="bold" />
    </LinearLayout>

    <TextView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:text="Pantalla de $($act.title)\nEn construcción..."
        android:gravity="center"
        android:textSize="18sp"
        android:textColor="#666666"/>
</LinearLayout>
"@

    Out-File -FilePath "app\src\main\java\com\sise\appmobil\$($act.name)Activity.java" -InputObject $javaCode -Encoding UTF8
    Out-File -FilePath "app\src\main\res\layout\$($act.xml).xml" -InputObject $xmlCode -Encoding UTF8
}
