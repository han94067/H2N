package com.example.han94.navigationdrawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by han94 on 28/05/2017.
 */

public class Custom_Gridview_Suatchieu extends BaseAdapter {

    ArrayList<String> Suat = new ArrayList<>();
    Context context;

    public Custom_Gridview_Suatchieu(Context con, ArrayList<String> arr)
    {
        this.context = con;
        this.Suat = arr;
    }

    @Override
    public int getCount() {
        return Suat.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.custom_gridview_suatchieu, null);

        TextView btn = (TextView) rowView.findViewById(R.id.btnSuatChieu);

        btn.setText(Suat.get(position));

        return rowView;
    }
}
