package com.harimi.bookbooks;

import java.io.Serializable;


public class OnlineBook implements Serializable {

    String cover,title,writer,publisher;

    //   public OnlineBook(String cover, String title, String writer, String publisher) {
    //       this.cover = cover;
    //       this.title = title;
    //       this.writer = writer;
    //       this.publisher = publisher;
    //   }



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
}
