package com.jeeni.facultyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.card.MaterialCardView;
import com.jeeni.facultyapp.questionlist.QuestionListActivity;

public class DashboardActivity extends AppCompatActivity {

    MaterialCardView cardViewAssigendQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        getSupportActionBar().setTitle("Dashboard");

        // find by id's of all widgets
        cardViewAssigendQuestion = findViewById(R.id.cardView_assigend_question);

        cardViewAssigendQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, QuestionListActivity.class);
                startActivity(intent);
            }
        });
    }
}