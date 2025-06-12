package com.example.firstaidapp.models;

public class EmergencyCondition {
    private int id;
    private String title;
    private String description;
    private int imageResourceId;

    public EmergencyCondition(int id, String title, String description, int imageResourceId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageResourceId = imageResourceId;
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
}