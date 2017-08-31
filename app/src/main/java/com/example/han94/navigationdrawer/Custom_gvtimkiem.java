package com.example.han94.navigationdrawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by han94 on 01/06/2017.
 */

public class Custom_gvtimkiem extends BaseAdapter {

    Context context;
    ArrayList<String> sten = new ArrayList<String>();
    ArrayList<Custom_Gridview_Suatchieu> sgv = new ArrayList<Custom_Gridview_Suatchieu>();

    public Custom_gvtimkiem(Context context, ArrayList<String> sten, ArrayList<Custom_Gridview_Suatchieu> sgv) {
        this.context = context;
        this.sten = sten;
        this.sgv = sgv;
    }

    @Override
    public int getCount() {
        return sten.size();
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
        View rowView = inflater.inflate(R.layout.lvasdsadsa, null);

        TextView imgv = (TextView) rowView.findViewById(R.id.tenphimtxtx);
        GridView gv = (GridView)rowView.findViewById(R.id.gv_suatchieu8);

        imgv.setText(sten.get(position));
        gv.setAdapter(sgv.get(position));

        return rowView;
    }
}
