package com.jeeni.facultyapp.questiondetail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.card.MaterialCardView;
import com.jeeni.facultyapp.metadata.MetaDataActivity;
import com.jeeni.facultyapp.R;

public class QuestionDetailActivity extends AppCompatActivity {

    MaterialCardView cardViewMetaData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);

        this.getSupportActionBar().hide();


        // get the id's of all widgets
        cardViewMetaData = findViewById(R.id.cardView_metadata);

        cardViewMetaData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuestionDetailActivity.this, MetaDataActivity.class);
                startActivity(intent);
            }
        });
    }
}