package com.harimi.bookbooks;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * ChartData.class - barChart에 들어갈 list를 처리해주는 데이터 클래스
 *
 */


public class ChartData implements Serializable {

    String year;
    String month;


    public ChartData(String year, String month) {
        this.year = year;
        this.month = month;
    }


    



    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }



}
