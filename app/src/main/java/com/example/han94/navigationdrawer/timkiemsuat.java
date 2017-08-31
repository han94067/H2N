package com.example.han94.navigationdrawer;

import android.app.DatePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by han94 on 01/06/2017.
 */

public class timkiemsuat extends AppCompatActivity {

    Calendar cal;
    Date dateFinish;
    TextView txtNgay;
    Button btnNgay, btnTimKiem;
    String tg;
    ArrayList<String> sSuatChieu = new ArrayList<String>();
    ArrayList<String> sKeyPhim = new ArrayList<String>();
    ArrayList<String> sTemp = new ArrayList<String>();
    Custom_Gridview_Suatchieu adap;
    ArrayList<String> sTenPhim = new ArrayList<String>();
    ArrayList<Custom_Gridview_Suatchieu> sgv = new ArrayList<Custom_Gridview_Suatchieu>();
    Custom_gvtimkiem adapaa;
    int GioTu, GioDen;
    String[] gio = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" };

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.timkiemsuatchieu);

        final DatabaseReference mdata = (DatabaseReference) FirebaseDatabase.getInstance().getReference();

        txtNgay = (TextView)findViewById(R.id.txttgngay);
        btnNgay = (Button)findViewById(R.id.btnchonngay);
        btnTimKiem = (Button)findViewById(R.id.btnTimkiemsuatchieu);
        final ListView lv = (ListView)findViewById(R.id.lvtimkiemtheotg);
        final Spinner spgiotu = (Spinner)findViewById(R.id.spGioTu);
        final Spinner spgioden = (Spinner)findViewById(R.id.spGioDen);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(timkiemsuat.this, android.R.layout.simple_spinner_item, gio);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spgiotu.setAdapter(adapter);
        spgioden.setAdapter(adapter);
        Button btnOK = (Button)findViewById(R.id.btnOK);

        getDefaultInfor();

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (String key : sKeyPhim) {
                    GioTu = Integer.parseInt(spgiotu.getSelectedItem().toString());
                    GioDen = Integer.parseInt(spgioden.getSelectedItem().toString());
                    Query qu = mdata.child("Phim").child(key).child("SuatChieu").orderByChild("Ngay").equalTo(txtNgay.getText().toString());
                    qu.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot1, String s) {
                            Toast.makeText(timkiemsuat.this, "c", Toast.LENGTH_SHORT).show();
                            tg = dataSnapshot1.child("ThoiGian").getValue().toString();
                            String temp = "";
                            temp += tg.charAt(0);
                            temp += tg.charAt(1);
                            int w = Integer.parseInt(temp);

                            if (w >= GioTu && w <= GioDen) {
                                sTemp.add(dataSnapshot1.child("KeyPhim").getValue().toString());
                                sSuatChieu.add(dataSnapshot1.child("ThoiGian").getValue().toString());
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

//                        adap = new Custom_Gridview_Suatchieu(timkiemsuat.this, sSuatChieu);
//                        sgv.add(adap);
//                        adapaa.notifyDataSetChanged();
                }
            }
        });


        btnNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();


                Query qu1 = mdata.child("Phim");
                qu1.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        sTenPhim.add(dataSnapshot.child("TenPhim").getValue().toString());
                        sKeyPhim.add(dataSnapshot.getKey().toString());

                        Toast.makeText(timkiemsuat.this, "a", Toast.LENGTH_SHORT).show();
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
            }
        });



        btnTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GioTu = Integer.parseInt(spgiotu.getSelectedItem().toString());
                GioDen = Integer.parseInt(spgioden.getSelectedItem().toString());
                if(GioTu >= GioDen)
                {
                    Toast.makeText(timkiemsuat.this, "Vui lòng chọn lại giờ", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(timkiemsuat.this, sSuatChieu.size() + "", Toast.LENGTH_SHORT).show();

                    int n;
                    for (n = 0; n < sKeyPhim.size(); n++)
                    {
                        ArrayList<String> abc = new ArrayList<String>();
                        for (int i = 0; i < sTemp.size(); i++) {

                            if(sTemp.get(i).equals(sKeyPhim.get(n)))
                            {
                                abc.add(sSuatChieu.get(i));
                            }

                        }
                        if (abc.size() == 0)
                        {
                            Toast.makeText(timkiemsuat.this, "ero", Toast.LENGTH_SHORT).show();
                            sTenPhim.remove(sTenPhim.get(n));
                        }
                        else {
                            adap = new Custom_Gridview_Suatchieu(timkiemsuat.this, abc);
                            sgv.add(adap);
                           // abc.clear();
                        }


                    }

              //      adapaa = new Custom_gvtimkiem(timkiemsuat.this, sTenPhim, sgv);
              //      lv.setAdapter(adapaa);
                    adapaa.notifyDataSetChanged();
                }

            }
        });


        adapaa = new Custom_gvtimkiem(timkiemsuat.this, sTenPhim, sgv);
        lv.setAdapter(adapaa);


    }


    public void showDatePickerDialog()
    {
        DatePickerDialog.OnDateSetListener callback=new DatePickerDialog.OnDateSetListener() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {

            txtNgay.setText((dayOfMonth) +"-"+(monthOfYear+1)+"-"+year);

            cal.set(year, monthOfYear, dayOfMonth);
            dateFinish=cal.getTime();
        }
        };

        String s=txtNgay.getText()+"";
        String strArrtmp[]=s.split("-");
        int ngay=Integer.parseInt(strArrtmp[0]);
        int thang=Integer.parseInt(strArrtmp[1])-1;
        int nam=Integer.parseInt(strArrtmp[2]);
        DatePickerDialog pic=new DatePickerDialog(timkiemsuat.this, callback, nam, thang, ngay);
        pic.setTitle("Chọn ngày hoàn thành");
        pic.show();
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getDefaultInfor()
    {
        cal=Calendar.getInstance();
        SimpleDateFormat dft=null;

        dft=new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String strDate=dft.format(cal.getTime());

        txtNgay.setText(strDate);
    }

}
