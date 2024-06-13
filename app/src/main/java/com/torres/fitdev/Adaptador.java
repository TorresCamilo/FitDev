package com.torres.fitdev;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    public Adaptador(Context context, String[][] datos, String plan){
        this.context = context;
        this.datos = datos;
        InicializarImg(plan);
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }
    public void InicializarImg(String plan){
        switch (plan) {
            case "Pecho":
                this.datosImg = new int[]{R.drawable.ej15, R.drawable.ej16, R.drawable.ej20, R.drawable.ej15, R.drawable.ej36};
                break;
            case "Espalda":
                this.datosImg = new int[]{R.drawable.ej19, R.drawable.ej18, R.drawable.ej33, R.drawable.ej37};
                break;
            case "Brazos":
                this.datosImg = new int[]{R.drawable.ej17, R.drawable.ej20, R.drawable.ej27, R.drawable.ej28, R.drawable.ej21};
                break;
            case "Piernas":
                this.datosImg = new int[]{R.drawable.ej3, R.drawable.ej4, R.drawable.ej6, R.drawable.ej8};
                break;
            case "Abdomen":
                this.datosImg = new int[]{R.drawable.ej12, R.drawable.ej28, R.drawable.ej29, R.drawable.ej38};
                break;
            case "Gluteos":
                this.datosImg = new int[]{R.drawable.ej6, R.drawable.ej25, R.drawable.ej24};
                break;
        }
    }
    @Override
    public View getView(int i, View view, ViewGroup parent) {
        final View vista = inflater.inflate(R.layout.elemento_lista, null);
        TextView titulo = (TextView) vista.findViewById(R.id.txtTituloEjercicio);
        TextView reps = (TextView) vista.findViewById(R.id.txtReps);
        TextView series = (TextView) vista.findViewById(R.id.txtSeries);
        TextView duracion = (TextView) vista.findViewById(R.id.txtDuracion);
        ImageView imagen = (ImageView)vista.findViewById(R.id.imgEjercicio);

        titulo.setText(datos[i][0]);
        reps.setText(datos[i][1]);
        series.setText(datos[i][2]);
        duracion.setText((datos[i][3]));
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
