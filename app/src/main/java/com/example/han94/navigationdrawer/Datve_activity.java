package com.example.han94.navigationdrawer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by han94 on 28/05/2017.
 */

public class Datve_activity extends AppCompatActivity {

    Button btnCong, btnTru, btnTiepTuc;
    TextView txtSoLuong, txtThanhtien, txtTenPhim, txtNgay, txtGio, txtRap;
    int soluong = 0, tien = 0;
    String keyphim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.datve_layout);

        btnCong = (Button)findViewById(R.id.btnCong);
        btnTru = (Button)findViewById(R.id.btnTru);
        txtSoLuong = (TextView)findViewById(R.id.txtSoLuong);
        txtThanhtien = (TextView)findViewById(R.id.txtThanhtien);
        txtTenPhim = (TextView)findViewById(R.id.txtTenPhim2);
        txtNgay = (TextView)findViewById(R.id.txtNgay);
        txtGio = (TextView)findViewById(R.id.txtGio);
        txtRap = (TextView)findViewById(R.id.txtRap);
        btnTiepTuc = (Button)findViewById(R.id.btnTiepTuc);
        ImageButton btnBack = (ImageButton)findViewById(R.id.btnBack);

        Intent intent = getIntent();
        Bundle bun = intent.getBundleExtra("DETAILSUAT");
        keyphim = bun.getString("KEY");
        final String keysuat = bun.getString("KEYSUAT");
        final String tenphim = bun.getString("TEN");

        txtTenPhim.setText(tenphim);

        DatabaseReference mdata = (DatabaseReference) FirebaseDatabase.getInstance().getReference();

        mdata.child("Phim").child(keyphim).child("SuatChieu").child(keysuat).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                txtNgay.setText(dataSnapshot.child("Ngay").getValue().toString());
                txtGio.setText(dataSnapshot.child("ThoiGian").getValue().toString());
                txtRap.setText(dataSnapshot.child("Rap").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(soluong < 9) {
                    soluong++;
                    txtSoLuong.setText(soluong + "");
                    String s = (String.format("%,d", soluong * 80000)).replace(',', ' ');
                    txtThanhtien.setText(s + " VND");
                    if(soluong >= 1)
                    {
                        btnTiepTuc.setBackgroundColor(Color.rgb(255, 51, 51));
                    }
                }
            }
        });

        btnTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(soluong > 0)
                {
                    soluong--;
                    txtSoLuong.setText(soluong + "");
                    String s = (String.format("%,d", soluong * 80000)).replace(',', ' ');
                    txtThanhtien.setText(s + " VND");
                    if (soluong == 0)
                    {
                        btnTiepTuc.setBackgroundColor(Color.rgb(193, 157, 157));
                    }

                }
            }
        });

        btnTiepTuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(soluong > 0) {
                    Bundle bun = new Bundle();
                    bun.putString("KEYSUAT", keysuat);
                    bun.putString("KEY", keyphim);
                    bun.putInt("VE", soluong);
                    bun.putString("TENPHIM", tenphim);
                    Intent intent = new Intent(Datve_activity.this, Datve_Rap1.class);
                    intent.putExtra("CHONVE", bun);
                    finish();
                    startActivity(intent);
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Bundle bun = new Bundle();
        bun.putString("KEY", keyphim);
        Intent intent = new Intent(Datve_activity.this, detail_movie.class);
        intent.putExtra("DETAIL", bun);
        finish();
        startActivity(intent);
        super.onBackPressed();
    }

}
