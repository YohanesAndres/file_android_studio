package com.if5a.chattogether.models;

public class Data {
    private String title;
    private String body;
    private String sender;
    private String imageUrl;

    public Data(String title, String body, String sender) {
        this.title = title;
        this.body = body;
        this.sender = sender;
    }

    public Data(String title, String body, String sender, String imageUrl) {
        this.title = title;
        this.body = body;
        this.sender = sender;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getSender() {
        return sender;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
