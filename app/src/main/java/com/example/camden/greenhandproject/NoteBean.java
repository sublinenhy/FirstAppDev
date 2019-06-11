package com.example.camden.greenhandproject;

import java.util.UUID;

public class NoteBean {

    private String type;
    private String title;
    private String content;
    private String pictureLink;



    private String uuid;

    public NoteBean() {
        uuid = UUID.randomUUID().toString();

    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPictureLink(String pictureLink) {
        this.pictureLink = pictureLink;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getPictureLink() {
        return pictureLink;
    }

    public String getUuid() {
        return uuid;
    }

}
