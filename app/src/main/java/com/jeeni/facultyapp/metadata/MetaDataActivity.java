package com.jeeni.facultyapp.metadata;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.slider.Slider;
import com.jeeni.facultyapp.R;
import com.jeeni.facultyapp.helper.DatabaseHandler;
import com.jeeni.facultyapp.helper.FacultyPref;
import com.jeeni.facultyapp.services.Api;
import com.jeeni.facultyapp.services.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MetaDataActivity extends AppCompatActivity {

    DatabaseHandler db;
    int courseId, subjId;
    AutoCompleteTextView chapterAutoCompleteTextView, topicAutoCompleteTextView;
    List<ChapterTopicListPojo> chapterPojoList;
    List<ChapterTopicListPojo> topicPojoList;
    TextView txtUnattempted, txtTotalNoOfAttempts, txtCorrectAns, txtInCorrectAns;
    FacultyPref facultyPref;
    Slider sliderComplexity;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meta_data);

        getSupportActionBar().setTitle("Edit Metadata");
        //find by id's for all widgets
        findById();
        // db handler object
        db = new DatabaseHandler(this);
        facultyPref = FacultyPref.getInstance(this);
        Cursor cursor = db.getCurrentQuestion(getIntent().getIntExtra("questionId", 0));
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            courseId = cursor.getInt(5);
            subjId = cursor.getInt(7);
        }

        // check chapter list and topic list is in local storage
        chapterPojoList = db.readChapterList(courseId, subjId);
        if (chapterPojoList.size() > 0) {
            fetchChapterDropDown(chapterPojoList);
        } else { // local call
            cursor.moveToFirst();
            fetchChapterTopicListFromServer(courseId, subjId);
        }

        // set data
        setCommanData(cursor);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setCommanData(Cursor cursor) {
        txtTotalNoOfAttempts.setText("" + cursor.getInt(19));
        txtCorrectAns.setText("" + cursor.getInt(21));
        txtInCorrectAns.setText("" + cursor.getInt(20));
        txtUnattempted.setText("");
        sliderComplexity.setValue(cursor.getInt(13));
       // set chapter dropdown
        int index = 0;
        for (int i =0; i<chapterPojoList.size();i++) {
            if (chapterPojoList.get(i).getId() == cursor.getInt(9)) {
              index = i;
            }
        }
        chapterAutoCompleteTextView.setText(chapterPojoList.get(index).getName(),false);
        // set topic dropdown
        topicPojoList = db.readTopicList(chapterPojoList.get(index).getId());
        fetchTopicDropDown(topicPojoList);
        int topicIndex = 0;
        int topicId = cursor.getInt(11);
        for (int i =0; i<topicPojoList.size();i++) {
            if (topicPojoList.get(i).getId() == topicId ) {
                topicIndex = i;
            }
        }
        topicAutoCompleteTextView.setText(topicPojoList.get(topicIndex).getName(),false);

    }

    private void findById() {
        chapterAutoCompleteTextView = findViewById(R.id.auto_complete_chapter);
        topicAutoCompleteTextView = findViewById(R.id.auto_complete_topic);
        txtCorrectAns = findViewById(R.id.txt_md_correct_ans);
        txtInCorrectAns = findViewById(R.id.txt_md_incorrect);
        txtTotalNoOfAttempts = findViewById(R.id.txt_md_no_of_attempts);
        txtUnattempted = findViewById(R.id.txt_md_unattempted);
        sliderComplexity = findViewById(R.id.seekBar);
        topicAutoCompleteTextView.setOnItemClickListener(onItemClickListenerForTopic);
        chapterAutoCompleteTextView.setOnItemClickListener(onItemClickListener);

    }

    private AdapterView.OnItemClickListener onItemClickListener =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(MetaDataActivity.this, "" + chapterPojoList.get(i).getId(), Toast.LENGTH_SHORT).show();
                    topicPojoList = db.readTopicList(chapterPojoList.get(i).getId());
                    fetchTopicDropDown(topicPojoList);
                }
            };
    private AdapterView.OnItemClickListener onItemClickListenerForTopic =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(MetaDataActivity.this, "" + topicPojoList.get(i).getName(), Toast.LENGTH_SHORT).show();
                }
            };

    private void fetchChapterTopicListFromServer(int courseId, int subjId) {
        Api retrofitServices = RetrofitClient
                .getClient(30, true)
                .create(Api.class);
        Call<List<ChapterTopicListDto>> call = retrofitServices.getChapterTopicList(facultyPref.getData("jauth"), courseId, subjId);
        call.enqueue(new Callback<List<ChapterTopicListDto>>() {
            @Override
            public void onResponse(Call<List<ChapterTopicListDto>> call, Response<List<ChapterTopicListDto>> response) {
                // progressBar.setVisibility(View.GONE);
                if (response.code() == 200) {
                    List<ChapterTopicListDto> chapterTopicList = response.body();
                    // db.clearQuesForReview();
                    db.addChapterTopic(chapterTopicList, courseId, subjId);
                    chapterPojoList = db.readChapterList(courseId, subjId);
                     fetchChapterDropDown(chapterPojoList);
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

    private void fetchTopicDropDown(List<ChapterTopicListPojo> chapterTopicListPojos) {

        AutoCompleteChapterAdapter chapterAdapter = new AutoCompleteChapterAdapter(this, chapterTopicListPojos);
        topicAutoCompleteTextView.setAdapter(chapterAdapter);
    }
}