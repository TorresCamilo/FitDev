package com.torres.fitdev;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Form5Activity extends AppCompatActivity {
    private TimePicker timePckHoraEntreno;
    private EditText nombreGym;
    private String [] datosUsuario;
    private String fechaNacimiento, genero, objetivo, musculoObjetivo, experiencia, diasEntrenar;
    private int altura, peso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form5);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        timePckHoraEntreno = (TimePicker) findViewById(R.id.timePckHoraEntreno);
        timePckHoraEntreno.setIs24HourView(true);
        nombreGym = (EditText) findViewById(R.id.edtNombreGym);

        datosUsuario = getIntent().getStringArrayExtra("datosUsuario");
        fechaNacimiento= getIntent().getStringExtra("fechaNacimiento");
        genero= getIntent().getStringExtra("genero");
        altura= getIntent().getIntExtra("altura",0);
        peso= getIntent().getIntExtra("peso",0);
        objetivo= getIntent().getStringExtra("objetivo");
        musculoObjetivo= getIntent().getStringExtra("musculoObjetivo");
        experiencia= getIntent().getStringExtra("experiencia");
        diasEntrenar= getIntent().getStringExtra("diasEntrenar");
    }
    public void volverAtras(View v){
        finish();
    }
    public void Continuar5(View v){
        String time = getDateTime();
        String nombreGym_str = nombreGym.getText().toString();
        if (!(time.isEmpty() || nombreGym_str.isEmpty())) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("userName",datosUsuario[0]);
            intent.putExtra("email",datosUsuario[1]);

            Registrar();
            Toast.makeText(this, "Usuario creado exitosamente", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            //finish();
        } else {
            Toast.makeText(this, "Rellene todos los campos!", Toast.LENGTH_SHORT).show();
        }
    }
    public void Registrar(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "FitdevDB", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase(); //abrimos la bd con la opcion de lectura escritura

        ContentValues registro = new ContentValues();
        registro.put("nombre", datosUsuario[0]);
        registro.put("email", datosUsuario[1]);
        registro.put("password", datosUsuario[2]);
        registro.put("tipoUsuario", "Cliente");
        db.insert("Usuario", null, registro);//Escribimos a la bd en la tabla usuario

        ContentValues cliente = new ContentValues();
        cliente.put("fechaNacimiento", fechaNacimiento);
        cliente.put("genero", genero);
        cliente.put("altura", altura);
        cliente.put("peso", peso);
        cliente.put("objetivo", objetivo);
        cliente.put("musculoObjetivo", musculoObjetivo);
        cliente.put("experiencia", experiencia);
        cliente.put("diasEntrenar", diasEntrenar);
        cliente.put("horaEntreno", getDateTime());
        cliente.put("gym", nombreGym.getText().toString());
        cliente.put("email", datosUsuario[1]);
        db.insert("Cliente", null, cliente);

        db.close();
    }
    private String getDateTime() {
        int hour = timePckHoraEntreno.getCurrentHour();
        int minute = timePckHoraEntreno.getCurrentMinute();

        // Format the time into a String
        return String.format("%02d:%02d", hour, minute);
    }
}