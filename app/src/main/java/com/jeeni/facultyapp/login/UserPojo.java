package com.jeeni.facultyapp.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserPojo {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("role")
    @Expose
    private Integer role;
    @SerializedName("password")
    @Expose
    private Object password;
    @SerializedName("encrypted")
    @Expose
    private Boolean encrypted;
    @SerializedName("deleted")
    @Expose
    private Boolean deleted;
    @SerializedName("lastAccessTime")
    @Expose
    private Long lastAccessTime;
    @SerializedName("questionPaperVos")
    @Expose
    private Object questionPaperVos;
    @SerializedName("hideFunctionalityIds")
    @Expose
    private Object hideFunctionalityIds;
    @SerializedName("jauth")
    @Expose
    private String jauth;
    @SerializedName("onPrem")
    @Expose
    private Boolean onPrem;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Object getPassword() {
        return password;
    }

    public void setPassword(Object password) {
        this.password = password;
    }

    public Boolean getEncrypted() {
        return encrypted;
    }

    public void setEncrypted(Boolean encrypted) {
        this.encrypted = encrypted;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Long lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public Object getQuestionPaperVos() {
        return questionPaperVos;
    }

    public void setQuestionPaperVos(Object questionPaperVos) {
        this.questionPaperVos = questionPaperVos;
    }

    public Object getHideFunctionalityIds() {
        return hideFunctionalityIds;
    }

    public void setHideFunctionalityIds(Object hideFunctionalityIds) {
        this.hideFunctionalityIds = hideFunctionalityIds;
    }

    public String getJauth() {
        return jauth;
    }

    public void setJauth(String jauth) {
        this.jauth = jauth;
    }

    public Boolean getOnPrem() {
        return onPrem;
    }

    public void setOnPrem(Boolean onPrem) {
        this.onPrem = onPrem;
    }

}
