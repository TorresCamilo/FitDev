package com.torres.fitdev;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private AdminSQLiteOpenHelper admin;
    private SQLiteDatabase baseDeDatos;
    private EditText edtEmail, edtPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        admin = new AdminSQLiteOpenHelper(this, "FitdevDB", null, 1);
        baseDeDatos = admin.getWritableDatabase();

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);


    }
    public void Registrar(View v){
        Intent siguiente = new Intent(this, SignupActivity.class);
        startActivity(siguiente);
    }
    public void Ingresar(View v){
        String email = edtEmail.getText().toString(),
                password = edtPassword.getText().toString();
        if(!(email.isEmpty() || password.isEmpty())){
            String userType = Login(email, password);
            if (userType != null) {
                Intent siguiente = null;
                if (userType.equals("Administrador")) {
                    siguiente = new Intent(this, AdministradorActivity.class);
                    
                } else if (userType.equals("Cliente")) {
                    siguiente = new Intent(this, DashboardActivity.class);
                }
                siguiente.putExtra("email", email);
                startActivity(siguiente);
                edtEmail.setText("");
                edtPassword.setText("");
            } else
                Toast.makeText(this, "Email o contraseña incorrecta", Toast.LENGTH_SHORT).show();
        }


    }
    private String Login(String email, String password) {
        String[] columns = {"id", "tipoUsuario"};
        String selection = "email = ? AND password = ?";
        String[] selectionArgs = {email, password};
        Cursor cursor = baseDeDatos.query("Usuario", columns, selection, selectionArgs, null, null, null);

        String result = null;
        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex("tipoUsuario");
            if (columnIndex != -1) { // Verifica si la columna existe
                result = cursor.getString(columnIndex);
            }
        }
        cursor.close();
        return result;
    }
}