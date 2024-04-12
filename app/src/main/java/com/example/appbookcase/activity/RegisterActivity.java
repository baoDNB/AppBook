package com.example.appbookcase.activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

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

import com.example.appbookcase.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText email, name, password, confirmPass;
    boolean passwordVisible, confirmPassVisible;
    private FirebaseAuth mAuth;
    Button RegisterButton;
    TextView LoginButton;



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dangky);
        mAuth= FirebaseAuth.getInstance();
        name=findViewById(R.id.dk_name);
        email=findViewById(R.id.dk_email);
        password=findViewById(R.id.dk_password);
        confirmPass=findViewById(R.id.dk_comfirmpass);
        RegisterButton=findViewById(R.id.regisbtn);
        LoginButton=findViewById(R.id.btn_button);


        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_email, txt_password,txt_confirmPass;
                txt_email = String.valueOf(email.getText());
                txt_password = String.valueOf(password.getText());
                txt_confirmPass = String.valueOf(confirmPass.getText());
                if(TextUtils.isEmpty(txt_email)){
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(txt_password)){
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!txt_password.equals(txt_confirmPass)){
                    Toast.makeText(RegisterActivity.this, "Mật khẩu mới không khớp", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(txt_email, txt_password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Đăng kí thành công",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(intent);

                                } else {
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(RegisterActivity.this, "Đăng kí thất bại",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int right = 2;
                if (event.getAction() == MotionEvent.ACTION_UP){
                    if (event.getRawX() >= password.getRight()-password.getCompoundDrawables()[right].getBounds().width()){
                        int selection = password.getSelectionEnd();
                        if (passwordVisible){
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.hide_password,0);
                            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible = false;
                        }
                        else{
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.show_password,0);
                            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible = true;
                        }
                        password.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        confirmPass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int right = 2;
                if (event.getAction() == MotionEvent.ACTION_UP){
                    if (event.getRawX() >= confirmPass.getRight()-confirmPass.getCompoundDrawables()[right].getBounds().width()){
                        int selection = confirmPass.getSelectionEnd();
                        if (confirmPassVisible){
                            confirmPass.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.hide_password,0);
                            confirmPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            confirmPassVisible = false;
                        }
                        else{
                            confirmPass.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.show_password,0);
                            confirmPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            confirmPassVisible = true;
                        }
                        confirmPass.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
    }
}
