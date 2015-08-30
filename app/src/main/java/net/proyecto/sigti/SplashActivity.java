package net.proyecto.sigti;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by choqu_000 on 27/07/2015.
 */

public class SplashActivity extends Activity{
    //Atributos del sistema
    private long SPLASH_DELAY = 9000; //9 segundo maximos
    private static final int ALARM_REQUEST_CODE = 1;

    //creamos la actividad
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState); //cons
        //contiene la vista
        setContentView(R.layout.activity_splash);

        //Tiempo de nuestra actividad a ejecutar
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent mainIntetent = new Intent(getApplicationContext(), LoginActivity.class);
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

}
