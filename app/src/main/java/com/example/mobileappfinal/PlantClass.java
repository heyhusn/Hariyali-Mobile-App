package com.example.mobileappfinal;

import android.net.Uri;

public class PlantClass {
    private String plantName;
    private String description;
    private double humidity;
    private Uri imageUri;
    private double height;
    private double temperature;
    String category;
    private boolean suitability;
    PlantClass(Uri imageUri, String plantName, String description, double humidity, double height, double temperature, boolean suitability, String category)
    {
        this.plantName= plantName;
        this.category=category;
        this.imageUri=imageUri;
        this.description=description;
        this.suitability=suitability;
        this.height=height;
        this.humidity=humidity;
        this.temperature=temperature;
    }



    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getTemperature() {
        return temperature;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public boolean isSuitability() {
        return suitability;
    }

    public void setSuitability(boolean suitability) {
        this.suitability = suitability;
    }
}
