package net.proyecto.sigti.vista;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import net.proyecto.sigti.R;
import net.proyecto.sigti.orden_servicio.Orden_Servicio;
import net.proyecto.sigti.utilidad.CustomOnItemSelectedListener;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by choqu_000 on 18/08/2015.
 */
public class VistaFormularioEjecuciones extends Activity implements AdapterView.OnItemSelectedListener {

    //Atributos
    private Button update;
    String DatoEvento;

    String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
    Date currentDate = new Date(System.currentTimeMillis());

    Calendar c = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String strDate = sdf.format(c.getTime());

    TextView tHora;
    String EstaeslaHora;
    int hora=0, minuto =0, segundo = 0;
    Intent i;
    Thread iniReloj = null;
    Runnable r;
    boolean isUpdate = false;
    String sec, min, hor;

    String curTime;
    String myDateString = "15:45:01";
    SimpleDateFormat foratoTiempo = new SimpleDateFormat("hh:mm:ss");
    private String yourDate;
    //Atributos para la lista
    List<String> list = new ArrayList<String>();
    private int posicion=0;
    private Spinner spinner2;
    private Orden_Servicio personas;
    private List<Orden_Servicio> listaPersonas;
    private String jsonResult;
    private String urls = "http://cpriyankara.coolpage.biz/employee_details.php";
    private String url = "http://192.168.1.124/pruebasigti/selectConsultareal.php";
    //private String url = "http://192.168.1.195/pruebasigti/selectConsultareal.php";

