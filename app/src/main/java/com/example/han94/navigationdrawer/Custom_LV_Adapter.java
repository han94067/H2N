package com.example.han94.navigationdrawer;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by han94 on 24/05/2017.
 */

public class Custom_LV_Adapter extends BaseAdapter {

    ArrayList<String> TenPhim = new ArrayList<String>();
    ArrayList<String> TheLoai = new ArrayList<String>();
    ArrayList<Bitmap> Hinh = new ArrayList<Bitmap>();
 //   ArrayList<Integer> ImgHinhDaiDien = new ArrayList<Integer>();
    Context context;

    public Custom_LV_Adapter(ArrayList<String> tenPhim, ArrayList<String> theLoai, ArrayList<Bitmap> hinh, Context context) {
        TenPhim = tenPhim;
        TheLoai = theLoai;
        Hinh = hinh;
        this.context = context;
    }

    @Override
    public int getCount() {
        return TenPhim.size();
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
        View rowView = inflater.inflate(R.layout.custom_lv, parent, false);

        TextView txtTenPhim = (TextView)rowView.findViewById(R.id.txtTenPhim);
        TextView txtTheLoai = (TextView)rowView.findViewById(R.id.txtTheLoai);
        ImageView imgHinh = (ImageView)rowView.findViewById(R.id.imgHinhDaiDien);

        txtTenPhim.setText(TenPhim.get(position));
        txtTheLoai.setText(TheLoai.get(position));
      //  imgHinh.setImageResource(ImgHinhDaiDien.get(position));
        imgHinh.setImageBitmap(Hinh.get(position));

        return rowView;
    }

    public void update()
    {
        notifyDataSetChanged();
    }
}
