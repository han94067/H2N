package com.example.han94.navigationdrawer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by han94 on 28/05/2017.
 */

public class detail_movie extends YouTubeBaseActivity implements NavigationView.OnNavigationItemSelectedListener, YouTubePlayer.OnInitializedListener {

    private FirebaseAuth auth;
    Button btnDangKy, btnDangNhap, btnDangXuat;
    NavigationView navigationView;
    TextView txtTenND, txtTenPhim, txtTheLoai, txtNoiDung, txtXemND, txtNgayChieu;
    ArrayList<String> sSuatChieu = new ArrayList<String>();
    ArrayList<String> sKeySuat = new ArrayList<String>();
    Custom_Gridview_Suatchieu adap;
    String keyyoutube, tenphim;
    String a;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.detail_movie_layout);

        txtTenPhim = (TextView)findViewById(R.id.txtTenPhim1);
        txtNoiDung = (TextView)findViewById(R.id.txtNoiDung);
        txtTheLoai = (TextView)findViewById(R.id.txtTheLoai1);
        txtXemND = (TextView)findViewById(R.id.btnXemND);
        txtNgayChieu = (TextView)findViewById(R.id.txtNgaychieu);

        Intent intent = getIntent();
        Bundle bun = intent.getBundleExtra("DETAIL");
        a = bun.getString("KEY");

        DatabaseReference mdata = (DatabaseReference) FirebaseDatabase.getInstance().getReference();

        mdata.child("Phim").child(a).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                txtTenPhim.setText(dataSnapshot.child("TenPhim").getValue().toString());
                txtTheLoai.setText(dataSnapshot.child("TheLoai").getValue().toString());
                txtNoiDung.setText(dataSnapshot.child("NoiDung").getValue().toString());
                tenphim = dataSnapshot.child("TenPhim").getValue().toString();
                keyyoutube = dataSnapshot.child("Trailer").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Query qu = mdata.child("Phim").child(a).child("SuatChieu");
        qu.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                boolean temp = false;
                for(String n : sSuatChieu)
                {
                    if(dataSnapshot.getKey().equals(n))
                    {
                        temp = true;
                    }
                }
                if(temp == false)
                {
                    sSuatChieu.add(dataSnapshot.child("ThoiGian").getValue().toString());
                    sKeySuat.add(dataSnapshot.getKey().toString());
                    txtNgayChieu.setText(dataSnapshot.child("Ngay").getValue().toString());
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

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
//      //  setSupportActionBar(toolbar);
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout1);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view1);

        View navHeaderView;
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null)
        {
            navHeaderView = navigationView.inflateHeaderView(R.layout.nav_header_account);
            txtTenND = (TextView)navHeaderView.findViewById(R.id.txtTenNguoiDung);
            txtTenND.setText(auth.getCurrentUser().getEmail().toString());
            btnDangXuat = (Button)navHeaderView.findViewById(R.id.btnDangXuat);
            btnDangXuat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    auth.signOut();
                    Toast.makeText(detail_movie.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            });
            Button btnThongTin = (Button)navHeaderView.findViewById(R.id.btnThongTinTK);
            btnThongTin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bun = new Bundle();
                    bun.putInt("VITRI", 2);
                    bun.putString("KEY", a);
                    Intent intent = new Intent(detail_movie.this, Thongtin_TK_Activity.class);
                    intent.putExtra("DETAILSUAT", bun);
                    finish();
                    startActivity(intent);
                }
            });
        }
        else
        {
            navHeaderView = navigationView.inflateHeaderView(R.layout.nav_header_main);
            btnDangKy = (Button)navHeaderView.findViewById(R.id.btnDangKy);
            btnDangNhap = (Button)navHeaderView.findViewById(R.id.btnDangNhap);
            btnDangKy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bun = new Bundle();
                    bun.putInt("VITRI", 2);
                    bun.putString("KEY", a);
                    Intent intent = new Intent(detail_movie.this, Dangky_Activity.class);
                    intent.putExtra("DETAILSUAT", bun);
                    finish();
                    startActivity(intent);
                }
            });

            btnDangNhap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bun = new Bundle();
                    bun.putInt("VITRI", 2);
                    bun.putString("KEY", a);
                    Intent intent = new Intent(detail_movie.this, Dangnhap_Activity.class);
                    intent.putExtra("DETAILSUAT", bun);
                    finish();
                    startActivity(intent);
                }
            });
        }

        navigationView.setNavigationItemSelectedListener(detail_movie.this);

    //    ScrollView scrool = (ScrollView)findViewById(R.id.srool);

        adap = new Custom_Gridview_Suatchieu(detail_movie.this, sSuatChieu);
        final GridView gv = (GridView)findViewById(R.id.gv_suatchieu);
        gv.setAdapter(adap);

        txtXemND.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtXemND.getText() == "Xem thêm ...")
                {
                    txtNoiDung.setMaxLines(Integer.MAX_VALUE);
                    txtXemND.setText("Rút gọn");
                }
                else
                {
                    txtNoiDung.setMaxLines(3);
                    txtXemND.setText("Xem thêm ...");
                }
            }
        });

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(auth.getCurrentUser() != null) {
                    Bundle bun = new Bundle();
                    bun.putString("KEYSUAT", sKeySuat.get(position));
                    bun.putString("KEY", a);
                    bun.putString("TEN", tenphim);
                    Intent intent = new Intent(detail_movie.this, Datve_activity.class);
                    intent.putExtra("DETAILSUAT", bun);
                    finish();
                    startActivity(intent);
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(detail_movie.this);
                    builder.setTitle("Thông Báo");
                    builder.setMessage("Vui lòng đăng nhập trước khi đặt vé.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Bundle bun = new Bundle();
                            bun.putInt("VITRI", 2);
                            bun.putString("KEY", a);
                            Intent intent = new Intent(detail_movie.this, Dangnhap_Activity.class);
                            intent.putExtra("DETAILSUAT", bun);
                            finish();
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert11 = builder.create();
                    alert11.show();
                }
            }
        });

        String API_KEY= "AIzaSyAwGPy3j-j8eCOSFQ_uLGorMy4HEpHjXwQ";
        YouTubePlayerView you = (YouTubePlayerView)findViewById(R.id.youtube2);
        you.initialize(API_KEY, this);

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        int id = item.getItemId();
        Intent intent;
        if (id == R.id.nav_thongtin) {
            intent = new Intent(detail_movie.this, ThongTinApp_Activity.class);
            finish();
            startActivity(intent);
        }
        else if (id == R.id.nav_phim) {
            intent = new Intent(detail_movie.this, MainActivity.class);
            finish();
            startActivity(intent);
        }
        else if(id == R.id.nav_timkiem)
        {
            Bundle bun = new Bundle();
            bun.putString("KEY", a);
            Intent intent1 = new Intent(detail_movie.this, timkiemsuat.class);
            intent1.putExtra("DETAIL", bun);
            finish();
            startActivity(intent1);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout1);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout1);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(detail_movie.this, MainActivity.class);
            startActivity(intent);
            finish();
            super.onBackPressed();
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.cueVideo(keyyoutube);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
    }
}

