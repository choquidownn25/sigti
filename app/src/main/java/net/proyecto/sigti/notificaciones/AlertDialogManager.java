package net.proyecto.sigti.notificaciones;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import net.proyecto.sigti.R;

/**
 * Created by choqu_000 on 06/08/2015.
 * Funcion que despliega el el mensaje de dialogo
 * @param //context - parametro contexto
 * @param //parametro title - alert dialog title
 * @param //mensaje - alert message
 * @param //status - success/failure (used to set icon)
 * 				 - pass null if you don't want icon
 */
public class AlertDialogManager {

    public void showAlertDialog(Context context, String title, String message,
        Boolean status){
        //Intancia de Clases para crear las alertas de dialogo
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        //Ajuste del titulo del dialogo
        alertDialog.setTitle(title);
        //Ajuste al mensaje de dialogo
        alertDialog.setMessage(message);
        //Si logica estatica diferente de null haga
        if(status != null)
            // Ajuste a la alerta de dialogo
            alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);

        // Ajuste del Botom OK
        // cuadro de dialogo para ejecutar codigo cuando se hace clic en un elemento
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        //Mostrar el mensaje
        alertDialog.show();
    }
}
