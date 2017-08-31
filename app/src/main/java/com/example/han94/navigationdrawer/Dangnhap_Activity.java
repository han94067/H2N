package com.example.han94.navigationdrawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by han94 on 25/05/2017.
 */

public class Dangnhap_Activity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(Dangnhap_Activity.this, MainActivity.class));
            finish();
        }

        // set the view now
        setContentView(R.layout.login_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
//        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.btn_login);
//        btnReset = (Button) findViewById(R.id.btn_reset_password);
        ImageButton btnBack = (ImageButton)findViewById(R.id.btnBack4);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
//
//        btnSignup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(Dangnhap_Activity.this, SignupActivity.class));
//            }
//        });

//        btnReset.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
//            }
//        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    //Toast.makeText(getApplicationContext(), "Xin mời nhập Email !", Toast.LENGTH_SHORT).show();
                    inputEmail.setError("Xin mời nhập Email !");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    //Toast.makeText(getApplicationContext(), "Xin mời nhập Mật khẩu !!", Toast.LENGTH_SHORT).show();
                    inputPassword.setError("Xin mời nhập Mật khẩu !");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Dangnhap_Activity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        inputPassword.setError("Mật khẩu quá ngắn, phải nhiều hơn 6 kí tự !");
                                    } else {
                                        Toast.makeText(Dangnhap_Activity.this, "Thông tin đăng nhập không chính xác !", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(Dangnhap_Activity.this, "Đăng nhập thành công !", Toast.LENGTH_LONG).show();
                                    Intent intent = getIntent();
                                    Bundle bun = intent.getBundleExtra("DETAILSUAT");
                                    int key = bun.getInt("VITRI");
                                    if(key == 1)
                                    {
                                        intent = new Intent(Dangnhap_Activity.this, MainActivity.class);
                                        finish();
                                        startActivity(intent);
                                    }
                                    else if(key == 2)
                                    {
                                        String s = bun.getString("KEY");
                                        Bundle bun1 = new Bundle();
                                        bun1.putString("KEY", s);
                                        intent = new Intent(Dangnhap_Activity.this, detail_movie.class);
                                        intent.putExtra("DETAIL", bun1);
                                        finish();
                                        startActivity(intent);
                                    }
                                }
                            }
                        });
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
            intent = new Intent(Dangnhap_Activity.this, MainActivity.class);
            finish();
            startActivity(intent);
        }
        else if(key == 2)
        {
            String s = bun.getString("KEY");
            Bundle bun1 = new Bundle();
            bun1.putString("KEY", s);
            intent = new Intent(Dangnhap_Activity.this, detail_movie.class);
            intent.putExtra("DETAIL", bun1);
            finish();
            startActivity(intent);
        }
        super.onBackPressed();
    }
}
