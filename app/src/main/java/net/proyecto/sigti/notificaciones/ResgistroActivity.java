package net.proyecto.sigti.notificaciones;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import net.proyecto.sigti.R;

import static net.proyecto.sigti.notificaciones.CommonUtilities.SENDER_ID;
import static net.proyecto.sigti.notificaciones.CommonUtilities.SERVER_URL;

/**
 * Created by choqu_000 on 18/08/2015.
 */
public class ResgistroActivity extends Activity {

    //Attributes
    // Instancia de clase de alertas
    AlertDialogManager alert = new AlertDialogManager();

    // Conexion de internet
    ConnectionDetector cd;

    // Atreibutos de objetos
    EditText txtName;
    EditText txtEmail;

    // Botom de registro
    Button btnRegister;
    //Creacion de Vista
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // implementa el layout a este fragmento
        setContentView(R.layout.activity_register);

        //Instancion de conecion a internet
        cd = new ConnectionDetector(getApplicationContext());
        //Checkea la conexion a internet
        if(!cd.isConnectingToInternet()){
            //Internet presenta fallso a la conexion
            alert.showAlertDialog(ResgistroActivity.this,
                    "Internet : Error de Conexion",
                    "Por favor intente conectarse de nuevo!", false);
            //para la ejecucion y retorna de nuevo
            return;
        }
        //Comprobamos si la configuracion del GCMse establece
        if(SERVER_URL==null || SENDER_ID == null || SERVER_URL.length() == 0 || SENDER_ID.length()==0){
            //Compara el server id y la url se cumpla
            alert.showAlertDialog(ResgistroActivity.this, "Error de Configuracion!",
                    "Por favor verique el Server ID y la URL", false);
            //para la ejecucion y retorna
            return;
        }

        //Mostrar los objetos de dise?os
        txtName = (EditText)findViewById(R.id.txtName);
        txtEmail = (EditText)findViewById(R.id.txtEmail);
        btnRegister = (Button)findViewById(R.id.btnRegister);

        /**
         * Evento del botom del regitro
         */
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //Los datos en el texto editados
                String name=txtName.getText().toString();
                String email=txtEmail.getText().toString();
                //Chequea la validacion
                if(name.trim().length()>0 && email.trim().length()>0){
                    Intent intent = new Intent(getApplicationContext(), MainActivityNotificaciones.class);
                    // Registro de usuario en nuestro servidor
                    // Envio detalles registraiton a MainActivity
                    intent.putExtra("name", name);
                    intent.putExtra("email", email);
                    startActivity(intent);
                    finish();
                } else {
                    //Usuario no puede llenar datos del formulario
                    //se le pide que complete el registro
                    alert.showAlertDialog(ResgistroActivity.this, "Error de Registro!", "Por favor complete sus datos gracias", false);
                }
            }
        });

    }
}