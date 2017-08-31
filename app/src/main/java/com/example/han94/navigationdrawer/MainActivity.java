package com.example.han94.navigationdrawer;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

//    public static int[] ImgHinh = {R.drawable.hinh1, R.drawable.hinh2, R.drawable.hinh3,
//            R.drawable.hinh1, R.drawable.hinh2, R.drawable.hinh3,
//            R.drawable.hinh1, R.drawable.hinh2, R.drawable.hinh3};
//    public static String[] TenPhim = {"PIRATES OF THE CARIBBEAN: SALAZAR BÁO THÙ",
//            "PHIM DORAEMON: NOBITA VÀ CHUYẾN THÁM HIỂM NAM CỰC KACHI KOCHI",
//            "RICHARD THE STORK / VẸT CÒ PHIÊU LƯU KÝ",
//            "PIRATES OF THE CARIBBEAN: SALAZAR BÁO THÙ",
//            "PHIM DORAEMON: NOBITA VÀ CHUYẾN THÁM HIỂM NAM CỰC KACHI KOCHI",
//            "RICHARD THE STORK / VẸT CÒ PHIÊU LƯU KÝ",
//            "PIRATES OF THE CARIBBEAN: SALAZAR BÁO THÙ",
//            "PHIM DORAEMON: NOBITA VÀ CHUYẾN THÁM HIỂM NAM CỰC KACHI KOCHI",
//            "RICHARD THE STORK / VẸT CÒ PHIÊU LƯU KÝ"};
//    public static String[] TheLoai = {"Phiêu lưu, Hành động", "Hoạt hình", "Phiêu lưu, hoạt hình",
//            "Phiêu lưu, Hành động", "Hoạt hình", "Phiêu lưu, hoạt hình",
//            "Phiêu lưu, Hành động", "Hoạt hình", "Phiêu lưu, hoạt hình"};

    private static ArrayList<Bitmap> sImg = new ArrayList<Bitmap>();
    private static ArrayList<String> sTenPhim = new ArrayList<String>();
    private static ArrayList<String> sTheLoai = new ArrayList<String>();
    private static ArrayList<String> sKey = new ArrayList<String>();

    private FirebaseAuth auth;
    TabHost tab;
    Button btnDangKy, btnDangNhap, btnDangXuat;
    NavigationView navigationView;
    TextView txtTenND, txtabc;
    Custom_LV_Adapter adap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        txtabc = (TextView)findViewById(R.id.txtabc);

        DatabaseReference mdata = (DatabaseReference) FirebaseDatabase.getInstance().getReference();




        Query qu = mdata.child("Phim");
        qu.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                txtabc.setText(dataSnapshot.getKey().toString());
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
                    sTenPhim.add(dataSnapshot.child("TenPhim").getValue().toString());
                    sTheLoai.add(dataSnapshot.child("TheLoai").getValue().toString());

                    byte[] MangHinh = Base64.decode(dataSnapshot.child("Logo").getValue().toString(), Base64.DEFAULT);
                    Bitmap bmp = BitmapFactory.decodeByteArray(MangHinh, 0, MangHinh.length);
                    sImg.add(bmp);
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


