package net.proyecto.sigti.vista;

import android.app.Activity;
import android.os.Bundle;

import net.proyecto.sigti.R;

/**
 * Created by choqu_000 on 29/07/2015.
 */
public class HomeFragment extends Activity {

    public HomeFragment() {
    }


   protected void onCreate(Bundle saveInstanceState){
       super.onCreate(saveInstanceState);
       setContentView(R.layout.home);
   }
}