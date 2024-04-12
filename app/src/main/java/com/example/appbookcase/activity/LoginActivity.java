package com.example.appbookcase.activity;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appbookcase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText email,pass;
    private Button LoginButton;
    private TextView register,forgotpass;
    boolean passwordVisible;
    private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
    }



    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dangnhap);
        mAuth= FirebaseAuth.getInstance();
        email=findViewById(R.id.dn_email);
        pass=findViewById(R.id.dn_password);
        LoginButton=findViewById(R.id.loginbutton);
        register=findViewById(R.id.register);
        forgotpass=findViewById(R.id.forgotpass);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailInput, passwordInput;
                emailInput = String.valueOf(email.getText());
                passwordInput = String.valueOf(pass.getText());
                if(TextUtils.isEmpty(emailInput)){
                    Toast.makeText(LoginActivity.this,"Email không được để trống", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(passwordInput)){
                    Toast.makeText(LoginActivity.this,"Mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(emailInput, passwordInput)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "Đăng nhập thành công");
                                    Toast.makeText(LoginActivity .this, "Đăng nhập thành công",
                                            Toast.LENGTH_SHORT).show();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Log.d(TAG, "Đã chuyển sang màn hình chính");
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Log.w(TAG, "Đăng nhập thất bại", task.getException());
                                    Toast.makeText(LoginActivity .this, "Đăng nhập thất bại",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        pass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int right = 2;
                if (event.getAction() == MotionEvent.ACTION_UP){
                    if (event.getRawX() >= pass.getRight()-pass.getCompoundDrawables()[right].getBounds().width()){
                        int selection = pass.getSelectionEnd();
                        if (passwordVisible){
                            pass.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.hide_password,0);
                            pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible= false;
                        }
                        else{
                            pass.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.show_password,0);
                            pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible = true;
                        }
                        pass.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
    }
}