    private TextView id_orden_servicio;
    private TextView numero_wo;
    private TextView id_coordinador;
    private TextView documento;
    private TextView primer_nombre;
    private TextView direccion;
    private TextView id_jornada;
    private TextView hora_resultado;
    private TextView id_categoria;
    private TextView id_servicio;
    private TextView codigo_ciudad;
    private TextView id_novedades;
    private TextView evento;
    private TextView fechaorden;
    private ImageButton mas;
    private ImageButton menos;
    private static final int ALARM_REQUEST_CODE = 1;

    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.orden_servicio);

        //Definimos el objeto texto a partir de el elemento con id caja_de_texto
        TextView texto = (TextView)findViewById(R.id.textViewFecha);
        //texto.setText(currentDateTimeString);
        texto.setText(strDate);
        //Muestra la Hora
        tHora = (TextView) findViewById(R.id.textViewJornada);
        r = new RefreshClock();
        iniReloj= new Thread(r);
        iniReloj.start();
        //Casting del evento
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        populateSpinner(); //Metodo de cambio del spinner

        // Spinner item selection Listener
        addListenerOnSpinnerItemSelection();

        // Aqu? Spinner se ha establecido esta clase para escuchar en cualquier
        // cambios seleccionando cualquiera de su elemento que eventualmente
        //desencadenar el m?todo onItemSelected.
        spinner2.setOnItemSelectedListener(this);
        //Instacia de clase para array
        listaPersonas=new ArrayList<Orden_Servicio>();
        //Casting
        id_orden_servicio=(TextView)findViewById(R.id.textViewid_orden_servicio);
        numero_wo=(TextView)findViewById(R.id.textnumero_wo);
        id_coordinador=(TextView)findViewById(R.id.textViewid_coordinador);
        documento=(TextView)findViewById(R.id.textViewdocumento);
        primer_nombre=(TextView)findViewById(R.id.textViewNombreCliente);
        direccion=(TextView)findViewById(R.id.textViewDireccion);
        id_categoria=(TextView)findViewById(R.id.textViewCategoria);
        id_jornada=(TextView)findViewById(R.id.textViewJornada);
        id_servicio = (TextView)findViewById(R.id.textViewid_servicio);
        codigo_ciudad = (TextView)findViewById(R.id.textViewcodigo_ciudad);
        id_novedades = (TextView)findViewById(R.id.textViewid_novedades);
        evento=(TextView)findViewById(R.id.evento);
        fechaorden=(TextView)findViewById(R.id.textViewFecha);
        //metodo de acceso del wb service
        accessWebService();
        //Actualizar datos de la persona
        update=(Button)findViewById(R.id.actualizar); //Intancia para la vista del botom
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!id_orden_servicio.getText().toString().trim().equalsIgnoreCase("")||
                        !numero_wo.getText().toString().trim().equalsIgnoreCase("")||
                        !id_coordinador.getText().toString().trim().equalsIgnoreCase("")||
                        !documento.getText().toString().trim().equalsIgnoreCase("")||
                        !id_jornada.getText().toString().trim().equalsIgnoreCase("")||

                        !primer_nombre.getText().toString().trim().equalsIgnoreCase("")||
                        !direccion.getText().toString().trim().equalsIgnoreCase("")||

                        !hora_resultado.getText().toString().trim().equalsIgnoreCase("")||
                        !id_categoria.getText().toString().trim().equalsIgnoreCase("")||
                        !id_servicio.getText().toString().trim().equalsIgnoreCase("")||
                        !codigo_ciudad.getText().toString().trim().equalsIgnoreCase("")||
                        !id_novedades.getText().toString().trim().equalsIgnoreCase("")||
                        !evento.getText().toString().trim().equalsIgnoreCase("")
                        )
                    if(compruebaId_Orden_Servicio())
                        new Update(VistaFormularioEjecuciones.this).execute();

                    else
                        Toast.makeText(VistaFormularioEjecuciones.this, "Hay informacion por rellenar", Toast.LENGTH_LONG).show();
            }
        });

        mas=(ImageButton)findViewById(R.id.mas);
        mas.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(!listaPersonas.isEmpty()){
                    if(posicion>=listaPersonas.size()-1){
                        posicion=listaPersonas.size()-1;
                        mostrarPersona(posicion);
                    }else{
                        posicion++;

                        mostrarPersona(posicion);
                    }
                }
            }

        });

        //Se mueve por nuestro ArrayList mostrando el objeto anterior
        menos=(ImageButton)findViewById(R.id.menos);
        menos.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(!listaPersonas.isEmpty()){
                    if(posicion<=0){
                        posicion=0;
                        mostrarPersona(posicion);
                    }
                    else{
                        posicion--;
                        mostrarPersona(posicion);
                    }
                }
            }
        });


    }

    //Metodo para mostrar datos de jason
    private void mostrarPersona(final int posicion) {
        runOnUiThread(new Runnable(){
            @Override
            public void run() {
                // TODO Auto-generated method stub
                Orden_Servicio personas=listaPersonas.get(posicion);
                int datoOrdenservicio = personas.getId_orden_servicio();
                int datonumero_wo = personas.getNumero_wo();
                int datoidcoordinador = personas.getId_coordinador();
                int datoCategoria = personas.getId_categoria();
                int datoServicio=personas.getId_servicio();
                int datoCiudad=personas.getCodigo_ciudad();
                int datoNovedad=personas.getId_novedades();
                //id_orden_servicio.setText(personas.getId_orden_servicio());
                id_orden_servicio.setText(String.valueOf(datoOrdenservicio));
                numero_wo.setText(String.valueOf(datonumero_wo));

                id_coordinador.setText(String.valueOf(datoidcoordinador));
                documento.setText(personas.getDocumento());
                primer_nombre.setText(personas.getNombre());
                direccion.setText(personas.getDireccion());
                id_categoria.setText(String.valueOf(datoCategoria));
                id_servicio.setText(String.valueOf(datoServicio));
                codigo_ciudad.setText(String.valueOf(datoCiudad));
                id_novedades.setText(String.valueOf(datoNovedad));

            }
        });
    }

    // Este m?todo pueblan el ArrayList <String> y despu?s
    //utilizar que ArrayList a ArrayAdapter que eventualmente
    //es la fuente de los datos Spinner
    public void populateSpinner(){
        list.add("Inactivo");
        list.add("Activo");
        list.add("No se pudo realizar");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,list);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        spinner2.setAdapter(dataAdapter);
    }

    //Metodo para el Web service
    public void accessWebService() {
        JsonReadTask task = new JsonReadTask();
        // passes values for the urls string array
        task.execute(new String[] { url });
    }

    //M?todo de devoluci?n de llamada que se invoca cuando un elemento en este punto de vista ha sido seleccionado.
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String strEvento = list.get(position).toString();
        evento.setText(strEvento);
        EstaeslaHora = curTime;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // Metodo Async Task de accesso del web service
    private class JsonReadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(params[0]);
            try {
                HttpResponse response = httpclient.execute(httppost);
                jsonResult = inputStreamToString(
                        response.getEntity().getContent()).toString();
                if(ListDrwaer())mostrarPersona(posicion);
            }

            catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }



        //MEtodo de StringBuilder
        //Clase Iostream es la superclase de todas las clases que representan un flujo de entrada de bytes
        private StringBuilder inputStreamToString(InputStream is) {
            String rLine = "";
            StringBuilder answer = new StringBuilder();
            //Instancia de clase BufferedReader
            //Lee el texto de una corriente de caracteres de entrada, amortiguando los personajes
            // con el fin de prever la lectura eficiente de los personajes, matrices y l?neas.
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));

            try {
                while ((rLine = rd.readLine()) != null) {
                    answer.append(rLine);
                }
            }

            catch (IOException e) {
                // Mensaje de error
                Toast.makeText(getApplicationContext(),
                        "Error..." + e.toString(), Toast.LENGTH_LONG).show();
            }
            return answer;
        }

        @Override
        protected void onPostExecute(String result) {
            ListDrwaer();
        }
    }// end async task

    // build hash set for list view
    public boolean ListDrwaer() {
        List<Map<String, String>> employeeList = new ArrayList<Map<String, String>>();

        try {
            JSONObject jsonResponse = new JSONObject(jsonResult);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("sigti_instalaciones");

            for (int i = 0; i < jsonMainNode.length(); i++) {
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);

                /*
                String number = jsonChildNode.optString("id_orden_servicio");
                String name = jsonChildNode.optString("numero_wo");
                String telephone = jsonChildNode.optString("id_coordinador");
                String email = jsonChildNode.optString("documento");
                String nombre = jsonChildNode.optString("primer_nombre");
                String direccion = jsonChildNode.optString("direccion");
                String categoria = jsonChildNode.optString("id_categoria");
                String servicio = jsonChildNode.optString("id_servicio");
                String ciudad = jsonChildNode.optString("codigo_ciudad");
                String novedad = jsonChildNode.getString("id_novedades");
                //String eventos = jsonChildNode.getString("eventos");
                //String outPut = name + "-" + number + "-" + telephone + "-" + email;
                String outPut = number + "-" + name + "-" + telephone + "-" + email + "-" + nombre + "-" + direccion +
                        "-" + categoria  + servicio + "-" + ciudad + "-" + novedad;
                employeeList.add(createEmployee("employees", outPut)); */


                personas=new Orden_Servicio();
                JSONObject jsonArrayChild = jsonMainNode.getJSONObject(i);
                personas.setId_orden_servicio(jsonArrayChild.optInt("id_orden_servicio"));
                personas.setNumero_wo(jsonArrayChild.optInt("numero_wo"));
                personas.setId_coordinador(jsonArrayChild.optInt("id_coordinador"));
                personas.setDocumento(jsonArrayChild.optString("documento"));
                personas.setNombre(jsonArrayChild.optString("primer_nombre"));
                personas.setDireccion(jsonArrayChild.optString("direccion"));
                personas.setId_categoria(jsonArrayChild.optInt("id_categoria"));
                personas.setId_servicio(jsonArrayChild.optInt("id_servicio"));
                personas.setCodigo_ciudad(jsonArrayChild.getInt("codigo_ciudad"));
                personas.setId_novedades(jsonArrayChild.getInt("id_novedades"));

                listaPersonas.add(personas);

            }
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "Error" + e.toString(),
                    Toast.LENGTH_SHORT).show();
        }

        return true;


    }
    //Metodo para obtner si es igual
    private boolean compruebaId_Orden_Servicio(){
        for (int i = 0; i < listaPersonas.size(); i++) {
            Orden_Servicio p=listaPersonas.get(i);
            if(id_orden_servicio.getText().toString().trim().equalsIgnoreCase(((new Integer(p.getId_orden_servicio()).toString().trim()))))return true;
        }
        runOnUiThread(new Runnable(){
            @Override
            public void run() {
                // TODO Auto-generated method stub
                Toast.makeText(VistaFormularioEjecuciones.this, "El registro no existe", Toast.LENGTH_LONG).show();
            }
        });
        return false;
    }

    //AsyncTask para actulizar Personas

    class  Update extends  AsyncTask<String, String, String>{

        //Atributos
        private Activity context;
        //Constructor con parametros
        Update(Activity context){
            this.context=context;
        }
        @Override
        //Metodo   as?ncrona se pasan parametros paso por paso
        protected String doInBackground(String... params) {
            if(update())
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Persona actulizada con ?xito",
                                Toast.LENGTH_LONG).show();

                        id_orden_servicio.setText("");
                        numero_wo.setText("");

                        id_coordinador.setText("");
                        documento.setText("");
                        primer_nombre.setText("");
                        direccion.setText("");
                        id_jornada.setText("");
                        //hora_resultado.setText("");

                        id_categoria.setText("");
                        id_servicio.setText("");

                        codigo_ciudad.setText("");
                        id_novedades.setText("");
                        //eventos.setText("");
                    }
                });
            else
                //Casi todas las actividades de interactuar con el usuario, por lo que el
                // tipo de actividad se encarga de crear una ventana para usted en la que usted
                // puede poner su interfaz de usuario con
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() { //Hilo
                        Toast.makeText(context, "Persona no actulizada con ?xito",
                                Toast.LENGTH_LONG).show();
                    }
                });
            return null;
        }
    }

    //Actualiza los datos de las Personas en el servidor.
    private boolean update(){

        //Clientes HTTP encapsulan una mezcla heterog?nea de objetos necesarios para ejecutar
        // las peticiones HTTP al manipular las cookies,
        HttpClient httpclient;
        //Esta Lista de clase comforma a las reglas gramaticales y de formato
        List<NameValuePair> nombreValuePairs;
        //El m?todo POST se utiliza para solicitar que el servidor de origen
        // acepta la entidad incluida en la solicitud como un nuevo subordinado del
        // recurso identificado por el Request-URI en el Request-Line
        HttpPost httppost;
        httpclient=new DefaultHttpClient();
        httppost= new HttpPost("http://192.168.1.124/pruebasigti/update2.php"); // Url del Servidor 192.168.1.124
        //httppost= new HttpPost("http://10.16.31.203/pruebasigti/update2.php"); // Url del Servidor
        //A?adimos nuestros datos
        nombreValuePairs = new ArrayList<NameValuePair>(4);

        nombreValuePairs.add(new BasicNameValuePair("id_orden_servicio",id_orden_servicio.getText().toString().trim())); //formato de la linea
        nombreValuePairs.add(new BasicNameValuePair("numero_wo",numero_wo.getText().toString().trim()));

        nombreValuePairs.add(new BasicNameValuePair("id_coordinador",id_coordinador.getText().toString().trim()));
        nombreValuePairs.add(new BasicNameValuePair("documento",documento.getText().toString().trim()));

        // numero_wo.setText(String.valueOf(datonumero_wo));
        nombreValuePairs.add(new BasicNameValuePair("id_jornada",id_jornada.getText().toString().trim())); //formato de la linea
        //nombreValuePairs.add(new BasicNameValuePair("id_jornada",EstaeslaHora.toString().trim())); //formato de la linea
        nombreValuePairs.add(new BasicNameValuePair("hora_resultado",EstaeslaHora.toString().trim()));

        nombreValuePairs.add(new BasicNameValuePair("id_categoria",id_categoria.getText().toString().trim()));
        nombreValuePairs.add(new BasicNameValuePair("id_servicio",id_servicio.getText().toString().trim()));

        nombreValuePairs.add(new BasicNameValuePair("codigo_ciudad",codigo_ciudad.getText().toString().trim()));

        nombreValuePairs.add(new BasicNameValuePair("id_novedades",id_novedades.getText().toString().trim()));
        nombreValuePairs.add(new BasicNameValuePair("eventos",evento.getText().toString().trim()));
        nombreValuePairs.add(new BasicNameValuePair("fechaorden", fechaorden.getText().toString().trim()));
        //Realizamos el try cath
        try {
            //Metodo para la entidad requerida
            httppost.setEntity(new UrlEncodedFormEntity(nombreValuePairs));
            httpclient.execute(httppost);//Ejecucion

            return true; //retorna verdadero
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return false;
    }

    private HashMap<String, String> createEmployee(String name, String number) {
        HashMap<String, String> employeeNameNo = new HashMap<String, String>();
        employeeNameNo.put(name, number);
        return employeeNameNo;
    }
    // Adiciona spinner data

    public void addListenerOnSpinnerItemSelection() {

        spinner2.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }
    /**
     *
     * Esta clase hace uso de la interface Runnable la cual es la encargada de estar
     refrescando cada 1000 milisegundos es decir, un segudo, no tiene gran ciencia

     Esta clase hace uso de la interface Runnable la cual es la encargada de estar
     refrescando cada 1000 milisegundos es decir, un segudo, no tiene gran ciencia
     @SuppressWarnings("unused") es para decirle al compilador que obvie la advertencia
     que se genera, pero la verdad no afecta en nada el funcionamiento del mismo

     */
    class RefreshClock implements Runnable{

        @Override
        public void run() {

            while(!Thread.currentThread().isInterrupted()){
                try {
                    initClock();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }catch(Exception e){
                }
            }
        }
    }

    /**
     basicamente es el que hace el limpiado del layout cada segundo, un simple if es el
     que identifica si se ah actualizado la hora desde ajustes oh si tiene que seguir
     mostrando la hora actual
     isUpdate muestra el valor que se envio de la  clase Ajustes
     */
    private void initClock() {
        runOnUiThread(new Runnable() {
            public void run() {
                try{
                    //aca podemos comprara la alarta
                    if(isUpdate){
                        settingNewClock();

                    } else {
                        updateTime();
                    }
                    curTime =hor+ hora + min + minuto + sec + segundo;
                    tHora.setText(curTime);

                }catch (Exception e) {}
            }
        });
    }

    /**
     Este es el metodo inicial del reloj, a partir de el es que se muestra la hora
     cada segundo es la encargada Java.Util.Calendar
     */

    private void updateTime(){

        Calendar c = Calendar.getInstance();
        hora = c.get(Calendar.HOUR_OF_DAY);
        minuto = c.get(Calendar.MINUTE);
        segundo = c.get(Calendar.SECOND);
        setZeroClock();

    }
    /**
     setZeroClock es para que se nos ponga el numero 0 en aquellos valores menores a
     10, pero no he podido resolver un peque?o inconveniente al momento de la llegada
     de 0:0:0 y por ende en sus derivadas, aunque no es por falta de logica, he revisado
     muy bien, pero si le encuentran arreglo me hacen el favor y me avisan de como
     solucionarlo
     */
    private void setZeroClock(){
        if(hora >=0 & hora <=9){
            hor = "0";
        }else{
            hor = "";
        }

        if(minuto >=0 & minuto <=9){
            min = ":0";
        }else{
            min = ":";
        }

        if(segundo >=0 & segundo <=9){
            sec = ":0";

        }else{
            sec = ":";
        }
    }
    /**
     Que puedo decir de este metodo mas que es el encargado de parsear la hora de una
     manera que al llegar a 24:59:59 esta retome los valores de 00:00:00 aunque en la practica
     como mencionaba en un comentario anterior esta se pone en 0:0:0, pero luego se restaura a
     00:00:01
     */
    private void settingNewClock(){
        segundo +=1;

        setZeroClock();

        if(segundo >=0 & segundo <=59){

        }else {
            segundo = 0;
            minuto +=1;
        }
        if(minuto >=0 & minuto <=59){

        }else{
            minuto = 0;
            hora +=1;
        }
        if(hora >= 0 & hora <= 24){

        }else{
            hora = 0;
        }

    }




}
