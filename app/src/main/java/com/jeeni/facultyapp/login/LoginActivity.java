package com.jeeni.facultyapp.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.jeeni.facultyapp.DashboardActivity;
import com.jeeni.facultyapp.R;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout textInputLayoutPwd;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textInputLayoutPwd = findViewById(R.id.text_pwd);
        btnLogin = findViewById(R.id.button_login);
        //  textInputLayoutPwd.setError("Pwd required");
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });
    }
}