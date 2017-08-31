package com.example.han94.navigationdrawer;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by han94 on 30/05/2017.
 */

public class Xacnhan_Activity extends AppCompatActivity {

    String keysuat, keyphim, tenphim;
    int soluongve;
    ArrayList<Integer> sKeyGhe = new ArrayList<Integer>();
    TextView txtTenphim, txtGio, txtNgay, txtRap, txtTong, txtGheNgoi;
    ArrayList<String> sGhe = new ArrayList<String>();
    Button btnXacNhan, btnHuyBo;
    boolean temp = true;
    private FirebaseAuth auth;
    ImageView img;
    String Hinh = null, Ghe = "";
    Ve ve1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.xacnhanve_layout);

        Intent intent = getIntent();
        Bundle bun = intent.getBundleExtra("XACNHAN");
        keyphim = bun.getString("KEY");
        keysuat = bun.getString("KEYSUAT");
        tenphim = bun.getString("TEN");
        soluongve = bun.getInt("VE");
        sKeyGhe = bun.getIntegerArrayList("KEYGHENGOI");

        txtTenphim = (TextView)findViewById(R.id.txtTenPhim11);
        txtNgay = (TextView)findViewById(R.id.txtNgay1);
        txtGio = (TextView)findViewById(R.id.txtGio1);
        txtRap = (TextView)findViewById(R.id.txtRap1);
        txtTong = (TextView)findViewById(R.id.txtTongtien);
        txtGheNgoi = (TextView)findViewById(R.id.txtghengoi1);
        ImageButton btnBack = (ImageButton)findViewById(R.id.btnBack3);
        btnXacNhan = (Button)findViewById(R.id.btnXacNhan);
        btnHuyBo = (Button)findViewById(R.id.btnHuyBo);
        img = (ImageView)findViewById(R.id.imgHinhDaiDien1);



        for(int i = 0; i < sKeyGhe.size(); i++)
        {
            int temp = sKeyGhe.get(i) + 1;
            int a = temp / 8;
            int b = temp % 8;
            String s = "";
            if(b == 0)
            {
                switch (a)
                {
                    case 1:
                        s = "A";
                        break;
                    case 2:
                        s = "B";
                        break;
                    case 3:
                        s = "C";
                        break;
                    case 4:
                        s = "D";
                        break;
                    case 5:
                        s = "E";
                        break;
                    case 6:
                        s = "F";
                        break;
                    case 7:
                        s = "G";
                        break;
                    case 8:
                        s = "H";
                        break;
                }
                b = 8;
            }
            else
            {
                switch (a)
                {
                    case 0:
                        s = "A";
                        break;
                    case 1:
                        s = "B";
                        break;
                    case 2:
                        s = "C";
                        break;
                    case 3:
                        s = "D";
                        break;
                    case 4:
                        s = "E";
                        break;
                    case 5:
                        s = "F";
                        break;
                    case 6:
                        s = "G";
                        break;
                    case 7:
                        s = "H";
                        break;
                }

            }
            sGhe.add(s + b);
        }

        for(int i = 0; i < sGhe.size();i++)
        {
            txtGheNgoi.setText(txtGheNgoi.getText() + sGhe.get(i) + " ");
            Ghe = Ghe + sGhe.get(i) + " ";
        }

        txtTenphim.setText(tenphim);
        String tien = (String.format("%,d", soluongve * 80000)).replace(',', ' ');
        txtTong.setText(tien + " VND");

        final DatabaseReference mdata = (DatabaseReference) FirebaseDatabase.getInstance().getReference();


        mdata.child("Phim").child(keyphim).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Hinh = dataSnapshot.child("Logo").getValue().toString();
                byte[] MangHinh = Base64.decode(dataSnapshot.child("Logo").getValue().toString(), Base64.DEFAULT);
                Bitmap bmp = BitmapFactory.decodeByteArray(MangHinh, 0, MangHinh.length);
                img.setImageBitmap(bmp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mdata.child("Phim").child(keyphim).child("SuatChieu").child(keysuat).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                txtNgay.setText(dataSnapshot.child("Ngay").getValue().toString());
                txtGio.setText(dataSnapshot.child("ThoiGian").getValue().toString());
                txtRap.setText(dataSnapshot.child("Rap").getValue().toString());
                ve1 = new Ve(tenphim, dataSnapshot.child("Ngay").getValue().toString(), dataSnapshot.child("ThoiGian").getValue().toString(),
                        dataSnapshot.child("Rap").getValue().toString(), Ghe, Hinh);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnHuyBo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i < sKeyGhe.size(); i++)
                {
                    mdata.child("Phim").child(keyphim).child("SuatChieu").child(keysuat).child("GheNgoi").child((sKeyGhe.get(i) + 1) + "").setValue(1);
                }
                Intent intent = new Intent(Xacnhan_Activity.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference mdata1 = (DatabaseReference) FirebaseDatabase.getInstance().getReference().child("Phim").child(keyphim).child("SuatChieu").child(keysuat).child("GheNgoi");
                auth = FirebaseAuth.getInstance();
                final String sid = auth.getCurrentUser().getUid().toString();
                for(int i = 0; i < sKeyGhe.size(); i++)
                {
                    mdata1.child((sKeyGhe.get(i) + 1) + "").setValue(null);
                    mdata1.child((sKeyGhe.get(i) + 1) + "").setValue(sid);
                }
                DatabaseReference mdata2 = (DatabaseReference) FirebaseDatabase.getInstance().getReference().child("User");
                mdata2.child(auth.getCurrentUser().getUid().toString()).push().setValue(ve1);

                AlertDialog.Builder builder = new AlertDialog.Builder(Xacnhan_Activity.this);
                builder.setTitle("Thông Báo");
                builder.setMessage("Đặt vé thành công.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Xacnhan_Activity.this, MainActivity.class);
                        finish();
                        startActivity(intent);
                    }
                });

