package com.torres.fitdev;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ListEjercicios extends AppCompatActivity {
    private ListView lista;
    private String[][] datos;
    int[] datosImg = {
            R.drawable.ej1, R.drawable.ej2, R.drawable.ej3, R.drawable.ej4, R.drawable.ej5,
            R.drawable.ej6, R.drawable.ej7, R.drawable.ej8, R.drawable.ej9, R.drawable.ej10,
            R.drawable.ej11, R.drawable.ej12, R.drawable.ej13, R.drawable.ej14, R.drawable.ej15,
            R.drawable.ej16, R.drawable.ej17, R.drawable.ej18, R.drawable.ej19, R.drawable.ej20,
            R.drawable.ej21, R.drawable.ej22, R.drawable.ej23, R.drawable.ej24, R.drawable.ej25,
            R.drawable.ej26, R.drawable.ej27, R.drawable.ej28, R.drawable.ej29, R.drawable.ej30,
            R.drawable.ej31, R.drawable.ej32, R.drawable.ej33, R.drawable.ej34, R.drawable.ej35,
            R.drawable.ej36, R.drawable.ej37, R.drawable.ej38, R.drawable.ej39
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_ejercicios);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        lista = (ListView) findViewById(R.id.lvEjercicios);
        this.obtenerEjercicios();

        lista.setAdapter(new Adaptador(this, datos, datosImg));
    }
    public void obtenerEjercicios() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "FitdevDB", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        //SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT nombre, series, repeticiones FROM Ejercicio", null);

        this.datos = new String[cursor.getCount()][3];
        int i = 0;

        if (cursor.moveToFirst()) {
            do {
                datos[i][0] = cursor.getString(0); // Nombre del ejercicio
                datos[i][1] = cursor.getString(1); // Series del ejercicio
                datos[i][2] = cursor.getString(2); // Repeticiones del ejercicio
                i++;
            } while (cursor.moveToNext());
        }
        cursor.close();

    }
    public void Volver(View v){
        finish();
    }
}