//        mdata.child("Phim").orderByChild("Ngay").equalTo("2017-05-27").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                txtabc.setText(dataSnapshot.child("TenPhim").getValue(String.class));
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//        mdata.child("Phim").push().child("TenPhim").setValue("PIRATES OF THE CARIBBEAN: SALAZAR BÁO THÙ");
//        mdata.child("Phim").push().child("TenPhim").setValue("PHIM DORAEMON: NOBITA VÀ CHUYẾN THÁM HIỂM NAM CỰC KACHI KOCHI");
//        mdata.child("Phim").push().child("TenPhim").setValue("RICHARD THE STORK / VẸT CÒ PHIÊU LƯU KÝ");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);

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
                    Toast.makeText(MainActivity.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
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
                    bun.putInt("VITRI", 1);
                    Intent intent = new Intent(MainActivity.this, Thongtin_TK_Activity.class);
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
                    bun.putInt("VITRI", 1);
                    Intent intent = new Intent(MainActivity.this, Dangky_Activity.class);
                    intent.putExtra("DETAILSUAT", bun);
                    finish();
                    startActivity(intent);
                }
            });

            btnDangNhap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bun = new Bundle();
                    bun.putInt("VITRI", 1);
                    Intent intent = new Intent(MainActivity.this, Dangnhap_Activity.class);
                    intent.putExtra("DETAILSUAT", bun);
                    finish();
                    startActivity(intent);
                }
            });
        }

        navigationView.setNavigationItemSelectedListener(this);

        ListView LV = (ListView) findViewById(R.id.lvPhimDangChieu);
        LoadTab();
        adap = new Custom_LV_Adapter(sTenPhim, sTheLoai, sImg, MainActivity.this);
        LV.setAdapter(adap);

        LV.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this){
            public void onSwipeLeft() {
                tab.setCurrentTab(1);
            }
        });

        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bun = new Bundle();
                bun.putString("KEY", sKey.get(position));
                Intent intent = new Intent(MainActivity.this, detail_movie.class);
                intent.putExtra("DETAIL", bun);
                finish();
                startActivity(intent);
            }
        });

    }

//    @Override
//    protected void onDestroy()
//    {
//        auth.signOut();
//        super.onDestroy();
//    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            if (doubleBackToExitPressedOnce) {
//                super.onBackPressed();
//                return;
//            }
//
//            this.doubleBackToExitPressedOnce = true;
//            Toast.makeText(this, "Nhấn trở về 2 lần để thoát.", Toast.LENGTH_SHORT).show();
//
//            new Handler().postDelayed(new Runnable() {
//
//                @Override
//                public void run() {
//                    doubleBackToExitPressedOnce=false;
//                }
//            }, 2000);
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Thoát");
            builder.setMessage("Bạn thực sự muốn thoát ?");
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            auth.signOut();
                            System.exit(0);
                        }
                    });

            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert11 = builder.create();
            alert11.show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        int id = item.getItemId();
        Intent intent;
        if (id == R.id.nav_thongtin) {
            intent = new Intent(MainActivity.this, ThongTinApp_Activity.class);
            finish();
            startActivity(intent);
        }
        else if (id == R.id.nav_phim) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if(id == R.id.nav_timkiem)
        {
            Bundle bun = new Bundle();
            bun.putString("KEY", "-Kl78IZmj5mO8MYyklVt");
            Intent intent1 = new Intent(MainActivity.this, timkiemsuat.class);
            intent1.putExtra("DETAIL", bun);
            finish();
            startActivity(intent1);
        }
        else if(id == R.id.nav_daodien)
        {
            intent = new Intent(MainActivity.this, daodienlayout.class);
            startActivity(intent);
        }


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void LoadTab() {
        tab = (TabHost) findViewById(android.R.id.tabhost);
        // setup tabhost
        tab.setup();
        TabHost.TabSpec spec;

        //Tạo tab 1
        spec = tab.newTabSpec("t1");
        spec.setContent(R.id.tab1);//set id của Tab1
        spec.setIndicator("Phim Đang Chiếu");//set tên tab1
        tab.addTab(spec);

        //Tạo tab 2
        spec = tab.newTabSpec("t2");
        spec.setContent(R.id.tab2);//set id của Tab2
        spec.setIndicator("Phim Sắp Chiếu");//set tên tab2
        tab.addTab(spec);

        //Thiết lập tab mặc định ban đầu
        tab.setCurrentTab(0);

        //Xử lý khi chuyển tab
        tab.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                // Câu lệnh xử lý
            }
        });

        tab.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this){
            public void onSwipeRight() {
                tab.setCurrentTab(0);
            }
        });
    }

}
