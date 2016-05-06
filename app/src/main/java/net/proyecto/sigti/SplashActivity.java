package net.proyecto.sigti;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import net.proyecto.sigti.notifica.MainActivityNotificacion;
import net.proyecto.sigti.notifica.Util;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by choqu_000 on 27/07/2015.
 * net.proyecto.sigti
 */

public class SplashActivity extends Activity{
    //Atributos del sistema
    private long SPLASH_DELAY = 9000; //9 segundo maximos
    private static final int ALARM_REQUEST_CODE = 1;
    private Context contexts;

    String User_name;
    String Id_User_Name;
    String Email_User_Name;
    String Codigo_Empresa;
    String Codigo_Usuario;
    public String id_gcm_Google_Messeging;
    public String NombrePoseeCelular;
    private String Dato_id_usuarioNotificacion;
    private String Dato_Id_Google_Messeging;
    private String Dato_id_Gooogle_email;
    static final String TAG = "pavan";

    //creamos la actividad
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState); //cons
        //contiene la vista
        setContentView(R.layout.activity_splash);
        //Bloquear orientación de pantalla
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        contexts = getApplicationContext();
        if (isUserRegistered(contexts)) {

            Toast.makeText(contexts, "El Usuario es :  " + User_name, Toast.LENGTH_LONG).show();
            Toast.makeText(contexts, "Su ID  es :  " + Id_User_Name, Toast.LENGTH_LONG).show();
        }

        //Tiempo de nuestra actividad a ejecutar
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                //Intent mainIntetent = new Intent(getApplicationContext(), LoginActivity.class);
                //Intent mainIntetent = new Intent(getApplicationContext(), NotificaAlMainActivity.class);

                    //Intent mainIntetent = new Intent(getApplicationContext(), MainActivity.class);
                    //Intent mainIntetent = new Intent(getApplicationContext(), GridActiviti.class);
                    //Intent mainIntetent = new Intent(getApplicationContext(), MarcoMainActivity.class);
                Intent mainIntetent = new Intent(getApplicationContext(), MainActivityCarViewMarco.class);
                    startActivity(mainIntetent);
                    //Destruimos esta activity para prevenit
                    //que el usuario retorne aqui presionando el boton Atras.
                    finish();
            }
        };
        //Instancia de Clase tiemer
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_DELAY);

    }


    //Metodo en la cual  esta o no registrado
    public boolean isUserRegistered(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        //String User_name = prefs.getString(Util.USER_NAME, "");
        User_name = prefs.getString(Util.USER_NAME, "");
        //String Id_User_Name=prefs.getString(Util.PROPERTY_REG_ID, "");
        Id_User_Name=prefs.getString(Util.ID_USUARIO, "");
        Email_User_Name=prefs.getString(Util.EMAIL, "");
        id_gcm_Google_Messeging = prefs.getString(Util.PROPERTY_REG_ID, "");
        Codigo_Empresa=prefs.getString(Util.CODIGO_EMPRESA, "");
        String datoCodigo = prefs.getString(Util.ID_USUARIO, "");
        NombrePoseeCelular = User_name;
        Dato_Id_Google_Messeging=id_gcm_Google_Messeging;
        Dato_id_usuarioNotificacion = User_name;
        Dato_id_Gooogle_email = Email_User_Name;
        Codigo_Usuario = datoCodigo;


        if (User_name.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return false;
        }

        return true;
    }

    private SharedPreferences getGCMPreferences(Context context) {
        // Esta aplicacion muestra persiste el ID de registro en las preferencias compartidas
        // Como se almacena el ID de registro en su aplicacion depende de usted.
        return getSharedPreferences(MainActivityNotificacion.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }

}
