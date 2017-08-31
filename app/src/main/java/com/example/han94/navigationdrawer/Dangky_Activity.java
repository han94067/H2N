package com.example.han94.navigationdrawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

public class Dangky_Activity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.signup_layout);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

//        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        ImageButton btnBack = (ImageButton)findViewById(R.id.btnBack5);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
//        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);

//        btnResetPassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(SignupActivity.this, ResetPasswordActivity.class));
//            }
//        });

//        btnSignIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
//                startActivity(intent);
//            }
//        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    //Toast.makeText(getApplicationContext(), "Xin mời nhập Email !", Toast.LENGTH_SHORT).show();
                    inputEmail.setError("Xin mời nhập Email !");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    //Toast.makeText(getApplicationContext(), "Xin mời nhập Mật khẩu !", Toast.LENGTH_SHORT).show();
                    inputPassword.setError("Xin mời nhập Mật khẩu !");
                    return;
                }

                if (password.length() < 6) {
                    inputPassword.setError("Mật khẩu quá ngắn, phải nhiều hơn 6 kí tự !");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Dangky_Activity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(Dangky_Activity.this, "Đăng ký thất bại !", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Dangky_Activity.this, "Đăng ký thành công !", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Dangky_Activity.this, Dangnhap_Activity.class));
                                    finish();
                                }
                            }
                        });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        Intent intent = getIntent();
        Bundle bun = intent.getBundleExtra("DETAILSUAT");
        int key = bun.getInt("VITRI");
        if(key == 1)
        {
            intent = new Intent(Dangky_Activity.this, MainActivity.class);
            finish();
            startActivity(intent);
        }
        else if(key == 2)
        {
            String s = bun.getString("KEY");
            Bundle bun1 = new Bundle();
            bun1.putString("KEY", s);
            intent = new Intent(Dangky_Activity.this, detail_movie.class);
            intent.putExtra("DETAIL", bun1);
            finish();
            startActivity(intent);
        }
        super.onBackPressed();
    }

}
