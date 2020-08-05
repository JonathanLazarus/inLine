package com.inLine.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "ownership")
public class Ownership {

    @Id
    @GeneratedValue
    @Column(name = "ownership_id")
    private int id;

    @Column(name = "account_id")
    private int accountId;

    @Column(name = "store_id")
    private int storeId;

    public Ownership(//@JsonProperty("id") int id,
                     @JsonProperty("account_id") int accountId,
                     @JsonProperty("store_id") int storeId)
    {
        //this.id = id;
        this.accountId = accountId;
        this.storeId = storeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }
}