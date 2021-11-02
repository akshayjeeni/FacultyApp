package com.jeeni.facultyapp.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.jeeni.facultyapp.metadata.ChapterTopicListDto;
import com.jeeni.facultyapp.metadata.ChapterTopicListPojo;
import com.jeeni.facultyapp.questionlist.PendingQuestionsPojo;
import com.jeeni.facultyapp.questionlist.QuestionListPojo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "facultyQB";
    private static final String TABLE_QUESTION_FOR_REVIEW = "questionForReview";
    private static final String TABLE_CHAPTER_TOPIC = "chapter_topic";

    FacultyPref facultyPref;
    // column name - questionForReview table
    private static final String KEY_ID = "id";
    private static final String KEY_QUESTION_ID = "question_id";
    private static final String KEY_DURATION = "duration";
    private static final String KEY_QUESTION_TEXT = "question_text";
    private static final String KEY_SOLUTION_TEXT_DATA = "solutiontext_data";
    private static final String KEY_COURSE_ID = "course_id";
    private static final String KEY_COURSE_NAME = "course_name";
    private static final String KEY_SUBJECT_ID = "subject_id";
    private static final String KEY_SUBJECT_NAME = "subject_name";
    private static final String KEY_CHAPTER_ID = "chapter_id";
    private static final String KEY_CHAPTER_NAME = "chapter_name";
    private static final String KEY_TOPIC_ID = "topic_id";
    private static final String KEY_TOPIC_NAME = "topic_name";
    private static final String KEY_COMPLEXITY = "complexity";
    private static final String KEY_QUESTION_TYPE = "question_type";
    private static final String KEY_QUESTION_SUBTYPE = "question_subtype";
    private static final String KEY_GEN_URL = "gen_url";
    private static final String KEY_HDPI_URL = "hdpi_url";
    private static final String KEY_SOLN_URL = "soln_url";
    private static final String KEY_TOTAL_ATTEMPT = "total_attepmt";
    private static final String KEY_CORRECT = "correct";
    private static final String KEY_INCORRECT = "incorrect";

    // column name - chapter_topic table
    private static final String KEY_CT_ID = "id";
    private static final String KEY_CT_COURSE_ID = "course_id";
    private static final String KEY_CT_SUBJECT_ID = "subject_id";
    private static final String KEY_CT_CHAPTER_ID = "chapter_id";
    private static final String KEY_CT_CHAPTER_NAME = "chapter_name";
    private static final String KEY_CT_TOPIC_ID = "topic_id";
    private static final String KEY_CT_TOPIC_NAME = "topic_name";

    // create questionForReview table
    String CREATE_TABLE_QUESTION_FOR_REVIEW = "CREATE TABLE " + TABLE_QUESTION_FOR_REVIEW + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_QUESTION_ID + " INTEGER,"
            + KEY_DURATION + " INTEGER,"
            + KEY_QUESTION_TEXT + " TEXT,"
            + KEY_SOLUTION_TEXT_DATA + " TEXT,"
            + KEY_COURSE_ID + " INTEGER,"  //5
            + KEY_COURSE_NAME + " TEXT,"
            + KEY_SUBJECT_ID + " INTEGER," // 7
            + KEY_SUBJECT_NAME + " TEXT,"
            + KEY_CHAPTER_ID + " INTEGER,"
            + KEY_CHAPTER_NAME + " TEXT," // 10
            + KEY_TOPIC_ID + " INTEGER,"
            + KEY_TOPIC_NAME + " TEXT," //12
            + KEY_COMPLEXITY + " INTEGER," // 13
            + KEY_QUESTION_TYPE + " INTEGER,"
            + KEY_QUESTION_SUBTYPE + " INTEGER,"
            + KEY_GEN_URL + " TEXT," //16
            + KEY_SOLN_URL + " TEXT," // 17
            + KEY_HDPI_URL + " TEXT,"  // 18
            + KEY_TOTAL_ATTEMPT + " INTEGER," // 19
            + KEY_INCORRECT + " INTEGER,"  // 20
            + KEY_CORRECT + " INTEGER" + ")"; //21

    String CREATE_TABLE_CHAPTER_TOPIC = "CREATE TABLE " + TABLE_CHAPTER_TOPIC + "("
            + KEY_CT_ID + " INTEGER PRIMARY KEY,"
            + KEY_CT_COURSE_ID + " INTEGER,"
            + KEY_CT_SUBJECT_ID + " INTEGER,"
            + KEY_CT_CHAPTER_ID + " INTEGER," // 3
            + KEY_CT_CHAPTER_NAME + " TEXT," // 4
            + KEY_CT_TOPIC_ID + " INTEGER,"
            + KEY_CT_TOPIC_NAME + " TEXT" + ")";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        facultyPref = FacultyPref.getInstance(context);
        Log.d("XXX:", "db create: ");
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QUESTION_FOR_REVIEW);
        db.execSQL(CREATE_TABLE_CHAPTER_TOPIC);
        Log.d("XXX:", "table create: ");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION_FOR_REVIEW);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHAPTER_TOPIC);

        // Create tables again
        onCreate(db);
    }

    // code to add the new pending questions for review
    public void addQuestionsForReview(List<PendingQuestionsPojo> pendingQuestionsPojoList) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.beginTransaction();
        if (pendingQuestionsPojoList != null && !pendingQuestionsPojoList.isEmpty()) {
            try {
                for (PendingQuestionsPojo response : pendingQuestionsPojoList) {
                    ContentValues values = new ContentValues();
                    values.put(KEY_QUESTION_ID, response.getId());
                    values.put(KEY_DURATION, response.getDuration());
                    values.put(KEY_QUESTION_TEXT, response.getQuestionText());
                    values.put(KEY_SOLUTION_TEXT_DATA, response.getSolutionText());
                    values.put(KEY_COURSE_ID, response.getCourseId());
                    values.put(KEY_COURSE_NAME, response.getCourseName());
                    values.put(KEY_SUBJECT_ID, response.getSubjectId());
                    values.put(KEY_SUBJECT_NAME, response.getSubjectName());
                    values.put(KEY_CHAPTER_ID, response.getChapterId());
                    values.put(KEY_CHAPTER_NAME, response.getChapterName());
                    values.put(KEY_TOPIC_ID, response.getTopicId());
                    values.put(KEY_TOPIC_NAME, response.getTopicName());
                    values.put(KEY_COMPLEXITY, response.getComplexity());
                    values.put(KEY_QUESTION_TYPE, response.getQuestionTypeId());
                    values.put(KEY_QUESTION_SUBTYPE, response.getQuestionSubType());
                    values.put(KEY_GEN_URL, response.getGenericImageUrl());
                    values.put(KEY_SOLN_URL, response.getSolutionUrl());
                    values.put(KEY_HDPI_URL, response.getHdpiImageUrl());
                    //     values.put(KEY_TOTAL_ATTEMPT,null);
                    values.put(KEY_INCORRECT, response.getInCorrectQues());
                    values.put(KEY_CORRECT, response.getCorrectQues());

                    db.insert(TABLE_QUESTION_FOR_REVIEW, null, values);
                    Log.d("XXX:", "table question for review: data inserted");
                }
                db.setTransactionSuccessful();

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
                String currentDateandTime = sdf.format(new Date());

                facultyPref.saveData("question_for_review_last_sync_datetime", currentDateandTime);
            } finally {
                db.endTransaction();
            }
        }
    }


    // code to add the chapter topic list
    public void addChapterTopic(List<ChapterTopicListDto> chapterTopicListDtos, int courseId, int subjId) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.beginTransaction();
        if (chapterTopicListDtos != null && !chapterTopicListDtos.isEmpty()) {
            try {
                for (ChapterTopicListDto response : chapterTopicListDtos) {
                    ContentValues values = new ContentValues();
                    values.put(KEY_CT_COURSE_ID, courseId);
                    values.put(KEY_CT_SUBJECT_ID, subjId);
                    values.put(KEY_CT_CHAPTER_NAME, response.getChapterName());
                    values.put(KEY_CT_CHAPTER_ID, response.getChapterId());
                    values.put(KEY_CT_TOPIC_ID, response.getTopicId());
                    values.put(KEY_CT_TOPIC_NAME, response.getTopicName());


                    db.insert(TABLE_CHAPTER_TOPIC, null, values);
                    Log.d("XXX:", "table chapte_topic: data inserted");
                }
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        }
    }

    // read pending question's
    public ArrayList<QuestionListPojo> readPendingQuestions() {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorQuestionList = db.rawQuery("SELECT * FROM " + TABLE_QUESTION_FOR_REVIEW, null);

        // on below line we are creating a new array list.
        ArrayList<QuestionListPojo> questionModalArrayList = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorQuestionList.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                questionModalArrayList.add(new QuestionListPojo(cursorQuestionList.getString(16),
                        cursorQuestionList.getInt(1)));
            } while (cursorQuestionList.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorQuestionList.close();
        return questionModalArrayList;
    }

    // fetch chapter list
    public ArrayList<ChapterTopicListPojo> readChapterList(int courseId, int subId) {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to read data from database.
        String selectQuesry = "SELECT distinct(chapter_id),chapter_name FROM " + TABLE_CHAPTER_TOPIC + "\t" + " WHERE course_id  = " + courseId + " AND subject_id = " + subId;

        Cursor cursorChapterTopicList = db.rawQuery(selectQuesry, null);

        // on below line we are creating a new array list.
        ArrayList<ChapterTopicListPojo> chapterTopicListPojos = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorChapterTopicList.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                chapterTopicListPojos.add(new ChapterTopicListPojo(cursorChapterTopicList.getInt(cursorChapterTopicList.getColumnIndex(KEY_CT_CHAPTER_ID)),
                        cursorChapterTopicList.getString(cursorChapterTopicList.getColumnIndex(KEY_CT_CHAPTER_NAME))));
            } while (cursorChapterTopicList.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorChapterTopicList.close();
        return chapterTopicListPojos;
    }
   // fetch topic list
   public ArrayList<ChapterTopicListPojo> readTopicList(int chapterId) {
       Log.d("XXX: ", "chpter id: "+chapterId);

       SQLiteDatabase db = this.getReadableDatabase();

       // on below line we are creating a cursor with query to read data from database.
       String selectQuesry = "SELECT * FROM " + TABLE_CHAPTER_TOPIC + "\t" + " WHERE chapter_id  = " + chapterId;

       Cursor cursorChapterTopicList = db.rawQuery(selectQuesry, null);

       // on below line we are creating a new array list.
       ArrayList<ChapterTopicListPojo> chapterTopicListPojos = new ArrayList<>();

       // moving our cursor to first position.
       if (cursorChapterTopicList.moveToFirst()) {
           do {
               // on below line we are adding the data from cursor to our array list.
               chapterTopicListPojos.add(new ChapterTopicListPojo(cursorChapterTopicList.getColumnIndex(KEY_CT_TOPIC_ID),
                       cursorChapterTopicList.getString(cursorChapterTopicList.getColumnIndex(KEY_CT_TOPIC_NAME))));
           } while (cursorChapterTopicList.moveToNext());
           // moving our cursor to next.
       }
       // at last closing our cursor
       // and returning our array list.
       cursorChapterTopicList.close();
       return chapterTopicListPojos;
   }

    // delete records from quesion_for_review table
    public void clearQuesForReview() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_QUESTION_FOR_REVIEW);
        Log.d("XXX: ", "clearQuesForReview: ");
    }

    public Cursor getCurrentQuestion(Integer questionId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuesry = "SELECT * FROM " + TABLE_QUESTION_FOR_REVIEW + "\t" + " WHERE question_id  = " + questionId;
        return db.rawQuery(selectQuesry, null);
    }

    public Cursor getNextQuestion(Integer questionId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_QUESTION_FOR_REVIEW + "\t" + " WHERE id > (SELECT id FROM " + TABLE_QUESTION_FOR_REVIEW + "\t" + " WHERE question_id =  " + questionId + ")\t ORDER BY id ASC LIMIT 1";
        return db.rawQuery(query, null);
    }

    public Cursor getPreviousQuestion(Integer questionId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_QUESTION_FOR_REVIEW + "\t" + "WHERE id < (SELECT id FROM " + TABLE_QUESTION_FOR_REVIEW + "\t" + "WHERE question_id =  " + questionId + ")\t ORDER BY id DESC LIMIT 1";
        return db.rawQuery(query, null);
    }

    public Cursor getSolutionImage(Integer questionId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuesry = "SELECT * FROM " + TABLE_QUESTION_FOR_REVIEW + "\t" + " WHERE question_id = " + "\t" + questionId;
        return db.rawQuery(selectQuesry, null);
    }


    public Cursor getChapterTopicListByCourseIdAndSubId(int courseId, int subId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuesry = "SELECT * FROM " + TABLE_CHAPTER_TOPIC + "\t" + " WHERE course_id  = " + courseId + " AND subject_id = " + subId;
        Log.d("XXX: ", "getChapterTopicListByCourseIdAndSubId: " + selectQuesry);
        return db.rawQuery(selectQuesry, null);
    }
}
