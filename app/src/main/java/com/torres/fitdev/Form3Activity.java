package com.torres.fitdev;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Form3Activity extends AppCompatActivity {
    private RadioButton[] radioButtons;
    private RadioButton rdbHipertrofia, rdbDefinicion, rdbPerderPeso;
    private String [] datosUsuario;
    private String fechaNacimiento, genero;
    private int altura, peso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        rdbHipertrofia = (RadioButton) findViewById(R.id.rdbHipertrofia);
        rdbDefinicion = (RadioButton) findViewById(R.id.rdbDefinicion);
        rdbPerderPeso = (RadioButton) findViewById(R.id.rdbPerderPeso);

        datosUsuario = getIntent().getStringArrayExtra("datosUsuario");
        fechaNacimiento= getIntent().getStringExtra("fechaNacimiento");
        genero= getIntent().getStringExtra("genero");
        altura= getIntent().getIntExtra("altura",0);
        peso= getIntent().getIntExtra("peso",0);

        setRdbMusculoObjetivo();
    }
    private void setRdbMusculoObjetivo() {
        radioButtons = new RadioButton[] {
                findViewById(R.id.rdbBalanceado),
                findViewById(R.id.rdbPecho),
                findViewById(R.id.rdbEspalda),
                findViewById(R.id.rdbBrazos),
                findViewById(R.id.rdbPiernas),
                findViewById(R.id.rdbAbdomen),
                findViewById(R.id.rdbGluteos)
        };

        for (RadioButton radioButton : radioButtons) {
            radioButton.setOnClickListener(v -> {
                for (RadioButton rb : radioButtons) {
                    if (rb != radioButton) {
                        rb.setChecked(false);
                    }
                }
            });
        }
    }
    public void Continuar3(View v){
        String objetivo = getObjetivo();
        String musculoObjetivo = getMusculoObjetivo();
        if (!(objetivo.isEmpty() || musculoObjetivo.isEmpty())) {
            Intent intent = new Intent(this, Form4Activity.class);
            intent.putExtra("datosUsuario",datosUsuario);
            intent.putExtra("fechaNacimiento",fechaNacimiento);
            intent.putExtra("genero",genero);
            intent.putExtra("altura",altura);
            intent.putExtra("peso",peso);
            intent.putExtra("objetivo",objetivo);
            intent.putExtra("musculoObjetivo",musculoObjetivo);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Rellene todos los campos!", Toast.LENGTH_SHORT).show();
        }
    }
    public void volverAtras(View v){
        finish();
    }
    public String getMusculoObjetivo() {
        for (RadioButton radioButton : radioButtons) {
            if (radioButton.isChecked()) {
                return radioButton.getText().toString();
            }
        }
        return "";
    }
    @NonNull
    private String getObjetivo() {
        String objetivo = "";
        if (rdbHipertrofia.isChecked()) {
            objetivo = "Hipertrofia";
        } else if (rdbDefinicion.isChecked()) {
            objetivo = "Definicion muscular";
        } else if (rdbPerderPeso.isChecked()) {
            objetivo = "Perder peso";
        }
        return objetivo;
    }
}