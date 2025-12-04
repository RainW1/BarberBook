package com.example.barberbook.models;

public class Booking {
    private int id;
    private int userId;
    private int barberId;
    private String barberName;
    private String services;
    private String date;
    private String time;
    private int totalPrice;
    private String status; // "Upcoming", "Completed", "Cancelled"
    private String notes;

    public Booking() {}

    public Booking(int id, int userId, int barberId, String barberName, String services,
                   String date, String time, int totalPrice, String status, String notes) {
        this.id = id;
        this.userId = userId;
        this.barberId = barberId;
        this.barberName = barberName;
        this.services = services;
        this.date = date;
        this.time = time;
        this.totalPrice = totalPrice;
        this.status = status;
        this.notes = notes;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getBarberId() { return barberId; }
    public void setBarberId(int barberId) { this.barberId = barberId; }

    public String getBarberName() { return barberName; }
    public void setBarberName(String barberName) { this.barberName = barberName; }

    public String getServices() { return services; }
    public void setServices(String services) { this.services = services; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public int getTotalPrice() { return totalPrice; }
    public void setTotalPrice(int totalPrice) { this.totalPrice = totalPrice; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
