 package com.torres.fitdev;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;



 public class DashboardActivity extends AppCompatActivity {
    private String userName, email;
    private String objetivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        email = getIntent().getStringExtra("email");
        BuscarCliente();
        BuscarProgreso();
        BuscarNameUser();
    }
    public void Notificaciones(View v){
        Intent siguiente = new Intent(this, NotificacionesActivity.class);
        startActivity(siguiente);
    }
    public void ListaEjercicios(View v){
        Intent siguiente = new Intent(this, ListEjercicios.class);
        startActivity(siguiente);
    }
    public void BuscarProgreso(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "FitdevDB", null, 1);
        SQLiteDatabase BaseDeDatabase = admin.getWritableDatabase();

        String sql = "SELECT diasObjetivos, diasCompletados FROM PlanEntrenamiento WHERE etiquetas = '"+objetivo+"'";
        Cursor fila = BaseDeDatabase.rawQuery(sql, null);

        TextView txtProgresoCustom = (TextView) findViewById(R.id.txtProgresoCustom);

        if(fila.moveToFirst()){
            float diasObjetivos = fila.getFloat(0);
            float diasCompletados = fila.getFloat(1);
            int progreso = (int) Math.round((diasCompletados / diasObjetivos) * 100);
            String progreso_str = "Avance  "+progreso+"%";

            txtProgresoCustom.setText(progreso_str);
        }else{
            Toast.makeText(this,"No existe plan para este usuario", Toast.LENGTH_SHORT).show();
        }
        fila.close();
        BaseDeDatabase.close();
    }
    public void BuscarCliente(){
         AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "FitdevDB", null, 1);
         SQLiteDatabase BaseDeDatabase = admin.getWritableDatabase();

         String sql = "SELECT musculoObjetivo, experiencia FROM Cliente WHERE email = '"+email+"'";
         // Ejecutar la consulta con parámetros seguros
         Cursor fila = BaseDeDatabase.rawQuery(sql, null);
         TextView txtTitleCustom = (TextView) findViewById(R.id.txtTitleCustom);

         if(fila.moveToFirst()){
             objetivo = fila.getString(0);
             String texto = fila.getString(0) + " - " + fila.getString(1);
             txtTitleCustom.setText(texto);
         }else{
             Toast.makeText(this,"No se encontro el usuario", Toast.LENGTH_SHORT).show();
         }
         fila.close();
         BaseDeDatabase.close();
     }


    public void BuscarNameUser(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "FitdevDB", null, 1);
        SQLiteDatabase BaseDeDatabase = admin.getWritableDatabase();

        String sql = "SELECT nombre FROM Usuario WHERE email = '"+email+"'";
        Cursor fila = BaseDeDatabase.rawQuery(sql, null);

        TextView txtUserName = (TextView) findViewById(R.id.txtUserName);
        if(fila.moveToFirst()){
            txtUserName.setText(fila.getString(0));
            Toast.makeText(this, "Entroo", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"No existe este usuario", Toast.LENGTH_SHORT).show();
        }
        fila.close();
        BaseDeDatabase.close();
    }
}