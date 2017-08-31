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

import static com.example.han94.navigationdrawer.R.id.txtGio22;
import static com.example.han94.navigationdrawer.R.id.txtNgay22;
import static com.example.han94.navigationdrawer.R.id.txtRap22;
import static com.example.han94.navigationdrawer.R.id.txtghengoi22;

/**
 * Created by han94 on 31/05/2017.
 */

public class Custom_vedadat extends BaseAdapter {

    Context context;
    ArrayList<String> TenPhim = new ArrayList<String>();
    ArrayList<String> Ngay = new ArrayList<String>();
    ArrayList<String> Gio = new ArrayList<String>();
    ArrayList<String> Rap = new ArrayList<String>();
    ArrayList<String> Ghe = new ArrayList<String>();
    ArrayList<Bitmap> ImgHinhDaiDien = new ArrayList<Bitmap>();


    public Custom_vedadat(Context context, ArrayList<String> tenPhim, ArrayList<String> ngay, ArrayList<String> gio, ArrayList<String> rap, ArrayList<String> ghe, ArrayList<Bitmap> imgHinhDaiDien) {
        this.context = context;
        TenPhim = tenPhim;
        Ngay = ngay;
        Gio = gio;
        Rap = rap;
        Ghe = ghe;
        ImgHinhDaiDien = imgHinhDaiDien;
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
        View rowView = inflater.inflate(R.layout.custom_listview_vedamua, parent, false);

        TextView txtTenPhim = (TextView)rowView.findViewById(R.id.txtTenPhim22);
        TextView txtNgay = (TextView)rowView.findViewById(txtNgay22);
        TextView txtGio = (TextView)rowView.findViewById(txtGio22);
        TextView txtRap = (TextView)rowView.findViewById(txtRap22);
        TextView txtGhe = (TextView)rowView.findViewById(txtghengoi22);
        ImageView imgHinh = (ImageView)rowView.findViewById(R.id.imgHinhDaiDien22);

        txtTenPhim.setText(TenPhim.get(position));
        txtNgay.setText(Ngay.get(position));
        txtGio.setText(Gio.get(position));
        txtRap.setText(Rap.get(position));
        txtGhe.setText(Ghe.get(position));
        imgHinh.setImageBitmap(ImgHinhDaiDien.get(position));

        return rowView;
    }
}
