package net.proyecto.sigti.notifica;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by choqu_000 on 23/08/2015.
 * Clase que recibe la notificaion del tiempo
 *
 */
public class MyReceiver extends BroadcastReceiver  {

    int position = 0;
    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Tu l�gica de negocio ir� aqu�. En caso de requerir m�s" +
                " de unos milisegundos, deber�a de la tarea a un servicio", Toast.LENGTH_LONG).show();


    }



}
