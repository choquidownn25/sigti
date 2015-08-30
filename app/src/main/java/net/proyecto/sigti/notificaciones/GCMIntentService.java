package net.proyecto.sigti.notificaciones;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;

import net.proyecto.sigti.R;

import static net.proyecto.sigti.notificaciones.CommonUtilities.SENDER_ID;
import static net.proyecto.sigti.notificaciones.CommonUtilities.displayMessage;

/**
 * Created by choqu_000 on 07/08/2015.
 *
 * Clase para registros de movil
 */
public class GCMIntentService extends GCMBaseIntentService {

    //Atributos
    private static final String TAG = "GCMIntentService";

    public GCMIntentService(){
        super(SENDER_ID);
    }

    //Method called on Receiving a new message
    @Override
    protected void onMessage(Context context, Intent intent) {

        Log.i(TAG, "Mensaje Recibido");
        String message = intent.getExtras().getString("price");
        displayMessage(context, message);
        //Notificacion de Usuarios
        generateNotification(context, message);
        

    }

    //Emite una notificaci?n para informar al usuario de que el servidor
    // ha enviado un mensaje.
    private void generateNotification(Context context, String message) {
        int icon = R.drawable.ic_launcher;
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(icon, message, when);
        String title=context.getString(R.string.app_name);
        Intent notificationIntent=new Intent(context, MainActivityNotificaciones.class);
        // establecer intenciones por lo que no se inicia una nueva actividad
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent =
                PendingIntent.getActivity(context, 0, notificationIntent, 0);

        notification.setLatestEventInfo(context, title, message, intent);

        // Reproducir sonido de notificacion por defecto
        notification.defaults |= Notification.DEFAULT_SOUND;
        //notification.sound = Uri.parse("android.resource://" + context.getPackageName() + "your_sound_file_name.mp3");

        // Vibrar vibra si est? habilitado
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(0, notification);
    }

    //Metodo llamando el Error
    @Override
    protected void onError(Context context, String errorId) {

        Log.i(TAG, "Error Recibido: " + errorId);
        displayMessage(context, getString(R.string.gcm_error, errorId));

    }

    //Metodo llamado en el dispositivo registrado
    @Override
    protected void onRegistered(Context context, String registrationId) {
        Log.i(TAG, "Disppositivo Registrado: regId = " + registrationId);
        displayMessage(context, "Su dispositivo esta registrado con GCM");
        Log.d("NAME", MainActivityNotificaciones.name);
        ServerUtilities.register(context, MainActivityNotificaciones.name, MainActivityNotificaciones.email, registrationId);

    }

    @Override
    protected void onUnregistered(Context context, String registrationId) {
        Log.i(TAG, "Disositivo anulado");
        displayMessage(context, getString(R.string.gcm_unregistered));
        ServerUtilities.unregister(context, registrationId);
    }
    //Metodo Recuperar un error
    protected boolean onRecoverableError(Context context, String errorId) {
        //log de mensakes
        Log.i(TAG, "Recibido error recuperable: " + errorId);
        displayMessage(context, getString(R.string.gcm_recoverable_error, errorId));
        return super.onRecoverableError(context, errorId);
    }

    //Method called on receiving a deleted message
    protected void onDeletedMessages (Context context, int total){
        Log.i(TAG, "Recibir notificacion mensajes eliminados");
        String message = getString(R.string.gcm_deleted, total);
        displayMessage(context, message);
        //Notificacion de usuario
        generateNotification(context, message);

    }
}
