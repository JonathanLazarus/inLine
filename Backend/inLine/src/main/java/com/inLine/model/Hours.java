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

    //Tells JPA that there are many "hour" classes (one for each day of the week) for each store.
    //@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    //@JoinColumn(name = "")
    //private Store store;

    @Column(name = "day_of_the_week")
    private String day;

    @Column(name = "time_open")
    private Time open;

    @Column(name = "time_close")
    private Time close;

    public Hours() {
    }

    /*
    public Hours(@JsonProperty("day") String day,
                 @JsonProperty("open_hour") int openingHour,
                 @JsonProperty("open_minute") int openingMinute,
                 @JsonProperty("open_second") int openingSecond,
                 @JsonProperty("close_hour") int closingHour,
                 @JsonProperty("close_minute") int closingMinute,
                 @JsonProperty("close_second") int closingSecond)
    {

        this.day = day;
        this.open = new Time(openingHour, openingMinute, openingSecond);
        this.close = new Time(closingHour, closingMinute, closingSecond);
    }
    */

    //Constructor via Strings - uses 00:00:00 format. See Time javadocs.
    public Hours(@JsonProperty("day") String day,
                 @JsonProperty("open") String open,
                 @JsonProperty("close") String close)
    {
        this.day = day;
        this.open = Time.valueOf(open);
        this.close = Time.valueOf(close);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
