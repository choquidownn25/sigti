package net.proyecto.sigti;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import net.proyecto.sigti.contenga.PContenedora;
import net.proyecto.sigti.mapas.MapaPosicion;
import net.proyecto.sigti.notifica.MainActivityNotificacion;
import net.proyecto.sigti.notifica.MyReceiver;
import net.proyecto.sigti.notifica.NotificaAlMainActivity;
import net.proyecto.sigti.notifica.PasaLatLong;
import net.proyecto.sigti.notifica.Util;
import net.proyecto.sigti.notificaciones.ResgistroActivity;
import net.proyecto.sigti.vista.HomeFragment;
import net.proyecto.sigti.vista.Prueba;
import net.proyecto.sigti.vista.VistaFormularioEjecuciones;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class MainActivity extends ActionBarActivity {

    //Atributos

    //GoogleCloudMessaging gcm; //Para regito del movil
    AtomicInteger msgId = new AtomicInteger();
    SharedPreferences prefs;

    Calendar c = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String strDate = sdf.format(c.getTime());

    private float latitudes;
    private float longitudes;

    String regid;
    String msg;
    static final String TAG = "pavan";
    public MyReceiver miRecibimiento;

    private static final int ALARM_REQUEST_CODE = 1;

    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;

    private CharSequence activityTitle;
    private CharSequence itemTitle;
    private String[] tagTitles;

    int mPosition = -1;
    String mTitle = "";
    //Atributos
    private String horas;
    private String fecha;
    private String lat;
    private String lon;
    private String latitud;
    private String longitud;
    private String rehoras;
    // Array of strings storing country names
    String[] mCountries ;

    //Array
    int[] mFlags = new int[]{
            R.drawable.ic_html,
            R.drawable.ic_css,
            R.drawable.ic_javascript,
            R.drawable.ic_angular,
            R.drawable.ic_python,
            R.drawable.ic_ruby
    };

    // Array of strings to initial counts
    String[] mCount = new String[]{
            "", "", "", "", "",
            "", "", "", "", "" };

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private LinearLayout mDrawer ;
    private List<HashMap<String,String>> mList ;
    private SimpleAdapter mAdapter;
    final private String COUNTRY = "country";
    final private String FLAG = "flag";
    final private String COUNT = "count";
    private String[] titulos;
    private String mActivityTitle;
    Context context;

    Calendar HoraSystem = Calendar.getInstance();
    SimpleDateFormat foratoTiempo = new SimpleDateFormat("hh:mm:ss");
    String strHoradelSistema = foratoTiempo.format(HoraSystem.getTime());

    GoogleCloudMessaging gcm; //Atributo para llamar el servicio Google
    //Creamos la vista de la actividad
    static LocationManager locationManager;
    static MyLocationListener miLocationListener;
    private LocationManager locManager;
    private LocationListener locListener;
    private NotificaAlMainActivity objteoMainActivityNotificacion;
    String nombrecel;
    private long SPLASH_DELAY = 9000; //9 segundo maximos
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiti_vista_lateral);
        //Bloquear orientación de pantalla
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Intacia de Clase Contex que accedemos recursos
        objteoMainActivityNotificacion = new NotificaAlMainActivity();
        fecha = strDate;
        context = getApplicationContext();

        /*
        //Tiempo de nuestra actividad a ejecutar
        TimerTask task = new TimerTask() {
            public void run() {
                //Intent mainIntetent = new Intent(getApplicationContext(), LoginActivity.class);
                Intent mainIntetent = new Intent(getApplicationContext(), NotificaAlMainActivity.class);
                startActivity(mainIntetent);
                        //Destruimos esta activity para prevenit
                //que el usuario retorne aqui presionando el boton Atras.
                //finish();
                nombrecel = objteoMainActivityNotificacion.NombrePoseeCelular;
            }
        };
        //Instancia. de Clase tiemer
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_DELAY);



        if(isUserRegistered(context)){
        //if(objteoMainActivityNotificacion.isUserRegistered(context)){

            startActivity(new Intent(MainActivity.this,VistaFormularioEjecuciones.class));
            //finish();

        }*/
        //else {
            // Getting an array of country names
            //mCountries = getResources().getStringArray(R.array.countries);
            //nav_options
            mCountries = getResources().getStringArray(R.array.nav_options);

            // Title of the activity
            mTitle = (String) getTitle();

            miRecibimiento = new MyReceiver();
            // Getting a reference to the drawer listview
            mDrawerList = (ListView) findViewById(R.id.drawer_list);

            // Getting a reference to the sidebar drawer ( Title + ListView )
            mDrawer = (LinearLayout) findViewById(R.id.drawer);
            mActivityTitle = getTitle().toString();
            // Each row in the list stores country name, count and flag
            mList = new ArrayList<HashMap<String, String>>();


            for (int i = 0; i < 2; i++) {
                HashMap<String, String> hm = new HashMap<String, String>();
                hm.put(COUNTRY, mCountries[i]);
                hm.put(COUNT, mCount[i]);
                hm.put(FLAG, Integer.toString(mFlags[i]));
                mList.add(hm);
            }

            // Keys used in Hashmap
            String[] from = {FLAG, COUNTRY, COUNT};

            // Ids of views in listview_layout
            int[] to = {R.id.flag, R.id.country, R.id.count};

            // Instantiating an adapter to store each items
            // R.layout.drawer_layout defines the layout of each item
            mAdapter = new SimpleAdapter(this, mList, R.layout.drawer_layout, from, to);

            // Getting reference to DrawerLayout
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

            // Creating a ToggleButton for NavigationDrawer with drawer event listener
            mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

                /**
                 * Called when drawer is closed
                 */
                public void onDrawerClosed(View view) {
                    highlightSelectedCountry();
                    supportInvalidateOptionsMenu();
                }

                /**
                 * Called when a drawer is opened
                 */
                public void onDrawerOpened(View drawerView) {
                    getSupportActionBar().setTitle("Tematicas");
                    supportInvalidateOptionsMenu();
                }
            };

            // Setting event listener for the drawer
            mDrawerLayout.setDrawerListener(mDrawerToggle);


            //setupDrawer();

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);

            //Establecemos la accion al clickear sobre cualquier item del menu.
            //De la misma forma que hariamos en una app comun con un listview.
            mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                    MostrarFragment(position);
                }
            });

            //Cuando la aplicacion cargue por defecto mostrar la opcion Home



            // Enabling Up navigation
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            getSupportActionBar().setDisplayShowHomeEnabled(true);

            // Setting the adapter to the listView
            mDrawerList.setAdapter(mAdapter);
            //----
            //establecerAlarmaClick(1200);

            //yestablecescoAlarmaClick(10);
            comenzarLocalizacion();

            context = getApplicationContext();
            //Logica de si estalos egistros del telefono

            //MostramosMensajes();
            //new Insertar(MainActivity.this).execute();
            /*
            establecerAlarmaClick(27);
            establecerAlarmaClickMasMinutos(40);
            */



            MostrarFragment(0);

    }

    //Metodo par< establecer la alarma en minutos
    private void establecerAlarmaClickMasMinutos(int i) {
        AlarmManager manager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

//         (Modo no acoplado con un componente, ver AndroidManifest.xml)
//         Intent        intent  = new Intent("es.carlos_garcia.tutoriales.android.alarmmanager");

        Intent        intent  = new Intent(this, PasaLatLong.class);
        //Intent        intent  = new Intent(this, miResibimiento.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(this, ALARM_REQUEST_CODE, intent,  PendingIntent.FLAG_CANCEL_CURRENT);

        manager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + i * 1000, pIntent);


    }

    /*Pasando la posicion de la opcion en el menu nos mostrara el Fragment correspondiente*/
    private void MostrarFragment(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:

                //subCargaMapaPosicion();
                //fragment = new Contenedora();
                //subBienvenida();
                subCargaMapa();
                //Toast.makeText(getApplicationContext(), "Bienvenidos a Sigti " + titulos[position - 1] + "Herramienta Informatica para gestion de tecnicos!", Toast.LENGTH_SHORT).show();
                //MainActivity.this;
                //subPushNotificacion();
                //subCargaunPrueba();
            break;

            case 1:
                subCargaMapaRuta();
                //subCargaRegistroNotificacion();
                break;
            case 2:
                //subCarga();

                break;

            case 3:
                subCargaRegistroNoticaciones();
                //subCarga();
            break;
            case 4:

                subCargaMapa();



            break;
            default:
                //si no esta la opcion mostrara un toast y nos mandara a Home
                Toast.makeText(getApplicationContext(), "Opcion " + titulos[position - 1] + "no disponible!", Toast.LENGTH_SHORT).show();
                fragment = new Prueba();
                position = 1;
                break;
        }

        ///Validamos si el fragment no es nulo
        if(position > 1) { // Show fragment for countries : 0 to 4
            //showFragment(position);
            //subCarga();
            Toast.makeText(getApplicationContext(), mCountries[position], Toast.LENGTH_LONG).show();
        }
        //else{ // Show message box for countries : 5 to 9
        //    Toast.makeText(getApplicationContext(), mCountries[position], Toast.LENGTH_LONG).show();
        //}

    }
    //Metodo para cargar mapa posicion
    private void subCargaMapaPosicion(){
        Intent intent = new Intent(MainActivity.this, MapaPosicion.class);
        startActivity(intent);
    }

    //Metodo para cargar el mapa
    private void subCargaMapa(){

        //Intent intent = new Intent(MainActivity.this, MapaRuta.class);
        //Intent intent = new Intent(MainActivity.this, Contenedora.class);
        Intent intent = new Intent(MainActivity.this, PContenedora.class);
        startActivity(intent);
    }
    //Metodo para cargar mapa de la ruta
    private void subCargaMapaRuta(){
        //Intent intent = new Intent(MainActivity.this, MapaRuta.class);
        Intent intent = new Intent(MainActivity.this, VistaFormularioEjecuciones.class);
        startActivity(intent);

    }

    //Metodo para Registrar el telefono en DB y Google messaging
    private void subCargaRegistroNoticaciones(){
        Intent intent =  new Intent(MainActivity.this, MainActivityNotificacion.class);
        startActivity(intent);
    }
    //Metodo para enviar notificacion
    private void subPushNotificacion(){

        Intent intent=new Intent(MainActivity.this, ResgistroActivity.class);
        startActivity(intent);
    }
    //Metodo para cargar el otro intent
    private void subCarga() {
        Intent intent = new Intent(MainActivity.this, VistaFormularioEjecuciones.class);
        startActivity(intent);
    }
    //metodo para cargar la camara
    private void subCargaunPrueba(){
        Intent intent=new Intent(MainActivity.this, HomeFragment.class);
        startActivity(intent);
    }
    //metodo para cargar la camara
    private void subCargaRegistroNotificacion(){
        Intent intent = new Intent(MainActivity.this, MainActivityNotificacion.class);
        startActivity(intent);
    }

    //Si esta registrado
    private boolean isUserRegistered(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String User_name = prefs.getString(Util.USER_NAME, "");
        String Id_User_Name=prefs.getString(Util.PROPERTY_REG_ID, "");
        if (User_name.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return false;
        }

        return true;
    }

    //Metodo de accion asintonga del registro
    private void registerInBackground() {
        new AsyncTask() {



            @Override
            protected String doInBackground(Object[] params) {


                try {

                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(MainActivity.this);
                    }
                    regid = gcm.register(Util.SENDER_ID);
                    msg = "Device registered, registration ID=" + regid;



                    // You should send the registration ID to your server over HTTP,
                    //GoogleCloudMessaging gcm;/ so it can use GCM/HTTP or CCS to send messages to your app.
                    // The request to your server should be authenticated if your app
                    // is using accounts.
                    // sendRegistrationIdToBackend();

                    // For this demo: we don't need to send it because the device
                    // will send upstream messages to a server that echo back the
                    // message using the 'from' address in the message.

                    // Persist the registration ID - no need to register again.
                    storeRegistrationId(context, regid);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }
                return msg;



            }
        }.execute();

    }

    //Metodo para almaenar el registro del movil
    private void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGCMPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Util.PROPERTY_REG_ID, regId);
        editor.putInt(Util.PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }
    //Metoo de preferencias para el Google Messeging
    private SharedPreferences getGCMPreferences(Context context) {
        // This sample app persists the registration ID in shared preferences, but
        // how you store the registration ID in your app is up to you.
        return getSharedPreferences(MainActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }

//Metodo de version
    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();

    }

    @Override
    //Menu para cambiar el icono del deslizante a flecha
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            finish();
        }

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void incrementHitCount(int position){
        HashMap<String, String> item = mList.get(position);
        String count = item.get(COUNT);
        item.remove(COUNT);
        if(count.equals("")){
            count = "  1  ";
        }else{
            int cnt = Integer.parseInt(count.trim());
            cnt ++;
            count = "  " + cnt + "  ";
        }
        item.put(COUNT, count);
        mAdapter.notifyDataSetChanged();
    }

    public void showFragment(int position){

        //Currently selected country
        mTitle = mCountries[position];

        // Creating a fragment object
        CountryFragment cFragment = new CountryFragment();

        // Creating a Bundle object
        Bundle data = new Bundle();

        // Setting the index of the currently selected item of mDrawerList
        data.putInt("position", position);

        // Setting the position to the fragment
        cFragment.setArguments(data);

        // Getting reference to the FragmentManager
        FragmentManager fragmentManager  = getSupportFragmentManager();

        // Creating a fragment transaction
        FragmentTransaction ft = fragmentManager.beginTransaction();

        // Adding a fragment to the fragment transaction
        //ft.replace(R.id.content_frame, cFragment);

        // Committing the transaction
        ft.commit();
    }

    // Highlight the selected country : 0 to 4
    public void highlightSelectedCountry(){
        int selectedItem = mDrawerList.getCheckedItemPosition();

        if(selectedItem > 4)
            mDrawerList.setItemChecked(mPosition, true);
        else
            mPosition = selectedItem;

        if(mPosition!=-1)
            getSupportActionBar().setTitle(mCountries[mPosition]);
    }


    /**
     * Se ejecutará una única vez dentro de 'when' segundos el componente registrado
     * @param when Numero de segundos
     */
    private void establecerAlarmaClick(int when){
        AlarmManager manager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

//         (Modo no acoplado con un componente, ver AndroidManifest.xml)
//         Intent        intent  = new Intent("es.carlos_garcia.tutoriales.android.alarmmanager");

        Intent        intent  = new Intent(this, MyReceiver.class);
        //Intent        intent  = new Intent(this, miResibimiento.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(this, ALARM_REQUEST_CODE, intent,  PendingIntent.FLAG_CANCEL_CURRENT);

        manager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + when * 1000, pIntent);

    }

    //Bienvenida
    private void subBienvenida(){
        //Intent intent = new Intent(MainActivity.this, Contenedora.class);
        Intent intent = new Intent(MainActivity.this, NotificaAlMainActivity.class);
        startActivity(intent);
    }

    //Metodo para el popup
    public void clickPopup(){

    }

    //Metodo para mostrar el metodo o mensaje de ingresado o autulizado
    public void showAlert(){
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Login Error.");
                builder.setMessage("User not Found.")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }
    /**
     * Se ejecutará una única vez dentro de 'when' segundos el componente registrado
     * @param when Numero de segundos
     */
    private void establecescoAlarmaClick(int when){
        AlarmManager manager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

//         (Modo no acoplado con un componente, ver AndroidManifest.xml)
//         Intent        intent  = new Intent("es.carlos_garcia.tutoriales.android.alarmmanager");

        Intent        intent  = new Intent(this, MyReceiver.class);
        //Intent        intent  = new Intent(this, miResibimiento.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(this, ALARM_REQUEST_CODE, intent,  PendingIntent.FLAG_CANCEL_CURRENT);

        manager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + when * 1000, pIntent);


    }

    public void MostramosMensajes(){

        final CharSequence[] PhoneModels = {"iPhone", "Nokia", "Android"};
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(MainActivity.this);
        alt_bld.setIcon(R.drawable.common_signin_btn_icon_normal_dark);
        alt_bld.setTitle("Select a Phone Model");
        alt_bld.setSingleChoiceItems(PhoneModels, -1, new DialogInterface
                .OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                Toast.makeText(getApplicationContext(),
                        "Phone Model = "+PhoneModels[item], Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alert = alt_bld.create();
        alert.show();
    }

    //Metodo para encontrar el erro de red
    public void showRednoEncontrada(){
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                final CharSequence[] PhoneModels = {"iPhone", "Nokia", "Android"};
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setIcon(R.drawable.common_signin_btn_icon_normal_dark);
                builder.setTitle("Red no Encontrada.");

                builder.setSingleChoiceItems(PhoneModels, -1, new DialogInterface
                        .OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        Toast.makeText(getApplicationContext(),
                                "Phone Model = " + PhoneModels[item], Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setMessage("Usuario no identifica la Red.")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    //Inserta los datos de las Personas en el servidor.
    public boolean insertar(){
        HttpClient httpclient;
        List<NameValuePair> nameValuePairs;
        HttpPost httppost;
        httpclient=new DefaultHttpClient();
        //httppost= new HttpPost("http://192.168.1.124/pruebasigti/insert.php"); // Url del Servidor 192.168.1.124/pruebasigti/
        //httppost= new HttpPost("http://192.168.43.2/pruebasigti/insert.php");
        httppost= new HttpPost("https://controldeingreso.com/public_/graffity/reinsertarFotoPrueba.php");
        rehoras = strHoradelSistema;
        //Añadimos nuestros datos
        nameValuePairs = new ArrayList<NameValuePair>(4);

        String latt = String.valueOf(latitudes);
        String lonn = String.valueOf(longitudes);

        nameValuePairs.add(new BasicNameValuePair("fecha",fecha.toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("lat", latt.toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("lon", lonn.toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("hora", rehoras.toString().trim()));



        try {
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httpclient.execute(httppost);
            return true;
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

    //AsyncTask para insertar tbl_notificaciones
    class Insertar extends AsyncTask<String,String,String> {

        private Activity context;

        Insertar(Activity context){
            this.context=context;
        }
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            if(insertar())
                context.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        //Toast.makeText(context, "Persona insertada con éxito", Toast.LENGTH_LONG).show();
                        /*
                        nombre.setText("");
                        dni.setText("");
                        telefono.setText("");
                        email.setText("");*/
                        horas = null;
                        fecha = null;
                        lat = null;
                        lon = null;
                        latitud = null;
                        longitud = null;
                    }
                });
            else
                context.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Toast.makeText(context, "Persona no insertada con éxito", Toast.LENGTH_LONG).show();
                    }
                });
            return null;
        }
    }

    //Metodo de localizaicon GPS
    public void comenzarLocalizacion()
    {
        //Obtenemos una referencia al LocationManager
        locManager =
                (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        //Obtenemos la ?ltima posici?n conocida
        Location loc =
                locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        //Mostramos la ?ltima posici?n conocida
        mostrarPosicion(loc);

        //Nos registramos para recibir actualizaciones de la posici?n
        locListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                mostrarPosicion(location);
            }
            public void onProviderDisabled(String provider){
                // lblEstado.setText("Provider OFF");
            }
            public void onProviderEnabled(String provider){
                //lblEstado.setText("Provider ON ");
            }
            public void onStatusChanged(String provider, int status, Bundle extras){
                Log.i("", "Provider Status: " + status);
                //lblEstado.setText("Provider Status: " + status);
            }
        };

        locManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 30000, 0, locListener);
    }
    //Metodo para mostrar la posicion del gps
    private void mostrarPosicion(Location loc) {
        if(loc != null)
        {
            //latitud.setText("Latitud: " + String.valueOf(loc.getLatitude()));
            latitud = String.valueOf(loc.getLatitude());
            //longitud.setText("Longitud: " + String.valueOf(loc.getLongitude()));
            longitud = String.valueOf(loc.getLongitude());

            latitudes = Float.valueOf((float) loc.getLatitude());
            longitudes = Float.valueOf((float) loc.getLongitude());
            //lblPrecision.setText("Precision: " + String.valueOf(loc.getAccuracy()));
            Log.i("", String.valueOf(loc.getLatitude() + " - " + String.valueOf(loc.getLongitude())));
        }
        else
        {
            //latitud.setText("Latitud: (sin_datos)");
            latitud = "Latitud: (sin_datos)";
            //longitud.setText("L on: (sin_datos)");
            longitud = "Longitud sin Datos";

            latitudes = (float) 0.00;
            longitudes = (float) 0.00;
        }
    }

    //Clase qu caturo los datos del GPS
    public class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {

            DecimalFormat f = new DecimalFormat("###.#####");

            lat = f.format(location.getLatitude()).replace(",", ".");
            lon = f.format(location.getLongitude()).replace(",", ".");

            //latitud.setText(lat);
            latitud = lat.toString();
            //longitud.setText(lon);
            longitud = lon.toString();
        }
        /*
        Este metodo se llama cuando un proveedor no puede buscar una
        ubicacion o si el proveedor se ha convertido recientemente
        disponible despues de un periodo de indisponibilidad
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