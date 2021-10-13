package com.jeeni.facultyapp.questionlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.jeeni.facultyapp.R;

import java.util.ArrayList;
import java.util.List;

public class QuestionListActivity extends AppCompatActivity {

    RecyclerView recyclerViewAssignedQuestion;
    private List<QuestionListPojo> questionList = new ArrayList<>();
    QuestionListAdapter questionListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);
        getSupportActionBar().setTitle("Questions Assigned");
        // find id's of all widgets
        recyclerViewAssignedQuestion = findViewById(R.id.recycler_view_question_list);

        questionListAdapter = new QuestionListAdapter(questionList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewAssignedQuestion.setLayoutManager(mLayoutManager);
      //  recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerViewAssignedQuestion.setAdapter(questionListAdapter);

        prepareQuestionsData();
    }

    private void prepareQuestionsData() {
        QuestionListPojo questionListPojo = new QuestionListPojo("iimg");
        questionList.add(questionListPojo);

        questionListPojo = new QuestionListPojo("ff");
        questionList.add(questionListPojo);

        questionListPojo = new QuestionListPojo("ff");
        questionList.add(questionListPojo);

        questionListPojo = new QuestionListPojo("ff");
        questionList.add(questionListPojo);

        questionListPojo = new QuestionListPojo("ff");
        questionList.add(questionListPojo);

        questionListPojo = new QuestionListPojo("ff");
        questionList.add(questionListPojo);

        questionListPojo = new QuestionListPojo("ff");
        questionList.add(questionListPojo);

        questionListAdapter.notifyDataSetChanged();
    }
}