package net.proyecto.sigti.notificaciones;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;

import net.proyecto.sigti.R;
import static net.proyecto.sigti.notificaciones.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static net.proyecto.sigti.notificaciones.CommonUtilities.EXTRA_MESSAGE;
import static net.proyecto.sigti.notificaciones.CommonUtilities.SENDER_ID;

/**
 * Created by choqu_000 on 18/08/2015.
 */
public class MainActivityNotificaciones extends Activity {
    //Atributos


    // etiqueta para mostrar mensajes GCM
    TextView lblMessage;

    // Asyntask
    AsyncTask<Void, Void, Void> mRegisterTask;

    // Alerta dialog manager
    AlertDialogManager alert = new AlertDialogManager();

    // Connexion o detector
    ConnectionDetector cd;

    //Atributos para el nombre y email
    public static String name;
    public static String email;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cd = new ConnectionDetector(getApplicationContext());

        // Checkea si internet esta activo
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            alert.showAlertDialog(MainActivityNotificaciones.this,
                    "Internet Conexion Erronea",
                    "Por favor conectarse a internet", false);
            // detener la ejecuci?n de c?digo de retorno
            return;
        }

        // Obtener nombre, direcci?n de correo electr?nico de la intenci?n
        Intent i = getIntent();

        name = i.getStringExtra("name");
        email = i.getStringExtra("email");

        // Aseg?rese de que el dispositivo cuenta con las dependencias correspondientes.
        GCMRegistrar.checkDevice(this);

        // Aseg?rese de que el manifiesto se estableci? correctamente -
        // comente esta l?nea, mientras que el desarrollo de la aplicaci?n.
        GCMRegistrar.checkManifest(this);

        lblMessage = (TextView) findViewById(R.id.lblMessage);

        registerReceiver(mHandleMessageReceiver, new IntentFilter(
                DISPLAY_MESSAGE_ACTION));

        // Obt?n GCM Identificaci?n del registro
        final String regId = GCMRegistrar.getRegistrationId(this);

        // Compruebe si el registro ya se presenta
        if (regId.equals("")) {

            // Registration is not present, register now with GCM
            GCMRegistrar.register(this, SENDER_ID);
            //Log.v("GCMRegistrar", "No se puede registrar Movil al sistema");
            Toast.makeText(getApplicationContext(), "Error Registar Dispositivo ", Toast.LENGTH_LONG).show();

        } else {
            //if(regId.equals("")){
            final Context context = this;
            // Dispoditivo registrado
            if (GCMRegistrar.isRegisteredOnServer(this)) {

                mRegisterTask = new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        // Registrar en nuestro servidor
                        // El servidor crea un nuevo usuario
                        ServerUtilities.register(context, name, email, regId);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        mRegisterTask = null;
                    }

                };
                mRegisterTask.execute(null, null, null);

                // Omite registro.
                Toast.makeText(getApplicationContext(), "Ya se ha registrado en GCM", Toast.LENGTH_LONG).show();
            } else {
                // Trate de registrarse de nuevo, pero no en el hilo de interfaz de usuario.
                // Tambi?n es necesario cancelar el OnDestroy hilo (),
                // Por lo tanto, el uso de AsyncTask en lugar de un hilo en bruto.
                final Context contexts = this;
                mRegisterTask = new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {

                        // Registrar en nuestro servidor
                        // El servidor crea un nuevo usuario
                        ServerUtilities.register(contexts, name, email, regId);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        mRegisterTask = null;
                    }

                };
                mRegisterTask.execute(null, null, null);
            }
        }
    }


    /**
     * REciviendo push del mensaje
     * */
    private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
            // Despertar m?vil si est? durmiendo
            WakeLocker.acquire(getApplicationContext());

            /**
             *
             * Tome las medidas oportunas en este mensaje en funci?n de sus
             * necesidades de aplicaciones
             * Por ahora estoy simplemente mostrarlo en la pantalla
             *
             * */

            // Mostrando mensaje recibido
            lblMessage.append(newMessage + "\n");
            Toast.makeText(getApplicationContext(), "Nuevo Mensaje: " + newMessage, Toast.LENGTH_LONG).show();

            // Liberar bloqueo
            WakeLocker.release();
        }
    };

    /**
     * Metodo de destruccion
     */
    @Override
    protected void onDestroy() {
        if (mRegisterTask != null) {
            mRegisterTask.cancel(true);
        }
        try {
            unregisterReceiver(mHandleMessageReceiver);
            GCMRegistrar.onDestroy(this);
        } catch (Exception e) {
            Log.e("Error Registrado", "> " + e.getMessage());
        }
        super.onDestroy();
    }

}


