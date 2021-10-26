package com.jeeni.facultyapp.questiondetail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.jeeni.facultyapp.helper.DatabaseHandler;
import com.jeeni.facultyapp.metadata.MetaDataActivity;
import com.jeeni.facultyapp.R;
import com.squareup.picasso.Picasso;

public class QuestionDetailActivity extends AppCompatActivity implements View.OnClickListener {

    MaterialCardView cardViewMetaData;
    MaterialButton btnPrev, btnNext;
    DatabaseHandler db;
  //  ImageView imageViewQue;
    LinearLayout imageViewQue;
    TextView txtQuesId, txtChapterName, txtTopicName, txtComplexity, txtAttemptedCount, txtIncorrectCount, txtCorrectCount;
    Integer currentQuesId;
    MaterialButton btnSolution,btnApprove,btnReject,btnQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);

        this.getSupportActionBar().hide();

        // db handler object
        db = new DatabaseHandler(this);
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cardView_metadata:
                Intent intent = new Intent(QuestionDetailActivity.this, MetaDataActivity.class);
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
        }

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

    private void setSolutionImg(Cursor cursorSolution) {
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
}