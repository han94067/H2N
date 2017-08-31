package com.example.han94.navigationdrawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

/**
 * Created by han94 on 31/05/2017.
 */

public class ThongTinApp_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.thontinapp_layout);

        ImageButton btnBack = (ImageButton)findViewById(R.id.btnBack7);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intene = new Intent(ThongTinApp_Activity.this, MainActivity.class);
        finish();
        startActivity(intene);
        super.onBackPressed();
    }
}
