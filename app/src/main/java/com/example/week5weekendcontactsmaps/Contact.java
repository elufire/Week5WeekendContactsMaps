package com.example.week5weekendcontactsmaps;

import java.util.List;

public class Contact {
    private String name;
    private String number;
    private String location;

    public Contact(String name, String number, String location) {
        this.name = name;
        this.number = number;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
