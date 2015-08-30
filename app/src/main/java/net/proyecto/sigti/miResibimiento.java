package net.proyecto.sigti;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public final class miResibimiento extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Tu logica de negocio ira aqui. En caso de requerir mas" +
                " de unos milisegundos, deberia de la tarea a un servicio", Toast.LENGTH_LONG).show();

        MainActivity mostrar = new MainActivity();
        mostrar.showRednoEncontrada();
    }
}
