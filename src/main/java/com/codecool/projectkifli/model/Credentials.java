package com.codecool.projectkifli.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Credentials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String phone;
    private String country;
    private String state;
    private String city;
    private String street;

    public Credentials() {
    }

    public Credentials(Integer userId, String phone, String country, String state, String city, String street) {
        this.userId = userId;
        this.phone = phone;
        this.country = country;
        this.state = state;
        this.city = city;
        this.street = street;
    }

    public Integer getUser_id() {
        return userId;
    }

    public void setUser_id(Integer userId) {
        this.userId = userId;
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
