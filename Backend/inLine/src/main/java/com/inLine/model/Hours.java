package com.inLine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.sql.Time;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "store_hours")
public class Hours implements Comparable<Hours>{

    @Id
    @GeneratedValue
    @JsonIgnore
    @Column(name = "hours_id")
    private int id;

    @Schema(description = "Day of the week.",
            allowableValues = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"},
            name = "day",
            required = true)
    @NotBlank
    @Column(name = "day_of_the_week", columnDefinition = "enum('Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday')")
    private String day;

    @Schema(description = "Store opening time.",
            nullable = true,
            required = true,
            type = "string",
            pattern = "([0-1]?[0-9]|2[0-3]):([0-5]?[0-9]):([0-5]?[0-9])",
            example = "08:30:00",
            name = "open")
    @Nullable
    @Column(name = "time_open")
    private Time open;

    @Schema(description = "Store closing time.",
            nullable = true,
            required = true,
            type = "string",
            pattern = "([0-1]?[0-9]|2[0-3]):([0-5]?[0-9]):([0-5]?[0-9])",
            example = "22:15:00",
            name = "close")
    @Nullable
    @Column(name = "time_close")
    private Time close;

    protected enum DayEnum {
        Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday
    }

    //Constructor via Strings - uses 00:00:00 format. See Time javadocs.
    public Hours(@JsonProperty("day") DayEnum day,
                 @Pattern(regexp = "([0-1]?[0-9]|2[0-3]):([0-5]?[0-9]):([0-5]?[0-9])", message = "please provide pattern of \"HH:MM:SS\"")
                 @JsonProperty("open") String open,
                 @Pattern(regexp = "([0-1]?[0-9]|2[0-3]):([0-5]?[0-9]):([0-5]?[0-9])", message = "please provide pattern of \"HH:MM:SS\"")
                 @JsonProperty("close") String close)
    {
        this.day = day.toString();
        this.open = (open == null) ? null : Time.valueOf(open);
        this.close = (close == null) ? null : Time.valueOf(close);
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
