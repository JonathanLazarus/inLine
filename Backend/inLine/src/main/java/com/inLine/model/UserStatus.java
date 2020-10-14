package com.inLine.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.inLine.model.serialization.CustomAccountSerializer;
import com.inLine.model.serialization.CustomStoreSerializer;
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
public class UserStatus implements Comparable<UserStatus>{

    @Schema(hidden = true)
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    //@NotEmpty
    @Column(name = "user_state", columnDefinition = "enum('IDLE', 'WAITING', 'SHOPPING')")
    private String state;

    //@NotNull
    @Column(name = "time_stamp")
    private Timestamp datetime;

    /*TODO: when a Status is deleted, you dont want the associated user to be deleted as well
    UserStatus is a child of Account
    the child should have the JoinColumn attached to it.
     */
    @JsonSerialize(using = CustomAccountSerializer.class)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) //makes UserStatus the owning side
    private Account user;

    @JsonSerialize(using = CustomStoreSerializer.class)
    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false) //inverse side (Store is the owning side)
    private Store store;

    @Schema(description = "Indicates whether this status is currently active or not, aka history")
    @JsonIgnore
    private boolean active;

    public enum Status {
        IDLE, WAITING, SHOPPING
    }

    public UserStatus(@JsonProperty("state") Status state,
                      @JsonProperty("user") Account user,
                      @JsonProperty("store") Store store)
    {
        this.state = state.toString();
        this.store = store;
        this.user = user;
        this.datetime = new Timestamp(System.currentTimeMillis());
        this.active = true;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = new Timestamp(System.currentTimeMillis());
    }

    public void makeInactive() {
        this.active = false;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof UserStatus)) return false;
        if (this.id == ((UserStatus) obj).id) {
            return this.datetime.equals(((UserStatus) obj).datetime);
        }
        return false;
    }

    @Override
    public int compareTo(UserStatus us) {
        if(us == null) return -1;
        return this.datetime.compareTo(us.datetime);
    }
}
