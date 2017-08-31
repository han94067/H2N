package com.example.han94.navigationdrawer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

/**
 * Created by han94 on 31/05/2017.
 */

public class Thongtin_TK_Activity extends AppCompatActivity {

    FirebaseAuth auth;
    ListView LV;
    TextView txtTenTK;
    ArrayList<String> TenPhim = new ArrayList<String>();
    ArrayList<String> Ngay = new ArrayList<String>();
    ArrayList<String> Gio = new ArrayList<String>();
    ArrayList<String> Rap = new ArrayList<String>();
    ArrayList<String> Ghe = new ArrayList<String>();
    ArrayList<Bitmap> Hinh = new ArrayList<Bitmap>();
    ArrayList<String> sKey = new ArrayList<String>();
    Custom_vedadat adap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.thongtintk_layout);

        LV = (ListView)findViewById(R.id.LVvedamua);
        txtTenTK = (TextView)findViewById(R.id.txtTenNguoiDung1);
        Button btnDX = (Button)findViewById(R.id.btnDX);
        ImageButton btnBack = (ImageButton) findViewById(R.id.btnBack6);

        auth = FirebaseAuth.getInstance();
        txtTenTK.setText(auth.getCurrentUser().getEmail().toString());

//        TenPhim = null;
//        Ngay = null;
//        Gio = null;
//        Rap = null;
//        Ghe = null;
//        Hinh = null;

        DatabaseReference mdata = (DatabaseReference) FirebaseDatabase.getInstance().getReference();

        Query qu = mdata.child("User").child(auth.getCurrentUser().getUid().toString());
        qu.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                boolean temp = false;
                for(String n : sKey)
                {
                    if(dataSnapshot.getKey().equals(n))
                    {
                        temp = true;
                    }
                }
                if(temp == false)
                {
                    TenPhim.add(dataSnapshot.child("TenPhim").getValue().toString());
                    Ngay.add(dataSnapshot.child("Ngay").getValue().toString());
                    Gio.add(dataSnapshot.child("Gio").getValue().toString());
                    Rap.add("Rạp: " + dataSnapshot.child("Rap").getValue().toString());
                    Ghe.add(dataSnapshot.child("Ghe").getValue().toString());
                    byte[] MangHinh = Base64.decode(dataSnapshot.child("Hinh").getValue().toString(), Base64.DEFAULT);
                    Bitmap bmp = BitmapFactory.decodeByteArray(MangHinh, 0, MangHinh.length);
                    Hinh.add(bmp);
                    sKey.add(dataSnapshot.getKey().toString());
                }
                adap.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        adap = new Custom_vedadat(Thongtin_TK_Activity.this, TenPhim, Ngay, Gio, Rap, Ghe, Hinh);
        LV.setAdapter(adap);

        btnDX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Toast.makeText(Thongtin_TK_Activity.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                Intent intent = getIntent();
                Bundle bun = intent.getBundleExtra("DETAILSUAT");
                int key = bun.getInt("VITRI");
                if(key == 1)
                {
                    intent = new Intent(Thongtin_TK_Activity.this, MainActivity.class);
                    finish();
                    startActivity(intent);
                }
                else if(key == 2)
                {
                    String s = bun.getString("KEY");
                    Bundle bun1 = new Bundle();
                    bun1.putString("KEY", s);
                    intent = new Intent(Thongtin_TK_Activity.this, detail_movie.class);
                    intent.putExtra("DETAIL", bun1);
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
        Intent intent = getIntent();
        Bundle bun = intent.getBundleExtra("DETAILSUAT");
        int key = bun.getInt("VITRI");
        if(key == 1)
        {
            intent = new Intent(Thongtin_TK_Activity.this, MainActivity.class);
            finish();
            startActivity(intent);
        }
        else if(key == 2)
        {
            String s = bun.getString("KEY");
            Bundle bun1 = new Bundle();
            bun1.putString("KEY", s);
            intent = new Intent(Thongtin_TK_Activity.this, detail_movie.class);
            intent.putExtra("DETAIL", bun1);
            finish();
            startActivity(intent);
        }
        super.onBackPressed();
    }

}
