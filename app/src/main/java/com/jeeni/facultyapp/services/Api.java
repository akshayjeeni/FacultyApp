package com.jeeni.facultyapp.services;

import com.jeeni.facultyapp.questionlist.PendingQuestionsPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface Api {
    @GET("faculty/getPendingQuestionsForFaculty1")
    Call<List<PendingQuestionsPojo>> getPendingQuestionsPojo(@Header("jauth") String jAuth);
}
