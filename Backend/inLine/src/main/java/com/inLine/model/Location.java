package com.inLine.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue
    @NonNull
    @Column(name = "location_id")
    private int id;

    @Column(name = "street_name")
    private String streetName;

    @Column(name = "house_number")
    private int houseNumber;

    @Column(name = "apt_number")
    private int aptNumber;

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
                    @JsonProperty("apt") int aptNumber,
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getAptNumber() {
        return aptNumber;
    }

    public void setAptNumber(int aptNumber) {
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
