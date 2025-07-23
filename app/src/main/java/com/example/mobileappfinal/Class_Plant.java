package com.example.mobileappfinal;

public class Class_Plant {
    private String name, description, imageUrl;

    public Class_Plant() {}

    public Class_Plant(String name, String description, String imageUrl) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
}