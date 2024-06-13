package com.torres.fitdev;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PlanEntrenamiento extends AppCompatActivity {
    private ListView listaEjercicios;
    private String etiquetaPlan;
    private String[][] datos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_plan_entrenamiento);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etiquetaPlan = getIntent().getStringExtra("etiquetaPlan");
        listaEjercicios = (ListView) findViewById(R.id.listaEjercicios);
        obtenerEjercicios();
        listaEjercicios.setAdapter(new Adaptador(this, datos, etiquetaPlan));


    }

    private void obtenerEjercicios() {

        String[] arrayEjercicios = {};

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "FitdevDB", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        String sql = "SELECT etiquetas, descripcion, ejercicios FROM PlanEntrenamiento WHERE etiquetas = '" + etiquetaPlan + "'";
        Cursor fila = db.rawQuery(sql, null);

        if(fila.moveToFirst()){
            TextView txtTipoPlan = (TextView) findViewById(R.id.txtTipoPlan);
            TextView txtDescripcion = (TextView) findViewById(R.id.txtDescripcionPlan);
            txtTipoPlan.setText(fila.getString(0));
            txtDescripcion.setText(fila.getString(1));
            String ejercicios = fila.getString(2);
            arrayEjercicios = ejercicios.split(",\\s*");
        }else
            Toast.makeText(this,"No existe el plan de entrenamiento", Toast.LENGTH_SHORT).show();
        fila.close();

        this.datos = new String[arrayEjercicios.length][4];
        int i = 0;
        for (String nombreEjercicio : arrayEjercicios) {
            Cursor cursor = db.rawQuery("SELECT nombre, repeticiones, series, duracion, descripcion FROM Ejercicio WHERE nombre = ?", new String[]{nombreEjercicio});
            // Recorre los resultados
            if (cursor.moveToFirst()) {
                do {
                    datos[i][0] = cursor.getString(0); // Nombre del ejercicio
                    datos[i][1] = cursor.getString(1); // Repeticiones del ejercicio
                    datos[i][2] = cursor.getString(2); // Series del ejercicio
                    datos[i][3] = cursor.getString(3); // Duracion del ejercicio
                    i++;
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        db.close();
    }
    public void Volver(View v){
        finish();
    }
}