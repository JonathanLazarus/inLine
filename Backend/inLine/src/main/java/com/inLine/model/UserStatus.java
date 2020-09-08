package com.inLine.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "user_state")
public class UserStatus {

    @Schema(hidden = true)
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @NotEmpty
    @Column(name = "user_state", columnDefinition = "enum('IDLE', 'WAITING', 'SHOPPING')")
    private String state;

    @NotNull
    @Column(name = "store_id")
    private int storeId;

    @NotNull
    @Column(name = "time_stamp")
    private Timestamp datetime;

    @OneToOne(mappedBy = "status")
    private Account user;

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

    public void setDatetime(Timestamp datetime) {
        this.datetime = new Timestamp(System.currentTimeMillis());
    }
}
