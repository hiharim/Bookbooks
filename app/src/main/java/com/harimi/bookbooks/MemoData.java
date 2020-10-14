package com.harimi.bookbooks;

import android.net.Uri;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class MemoData implements Serializable {

    private String line;
    private String memo;
    private String page;
    private String picture;
    private String date;


    public MemoData(String date,String page, String line,String picture,String memo) {
        this.line = line;
        this.memo = memo;
        this.page = page;
        this.picture = picture;
        this.date = date;
    }


    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("saveline",this.getLine());
            obj.put("savememo", this.getMemo());
            obj.put("savepage", this.getPage());
            obj.put("savepicture", this.getPicture());
            obj.put("savedate", this.getDate());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }




    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }


    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
