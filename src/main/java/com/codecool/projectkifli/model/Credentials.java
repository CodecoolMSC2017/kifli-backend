package com.codecool.projectkifli.model;

import javax.persistence.Entity;

@Entity
public class Credentials {

    private Integer user_id;
    private String phone;
    private String country;
    private String state;
    private String city;
    private String street;

    public Credentials(Integer user_id, String phone, String country, String state, String city, String street) {
        this.user_id = user_id;
        this.phone = phone;
        this.country = country;
        this.state = state;
        this.city = city;
        this.street = street;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
