package net.proyecto.sigti.notificaciones;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by choqu_000 on 06/08/2015.
 * Clase de conexion a internet
 */
public class ConnectionDetector {
    //Atributos
    private Context _context;
    //Constructor poliformismo
    public ConnectionDetector(Context context) {
        this._context = context;
    }

    //Metodo que comprobamos nuesta
    //Conexion internet
    public boolean isConnectingToInternet(){
        //Clase que responde a consultas sobre el estado de la conectividad de red
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity !=null){
            //Clase de la descripcion de la redfo
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if(info != null)
                for (int i = 0; i<info.length; i++)
                    if (info[i].getState()==NetworkInfo.State.CONNECTED)
                        return true;
        }
        return false;
    }

}
