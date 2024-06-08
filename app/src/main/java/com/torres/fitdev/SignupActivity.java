package com.torres.fitdev;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Date;

public class SignupActivity extends AppCompatActivity {
    private EditText nombre, email, password;
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
            if (bndUsuarioAdmin) {
                siguiente.putExtra("datosUsuario",new String[]{nombre_str, email_str, password_str, "Cliente"});
            }else {
                siguiente.putExtra("datosUsuario",new String[]{nombre_str, email_str, password_str, "Admin"});
            }
            startActivity(siguiente);
        } else {
            Toast.makeText(this, "Rellene todos los campos!", Toast.LENGTH_SHORT).show();
        }

    }
}