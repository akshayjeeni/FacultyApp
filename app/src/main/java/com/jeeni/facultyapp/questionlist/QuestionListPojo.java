package com.jeeni.facultyapp.questionlist;

public class QuestionListPojo {

    private String questionGenImgUrl;
   private int questionId;

    public QuestionListPojo(String questionGenImgUrl, int questionId) {
        this.questionGenImgUrl = questionGenImgUrl;
        this.questionId = questionId;
    }

    public String getQuestionGenImgUrl() {
        return questionGenImgUrl;
    }

    public void setQuestionGenImgUrl(String questionGenImgUrl) {
        this.questionGenImgUrl = questionGenImgUrl;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }
}
