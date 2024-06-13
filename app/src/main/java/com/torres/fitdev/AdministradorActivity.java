package com.torres.fitdev;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdministradorActivity extends AppCompatActivity {
    private String email;
    private EditText edtNombre, edtDesc, edtEjercicios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_administrador);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        email = getIntent().getStringExtra("email");
        edtNombre = (EditText) findViewById(R.id.edtNombrePlan);
        edtDesc = (EditText) findViewById(R.id.edtDescPlan);
        edtEjercicios = (EditText) findViewById(R.id.edtEjercicios);
        BuscarNameUser();
    }
    public void ListaEjercicios(View v){
        Intent siguiente = new Intent(this, ListEjercicios.class);
        startActivity(siguiente);
    }
    public void BuscarNameUser(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "FitdevDB", null, 1);
        SQLiteDatabase BaseDeDatabase = admin.getWritableDatabase();

        String sql = "SELECT nombre FROM Usuario WHERE email = '"+email+"'";
        Cursor fila = BaseDeDatabase.rawQuery(sql, null);

        TextView txtUserName = (TextView) findViewById(R.id.txtUserNameAdmin);
        if(fila.moveToFirst()){
            txtUserName.setText(fila.getString(0));
        }else{
            Toast.makeText(this,"No existe este usuario", Toast.LENGTH_SHORT).show();
        }
        fila.close();
        BaseDeDatabase.close();
    }
    public void RegistrarPlanes(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "FitdevDB", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase(); //abrimos la bd con la opcion de lectura escritura

        ContentValues registro = new ContentValues();
        registro.put("nombre", edtNombre.getText().toString());
        registro.put("etiquetas", edtNombre.getText().toString());
        registro.put("descripcion", edtDesc.getText().toString());
        registro.put("ejercicios", edtEjercicios.getText().toString());
        registro.put("clienteEmail", "");
        registro.put("retroalimentacion", "");

        db.insert("PlanEntrenamiento", null, registro);//Escribimos a la bd en la tabla plan
        db.close();
        edtNombre.setText("");
        edtDesc.setText("");
        edtEjercicios.setText("");
        Toast.makeText(this, "Se ha registrado el plan de entrenamiento!", Toast.LENGTH_SHORT).show();
    }

}