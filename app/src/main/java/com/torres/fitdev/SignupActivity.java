package com.torres.fitdev;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Date;

public class SignupActivity extends AppCompatActivity {
    private EditText nombre, email, password;
    private Switch swAdmin;
    private Boolean bndUsuarioAdmin = false;//Si es falso es un cliente, de lo contrario es admin
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        nombre = (EditText) findViewById(R.id.edtNombre);
        email = (EditText) findViewById(R.id.edtEmail_registro);
        password = (EditText) findViewById(R.id.edtPassword_registro);
        swAdmin = (Switch) findViewById(R.id.swAdmin);

        DetectarModoDios();

    }

    private void DetectarModoDios() {
        nombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No necesitas hacer nada aquí
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Aquí verificamos si el texto ingresado es "Adm"
                if (s.toString().contains("Adm")) {
                    swAdmin.setVisibility(View.VISIBLE);
                } else {
                    swAdmin.setVisibility(View.GONE);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                // No necesitas hacer nada aquí
            }
        });
    }

    public void Volver (View v){
        finish();
    }
    public void Crear(View v){
        String nombre_str = nombre.getText().toString(),
                email_str = email.getText().toString(),
                password_str = password.getText().toString();
        if (!(nombre_str.isEmpty() || email_str.isEmpty() || password_str.isEmpty())) {
            Intent siguiente = new Intent(this, Form1Activity.class);
            if (!swAdmin.isChecked()) {
                siguiente.putExtra("datosUsuario",new String[]{nombre_str, email_str, password_str, "Cliente"});
                startActivity(siguiente);
            }else {
                this.RegistrarAdmin(new String[]{nombre_str, email_str, password_str});
                Toast.makeText(this, "Administrador registrado", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "Rellene todos los campos!", Toast.LENGTH_SHORT).show();
        }

    }
    public void RegistrarAdmin(String[] datosUsuario){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "FitdevDB", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase(); //abrimos la bd con la opcion de lectura escritura

        ContentValues registro = new ContentValues();
        registro.put("nombre", datosUsuario[0]);
        registro.put("email", datosUsuario[1]);
        registro.put("password", datosUsuario[2]);
        registro.put("tipoUsuario", "Administrador");
        db.insert("Usuario", null, registro);//Escribimos a la bd en la tabla usuario

        db.close();
    }
}