package com.inLine.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inLine.dao.LocationDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

@Entity
@Table(name = "stores")
public class Store {

    @Id
    @GeneratedValue
    @Column(name = "store_id")
    private int id;

    @Column(name = "store_name")
    private String name;

    @Column(name = "location_id")
    private int locationId;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private long phoneNumber;

    @Column(name = "capacity")
    private int capacity;

    public Store(){}

    public Store(@JsonProperty("name") String name,
                 //@JsonProperty("location_id") int locationId,
                 @JsonProperty("email") String email,
                 @JsonProperty("phone") long phoneNumber,
                 @JsonProperty("capacity") int capacity)
    {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.capacity = capacity;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) { this.locationId = locationId; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }
}
