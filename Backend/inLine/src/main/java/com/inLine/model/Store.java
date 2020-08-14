package com.inLine.model;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    final private List<Hours> hours = new ArrayList<>(7);

    @ManyToMany(targetEntity = Account.class, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "ownership", joinColumns = @JoinColumn(name = "store_id"), inverseJoinColumns = @JoinColumn(name = "account_id"))
    @JsonIgnore
    private List<Account> managers = new ArrayList<>();


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
        this.hours.addAll(hours);
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
        Collections.sort(this.hours);
        return this.hours;
    }

    public void changeHours(Hours h) {
        Collections.sort(this.hours);
        this.hours.set(h.getDayEnum().ordinal(), h);
    }

    @JsonIgnore
    public List<Account> getManagers() {
        return managers;
    }

    public void setManagers(List<Account> managers) {
        this.managers = managers;
    }

    public void addManager(Account admin) {
        managers.add(admin);
    }

    public void removeManager(Account admin) {
        managers.remove(admin);
    }
}