//                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//                    }
//                });
                AlertDialog alert11 = builder.create();
                alert11.show();
//                Intent intent = new Intent(Xacnhan_Activity.this, MainActivity.class);
//                finish();
//                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DatabaseReference mdata = (DatabaseReference) FirebaseDatabase.getInstance().getReference();
        for(int i = 0; i < sKeyGhe.size(); i++)
        {
            mdata.child("Phim").child(keyphim).child("SuatChieu").child(keysuat).child("GheNgoi").child((sKeyGhe.get(i) + 1) + "").setValue(1);
        }
        Bundle bun = new Bundle();
        bun.putString("KEYSUAT", keysuat);
        bun.putString("KEY", keyphim);
        bun.putInt("VE", soluongve);
        bun.putString("TENPHIM", tenphim);
        Intent intent = new Intent(Xacnhan_Activity.this, Datve_Rap1.class);
        intent.putExtra("CHONVE", bun);
        finish();
        startActivity(intent);
        super.onBackPressed();
    }

//    @Override
//    protected void onDestroy() {
//        DatabaseReference mdata = (DatabaseReference) FirebaseDatabase.getInstance().getReference();
//        for(int i = 0; i < sKeyGhe.size(); i++)
//        {
//            mdata.child("Phim").child(keyphim).child("SuatChieu").child(keysuat).child("GheNgoi").child((sKeyGhe.get(i) + 1) + "").setValue(1);
//        }
//        super.onDestroy();
//    }

//    @Override
//    protected void onStop() {
//        DatabaseReference mdata = (DatabaseReference) FirebaseDatabase.getInstance().getReference();
//        for(int i = 0; i < sKeyGhe.size(); i++)
//        {
//            mdata.child("Phim").child(keyphim).child("SuatChieu").child(keysuat).child("GheNgoi").child((sKeyGhe.get(i) + 1) + "").setValue(1);
//        }
//        super.onStop();
//    }

//    @Override
//    public void onAttachedToWindow() {
//        for(int i = 0; i < sKeyGhe.size(); i++)
//        {
//            mdata.child("Phim").child(keyphim).child("SuatChieu").child(keysuat).child("GheNgoi").child((sKeyGhe.get(i) + 1) + "").setValue(1);
//        }
//        super.onAttachedToWindow();
//    }


}
