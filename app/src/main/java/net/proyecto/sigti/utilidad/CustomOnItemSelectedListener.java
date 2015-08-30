package net.proyecto.sigti.utilidad;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

/**
 * Created by choqu_000 on 22/07/2015.
 */
public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(parent.getContext(),
                "Evento : \n" + parent.getItemAtPosition(position).toString(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

