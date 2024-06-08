package com.torres.fitdev;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Form2Activity extends AppCompatActivity {
    private String [] datosUsuario;
    private String fechaNacimiento, genero;
    private NumberPicker numberPicker1, numberPicker2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        numberPicker1 = findViewById(R.id.nmbPckAltura);
        numberPicker2 = findViewById(R.id.nmbPckPeso);
        this.EstablecerValoresPickers();

        datosUsuario = getIntent().getStringArrayExtra("datosUsuario");
        fechaNacimiento= getIntent().getStringExtra("fechaNacimiento");
        genero= getIntent().getStringExtra("genero");

    }
    public void EstablecerValoresPickers(){
        numberPicker1.setMinValue(100); // valor mínimo
        numberPicker1.setMaxValue(250); // valor máximo
        numberPicker1.setValue(170);
        numberPicker1.setWrapSelectorWheel(false); // deshabilitar el ciclo de valores
        numberPicker2.setMinValue(30);
        numberPicker2.setMaxValue(200);
        numberPicker2.setValue(60);
        numberPicker2.setWrapSelectorWheel(false);
    }
    public void volverAtras(View v){
        finish();
    }
    public void Continuar2(View v){
        int altura = numberPicker1.getValue(),
                peso = numberPicker2.getValue();
        Intent intent = new Intent(this, Form3Activity.class);
        intent.putExtra("datosUsuario",datosUsuario);
        intent.putExtra("fechaNacimiento",fechaNacimiento);
        intent.putExtra("genero",genero);
        intent.putExtra("altura", altura);
        intent.putExtra("peso", peso);

        startActivity(intent);
    }
}