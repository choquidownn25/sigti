package net.proyecto.sigti.notificaciones;

import android.content.Context;
import android.content.Intent;

/**
 *
 * Created by choqu_000 on 06/08/2015.
 *
 * Mensaje de Notificacion
 * Este metodo se define la interfaz de
 * usuario y el servicio de fondo.
 *
 * @param //clase contexto de la aplicacion.
 * @param //mensaje mensaje a desplegar.
 *
 */
public class CommonUtilities {


    // Registro de nuestro servidor colocar la url
    static final String SERVER_URL = "http://192.168.1.124/directv/register.php";

    // Numero del prooyecto Google "project id"
    static final String SENDER_ID = "670256636250";


    /**
     * Tag Usando los log de mensajes.
     */
    static final String TAG = "AndroidHive GCM";

    static final String DISPLAY_MESSAGE_ACTION =
            "com.androidhive.pushnotifications.DISPLAY_MESSAGE";

    //Mesaje
    static final String EXTRA_MESSAGE = "message";

    //Metodo para desplegar mensaje
    static void displayMessage(Context context, String message){
        //intnet para envio del mensaje
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }

}
