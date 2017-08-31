package com.example.han94.navigationdrawer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

/**
 * Created by han94 on 01/06/2017.
 */

public class lvphimdaodien extends AppCompatActivity {

    String a;
    private static ArrayList<Bitmap> sImg = new ArrayList<Bitmap>();
    private static ArrayList<String> sTenPhim = new ArrayList<String>();
    private static ArrayList<String> sTheLoai = new ArrayList<String>();
    private static ArrayList<String> sKey = new ArrayList<String>();
    Custom_LV_Adapter adap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.phimdaodien);

        Intent intent = getIntent();
        Bundle bun = intent.getBundleExtra("DETAIL");
        a = bun.getString("KEY");

        ListView lv = (ListView)findViewById(R.id.lvphimdaodien);
        DatabaseReference mdata = (DatabaseReference) FirebaseDatabase.getInstance().getReference();

        Query qu = mdata.child("Phim");
        qu.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if(dataSnapshot.child("DaoDien").getValue().toString().equals(a)) {
                    boolean temp = false;
                    for (String n : sKey) {
                        if (dataSnapshot.getKey().equals(n)) {
                            temp = true;
                        }
                    }
                    if (temp == false) {
                        sTenPhim.add(dataSnapshot.child("TenPhim").getValue().toString());
                        sTheLoai.add(dataSnapshot.child("TheLoai").getValue().toString());

                        byte[] MangHinh = Base64.decode(dataSnapshot.child("Logo").getValue().toString(), Base64.DEFAULT);
                        Bitmap bmp = BitmapFactory.decodeByteArray(MangHinh, 0, MangHinh.length);
                        sImg.add(bmp);
                        sKey.add(dataSnapshot.getKey().toString());
                    }
                    adap.notifyDataSetChanged();
                }
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

        adap = new Custom_LV_Adapter(sTenPhim, sTheLoai, sImg, lvphimdaodien.this);
        lv.setAdapter(adap);
    }
}
