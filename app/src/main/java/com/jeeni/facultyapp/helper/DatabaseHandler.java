package com.jeeni.facultyapp.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.jeeni.facultyapp.questionlist.PendingQuestionsPojo;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "facultyQB";
    private static final String TABLE_QUESTION_FOR_REVIEW = "questionForReview";

    // column name
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

    String CREATE_TABLE_QUESTION_FOR_REVIEW = "CREATE TABLE " + TABLE_QUESTION_FOR_REVIEW + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_DURATION + " INTEGER,"
            + KEY_QUESTION_TEXT + " TEXT,"
            + KEY_SOLUTION_TEXT_DATA + " TEXT,"
            + KEY_COURSE_ID + " INTEGER,"
            + KEY_COURSE_NAME + " TEXT,"
            + KEY_SUBJECT_ID + " INTEGER,"
            + KEY_SUBJECT_NAME + " TEXT,"
            + KEY_CHAPTER_ID + " INTEGER,"
            + KEY_CHAPTER_NAME + " TEXT,"
            + KEY_TOPIC_ID + " INTEGER,"
            + KEY_TOPIC_NAME + " TEXT,"
            + KEY_COMPLEXITY + " INTEGER,"
            + KEY_QUESTION_TYPE + " INTEGER,"
            + KEY_QUESTION_SUBTYPE + " INTEGER,"
            + KEY_GEN_URL + " TEXT,"
            + KEY_SOLN_URL + " TEXT,"
            + KEY_HDPI_URL + " TEXT,"
            + KEY_TOTAL_ATTEMPT + " INTEGER,"
            + KEY_INCORRECT + " INTEGER,"
            + KEY_CORRECT + " INTEGER" + ")";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("XXX:", "db create: ");
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QUESTION_FOR_REVIEW);
        Log.d("XXX:", "table create: ");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION_FOR_REVIEW);

        // Create tables again
        onCreate(db);
    }
    // code to add the new contact
    public void addContact(PendingQuestionsPojo pendingQuestionsPojo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CHAPTER_ID, pendingQuestionsPojo.getChapterId()); // Contact Name
        values.put(KEY_COURSE_ID, pendingQuestionsPojo.getCourseId()); // Contact Phone

        // Inserting Row
        db.insert(TABLE_QUESTION_FOR_REVIEW, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }
}
