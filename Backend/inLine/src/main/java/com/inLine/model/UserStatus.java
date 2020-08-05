package com.inLine.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "user_state")
public class UserStatus {

    @Id
    @GeneratedValue
    @NonNull
    @Column(name = "id")
    private int id;

    private enum Status {
        IDLE, WAITING, SHOPPING;
    }

    @Column(name = "user_state")
    private Status state;

    @Column(name = "store_id")
    private int storeId;

    @Column(name = "time_stamp")
    private Timestamp datetime;

    public UserStatus(//@JsonProperty("id") int id,
                      @JsonProperty("state") Status state,
                      @JsonProperty("store_id") int storeId,
                      @JsonProperty("datetime") Timestamp datetime)
    {
        //this.id = id;
        this.state = state;
        this.storeId = storeId;
        this.datetime = datetime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getState() {
        return state;
    }

    public void setState(Status state) {
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
