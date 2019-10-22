package com.example.finalproject.carChargingStation;

import java.io.Serializable;

public class ChargingStationObject implements Serializable {

    private long id;
    private String title;
    private double latitude;
    private double longitude;
    private String phone;

    public ChargingStationObject(){}

    public ChargingStationObject(long id, String title, double latitude, double longitude, String phone) {
        this.id = id;
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phone = phone;
    }
    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getPhone() {
        return phone;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
