package net.proyecto.sigti;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by choqu_000 on 28/07/2015.
 */
public class CountryFragment extends Fragment{

    //Creacion de la vista
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState){
        //Recuperando el numero elemento seleccionado
        int position = getArguments().getInt("position");
        //Lista el menu
        String[] countries = getResources().getStringArray(R.array.countries);
        //SE crea el correspondiente fragmento
        View v = inflater.inflate(R.layout.fragment_layout, container, false);

        //obteniendo el texto del fragmento
        TextView tv =(TextView)v.findViewById(R.id.tv_content);
        //Obteniendo la seleccion del texto
        tv.setText(countries[position]);

        return v;
    }
}
