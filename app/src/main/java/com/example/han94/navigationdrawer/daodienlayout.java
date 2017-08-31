package com.example.han94.navigationdrawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class daodienlayout extends AppCompatActivity {

    ArrayList<String> arr = new ArrayList<String>();
    ArrayList<String> sKey = new ArrayList<String>();
    ArrayAdapter<String> adap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.layoutdaodien);

        ListView lv = (ListView)findViewById(R.id.lvdaodien);

        DatabaseReference mdata = (DatabaseReference) FirebaseDatabase.getInstance().getReference();

        Query qu = mdata.child("Phim");
        qu.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                boolean temp = false;
                for(String n : arr)
                {
                    if(dataSnapshot.child("DaoDien").getValue().toString().equals(n))
                    {
                        temp = true;
                    }
                }
                if(temp == false)
                {
                    arr.add(dataSnapshot.child("DaoDien").getValue().toString());
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

        adap = new  ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, arr);
        lv.setAdapter(adap);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bun = new Bundle();
                bun.putString("KEY", arr.get(position));
                Intent intent = new Intent(daodienlayout.this, lvphimdaodien.class);
                intent.putExtra("DETAIL", bun);
                finish();
                startActivity(intent);
            }
        });

    }
}
