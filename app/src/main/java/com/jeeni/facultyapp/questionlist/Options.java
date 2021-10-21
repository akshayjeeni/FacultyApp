package com.jeeni.facultyapp.questionlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Options {

    @SerializedName("1")
    @Expose
    private Boolean _1;
    @SerializedName("2")
    @Expose
    private Boolean _2;
    @SerializedName("3")
    @Expose
    private Boolean _3;
    @SerializedName("4")
    @Expose
    private Boolean _4;

    public Boolean get1() {
        return _1;
    }

    public void set1(Boolean _1) {
        this._1 = _1;
    }

    public Boolean get2() {
        return _2;
    }

    public void set2(Boolean _2) {
        this._2 = _2;
    }

    public Boolean get3() {
        return _3;
    }

    public void set3(Boolean _3) {
        this._3 = _3;
    }

    public Boolean get4() {
        return _4;
    }

    public void set4(Boolean _4) {
        this._4 = _4;
    }


}
