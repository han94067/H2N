package com.example.han94.navigationdrawer;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by han94 on 29/05/2017.
 */

public class Custom_gv_chonghe extends BaseAdapter {

    ArrayList<String> img = new ArrayList<String>();
    Context context;
    String[] s = {"A1", "A2", "A3", "A4", "A5", "A6", "A7","A8", "B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "C1","C2","C3","C4","C5","C6","C7","C8","D1","D2","D3","D4","D5","D6","D7","D8","E1","E2","E3","E4","E5","E6","E7","E8","F1","F2","F3","F4","F5","F6","F7","F8","G1","G2","G3","G4","G5","G6","G7","G8","H1","H2","H3","H4","H5","H6","H7","H8"};

    public Custom_gv_chonghe(ArrayList<String> img, Context context) {
        this.img = img;
        this.context = context;
    }

    @Override
    public int getCount() {
        return img.size();
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
        View rowView = inflater.inflate(R.layout.custom_gv_chongoi, null);

        TextView imgv = (TextView) rowView.findViewById(R.id.imgcho);

        imgv.setText(s[position]);

        if(img.get(position) == "3")
        {
            imgv.setBackgroundColor(Color.rgb(255, 153, 0));
        }
        if(img.get(position) == "1")
        {
            imgv.setBackgroundColor(Color.rgb(176, 164, 164));
        }


        return rowView;
    }
}
