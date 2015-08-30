package net.proyecto.sigti;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import net.proyecto.sigti.notifica.MainActivityNotificacion;
import net.proyecto.sigti.notificaciones.ResgistroActivity;
import net.proyecto.sigti.vista.HomeFragment;
import net.proyecto.sigti.vista.Prueba;
import net.proyecto.sigti.vista.VistaFormularioEjecuciones;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    //Atributos

    private static final int ALARM_REQUEST_CODE = 1;

    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;

    private CharSequence activityTitle;
    private CharSequence itemTitle;
    private String[] tagTitles;

    int mPosition = -1;
    String mTitle = "";

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

    //Creamos la vista de la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiti_vista_lateral);
        // Getting an array of country names
        //mCountries = getResources().getStringArray(R.array.countries);
        //nav_options
        mCountries = getResources().getStringArray(R.array.nav_options);

        // Title of the activity
        mTitle = (String)getTitle();

        // Getting a reference to the drawer listview
        mDrawerList = (ListView) findViewById(R.id.drawer_list);

        // Getting a reference to the sidebar drawer ( Title + ListView )
        mDrawer = ( LinearLayout) findViewById(R.id.drawer);
        mActivityTitle = getTitle().toString();
        // Each row in the list stores country name, count and flag
        mList = new ArrayList<HashMap<String,String>>();


        for(int i=0;i<5;i++){
            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put(COUNTRY, mCountries[i]);
            hm.put(COUNT, mCount[i]);
            hm.put(FLAG, Integer.toString(mFlags[i]) );
            mList.add(hm);
        }

        // Keys used in Hashmap
        String[] from = { FLAG,COUNTRY,COUNT };

        // Ids of views in listview_layout
        int[] to = { R.id.flag , R.id.country , R.id.count};

        // Instantiating an adapter to store each items
        // R.layout.drawer_layout defines the layout of each item
        mAdapter = new SimpleAdapter(this, mList, R.layout.drawer_layout, from, to);

        // Getting reference to DrawerLayout
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        // Creating a ToggleButton for NavigationDrawer with drawer event listener
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer , R.string.navigation_drawer_open, R.string.navigation_drawer_close){

            /** Called when drawer is closed */
            public void onDrawerClosed(View view) {
                highlightSelectedCountry();
                supportInvalidateOptionsMenu();
            }

            /** Called when a drawer is opened */
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
        MostrarFragment(0);


        // Enabling Up navigation
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Setting the adapter to the listView
        mDrawerList.setAdapter(mAdapter);

        establecerAlarmaClick(7);

        //MostramosMensajes();
    }


    /*Pasando la posicion de la opcion en el menu nos mostrara el Fragment correspondiente*/
    private void MostrarFragment(int position) {
        // update the main content by replacing fragments
        Fragment fragment;
        switch (position) {
            case 0:

                subPushNotificacion();
                break;

            case 1:

                subCargaRegistroNotificacion();
                break;
            case 2:
                subCarga();
                break;



            default:
                //si no esta la opcion mostrara un toast y nos mandara a Home
                Toast.makeText(getApplicationContext(), "Opcion " + titulos[position - 1] + "no disponible!", Toast.LENGTH_SHORT).show();
                fragment = new Prueba();
                position = 1;
                break;
        }
        //Validamos si el fragment no es nulo
        if(position > 5) { // Show fragment for countries : 0 to 4
            //showFragment(position);
            //subCarga();
            Toast.makeText(getApplicationContext(), mCountries[position], Toast.LENGTH_LONG).show();
        }
        //else{ // Show message box for countries : 5 to 9
        //    Toast.makeText(getApplicationContext(), mCountries[position], Toast.LENGTH_LONG).show();
        //}
    }

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
            return true;
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

        //Intent        intent  = new Intent(this, MyReceiver.class);
        Intent        intent  = new Intent(this, miResibimiento.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(this, ALARM_REQUEST_CODE, intent,  PendingIntent.FLAG_CANCEL_CURRENT);

        manager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + when * 1000, pIntent);


    }


    public void MostramosMensajes(){

        final CharSequence[] PhoneModels = {"iPhone", "Nokia", "Android"};
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(MainActivity.this);
        alt_bld.setIcon(R.drawable.common_signin_btn_icon_dark);
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

                builder.setIcon(R.drawable.common_signin_btn_icon_dark);
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


}

