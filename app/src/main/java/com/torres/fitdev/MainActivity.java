package com.torres.fitdev;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

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
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "FitdevDB", null, 1);
        SQLiteDatabase baseDeDatos = admin.getWritableDatabase();

    }
    public void Registrar(View v){
        Intent siguiente = new Intent(this, SignupActivity.class);
        startActivity(siguiente);
    }
    public void Ingresar(View v){
        Intent siguiente = new Intent(this, DashboardActivity.class);
        startActivity(siguiente);
    }
}