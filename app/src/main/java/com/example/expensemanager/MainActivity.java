package com.example.expensemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText mEmail;
    private  EditText mPass;
    private Button btnLogIn;
    private TextView mForgetPassword;
    private TextView mSignUpHere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginDetail();
    }
    public void loginDetail(){
        mEmail.findViewById(R.id.email_login);
        mPass.findViewById(R.id.password_login);
        btnLogIn.findViewById(R.id.btn_login);
        mForgetPassword.findViewById(R.id.fogret_password);
        mSignUpHere.findViewById(R.id.signup_reg);

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString().trim();
                String pass = mPass.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email bắt buộc...");
                    return;
                }
                if (TextUtils.isEmpty(pass)){
                    mPass.setError("Email bắt buộc...");
                    return;
                }
            }
        });

        //Registration Activity

        mSignUpHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
            }
        });

        //Reset Password Activity

        mForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ReseatActivity.class));
            }
        });

    }
}