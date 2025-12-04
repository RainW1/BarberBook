package com.example.barberbook.models;

public class Barber {
    private int id;
    private String name;
    private double rating;
    private int experience;
    private boolean available;

    public Barber() {}

    public Barber(int id, String name, double rating, int experience, boolean available) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.experience = experience;
        this.available = available;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public int getExperience() { return experience; }
    public void setExperience(int experience) { this.experience = experience; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
}