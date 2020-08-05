package com.inLine.model.containter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inLine.model.Location;
import com.inLine.model.Store;

public class StoreLocation {

    private Store store;
    private Location location;

    public StoreLocation(
            //Store properties:
            @JsonProperty("name") String name,
            @JsonProperty("email") String email,
            @JsonProperty("phone") long phoneNumber,
            @JsonProperty("capacity") int capacity,

            //location properties:
            @JsonProperty("street") String streetName,
            @JsonProperty("house_number") int houseNumber,
            @JsonProperty("apt") int aptNumber,
            @JsonProperty("country") String country,
            @JsonProperty("city") String city,
            @JsonProperty("state") String state,
            @JsonProperty("zip") int zip)
    {
        this.store = new Store(name, email, phoneNumber, capacity);
        this.location = new Location(streetName, houseNumber, aptNumber, country, city, state, zip);
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
