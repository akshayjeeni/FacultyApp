package com.jeeni.facultyapp.questionlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jeeni.facultyapp.R;
import com.jeeni.facultyapp.helper.DatabaseHandler;
import com.jeeni.facultyapp.questiondetail.QuestionDetailActivity;
import com.jeeni.facultyapp.services.Api;
import com.jeeni.facultyapp.services.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionListActivity extends AppCompatActivity {
    String jAuth = "bJoOPqXauoFxvWdam/+6WGx2e5y7xrF/P0K2GRkUVGS7Z9BEUZu70A==";
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

        // db handler object
        DatabaseHandler db = new DatabaseHandler(this);

        questionListAdapter = new QuestionListAdapter(questionList,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewAssignedQuestion.setLayoutManager(mLayoutManager);
      //  recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerViewAssignedQuestion.setAdapter(questionListAdapter);

        // get the pending question list for faculty api call
        Api retrofitServices = RetrofitClient
                .getClient(30, true)
                .create(Api.class);

        Call<List<PendingQuestionsPojo>> call = retrofitServices.getPendingQuestionsPojo(jAuth);
        call.enqueue(new Callback<List<PendingQuestionsPojo>>() {
            @Override
            public void onResponse(Call<List<PendingQuestionsPojo>> call, Response<List<PendingQuestionsPojo>> response) {
                if(response.code() == 200){
                    List<PendingQuestionsPojo> pendingQuestionsPojoList = response.body();
                    Log.d("XXXX: ", ": "+pendingQuestionsPojoList.size());
                    db.addContact(pendingQuestionsPojoList.get(0));
                }
            }

            @Override
            public void onFailure(Call<List<PendingQuestionsPojo>> call, Throwable t) {

            }
        });
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