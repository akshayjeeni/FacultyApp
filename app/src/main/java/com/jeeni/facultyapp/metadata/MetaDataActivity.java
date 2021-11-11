package com.jeeni.facultyapp.metadata;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.slider.Slider;
import com.google.gson.Gson;
import com.jeeni.facultyapp.R;
import com.jeeni.facultyapp.helper.DatabaseHandler;
import com.jeeni.facultyapp.helper.FacultyPref;
import com.jeeni.facultyapp.login.LoginActivity;
import com.jeeni.facultyapp.questiondetail.QuestionDetailActivity;
import com.jeeni.facultyapp.questiondetail.approveQuestionVo;
import com.jeeni.facultyapp.questionlist.QuestionListActivity;
import com.jeeni.facultyapp.services.Api;
import com.jeeni.facultyapp.services.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
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
    ProgressBar progressBar;
    MaterialButton btnApprove, btnCancel;
    EditText input;
    int qesId;
    int selectedChapterId, selectedTopicId;

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
        qesId = getIntent().getIntExtra("questionId", 0);
        Log.d("XXXX: ", "ques id: " + qesId);
        Cursor cursor = db.getCurrentQuestion(qesId);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            courseId = cursor.getInt(5);
            subjId = cursor.getInt(7);
        }

        // check chapter list and topic list is in local storage
        chapterPojoList = db.readChapterList(courseId, subjId);
        if (chapterPojoList.size() > 0) {
            fetchChapterDropDown(chapterPojoList);
            setCommanData(cursor);
        } else { // local call
            cursor.moveToFirst();
            fetchChapterTopicListFromServer(courseId, subjId);
        }


        btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // approveApiCall();
                openDialog();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setCommanData(Cursor cursor) {
        cursor.moveToFirst();
        txtTotalNoOfAttempts.setText("" + cursor.getInt(19));
        txtCorrectAns.setText("" + cursor.getInt(21));
        txtInCorrectAns.setText("" + cursor.getInt(20));
        txtUnattempted.setText("");
        sliderComplexity.setValue(cursor.getInt(13));
        // set chapter dropdown
        int index = 0;
        for (int i = 0; i < chapterPojoList.size(); i++) {
            if (chapterPojoList.get(i).getId() == cursor.getInt(9)) {
                index = i;
            }
        }
        chapterAutoCompleteTextView.setText(chapterPojoList.get(index).getName(), false);
        selectedChapterId = chapterPojoList.get(index).getId();
        // set topic dropdown
        topicPojoList = db.readTopicList(chapterPojoList.get(index).getId());
        fetchTopicDropDown(topicPojoList);
        int topicIndex = 0;
        int topicId = cursor.getInt(11);
        Log.d("XXXX: ", "topicId: " + cursor.getInt(11));
        for (int i = 0; i < topicPojoList.size(); i++) {
            if (topicPojoList.get(i).getId() == topicId) {
                topicIndex = i;
            }
        }
        Log.d("XXXX: ", "topic name: " + topicPojoList.get(topicIndex).getName());

        topicAutoCompleteTextView.setText(topicPojoList.get(topicIndex).getName(), false);
        selectedTopicId = topicPojoList.get(topicIndex).getId();
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
        progressBar = findViewById(R.id.progress_metat);
        btnApprove = findViewById(R.id.btn_meta_approve);
        btnCancel = findViewById(R.id.btn_meta_cancel);
    }

    private AdapterView.OnItemClickListener onItemClickListener =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                 //   Toast.makeText(MetaDataActivity.this, "" + chapterPojoList.get(i).getId(), Toast.LENGTH_SHORT).show();
                    selectedChapterId = chapterPojoList.get(i).getId();
                    Log.d("XXXX", "selected chapter id: " + selectedChapterId);
                    topicPojoList = db.readTopicList(chapterPojoList.get(i).getId());
                    fetchTopicDropDown(topicPojoList);
                }
            };
    private AdapterView.OnItemClickListener onItemClickListenerForTopic =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //    Toast.makeText(MetaDataActivity.this, "" + topicPojoList.get(i).getName(), Toast.LENGTH_SHORT).show();
                    selectedTopicId = topicPojoList.get(i).getId();
                    Log.d("XXXX", "selected topic id: " + selectedTopicId+"topic name: "+topicPojoList.get(i).getName());
                }
            };

    private void fetchChapterTopicListFromServer(int courseId, int subjId) {
        progressBar.setVisibility(View.VISIBLE);
        Api retrofitServices = RetrofitClient
                .getClient(30, true)
                .create(Api.class);
        Call<List<ChapterTopicListDto>> call = retrofitServices.getChapterTopicList(facultyPref.getData("jauth"), courseId, subjId);
        call.enqueue(new Callback<List<ChapterTopicListDto>>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onResponse(Call<List<ChapterTopicListDto>> call, Response<List<ChapterTopicListDto>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.code() == 200) {
                    List<ChapterTopicListDto> chapterTopicList = response.body();
                    // db.clearQuesForReview();
                    db.addChapterTopic(chapterTopicList, courseId, subjId);
                    chapterPojoList = db.readChapterList(courseId, subjId);
                    fetchChapterDropDown(chapterPojoList);
                }
                Cursor cursor = db.getCurrentQuestion(qesId);

                setCommanData(cursor);
            }

            @Override
            public void onFailure(Call<List<ChapterTopicListDto>> call, Throwable t) {
                //  progressBar.setVisibility(View.GONE);
                // getQuestionsDataFromLocalStorage();
                progressBar.setVisibility(View.GONE);
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

    private void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MetaDataActivity.this);
        input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHint("Please enter comment here");


        builder.setTitle("Approve");
        builder.setMessage("Are you sure you want to approve this question?");

        builder.setCancelable(false)
                .setView(input) //<-- layout containing EditText
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //All of the fun happens inside the CustomListener now.
                        //I had to move it to enable data validation.
                    }
                });
        builder.setNegativeButton("No ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Action for "Cancel".
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Button theButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        theButton.setOnClickListener(new CustomListener(alertDialog));

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    input.setError(null);
                } else {
                    input.setError("Comment can not be blank");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    class CustomListener implements View.OnClickListener {
        private final Dialog dialog;


        public CustomListener(Dialog dialog) {
            this.dialog = dialog;

        }

        @Override
        public void onClick(View v) {
            // put your code here
            String mValue = input.getText().toString();
            if (mValue.isEmpty() || mValue.equals("")) {
                // input.requestFocus();
                input.setError("Comment can not be blank");
                //  Toast.makeText(QuestionDetailActivity.this, "Invalid data", Toast.LENGTH_SHORT).show();
            } else {
                dialog.dismiss();

                approveApiCall();

            }
        }
    }

    private void approveApiCall() {
        Cursor cursor = db.getCurrentQuestion(qesId);
        cursor.moveToFirst();

        progressBar.setVisibility(View.VISIBLE);
        Api retrofitServices = RetrofitClient
                .getClient(30, true)
                .create(Api.class);

        approveQuestionVo approveQuestionVo = new approveQuestionVo();
        approveQuestionVo.setId(cursor.getInt(1));
        approveQuestionVo.setCourseId(cursor.getInt(5));
        approveQuestionVo.setSubjectId(cursor.getInt(7));
        approveQuestionVo.setChapterId(selectedChapterId); // updated chapter id
        approveQuestionVo.setTopicId(selectedTopicId); // updated topic id
        approveQuestionVo.setComplexity((int)sliderComplexity.getValue());
        approveQuestionVo.setType(cursor.getInt(14));
        approveQuestionVo.setComment(input.getText().toString());
        List<Integer> courseIdList = new ArrayList<>();
        List<Integer> subjectIdList = new ArrayList<>();
        List<Integer> chapterIdList = new ArrayList<>();
        List<Integer> topicIdList = new ArrayList<>();
        courseIdList.add(cursor.getInt(5));
        subjectIdList.add(cursor.getInt(7));
        chapterIdList.add(cursor.getInt(9));
        topicIdList.add(cursor.getInt(11));

        approveQuestionVo.setCourseIds(courseIdList);
        approveQuestionVo.setSubjectIds(subjectIdList);
        approveQuestionVo.setChapterIds(chapterIdList);
        approveQuestionVo.setTopicIds(topicIdList);

        String jsonString = new Gson().toJson(approveQuestionVo);
        String jsonType = "application/json";

        Log.d("xxxxx", "json: " + jsonString);
        Log.d("xxxxx", "facultyid: " + facultyPref.getData("facultyId"));
        Log.d("xxxxx", "jauth: " + facultyPref.getData("jauth"));
        Log.d("XXXX", "final chapter id: " + selectedChapterId);
        Log.d("XXXX", "final topic id: " + selectedTopicId);
        Log.d("XXXX", "final complexity: " + (int)sliderComplexity.getValue());


       Call<ResponseBody> call = retrofitServices.approveQuestion(facultyPref.getData("jauth"), jsonString, facultyPref.getData("facultyId"));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressBar.setVisibility(View.GONE);
                Log.d("xxxxx", "response: " + response.code());

                if (response.code() == 200) {
                    boolean result = db.deleteQuestion(qesId);
                    Log.d("XXXX", "delete ques : "+result);
                    Toast.makeText(MetaDataActivity.this, "Question approved successfully", Toast.LENGTH_SHORT).show();
                    facultyPref.saveBooleanData("questionListReload",true);
                    Intent intent = new Intent(MetaDataActivity.this, QuestionListActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                }
                if (response.code() == 401) {
                    facultyPref.loggedIn("loggedInStatus", false);
                    Toast.makeText(MetaDataActivity.this, "Session expired,please login again!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MetaDataActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
                if (response.code() == 500) {
                    Toast.makeText(MetaDataActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.d("XXXX", "onFailure: "+t.getMessage());
                Toast.makeText(MetaDataActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}