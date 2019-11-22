package com.example.dutnotifier.model;

public class modelNoti {
    String title;
    String content;

    public modelNoti() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public modelNoti(String date, String title, String content) {
        this.title = title;
        this.content = content;
    }
}
