package com.torres.fitdev;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Adaptador extends BaseAdapter {
    private static LayoutInflater inflater= null;
    Context context;
    String [][] datos;
    int[] datosImg;

    public Adaptador(Context context, String[][] datos, int[] datosImg) {
        this.context = context;
        this.datos = datos;
        this.datosImg = datosImg;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        final View vista = inflater.inflate(R.layout.elemento_lista, null);

        TextView titulo = (TextView) vista.findViewById(R.id.txtTituloEjercicio);
        TextView reps = (TextView) vista.findViewById(R.id.txtReps);
        TextView series = (TextView) vista.findViewById(R.id.txtSeries);
        ImageView imagen = (ImageView)vista.findViewById(R.id.imgEjercicio);

        titulo.setText(datos[i][0]);
        reps.setText(datos[i][1]);
        series.setText(datos[i][2]);
        imagen.setImageResource(datosImg[i]);

        return vista;
    }
    @Override
    public int getCount() {
        return datosImg.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


}
