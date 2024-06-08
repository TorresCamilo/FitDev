package com.torres.fitdev;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Date;

public class Form1Activity extends AppCompatActivity {
    private EditText txtFecha;
    private ImageButton btnFecha;
    private DatePicker dpFecha;
    private RadioButton rdbMasculino, rdbFemenino, rdbGay;
    private String[] datosUsuario;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txtFecha = findViewById(R.id.dateFechaNacimiento);
        btnFecha = findViewById(R.id.btnFecha);
        dpFecha = findViewById(R.id.dpFecha);
        txtFecha.setText(getFechaDpFecha());

        rdbFemenino = findViewById(R.id.rdbFemenino);
        rdbMasculino = findViewById(R.id.rdbMasculino);
        rdbGay = findViewById(R.id.rdbGay);

        datosUsuario = getIntent().getStringArrayExtra("datosUsuario");

        dpFecha.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                txtFecha.setText(getFechaDpFecha());
                dpFecha.setVisibility(View.GONE);
            }
        });

    }
    public String getFechaDpFecha(){
        String dia = String.valueOf(dpFecha.getDayOfMonth());
        String mes = String.valueOf(dpFecha.getMonth()+1);
        String anio = String.valueOf(dpFecha.getYear());

        return dia+"/"+mes+"/"+anio;
    }
    public void ShowCalendar(View v){
        dpFecha.setVisibility(View.VISIBLE);
    }
    public void volverAtras(View v){
        finish();
    }
    public void Continuar(View v){
        String genero = "";
        if (rdbMasculino.isChecked()) {
            genero = "Masculino";
        } else if (rdbFemenino.isChecked()) {
            genero = "Femenino";
        } else if (rdbGay.isChecked()) {
            genero = "Prefiere no decirlo";
        }

        String fechaNacimiento = getFechaDpFecha();

        if (!(genero.isEmpty() || fechaNacimiento.isEmpty())) {
            Intent intent = new Intent(this, Form2Activity.class);
            intent.putExtra("datosUsuario",datosUsuario);
            intent.putExtra("fechaNacimiento",fechaNacimiento);
            intent.putExtra("genero",genero);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Rellene todos los campos!", Toast.LENGTH_SHORT).show();
        }
    }
}