package com.inLine.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "stores")
public class Store {

    @Id
    @GeneratedValue
    @Column(name = "store_id")
    private int id;
    @Column(name = "store_name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "phone_number")
    private long phoneNumber;
    @Column(name = "capacity")
    private int capacity;
    @Embedded
    private Location location;
    //@OneToMany - Tells JPA that for each "Store" there are many "hours."
    //mappedBy - tells JPA which hours belong to this store by getting the store_id that lives in the "store' column within the "hours" table.
    @OneToMany(targetEntity = Hours.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "store_id", referencedColumnName = "store_id")
    private List<Hours> hours;

    @ManyToMany(targetEntity = Account.class, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "ownership", joinColumns = @JoinColumn(name = "store_id"), inverseJoinColumns = @JoinColumn(name = "account_id"))
    //@JsonBackReference
    private List<Account> managers;


    public Store(){ }

    public Store(@JsonProperty("name") String name,
                 @JsonProperty("email") String email,
                 @JsonProperty("phone") long phoneNumber,
                 @JsonProperty("capacity") int capacity,
                 @JsonProperty("location") Location location,
                 @JsonProperty("hours") List<Hours> hours)
    {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.capacity = capacity;
        this.location = location;
        this.hours = hours;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) { this.location = location; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public List<Hours> getHours() {
        return hours;
    }

    public void setHours(ArrayList<Hours> hours) {
        this.hours = hours;
    }

    public List<Account> getManagers() {
        return managers;
    }

    public void setManagers(List<Account> managers) {
        this.managers = managers;
    }
}
