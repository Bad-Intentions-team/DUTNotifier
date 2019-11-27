package com.example.dutnotifier.model;

public class modelNoti {
    int id;
    String title;
    String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public modelNoti(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public modelNoti() {
    }
}
