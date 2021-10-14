package com.jeeni.facultyapp.metadata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.jeeni.facultyapp.R;

public class MetaDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meta_data);

        getSupportActionBar().setTitle("Edit Metadata");


    }
}