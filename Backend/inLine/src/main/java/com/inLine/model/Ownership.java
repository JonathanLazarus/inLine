package com.inLine.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "ownership")
public class Ownership {

    @Id
    @GeneratedValue
    @Column(name = "ownership_id")
    private int id;
    @NotNull
    @Positive
    @Column(name = "account_id")
    private int accountId;
    @NotNull
    @Positive
    @Column(name = "store_id")
    private int storeId;

    public Ownership(@JsonProperty("account_id") int accountId,
                     @JsonProperty("store_id") int storeId)
    {
        this.accountId = accountId;
        this.storeId = storeId;
    }
}