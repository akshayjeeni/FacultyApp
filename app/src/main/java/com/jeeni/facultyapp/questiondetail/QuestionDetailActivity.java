package com.jeeni.facultyapp.questiondetail;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.jeeni.facultyapp.helper.DatabaseHandler;
import com.jeeni.facultyapp.helper.FacultyPref;
import com.jeeni.facultyapp.login.LoginActivity;
import com.jeeni.facultyapp.login.UserPojo;
import com.jeeni.facultyapp.metadata.MetaDataActivity;
import com.jeeni.facultyapp.R;
import com.jeeni.facultyapp.questionlist.PendingQuestionsPojo;
import com.jeeni.facultyapp.questionlist.QuestionListActivity;
import com.jeeni.facultyapp.services.Api;
import com.jeeni.facultyapp.services.RetrofitClient;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionDetailActivity extends AppCompatActivity implements View.OnClickListener {

    MaterialCardView cardViewMetaData;
    MaterialButton btnPrev, btnNext;
    DatabaseHandler db;
    //  ImageView imageViewQue;
    LinearLayout imageViewQue;
    TextView txtQuesId, txtChapterName, txtTopicName, txtComplexity, txtAttemptedCount, txtIncorrectCount, txtCorrectCount;
    Integer currentQuesId;
    MaterialButton btnSolution, btnApprove, btnReject, btnQuestion;
    EditText input;
    FacultyPref facultyPref;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);

        this.getSupportActionBar().hide();

        // db handler object
        db = new DatabaseHandler(this);
        facultyPref = FacultyPref.getInstance(this);
        // get the id's of all widgets
        findViewId();
        // onClick event
        cardViewMetaData.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnPrev.setOnClickListener(this);
        btnSolution.setOnClickListener(this);
        btnApprove.setOnClickListener(this);
        btnReject.setOnClickListener(this);
        btnQuestion.setOnClickListener(this);

        // set common data
        int questionId = getIntent().getIntExtra("questionId", 0);
        Cursor cursor = db.getCurrentQuestion(questionId);
        if (cursor != null && cursor.getCount() > 0) {
            setCommonData(cursor);
        }
    }

    private void findViewId() {
        cardViewMetaData = findViewById(R.id.cardView_metadata);
        btnPrev = findViewById(R.id.btn_prev);
        btnNext = findViewById(R.id.btn_next);
        imageViewQue = findViewById(R.id.img_question_deatils);
        txtQuesId = findViewById(R.id.txt_que_id);
        txtChapterName = findViewById(R.id.txt_chapter_name);
        txtTopicName = findViewById(R.id.txt_topic_name);
        txtComplexity = findViewById(R.id.txt_complexity);
        txtAttemptedCount = findViewById(R.id.txt_attempted_ques_count);
        txtIncorrectCount = findViewById(R.id.txt_incorrect_count);
        txtCorrectCount = findViewById(R.id.txt_correct_count);
        btnSolution = findViewById(R.id.btn_solution);
        btnApprove = findViewById(R.id.btn_approve);
        btnReject = findViewById(R.id.btn_reject);
        btnQuestion = findViewById(R.id.btn_question);
        progressBar = findViewById(R.id.progress_question_details);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cardView_metadata:
                Intent intent = new Intent(QuestionDetailActivity.this, MetaDataActivity.class);
                Log.d("XXX: ", "currentqid: " + currentQuesId);
                intent.putExtra("questionId", currentQuesId);
                startActivity(intent);
                break;
            case R.id.btn_next:
                btnSolution.setVisibility(View.VISIBLE);
                btnQuestion.setVisibility(View.GONE);
                Cursor cursor = db.getNextQuestion(currentQuesId);
                if (cursor != null && cursor.getCount() > 0) {
                    setCommonData(cursor);
                }
                break;
            case R.id.btn_prev:
                btnSolution.setVisibility(View.VISIBLE);
                btnQuestion.setVisibility(View.GONE);
                Cursor cursorNext = db.getPreviousQuestion(currentQuesId);
                if (cursorNext != null && cursorNext.getCount() > 0) {
                    setCommonData(cursorNext);
                }
                break;
            case R.id.btn_solution:
                Cursor cursorSolution = db.getSolutionImage(currentQuesId);

                if (cursorSolution != null && cursorSolution.getCount() > 0) {
                    setSolutionImg(cursorSolution);
                }
                break;

            case R.id.btn_question:
                Cursor cursorQuestion = db.getCurrentQuestion(currentQuesId);

                if (cursorQuestion != null && cursorQuestion.getCount() > 0) {
                    setQuestionImg(cursorQuestion);
                }
                break;
            case R.id.btn_approve:
                openDialog(true);
                break;
            case R.id.btn_reject:
                openDialog(false);
                break;
        }

    }

    private void openDialog(boolean approve) {
        AlertDialog.Builder builder = new AlertDialog.Builder(QuestionDetailActivity.this);
        input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHint("Please enter comment here");

        if (approve) {
            builder.setTitle("Approve");
            builder.setMessage("Are you sure you want to approve this question?");
        } else {
            builder.setTitle("Reject");
            builder.setMessage("Are you sure you want to reject this question?");
        }
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
                if(s.length()>0){
                    input.setError(null);
                }else {
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
            if(mValue.isEmpty()||mValue.equals("")){
               // input.requestFocus();
                input.setError("Comment can not be blank");
              //  Toast.makeText(QuestionDetailActivity.this, "Invalid data", Toast.LENGTH_SHORT).show();
            }else{
                dialog.dismiss();
                rejectQuestionApiCall(currentQuesId,mValue);
            }
        }
    }

    private void rejectQuestionApiCall(Integer currentQuesId, String comment) {
        progressBar.setVisibility(View.VISIBLE);
        Api retrofitServices = RetrofitClient
                .getClient(30, true)
                .create(Api.class);
        Call<ResponseBody> call = retrofitServices.rejectQuestion(facultyPref.getData("jauth"),currentQuesId,comment);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressBar.setVisibility(View.GONE);
                if (response.code() == 200) {
                    db.removeQuestion(currentQuesId);
                    finish();
                }
                if (response.code() == 401) {
                    facultyPref.loggedIn("loggedInStatus", false);
                    Toast.makeText(QuestionDetailActivity.this, "Session expired,please login again!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(QuestionDetailActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(QuestionDetailActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setQuestionImg(Cursor cursorQuestion) {
        btnSolution.setVisibility(View.VISIBLE);
        btnQuestion.setVisibility(View.GONE);
        cursorQuestion.moveToFirst();

        ImageView image = new ImageView(this);
        imageViewQue.removeAllViews();
        imageViewQue.addView(image);
        image.setScaleType(ImageView.ScaleType.FIT_CENTER);
        image.setAdjustViewBounds(true);

        Picasso.get()
                .load(cursorQuestion.getString(16))
                .placeholder(R.drawable.loading)
                .into(image);
    }

    private void setSolutionImg(@NotNull Cursor cursorSolution) {
        btnSolution.setVisibility(View.GONE);
        btnQuestion.setVisibility(View.VISIBLE);

        cursorSolution.moveToFirst();

        ImageView image = new ImageView(this);
        imageViewQue.removeAllViews();
        imageViewQue.addView(image);
        image.setScaleType(ImageView.ScaleType.FIT_CENTER);
        image.setAdjustViewBounds(true);

        Picasso.get()
                .load(cursorSolution.getString(17))
                .placeholder(R.drawable.loading)
                .into(image);

    }

    private void setCommonData(Cursor cursor) {
        cursor.moveToFirst();

        ImageView image = new ImageView(this);
        imageViewQue.removeAllViews();
        imageViewQue.addView(image);
        image.setScaleType(ImageView.ScaleType.FIT_CENTER);
        image.setAdjustViewBounds(true);

        Picasso.get()
                .load(cursor.getString(16))
                .placeholder(R.drawable.loading)
                .into(image);

        currentQuesId = cursor.getInt(1);
        txtQuesId.setText("" + cursor.getString(1));
        txtChapterName.setText("" + cursor.getString(10));
        txtTopicName.setText("" + cursor.getString(12));
        txtComplexity.setText("" + cursor.getString(13));
        txtAttemptedCount.setText("" + cursor.getString(19));
        txtIncorrectCount.setText("" + cursor.getString(20));
        txtCorrectCount.setText("" + cursor.getString(21));

        // check next question is availble or not
        Cursor cursorNxtbtn = db.getNextQuestion(currentQuesId);
        if (!cursorNxtbtn.moveToNext()) {
            btnNext.setVisibility(View.INVISIBLE);
        } else {
            btnNext.setVisibility(View.VISIBLE);
        }

        // check prev question is availble or not
        Cursor cursorPrvbtn = db.getPreviousQuestion(currentQuesId);
        if (!cursorPrvbtn.moveToNext()) {
            btnPrev.setVisibility(View.INVISIBLE);
        } else {
            btnPrev.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(QuestionDetailActivity.this,QuestionListActivity.class);
        startActivity(intent);
        finish();
    }
}