package com.jeeni.facultyapp.questionlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PendingQuestionsPojo {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("questionText")
    @Expose
    private String questionText;
    @SerializedName("courseId")
    @Expose
    private Integer courseId;
    @SerializedName("courseName")
    @Expose
    private String courseName;
    @SerializedName("subjectId")
    @Expose
    private Integer subjectId;
    @SerializedName("subjectName")
    @Expose
    private String subjectName;
    @SerializedName("chapterId")
    @Expose
    private Integer chapterId;
    @SerializedName("chapterName")
    @Expose
    private String chapterName;
    @SerializedName("topicId")
    @Expose
    private Integer topicId;
    @SerializedName("topicName")
    @Expose
    private String topicName;
    @SerializedName("complexity")
    @Expose
    private Integer complexity;
    @SerializedName("timeLimit")
    @Expose
    private Integer timeLimit;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("copyright")
    @Expose
    private String copyright;
    @SerializedName("approved")
    @Expose
    private Boolean approved;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("questionStatus")
    @Expose
    private Integer questionStatus;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("deleted")
    @Expose
    private Boolean deleted;
    @SerializedName("solution")
    @Expose
    private String solution;
    @SerializedName("solutionText")
    @Expose
    private String solutionText;
    @SerializedName("solutionHint")
    @Expose
    private String solutionHint;
    @SerializedName("tag")
    @Expose
    private String tag;
    @SerializedName("tagTypeName")
    @Expose
    private String tagTypeName;
    @SerializedName("genericImage")
    @Expose
    private String genericImage;
    @SerializedName("hdpiImage")
    @Expose
    private String hdpiImage;
    @SerializedName("mdpiImage")
    @Expose
    private String mdpiImage;
    @SerializedName("ldpiImage")
    @Expose
    private String ldpiImage;
    @SerializedName("a4Image")
    @Expose
    private String a4Image;
    @SerializedName("options")
    @Expose
    private Options options;
    @SerializedName("courseIds")
    @Expose
    private String courseIds;
    @SerializedName("subjectIds")
    @Expose
    private String subjectIds;
    @SerializedName("chapterIds")
    @Expose
    private String chapterIds;
    @SerializedName("topicIds")
    @Expose
    private String topicIds;
    @SerializedName("operatorId")
    @Expose
    private Integer operatorId;
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("facultySolution")
    @Expose
    private String facultySolution;
    @SerializedName("questionTypeId")
    @Expose
    private Integer questionTypeId;
    @SerializedName("groupId")
    @Expose
    private Integer groupId;
    @SerializedName("questionTypeName")
    @Expose
    private String questionTypeName;
    @SerializedName("groupName")
    @Expose
    private String groupName;
    @SerializedName("groupImage")
    @Expose
    private String groupImage;
    @SerializedName("groupText")
    @Expose
    private String groupText;
    @SerializedName("genericBlobImage")
    @Expose
    private String genericBlobImage;
    @SerializedName("numericAnswer")
    @Expose
    private String numericAnswer;
    @SerializedName("columnMatchAnswer")
    @Expose
    private List<Object> columnMatchAnswer = null;
    @SerializedName("duplicateQuestions")
    @Expose
    private String duplicateQuestions;
    @SerializedName("questionSubType")
    @Expose
    private Integer questionSubType;
    @SerializedName("questionMappers")
    @Expose
    private String questionMappers;
    @SerializedName("answer")
    @Expose
    private String answer;
    @SerializedName("correctAnsCount")
    @Expose
    private int correctAnsCount;
    @SerializedName("inCorrectAnsCount")
    @Expose
    private int inCorrectAnsCount;
    @SerializedName("issueReportedCount")
    @Expose
    private int issueReportedCount;
    @SerializedName("genericImageUrl")
    @Expose
    private String genericImageUrl;
    @SerializedName("hdpiImageUrl")
    @Expose
    private String hdpiImageUrl;
    @SerializedName("mdpiImageUrl")
    @Expose
    private String mdpiImageUrl;
    @SerializedName("ldpiImageUrl")
    @Expose
    private String ldpiImageUrl;
    @SerializedName("a4ImageUrl")
    @Expose
    private String a4ImageUrl;
    @SerializedName("questionUrl")
    @Expose
    private String questionUrl;
    @SerializedName("solutionUrl")
    @Expose
    private String solutionUrl;
    @SerializedName("correctQues")
    @Expose
    private Integer correctQues;
    @SerializedName("inCorrectQues")
    @Expose
    private Integer inCorrectQues;
    @SerializedName("unAttemptedQues")
    @Expose
    private Integer unAttemptedQues;
    @SerializedName("multipleAnswer")
    @Expose
    private Boolean multipleAnswer;
    @SerializedName("solutionAvailable")
    @Expose
    private Boolean solutionAvailable;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Integer getChapterId() {
        return chapterId;
    }

    public void setChapterId(Integer chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public Integer getComplexity() {
        return complexity;
    }

    public void setComplexity(Integer complexity) {
        this.complexity = complexity;
    }

    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getQuestionStatus() {
        return questionStatus;
    }

    public void setQuestionStatus(Integer questionStatus) {
        this.questionStatus = questionStatus;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getSolutionText() {
        return solutionText;
    }

    public void setSolutionText(String solutionText) {
        this.solutionText = solutionText;
    }

    public String getSolutionHint() {
        return solutionHint;
    }

    public void setSolutionHint(String solutionHint) {
        this.solutionHint = solutionHint;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTagTypeName() {
        return tagTypeName;
    }

    public void setTagTypeName(String tagTypeName) {
        this.tagTypeName = tagTypeName;
    }

    public String getGenericImage() {
        return genericImage;
    }

    public void setGenericImage(String genericImage) {
        this.genericImage = genericImage;
    }

    public String getHdpiImage() {
        return hdpiImage;
    }

    public void setHdpiImage(String hdpiImage) {
        this.hdpiImage = hdpiImage;
    }

    public String getMdpiImage() {
        return mdpiImage;
    }

    public void setMdpiImage(String mdpiImage) {
        this.mdpiImage = mdpiImage;
    }

    public String getLdpiImage() {
        return ldpiImage;
    }

    public void setLdpiImage(String ldpiImage) {
        this.ldpiImage = ldpiImage;
    }

    public String getA4Image() {
        return a4Image;
    }

    public void setA4Image(String a4Image) {
        this.a4Image = a4Image;
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions(Options options) {
        this.options = options;
    }

    public String getCourseIds() {
        return courseIds;
    }

    public void setCourseIds(String courseIds) {
        this.courseIds = courseIds;
    }

    public String getSubjectIds() {
        return subjectIds;
    }

    public void setSubjectIds(String subjectIds) {
        this.subjectIds = subjectIds;
    }

    public String getChapterIds() {
        return chapterIds;
    }

    public void setChapterIds(String chapterIds) {
        this.chapterIds = chapterIds;
    }

    public String getTopicIds() {
        return topicIds;
    }

    public void setTopicIds(String topicIds) {
        this.topicIds = topicIds;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getFacultySolution() {
        return facultySolution;
    }

    public void setFacultySolution(String facultySolution) {
        this.facultySolution = facultySolution;
    }

    public Integer getQuestionTypeId() {
        return questionTypeId;
    }

    public void setQuestionTypeId(Integer questionTypeId) {
        this.questionTypeId = questionTypeId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getQuestionTypeName() {
        return questionTypeName;
    }

    public void setQuestionTypeName(String questionTypeName) {
        this.questionTypeName = questionTypeName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupImage() {
        return groupImage;
    }

    public void setGroupImage(String groupImage) {
        this.groupImage = groupImage;
    }

    public String getGroupText() {
        return groupText;
    }

    public void setGroupText(String groupText) {
        this.groupText = groupText;
    }

    public String getGenericBlobImage() {
        return genericBlobImage;
    }

    public void setGenericBlobImage(String genericBlobImage) {
        this.genericBlobImage = genericBlobImage;
    }

    public String getNumericAnswer() {
        return numericAnswer;
    }

    public void setNumericAnswer(String numericAnswer) {
        this.numericAnswer = numericAnswer;
    }

    public List<Object> getColumnMatchAnswer() {
        return columnMatchAnswer;
    }

    public void setColumnMatchAnswer(List<Object> columnMatchAnswer) {
        this.columnMatchAnswer = columnMatchAnswer;
    }

    public String getDuplicateQuestions() {
        return duplicateQuestions;
    }

    public void setDuplicateQuestions(String duplicateQuestions) {
        this.duplicateQuestions = duplicateQuestions;
    }

    public Integer getQuestionSubType() {
        return questionSubType;
    }

    public void setQuestionSubType(Integer questionSubType) {
        this.questionSubType = questionSubType;
    }

    public String getQuestionMappers() {
        return questionMappers;
    }

    public void setQuestionMappers(String questionMappers) {
        this.questionMappers = questionMappers;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getCorrectAnsCount() {
        return correctAnsCount;
    }

    public void setCorrectAnsCount(int correctAnsCount) {
        this.correctAnsCount = correctAnsCount;
    }

    public int getInCorrectAnsCount() {
        return inCorrectAnsCount;
    }

    public void setInCorrectAnsCount(int inCorrectAnsCount) {
        this.inCorrectAnsCount = inCorrectAnsCount;
    }

    public int getIssueReportedCount() {
        return issueReportedCount;
    }

    public void setIssueReportedCount(int issueReportedCount) {
        this.issueReportedCount = issueReportedCount;
    }

    public String getGenericImageUrl() {
        return genericImageUrl;
    }

    public void setGenericImageUrl(String genericImageUrl) {
        this.genericImageUrl = genericImageUrl;
    }

    public String getHdpiImageUrl() {
        return hdpiImageUrl;
    }

    public void setHdpiImageUrl(String hdpiImageUrl) {
        this.hdpiImageUrl = hdpiImageUrl;
    }

    public String getMdpiImageUrl() {
        return mdpiImageUrl;
    }

    public void setMdpiImageUrl(String mdpiImageUrl) {
        this.mdpiImageUrl = mdpiImageUrl;
    }

    public String getLdpiImageUrl() {
        return ldpiImageUrl;
    }

    public void setLdpiImageUrl(String ldpiImageUrl) {
        this.ldpiImageUrl = ldpiImageUrl;
    }

    public String getA4ImageUrl() {
        return a4ImageUrl;
    }

    public void setA4ImageUrl(String a4ImageUrl) {
        this.a4ImageUrl = a4ImageUrl;
    }

    public String getQuestionUrl() {
        return questionUrl;
    }

    public void setQuestionUrl(String questionUrl) {
        this.questionUrl = questionUrl;
    }

    public String getSolutionUrl() {
        return solutionUrl;
    }

    public void setSolutionUrl(String solutionUrl) {
        this.solutionUrl = solutionUrl;
    }

    public Integer getCorrectQues() {
        return correctQues;
    }

    public void setCorrectQues(Integer correctQues) {
        this.correctQues = correctQues;
    }

    public Integer getInCorrectQues() {
        return inCorrectQues;
    }

    public void setInCorrectQues(Integer inCorrectQues) {
        this.inCorrectQues = inCorrectQues;
    }

    public Integer getUnAttemptedQues() {
        return unAttemptedQues;
    }

    public void setUnAttemptedQues(Integer unAttemptedQues) {
        this.unAttemptedQues = unAttemptedQues;
    }

    public Boolean getMultipleAnswer() {
        return multipleAnswer;
    }

    public void setMultipleAnswer(Boolean multipleAnswer) {
        this.multipleAnswer = multipleAnswer;
    }

    public Boolean getSolutionAvailable() {
        return solutionAvailable;
    }

    public void setSolutionAvailable(Boolean solutionAvailable) {
        this.solutionAvailable = solutionAvailable;
    }
}

