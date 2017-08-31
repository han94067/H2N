package com.example.han94.navigationdrawer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by han94 on 29/05/2017.
 */

public class Datve_Rap1 extends AppCompatActivity {

    ArrayList<String> sGheNgoi = new ArrayList<String>();
    int soluong;
    TextView txtThongBao, txtTieptuc;
    String keysuat, keyphim, tenphim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.datve_rap1);

        Intent intent = getIntent();
        Bundle bun = intent.getBundleExtra("CHONVE");
        keyphim = bun.getString("KEY");
        keysuat = bun.getString("KEYSUAT");
        soluong = bun.getInt("VE");
        final int sove = bun.getInt("VE");
        tenphim = bun.getString("TENPHIM");

        txtThongBao = (TextView)findViewById(R.id.txtThongBaoGhe);
        txtTieptuc = (TextView)findViewById(R.id.txtTT);
        txtThongBao.setText("Vui lòng chọn " + soluong + " ghế");
        ImageButton btnBack = (ImageButton)findViewById(R.id.btnBack1);

        GridView gv = (GridView)findViewById(R.id.gvChoNgoi);
        final Custom_gv_chonghe adap = new Custom_gv_chonghe(sGheNgoi, Datve_Rap1.this);


        final DatabaseReference mdata = (DatabaseReference) FirebaseDatabase.getInstance().getReference();

        mdata.child("Phim").child(keyphim).child("SuatChieu").child(keysuat).child("GheNgoi").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                sGheNgoi.add(dataSnapshot.getValue().toString());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if(Integer.parseInt(dataSnapshot.getValue().toString()) == 4)
                {
                    sGheNgoi.set(Integer.parseInt(dataSnapshot.getKey().toString()) - 1, "2");
                    adap.notifyDataSetChanged();
                }
                else if(Integer.parseInt(dataSnapshot.getValue().toString()) == 1)
                {
                    sGheNgoi.set(Integer.parseInt(dataSnapshot.getKey().toString()) - 1, "1");
                    adap.notifyDataSetChanged();
                }
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

        gv.setAdapter(adap);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    if (sGheNgoi.get(position) == "1") {
                        if(soluong > 0) {
                            sGheNgoi.set(position, "3");
                            soluong--;
                            txtThongBao.setText("Vui lòng chọn " + soluong + " ghế");
                        }
                        if (soluong == 0)
                        {
                            txtTieptuc.setBackgroundColor(Color.rgb(255, 159, 56));
                            txtThongBao.setText("Vui lòng nhấn tiếp tục");
                        }
                    } else if (sGheNgoi.get(position) == "3") {
                        sGheNgoi.set(position, "1");
                        soluong++;
                        txtThongBao.setText("Vui lòng chọn " + soluong + " ghế");
                        txtTieptuc.setBackgroundColor(Color.rgb(193, 157, 157));
                    }
                    adap.notifyDataSetChanged();

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        final ArrayList<Integer> skeyghengoi = new ArrayList<Integer>();

        txtTieptuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (soluong == 0) {
                    for (int i = 0; i < sGheNgoi.size(); i++) {
                        if (sGheNgoi.get(i) == "3") {
                            skeyghengoi.add(i);
                        }
                    }
                    for(int i = 0; i < skeyghengoi.size(); i++)
                    {
                        mdata.child("Phim").child(keyphim).child("SuatChieu").child(keysuat).child("GheNgoi").child((skeyghengoi.get(i) + 1) + "").setValue("4");
                    }
                    Bundle bun1 = new Bundle();
                    bun1.putString("KEYSUAT", keysuat);
                    bun1.putString("KEY", keyphim);
                    bun1.putString("TEN", tenphim);
                    bun1.putInt("VE", sove);
                    bun1.putIntegerArrayList("KEYGHENGOI", skeyghengoi);
                    Intent intent1 = new Intent(Datve_Rap1.this, Xacnhan_Activity.class);
                    intent1.putExtra("XACNHAN", bun1);
                    finish();
                    startActivity(intent1);
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        Bundle bun = new Bundle();
        bun.putString("KEYSUAT", keysuat);
        bun.putString("KEY", keyphim);
        bun.putString("TEN", tenphim);
        Intent intent = new Intent(Datve_Rap1.this, Datve_activity.class);
        intent.putExtra("DETAILSUAT", bun);
        finish();
        startActivity(intent);
        super.onBackPressed();
    }
}


