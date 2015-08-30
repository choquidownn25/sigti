package net.proyecto.sigti.vista;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.proyecto.sigti.R;

/**
 * Created by choqu_000 on 28/07/2015.
 */
public class Prueba extends Fragment{


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        //View rootView = inflater.inflate(R.layout.prueba, container, false);
        View rootView = inflater.inflate(R.layout.prueba, container, false);
        return rootView;
    }

}
