package com.inLine.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "store_hours")
public class Hours {

    @Id
    @GeneratedValue
    @NonNull
    @Column(name = "hours_id")
    private int id;

    @Column(name = "store_id")
    private int storeId;

    @Column(name = "day_of_the_week")
    private String day;

    @Column(name = "time_open")
    private Time open;

    @Column(name = "time_close")
    private Time close;

    public Hours(@JsonProperty("store_id") int storeId,
                 @JsonProperty("day") String day,
                 @JsonProperty("open") Time open,
                 @JsonProperty("close") Time close)
    {
        this.storeId = storeId;
        this.day = day;
        this.open = open;
        this.close = close;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Time getOpen() {
        return open;
    }

    public void setOpen(Time open) {
        this.open = open;
    }

    public Time getClose() {
        return close;
    }

    public void setClose(Time close) {
        this.close = close;
    }
}
