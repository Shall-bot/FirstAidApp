package com.example.firstaidapp.models;

public class FirstAidGuide {
    private int id;
    private String title;
    private String description;
    private int imageResourceId;
    private int lessons;
    private int progress;

    public FirstAidGuide(int id, String title, String description, int imageResourceId, int lessons, int progress) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageResourceId = imageResourceId;
        this.lessons = lessons;
        this.progress = progress;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public int getLessons() {
        return lessons;
    }

    public int getProgress() {
        return progress;
    }
}