package com.example.hishara.mapapp.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hishara on 6/14/2016.
 */
public class NewStatus {

    @SerializedName("status_id")
    private int statusID;
    @SerializedName("user_id")
    private int  userid;
    @SerializedName("status")
    private String status;
    @SerializedName("pic_url1")
    private String picURL1;
    @SerializedName("pic_url2")
    private String PicURL2;

    public NewStatus(int statusID, int userid, String status, String picURL1, String picURL2) {
        this.statusID = statusID;
        this.userid = userid;
        this.status = status;
        this.picURL1 = picURL1;
        PicURL2 = picURL2;
    }

    public int getStatusID() {
        return statusID;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPicURL1() {
        return picURL1;
    }

    public void setPicURL1(String picURL1) {
        this.picURL1 = picURL1;
    }

    public String getPicURL2() {
        return PicURL2;
    }

    public void setPicURL2(String picURL2) {
        PicURL2 = picURL2;
    }
}
