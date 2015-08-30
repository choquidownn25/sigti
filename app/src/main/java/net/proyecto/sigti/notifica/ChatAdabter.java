package net.proyecto.sigti.notifica;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.proyecto.sigti.R;

import java.util.List;

/**
 * Created by choqu_000 on 17/08/2015.
 *
 * Clase que alista el list del chat
 *
 */
public class ChatAdabter extends ArrayAdapter<ChatObject> {

    List<ChatObject> chat_data;
    Context context;
    int resource;


    public ChatAdabter(Context context, int resource, List<ChatObject>chat_data) {
        super(context, resource, chat_data);

        this.chat_data = chat_data;
        this.context = context;
        this.resource=resource;
    }

    private class ViewHolder{
        TextView textView_left_chat;
        TextView textView_right_chat;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if(convertView==null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.chat_view, null);
            holder = new ViewHolder();
            holder.textView_left_chat=(TextView)convertView.findViewById(R.id.textView_left_chat);
            holder.textView_right_chat=(TextView)convertView.findViewById(R.id.textView_right_chat);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder)convertView.getTag();
        }

        Log.d("pavan", "type " + chat_data.get(position).getType());
        Log.d("pavan","message "+chat_data.get(position).getMessage());
        if(chat_data.get(position).getType().equals("sent")){

            holder.textView_left_chat.setText(chat_data.get(position).getMessage());
            holder.textView_right_chat.setVisibility(View.GONE);
            holder.textView_left_chat.setVisibility(View.VISIBLE);

        } else {

            holder.textView_right_chat.setText(chat_data.get(position).getMessage());
            holder.textView_left_chat.setVisibility(View.GONE);
            holder.textView_right_chat.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    /**
     * Created by choqu_000 on 28/08/2015.
     */
    public static class Trabajo {

        static String[] code = new String[]{
                "Realizado",
                "Postponer"
        };
    }
}

