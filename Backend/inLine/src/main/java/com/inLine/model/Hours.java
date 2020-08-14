package com.inLine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "store_hours")
public class Hours implements Comparable<Hours>{

    @Id
    @GeneratedValue
    @Column(name = "hours_id")
    private int id;
    @Column(name = "day_of_the_week", columnDefinition = "enum('Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday')")
    private String day;
    @Column(name = "time_open")
    private Time open;
    @Column(name = "time_close")
    private Time close;

    protected enum DayEnum {
        Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday
    }

    public Hours() {
    }

    //Constructor via Strings - uses 00:00:00 format. See Time javadocs.
    public Hours(@JsonProperty("day") DayEnum day,
                 @JsonProperty("open") String open,
                 @JsonProperty("close") String close)
    {
        this.day = day.toString();
        this.open = (open == null) ? null : Time.valueOf(open);
        this.close = (close == null) ? null : Time.valueOf(close);
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

    @JsonIgnore
    protected DayEnum getDayEnum() {
        return DayEnum.valueOf(this.day);
    }

    @Override
    public int compareTo(Hours h) {
        return DayEnum.valueOf(this.day).compareTo(DayEnum.valueOf(h.day));
    }
}
