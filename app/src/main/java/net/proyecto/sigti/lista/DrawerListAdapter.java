package net.proyecto.sigti.lista;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.proyecto.sigti.R;
import net.proyecto.sigti.datos.DrawerItem;

import java.util.List;

/**
 * Created by choqu_000 on 28/07/2015.
 */
//Clase array lista los textos en la clase de la logica de datos
public class DrawerListAdapter extends ArrayAdapter<DrawerItem> {

    //Constructor poliformismo que lista los datos al cargar
    public DrawerListAdapter(Context context, List<DrawerItem> objects) {
        super(context, 0, objects);
    }

    //Metodo que obtiene la posicion
    public View getView(int position, View convertView, ViewGroup parent){
        //Si es null haga
        if(convertView == null){
            //objeto que contiene el texto
            LayoutInflater inflater =(LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.drawer_list_item, null);
        }
        //llamado de clases
        ImageView icon =(ImageView)convertView.findViewById(R.id.icon);
        TextView name = (TextView)convertView.findViewById(R.id.name);

        //Aca esta la imagen y el texto de nuestra lista desplegable
        DrawerItem item = getItem(position);
        icon.setImageResource(item.getIconId());
        name.setText(item.getName());

        //Retorne valro
        return convertView;
    }
}
