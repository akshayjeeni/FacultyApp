package com.jeeni.facultyapp.questionlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jeeni.facultyapp.R;
import com.jeeni.facultyapp.helper.DatabaseHandler;
import com.jeeni.facultyapp.helper.FacultyPref;
import com.jeeni.facultyapp.questiondetail.QuestionDetailActivity;
import com.jeeni.facultyapp.services.Api;
import com.jeeni.facultyapp.services.RetrofitClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionListActivity extends AppCompatActivity {
    String jAuth = "MBuvl1tXZUMlbjNQS/6CVvQscwTmrZvpY6IsXG0EyYOccey+zqPDLQ==";
    RecyclerView recyclerViewAssignedQuestion;
    private List<QuestionListPojo> questionList = new ArrayList<>();
    QuestionListAdapter questionListAdapter;
    DatabaseHandler db;
    FacultyPref facultyPref;
    ProgressBar progressBar;
    long day, hours;

    TextView quesListEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);
        getSupportActionBar().setTitle("Questions Assigned");
        // find id's of all widgets
        recyclerViewAssignedQuestion = findViewById(R.id.recycler_view_question_list);
        progressBar = findViewById(R.id.progress_pending_question);
        quesListEmpty = findViewById(R.id.txt_ques_empty);

        // db handler object
        db = new DatabaseHandler(this);
        facultyPref = FacultyPref.getInstance(this);


        questionListAdapter = new QuestionListAdapter(new ArrayList<QuestionListPojo>(), this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewAssignedQuestion.setLayoutManager(mLayoutManager);
        //  recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerViewAssignedQuestion.setAdapter(questionListAdapter);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        // DateTimeUtils obj = new DateTimeUtils();
        try {
            Date date1 = sdf.parse(facultyPref.getData("question_for_review_last_sync_datetime"));
            Date date2 = sdf.parse(currentDateandTime);

            printDifference(date1, date2);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        // get the pending question's list from server
        if (facultyPref.getData("question_for_review_last_sync_datetime").isEmpty()) {
           // Log.d("XXX: ", "first time call: " + currentDateandTime);
             serverCallFetchThePendingQuestionList();
        } else {
            if (day > 0) {
              //   Log.d("XXX: ", "1 day over: " + currentDateandTime);
                 serverCallFetchThePendingQuestionList();
            } else if (day <= 0) {
                if (hours >= 1) {
                //    Log.d("XXX: ", "1 hour over: " + currentDateandTime);
                     serverCallFetchThePendingQuestionList();
                }else{
                  //  Log.d("XXX: ", "local call: " + currentDateandTime);
                    getQuestionsDataFromLocalStorage();
                }
            }
        }
    }

    private void serverCallFetchThePendingQuestionList() {
        // get the pending question list for faculty api call
        progressBar.setVisibility(View.VISIBLE);
        Api retrofitServices = RetrofitClient
                .getClient(30, true)
                .create(Api.class);
        Call<List<PendingQuestionsPojo>> call = retrofitServices.getPendingQuestionsPojo(jAuth);
        call.enqueue(new Callback<List<PendingQuestionsPojo>>() {
            @Override
            public void onResponse(Call<List<PendingQuestionsPojo>> call, Response<List<PendingQuestionsPojo>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.code() == 200) {
                    List<PendingQuestionsPojo> pendingQuestionsPojoList = response.body();
                    db.clearQuesForReview();
                    db.addQuestionsForReview(pendingQuestionsPojoList);
                    getQuestionsDataFromLocalStorage();
                }
            }

            @Override
            public void onFailure(Call<List<PendingQuestionsPojo>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                getQuestionsDataFromLocalStorage();
                Log.d("failure: ", "fecth pending question api error " + t.getLocalizedMessage());
            }
        });
    }

    private void getQuestionsDataFromLocalStorage() {
        List<QuestionListPojo> questionArrayList = new ArrayList<>();
        db = new DatabaseHandler(this);

        progressBar.setVisibility(View.VISIBLE);
        // list from db .
        questionArrayList = db.readPendingQuestions();
        // Log.d("XXX: ", "prepareQuestionsData: "+questionArrayList.size());
        if (questionArrayList.size() <= 0) {
            progressBar.setVisibility(View.GONE);
            quesListEmpty.setVisibility(View.VISIBLE);
        } else {
            quesListEmpty.setVisibility(View.GONE);
            Log.d("XXX: ", "prepareQuestionsData: " + questionArrayList.size());
            progressBar.setVisibility(View.GONE);
            //  questionListAdapter = new QuestionListAdapter(questionArrayList, this);
            questionListAdapter.addAllData(questionArrayList);
        }

    }

    public void printDifference(Date startDate, Date endDate) {
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : " + endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;

        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        day = elapsedDays;
        hours = elapsedHours;
        System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);
    }
}