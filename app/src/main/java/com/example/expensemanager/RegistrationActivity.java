package com.example.expensemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegistrationActivity extends AppCompatActivity {
    private EditText mEmail;
    private  EditText mPassword;
    private Button btnReg;
    private TextView mSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        registration();
    }
    public void registration(){
        mEmail.findViewById(R.id.email_reg);
        mPassword.findViewById(R.id.password_reg);
        btnReg.findViewById(R.id.btn_reg);
        mSignIn.findViewById(R.id.signup_reg);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString().trim();
                String pass = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email bắt buộc...");
                    return;
                }
                if (TextUtils.isEmpty(pass)){
                    mPassword.setError("Email bắt buộc...");
                }
            }
        });
        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }
}