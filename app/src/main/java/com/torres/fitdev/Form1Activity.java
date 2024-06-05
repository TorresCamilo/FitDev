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

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Form1Activity extends AppCompatActivity {
    private EditText txtFecha;
    private ImageButton btnFecha;
    private DatePicker dpFecha;
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
        txtFecha = findViewById(R.id.txtFecha);
        btnFecha = findViewById(R.id.btnFecha);
        dpFecha = findViewById(R.id.dpFecha);

        txtFecha.setText(getFechaDpFecha());

        /*dpFecha.setOnDateChangedListener(dpFecha, anio, mes, dia ->{
            txtFecha.setText(getFechaDpFecha());
            dpFecha.setVisibility(View.GONE);
        });*/
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
        Intent intent = new Intent(this, Form2Activity.class);
        startActivity(intent);
    }
}