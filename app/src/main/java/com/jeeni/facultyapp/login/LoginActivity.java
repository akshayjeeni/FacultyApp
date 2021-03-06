package com.jeeni.facultyapp.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.jeeni.facultyapp.DashboardActivity;
import com.jeeni.facultyapp.R;
import com.jeeni.facultyapp.helper.FacultyPref;
import com.jeeni.facultyapp.questionlist.PendingQuestionsPojo;
import com.jeeni.facultyapp.services.Api;
import com.jeeni.facultyapp.services.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout textInputLayoutPwd, textInputLayoutUserName;
    Button btnLogin;
    FacultyPref facultyPref;
    TextInputEditText txtUserName, txtPassword;
    private TelephonyManager telephonyManager;
    String deviceIMEI;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        facultyPref = FacultyPref.getInstance(this);


        if (facultyPref.loggedIn("loggedInStatus")) {
            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_login);

        findAllWidget();
        txtUserName.addTextChangedListener(new ValidationTextWatcher(txtUserName));
        txtPassword.addTextChangedListener(new ValidationTextWatcher(txtPassword));

     //   getDeviceME();
        //  textInputLayoutPwd.setError("Pwd required");
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateEmail()) {
                    return;
                }
                if (!validatePassword()) {
                    return;
                }
                callLoginApi(txtUserName.getText().toString(), txtPassword.getText().toString());
            }
        });
    }

    /*private void getDeviceME() {
        telephonyManager = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
        } else {
            readWritePermissions();
        }

        if (deviceIMEI == null)
            deviceIMEI = android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
    }*/

    private void callLoginApi(String userName, String password) {
        progressBar.setVisibility(View.VISIBLE);
        Api retrofitServices = RetrofitClient
                .getClient(30, true)
                .create(Api.class);
        Call<UserPojo> call = retrofitServices.loginAuthentication(userName, password);
        call.enqueue(new Callback<UserPojo>() {
            @Override
            public void onResponse(Call<UserPojo> call, Response<UserPojo> response) {
                progressBar.setVisibility(View.GONE);

                if (response.code() == 401) {
                    Toast.makeText(LoginActivity.this, "Unauthorized credentails!", Toast.LENGTH_LONG).show();
                    return;
                }

                if (response.code() == 200 && response.isSuccessful()) {
                    UserPojo userPojo = response.body();

                    facultyPref.loggedIn("loggedInStatus", true);
                    facultyPref.saveData("jauth", userPojo.getJauth());
                    facultyPref.saveData("facultyId",userPojo.getId().toString());
                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<UserPojo> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, "Something went wrong, please try again!", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void findAllWidget() {

        textInputLayoutUserName = findViewById(R.id.text_lay_username);
        textInputLayoutPwd = findViewById(R.id.text_lay_pwd);
        btnLogin = findViewById(R.id.button_login);
        txtUserName = findViewById(R.id.txt_username);
        txtPassword = findViewById(R.id.txt_password);
        progressBar = findViewById(R.id.progress_login);
        // textInputLayoutUserName.
    }

    private void readWritePermissions() {
        String[] PERMISSIONS = {
                Manifest.permission.READ_PHONE_STATE};
        String android_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, 0);
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private class ValidationTextWatcher implements TextWatcher {
        private View view;

        private ValidationTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (view.getId()) {
                case R.id.txt_password:
                    validatePassword();
                    break;
                case R.id.txt_username:
                    validateEmail();
                    break;
            }
        }

    }

    private boolean validateEmail() {
        if (txtUserName.getText().toString().trim().isEmpty()) {
            textInputLayoutUserName.setError("username is required");
            requestFocus(txtUserName);
            return false;
        } else {
            textInputLayoutUserName.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validatePassword() {
        if (txtPassword.getText().toString().trim().isEmpty()) {
            textInputLayoutPwd.setError("Password is required");
            requestFocus(txtPassword);
            return false;
        } else {
            textInputLayoutPwd.setErrorEnabled(false);
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}

