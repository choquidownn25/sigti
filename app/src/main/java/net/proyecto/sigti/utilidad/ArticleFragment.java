package net.proyecto.sigti.utilidad;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.proyecto.sigti.R;

/**
 * Created by choqu_000 on 28/07/2015.
 */
public class ArticleFragment extends Fragment {
    //Atributos
    public static final String ARG_ARTICLES_NUMBER = "articles_number";
    //Constructor
    public ArticleFragment(){

    }
    //Vista para crear la vista en nuestra actividad
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //Clase intanciada a la vista
        View rootView = inflater.inflate(R.layout.fragment_article, container, false);
        int i = getArguments().getInt(ARG_ARTICLES_NUMBER);
        String article = getResources().getStringArray(R.array.Tags)[i];

        //Titulo
        getActivity().setTitle(article);
        TextView headline = (TextView)rootView.findViewById(R.id.headline);
        headline.append(" " + article);

        return rootView;
    }
}
