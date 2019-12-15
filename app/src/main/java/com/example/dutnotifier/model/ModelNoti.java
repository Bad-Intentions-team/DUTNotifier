package com.example.dutnotifier.model;

import java.io.Serializable;

public class ModelNoti implements Serializable {
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

    public ModelNoti(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public ModelNoti() {
    }
}
