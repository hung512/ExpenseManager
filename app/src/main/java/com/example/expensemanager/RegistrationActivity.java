package com.example.expensemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.logging.Logger;

public class RegistrationActivity extends AppCompatActivity {
    private EditText mEmail;
    private  EditText mPassword;
    private Button btnReg;
    private TextView mSignIn;
    private ProgressDialog mDialog;

    //Firebase....
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mDialog= new ProgressDialog(this);

        mAuth= FirebaseAuth.getInstance();

        registration();
    }
    public void registration(){
        mEmail=findViewById(R.id.email_reg);
        mPassword=findViewById(R.id.password_reg);
        btnReg=findViewById(R.id.btn_reg);
        mSignIn=findViewById(R.id.sign_in);

        btnReg.setOnClickListener(nv -> {
            String email = mEmail.getText().toString().trim();
            String pass = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                mEmail.setError("Email bắt buộc...");
                return;
            }
                if (TextUtils.isEmpty(pass)){
                mPassword.setError("Password bắt buộc...");
            }

                mDialog.setMessage("Đang kiểm tra...");
                mDialog.show();
                mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(task -> {
                if(task.isSuccessful()){

                    mDialog.dismiss();
                    ToastHelper toastHelper = new ToastHelper(this);
                    toastHelper.showMessage("Đăng kí thành công", 10);
                    startActivity(new Intent(this, MainActivity.class));
                }else {

                    mDialog.dismiss();

                    Exception e = task.getException();
                    // Kết quả được đưa ra là gì?
                    Logger.getLogger("DEBUG").warning(e.getMessage());
                    if (e.getMessage().equalsIgnoreCase("the email address is badly formatted.")) {
                        ToastHelper toastHelper = new ToastHelper(this);
                        toastHelper.showMessage("Email không đúng định dạng", 10);
                        return;
                    }
                }
            });
        });
    }
}