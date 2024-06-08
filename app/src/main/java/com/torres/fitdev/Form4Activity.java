package com.torres.fitdev;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Form4Activity extends AppCompatActivity {
    private RadioButton[] rdbExperiencia, rdbDiasEntrenar;
    private String [] datosUsuario;
    private String fechaNacimiento, genero, objetivo, musculoObjetivo;
    private int altura, peso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form4);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setRdbExperiencia();
        setRdbDiasEntrenar();

        datosUsuario = getIntent().getStringArrayExtra("datosUsuario");
        fechaNacimiento= getIntent().getStringExtra("fechaNacimiento");
        genero= getIntent().getStringExtra("genero");
        altura= getIntent().getIntExtra("altura",0);
        peso= getIntent().getIntExtra("peso",0);
        objetivo= getIntent().getStringExtra("objetivo");
        musculoObjetivo= getIntent().getStringExtra("musculoObjetivo");
    }

    public void setRdbExperiencia() {
        this.rdbExperiencia = new RadioButton[] {
                findViewById(R.id.rdbPrincipiante),
                findViewById(R.id.rdbIntermediario),
                findViewById(R.id.rdbAvanzado)
        };
    }
    public void setRdbDiasEntrenar() {
        this.rdbDiasEntrenar = new RadioButton[] {
                findViewById(R.id.rdnLunes),
                findViewById(R.id.rdbMartes),
                findViewById(R.id.rdbMiercoles),
                findViewById(R.id.rdbJueves),
                findViewById(R.id.rdbViernes),
                findViewById(R.id.rdbSabado),
                findViewById(R.id.rdbDomingo)
        };
    }

    public String getRdbExperiencia() {
        for (RadioButton radioButton : rdbExperiencia) {
            if (radioButton.isChecked()) {
                return radioButton.getText().toString();
            }
        }
        return "";
    }

    public String getRdbDiasEntrenar() {
        StringBuilder selectedDays = new StringBuilder();
        for (RadioButton radioButton : rdbDiasEntrenar) {
            if (radioButton.isChecked()) {
                if (selectedDays.length() > 0) {
                    selectedDays.append(", ");
                }
                selectedDays.append(radioButton.getText().toString());
            }
        }
        return selectedDays.toString();
    }

    public void volverAtras(View v){
        finish();
    }
    public void Continuar4(View v){
        String experiencia = getRdbExperiencia();
        String diasEntrenar = getRdbDiasEntrenar();
        if (!(experiencia.isEmpty() || diasEntrenar.isEmpty())) {
            Intent intent = new Intent(this, Form5Activity.class);
            intent.putExtra("datosUsuario",datosUsuario);
            intent.putExtra("fechaNacimiento",fechaNacimiento);
            intent.putExtra("genero",genero);
            intent.putExtra("altura",altura);
            intent.putExtra("peso",peso);
            intent.putExtra("objetivo",objetivo);
            intent.putExtra("musculoObjetivo",musculoObjetivo);
            intent.putExtra("experiencia",experiencia);
            intent.putExtra("diasEntrenar",diasEntrenar);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Rellene todos los campos!", Toast.LENGTH_SHORT).show();
        }
    }
}