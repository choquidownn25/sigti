package net.proyecto.sigti.notifica;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;

import net.proyecto.sigti.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by choqu_000 on 17/08/2015.
 *
 * Clase para el chat con notificacion la notificacion
 */
public class ChatActivity extends ActionBarActivity {

    EditText editText_mail_id;
    EditText editText_chat_message;
    ListView listView_chat_messages;
    Button button_send_chat;
    List<ChatObject> chat_list;
    TextView lblMessage;
    TextView latitud;
    TextView longitud;
    static LocationManager locationManager;
    static MyLocationListener miLocationListener;

    // GPS
    public static String lat, lon, loc;

    BroadcastReceiver recieve_chat;

    Map<Integer, String> map = new HashMap<Integer, String>();

    //se crea la vista
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        //llamos los objetos de la vista
        latitud = (TextView)findViewById(R.id.latitud);
        longitud=(TextView)findViewById(R.id.longitud);

        //iniciamos los controles para el GPS
        locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        //instanciamos.
        miLocationListener=new MyLocationListener();
        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 5, miLocationListener);
        // Escribimos los TextView el valor inicial
        lblMessage = (TextView)findViewById(R.id.lblMessage);
        editText_mail_id= (EditText) findViewById(R.id.editText_mail_id);
        editText_chat_message= (EditText) findViewById(R.id.editText_chat_message);
        listView_chat_messages= (ListView) findViewById(R.id.listView_chat_messages);

        button_send_chat=(Button)findViewById(R.id.button_send_chat);
        button_send_chat.setOnClickListener(new View.OnClickListener() {
            //Enviamos el metodo al servidor
            @Override
            public void onClick(View v) {

                String message = editText_chat_message.getText().toString();
                showChat("sent", message);
                new SendMessage().execute();
                editText_chat_message.setText("");
            }
        });

        recieve_chat=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                String newMessage = intent.getExtras().getString("price");

                Bundle extras = getIntent().getExtras();
                String userName;

                if (extras != null) {
                    userName = extras.getString("price");
                    // and get whatever type user account id is
                }

                //String recieved_message=intent.getStringExtra("price");
                String message=intent.getStringExtra("message");
                Log.d("pavan","in local braod "+message);
                showChat("recieve",message);


            }
        };
        //lblMessage.append(recieve_chat);
        LocalBroadcastManager.getInstance(this).registerReceiver(recieve_chat, new IntentFilter("message_recieved"));
    }
    //Metodo del Chat
    private void showChat(String type, String message){

        if(chat_list==null || chat_list.size()==0){

            chat_list= new ArrayList<ChatObject>();
        }

        chat_list.add(new ChatObject(message,type));

        ChatAdabter chatAdabter=new ChatAdabter(ChatActivity.this,R.layout.chat_view,chat_list);

        listView_chat_messages.setAdapter(chatAdabter);
        //chatAdabter.notifyDataSetChanged();

    }

    //Clase donde envia la hora de la notificacion de forma asincrona
    private class SendMessage extends AsyncTask<String, Void, String> {

        protected void onPreExecute(){
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... params) {


            String url = Util.send_chat_url+"?email_id="+editText_mail_id.getText().toString()+"&message="+editText_chat_message.getText().toString();
            Log.i("pavan", "url" + url);

            OkHttpClient client_for_getMyFriends = new OkHttpClient();;

            String response = null;
            // String response=Utility.callhttpRequest(url);

            try {
                url = url.replace(" ", "%20");
                response = callOkHttpRequest(new URL(url),
                        client_for_getMyFriends);
                for (String subString : response.split("<script", 2)) {
                    response = subString;
                    break;
                }
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            return response;
        }

        protected void onPostExecute(String result){
            super.onPostExecute(result);
        }
    }

    // Http request usando OkHttpClient
    String callOkHttpRequest(URL url, com.squareup.okhttp.OkHttpClient tempClient)
            throws IOException {

        HttpURLConnection connection = tempClient.open(url);

        connection.setConnectTimeout(40000);
        InputStream in = null;
        try {
            // Read the response.
            in = connection.getInputStream();
            byte[] response = readFully(in);
            return new String(response, "UTF-8");
        } finally {
            if (in != null)
                in.close();
        }
    }

    //Metodo para registro en el buffer
    byte[] readFully(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        for (int count; (count = in.read(buffer)) != -1;) {
            out.write(buffer, 0, count);
        }
        return out.toByteArray();
    }
    //Clase qu caturo los datos del GPS
    public class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {

            DecimalFormat f = new DecimalFormat("###.#####");

            lat = f.format(location.getLatitude()).replace(",", ".");
            lon = f.format(location.getLongitude()).replace(",", ".");

            latitud.setText(lat);
            longitud.setText(lon);
        }
        /*
        Este m?todo se llama cuando un proveedor no puede buscar una
        ubicaci?n o si el proveedor se ha convertido recientemente
        disponible despu?s de un per?odo de indisponibilidad
         */
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {


        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }


}
