package net.proyecto.sigti.notificaciones;

import android.content.Context;
import android.os.PowerManager;

/**
 * Created by choqu_000 on 08/08/2015.
 */


public class WakeLocker {

    //Atributos.
    // La clase PowerManager: controla los dispoditivos moviles
    private static PowerManager.WakeLock wakeLock;

    public static void acquire(Context context) {
        if (wakeLock != null) wakeLock.release();

        //La API de primaria que va a utilizar es para el contexto del dispositivo
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK |
                PowerManager.ACQUIRE_CAUSES_WAKEUP |
                PowerManager.ON_AFTER_RELEASE, "WakeLock");
        wakeLock.acquire();
    }
    //release
    public static void release() {
        if (wakeLock != null) wakeLock.release(); wakeLock = null;
    }
}
