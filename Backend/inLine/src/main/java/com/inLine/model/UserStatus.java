package com.inLine.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "user_state")
public class UserStatus {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @Column(name = "user_state", columnDefinition = "enum('IDLE', 'WAITING', 'SHOPPING')")
    private String state;
    @Column(name = "store_id")
    private int storeId;
    @Column(name = "time_stamp")
    private Timestamp datetime;

    private enum Status {
        IDLE, WAITING, SHOPPING
    }

    public UserStatus(@JsonProperty("state") Status state,
                      @JsonProperty("store_id") int storeId)
    {
        this.state = state.toString();
        this.storeId = storeId;
        this.datetime = new Timestamp(System.currentTimeMillis());
    }

    public UserStatus() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }
}
