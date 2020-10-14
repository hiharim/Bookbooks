package com.harimi.bookbooks;
import android.net.Uri;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**@BookData
 * @brief BookDataArrayList에서 사용할 BookData구성
 * 아이템뷰에 들어갈 것은 cover,title,writer 만
 * 생성자 함수를 통해 각 멤버 변수의 데이터 값들을 Setting하고 getter 함수를 구현하여 각 멤버변수의 값들을 return 하도록 구현
 * */

public class BookData implements Serializable {

    private static final String JSON_COVER="cover";
    private static final String JSON_TITLE="title";
    private static final String JSON_WRITER="writer";
    private static final String JSON_PUBLISHER="publisher";
    private static final String JSON_READDATE="readdate";
    private static final String JSON_STAR="star";


    private String cover, title, writer, publisher, readdate;
    private float star;

    public BookData(String cover, String title, String writer, String publisher, String readdate, float star) {
        this.cover = cover;
        this.title = title;
        this.writer = writer;
        this.publisher = publisher;
        this.readdate = readdate;
        this.star = star;
    }


    public BookData(String readdate){
        this.readdate=readdate;
    }


    //json.getString(JSON_TITLE) : 저장된 String값으로 해당 값 가져오기
//    public BookData(JSONObject json) throws JSONException{
//        cover=json.getString(JSON_COVER);
//        title=json.getString(JSON_TITLE);
//        writer=json.getString(JSON_WRITER);
//        publisher=json.getString(JSON_PUBLISHER);
//        readdate=json.getString(JSON_READDATE);
//        star=json.getInt(JSON_STAR);
//
//
//    }
//
//    // json으로 각각의 id에 데이터 입력
//    public JSONObject toJSON() throws JSONException{
//        JSONObject json=new JSONObject();
//        json.put(JSON_COVER,cover);
//        json.put(JSON_TITLE,title);
//        json.put(JSON_WRITER,writer);
//        json.put(JSON_PUBLISHER,publisher);
//        json.put(JSON_READDATE,readdate);
//        json.put(JSON_STAR,star);
//
//        return json;
//    }


    //arrayList 를 JSONObject로 변경시키는 메서드
    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("savecover",this.getCover());
            obj.put("savetitle", this.getTitle());
            obj.put("savewriter", this.getWriter());
            obj.put("savepublisher", this.getPublisher());
            obj.put("savedate", this.getReaddate());
            obj.put("savestar", this.getStar());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }




    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getReaddate() {
        return readdate;
    }

    public void setReaddate(String readdate) {
        this.readdate = readdate;
    }


    public float getStar() {
        return star;
    }

    public void setStar(float star) {
        this.star = star;
    }




}