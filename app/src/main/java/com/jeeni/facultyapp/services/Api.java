package com.jeeni.facultyapp.services;

import com.jeeni.facultyapp.login.UserPojo;
import com.jeeni.facultyapp.metadata.ChapterTopicListDto;
import com.jeeni.facultyapp.questionlist.PendingQuestionsPojo;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Api {
    @GET("faculty/getPendingQuestionsForFaculty1")
    Call<List<PendingQuestionsPojo>> getPendingQuestionsPojo(@Header("jauth") String jAuth);

    @GET("faculty/getChapterTopicList/{courseId}/{subjectId}")
    Call<List<ChapterTopicListDto>> getChapterTopicList(@Header("jauth") String jAuth,
                                                  @Path("courseId") int courseId,
                                                  @Path("subjectId") int subjectId);
    @FormUrlEncoded
    @POST("login/facultyLoginAuthentication")
    Call<UserPojo> loginAuthentication( @Field("email") String loginId,
                                        @Field("password") String password,
                                        @Field("deviceId") String deviceIMEI);
}
