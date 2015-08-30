package net.proyecto.sigti.notifica;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Created by choqu_000 on 17/08/2015.
 *
 * Clase brokast donde se recibe el push de notificacion
 *
 */
public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {

    private static final int ALARM_REQUEST_CODE = 1;

    @Override
    public void onReceive(Context context, Intent intent) {

        //Se carga nuestro intet de notificacion
        ComponentName comp = new ComponentName(context.getPackageName(),
                GcmIntentService.class.getName());
        //Inicie el servicio, manteniendo el dispositivo despierto
        // mientras se esta poniendo en marcha.
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);


    }


}
