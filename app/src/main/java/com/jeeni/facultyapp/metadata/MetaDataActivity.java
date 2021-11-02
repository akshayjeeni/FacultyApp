package com.jeeni.facultyapp.metadata;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.jeeni.facultyapp.R;
import com.jeeni.facultyapp.helper.DatabaseHandler;
import com.jeeni.facultyapp.services.Api;
import com.jeeni.facultyapp.services.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MetaDataActivity extends AppCompatActivity {
    String jAuth = "MBuvl1tXZUMlbjNQS/6CVvQscwTmrZvpY6IsXG0EyYOccey+zqPDLQ==";

    DatabaseHandler db;
    int courseId, subjId;
    AutoCompleteTextView chapterAutoCompleteTextView,topicAutoCompleteTextView;
    List<ChapterTopicListPojo> chapterPojoList;
    List<ChapterTopicListPojo> topicPojoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meta_data);

        getSupportActionBar().setTitle("Edit Metadata");
        //find by id's for all widgets
        findById();
        // db handler object
        db = new DatabaseHandler(this);
        Log.d("XXX: ", "qid: " + getIntent().getIntExtra("questionId", 0));
        Cursor cursor = db.getCurrentQuestion(getIntent().getIntExtra("questionId", 0));
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            courseId = cursor.getInt(5);
            subjId = cursor.getInt(7);
        }

        // check chapter list and topic list is in local storage
        chapterPojoList = db.readChapterList(courseId, subjId);
        if (chapterPojoList.size() > 0) {
            Log.d("XXX:", ": local call");
            fetchChapterDropDown(chapterPojoList);
        } else { // local call
            cursor.moveToFirst();
            Log.d("XXX", "server: " + cursor.getCount());
            fetchChapterTopicListFromServer(courseId, subjId);
        }

    }

    private void findById() {
        chapterAutoCompleteTextView = findViewById(R.id.auto_complete_chapter);
        topicAutoCompleteTextView = findViewById(R.id.auto_complete_topic);

        topicAutoCompleteTextView.setOnItemClickListener(onItemClickListenerForTopic);
        chapterAutoCompleteTextView.setOnItemClickListener(onItemClickListener);
    }

    private AdapterView.OnItemClickListener onItemClickListener =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(MetaDataActivity.this, ""+chapterPojoList.get(i).getId(), Toast.LENGTH_SHORT).show();
                    topicPojoList = db.readTopicList(chapterPojoList.get(i).getId());
                    Log.d("XXX: ", "onItemClick: "+topicPojoList.size());
                    fetchTopicDropDown(topicPojoList);
                }
            };
    private AdapterView.OnItemClickListener onItemClickListenerForTopic =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(MetaDataActivity.this, ""+topicPojoList.get(i).getName(), Toast.LENGTH_SHORT).show();
                }
            };
    private void fetchChapterTopicListFromServer(int courseId, int subjId) {
        Log.d("XXX:", "onCreate: call server");
        Api retrofitServices = RetrofitClient
                .getClient(30, true)
                .create(Api.class);
        Call<List<ChapterTopicListDto>> call = retrofitServices.getChapterTopicList(jAuth, courseId, subjId);
        call.enqueue(new Callback<List<ChapterTopicListDto>>() {
            @Override
            public void onResponse(Call<List<ChapterTopicListDto>> call, Response<List<ChapterTopicListDto>> response) {
                // progressBar.setVisibility(View.GONE);
                if (response.code() == 200) {
                    List<ChapterTopicListDto> chapterTopicList = response.body();
                    // db.clearQuesForReview();
                    db.addChapterTopic(chapterTopicList, courseId, subjId);
                    // fetchChapterDropDown(cursor);
                }
            }

            @Override
            public void onFailure(Call<List<ChapterTopicListDto>> call, Throwable t) {
                //  progressBar.setVisibility(View.GONE);
                // getQuestionsDataFromLocalStorage();
                Log.d("failure: ", "fecth chapter topic api error " + t.getLocalizedMessage());
            }
        });
    }

    private void fetchChapterDropDown(List<ChapterTopicListPojo> chapterTopicListPojos) {
        AutoCompleteChapterAdapter chapterAdapter = new AutoCompleteChapterAdapter(this, chapterTopicListPojos);
        chapterAutoCompleteTextView.setAdapter(chapterAdapter);

    }

    private void fetchTopicDropDown(List<ChapterTopicListPojo> chapterTopicListPojos){
        AutoCompleteChapterAdapter chapterAdapter = new AutoCompleteChapterAdapter(this, chapterTopicListPojos);
        topicAutoCompleteTextView.setAdapter(chapterAdapter);
    }
}