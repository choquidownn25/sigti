package net.proyecto.sigti.notificaciones;

import android.content.Context;
import android.util.Log;

import com.google.android.gcm.GCMRegistrar;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import net.proyecto.sigti.R;

import static net.proyecto.sigti.notificaciones.CommonUtilities.SERVER_URL;
import static net.proyecto.sigti.notificaciones.CommonUtilities.TAG;
import static net.proyecto.sigti.notificaciones.CommonUtilities.displayMessage;
/**
 * Created by choqu_000 on 06/08/2015.
 * Registrar esta cuenta para el dispositivo movil dentro del servidor.
 *
 */
public class ServerUtilities {
    //Atributos
    private static final int MAX_ATTEMPTS = 5;
    private static final int BACKOFF_MILLI_SECONDS = 2000;
    private static final Random random = new Random();

    //Metodo de registro
    static void register(final Context context, String name,
                         String email, final String regId){
        //Mensaje de para registro movil
        Log.i(TAG, "Registrando Movil (regId = " + regId + ")");
        String serverUrl = SERVER_URL;
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", regId);
        params.put("name", name);
        params.put("email", email);

        long backoff = BACKOFF_MILLI_SECONDS + random.nextInt(1000);

        // Una vez GCM devuelve un ID de registro, es necesario registrarse en nuestro servidor
        // Como el servidor puede estar abajo, nos volver? a intentar un par  Veces.

        for (int i = 1; i <= MAX_ATTEMPTS; i++) {
            Log.d(TAG, "Intentando #" + i + " registrar");
            try {
                displayMessage(context, context.getString(
                        R.string.server_registering, i, MAX_ATTEMPTS));
                post(serverUrl, params);
                GCMRegistrar.setRegisteredOnServer(context, true);
                String message = context.getString(R.string.server_registered);
                CommonUtilities.displayMessage(context, message);
                return;
            } catch (IOException e) {

                // Aqu? estamos simplificando y volver a intentar
                // Aplicaci?n, debe reintentar s?lo en errores irrecuperables
                // (Como c?digo de error HTTP 503).

                Log.e(TAG, "Error al intentar inscribirse " + i + ":" + e);
                if (i == MAX_ATTEMPTS) {
                    break;
                }
                try {
                    Log.d(TAG, "En espera para " + backoff + " ms Antes del intento");
                    Thread.sleep(backoff);
                } catch (InterruptedException e1) {
                    // Actividad terminado antes de que completemos - salida.
                    Log.d(TAG, "El paso fue Interrumpido: Abotar, intentar de nuevo!");
                    Thread.currentThread().interrupt();
                    return;
                }
                // Aumentar de forma exponencial
                backoff *= 2;
            }
        }
        String message = context.getString(R.string.server_register_error,
                MAX_ATTEMPTS);
        CommonUtilities.displayMessage(context, message);

    }

    /**
     * Requisito post en el servidor.
     *
     * @param endpoint POST direccion.
     * @param params request parameters.
     *
     * @throws IOException propagated from POST.
     */
    private static void post(String endpoint, Map<String, String> params)
            throws IOException {

        URL url;
        try {
            url = new URL(endpoint);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("URL Invalida: " + endpoint);
        }
        StringBuilder bodyBuilder = new StringBuilder();
        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
        // construye el cuerpo de POST utilizando los par?metros
        while (iterator.hasNext()) {
            Map.Entry<String, String> param = iterator.next();
            bodyBuilder.append(param.getKey()).append('=')
                    .append(param.getValue());
            if (iterator.hasNext()) {
                bodyBuilder.append('&');
            }
        }
        String body = bodyBuilder.toString();
        Log.v(TAG, "Posting '" + body + "' to " + url);
        byte[] bytes = body.getBytes();
        HttpURLConnection conn = null;
        try {
            Log.e("URL", "> " + url);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setFixedLengthStreamingMode(bytes.length);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded;charset=UTF-8");
            // post the request
            OutputStream out = conn.getOutputStream();
            out.write(bytes);
            out.close();
            // handle the response
            int status = conn.getResponseCode();
            if (status != 200) {
                throw new IOException("Post fallido con error de codigo " + status);
            }
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    //Metodo para Anular el registro para esta cuenta dispositivo registrado en el servidor.
    static void unregister(final Context context, final String regId){
        //mensaje de log
        Log.i(TAG, "Dispositivo anulado (regId = " + regId + ")");
        String serverUrl = SERVER_URL + "/unregister";
        Map<String, String>params = new HashMap<String, String>();
        params.put("regId", regId);
        try {
            //la URL con los parametros
            post(serverUrl, params);
            //SE registra en el servidor
            GCMRegistrar.setRegisteredOnServer(context,false);
            String message = context.getString(R.string.server_unregistered);
            CommonUtilities.displayMessage(context,message);

        } catch (IOException e){
            // En este punto el dispositivo no est? registrado desde GCM, pero
            // todav?a Registrado en el servidor.
            // Podr?amos tratar de anular el registro de nuevo, pero no es necesario:
            // Si el servidor intenta enviar un mensaje al dispositivo, se pondr?
            // Un mensaje de error "NotRegistered" y debe anular el registro del dispositivo.

            String message = context.getString(R.string.server_unregister_error,
                    e.getMessage());
            CommonUtilities.displayMessage(context, message);
        }

    }

}
