package com.delkappa.manos.myapplication;

import java.io.Serializable;

public class Event implements Serializable {

    int id;
    String name;
    double latitude;
    double longitude;
    String type;

    public Event() {

    }

    public Event(int id, String name, double latitude, double longitude, String type) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
    }

    // ID
    public int getID() {
        return this.id;
    }

    public void setID(int id) {
        this.id = id;
    }

    // Name
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Latitude
    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    // Longitude
    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    // Type
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
