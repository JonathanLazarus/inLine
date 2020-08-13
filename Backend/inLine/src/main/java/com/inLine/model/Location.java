package com.inLine.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Embeddable
public class Location {

    @Column(name = "street_name")
    private String streetName;
    @Column(name = "house_number")
    private int houseNumber;
    @Column(name = "apt_number")
    private Integer aptNumber;
    @Column(name = "country")
    private String country;
    @Column(name = "city")
    private String city;
    @Column(name = "state")
    private String state;
    @Column(name = "zip_code")
    private int zip;

    public Location(@JsonProperty("street") String streetName,
                    @JsonProperty("house_number") int houseNumber,
                    @JsonProperty("apt") Integer aptNumber,
                    @JsonProperty("country") String country,
                    @JsonProperty("city") String city,
                    @JsonProperty("state") String state,
                    @JsonProperty("zip") int zip)
    {
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.aptNumber = aptNumber;
        this.country = country;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public Location() {
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public Integer getAptNumber() {
        return aptNumber;
    }

    public void setAptNumber(Integer aptNumber) {
        this.aptNumber = aptNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }
}
