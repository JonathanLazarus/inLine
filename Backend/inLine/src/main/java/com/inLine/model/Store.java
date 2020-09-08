package com.inLine.model;

import com.fasterxml.jackson.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "stores")
public class Store {

    @Schema(hidden = true)
    @Id
    @GeneratedValue
    @Column(name = "store_id")
    private int id;

    @Schema(description = "Name of the store.",
            example = "Target",
            name = "name",
            required = true)
    @NotBlank
    @Column(name = "store_name")
    private String name;

    @Schema(description = "Email of the store.",
            example = "targetNJ@target.com",
            name = "email")
    @Email
    @Column(name = "email")
    private String email;

    @Schema(description = "Phone number of the store.",
            example = "8003095557",
            name = "phone",
            required = true)
    @Digits(integer = 10, fraction = 0)
    @Column(name = "phone_number")
    private long phoneNumber;

    @Schema(description = "Capacity of the store.",
            example = "150",
            name = "capacity",
            required = true)
    @Positive
    @Column(name = "capacity")
    private int capacity;

    @Schema(description = "Address of the store.",
            name = "location",
            required = true)
    @Valid
    @Embedded
    private Location location;

    //@OneToMany - Tells JPA that for each "Store" there are many "hours."
    //mappedBy - tells JPA which hours belong to this store by getting the store_id that lives in the "store' column within the "hours" table.
    @NotNull
    @Size(min = 7, max = 7)
    @OneToMany(targetEntity = Hours.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "store_id", referencedColumnName = "store_id")
    final private List<@Valid Hours> hours = new ArrayList<>(7);

    @Size(min = 1)
    @ManyToMany(targetEntity = Account.class, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "ownership", joinColumns = @JoinColumn(name = "store_id"), inverseJoinColumns = @JoinColumn(name = "account_id"))
    @JsonIgnore
    private List<@Valid Account> managers = new ArrayList<>();

    public Store(@JsonProperty("name") String name,
                 @JsonProperty("email") String email,
                 @JsonProperty("phone") long phoneNumber,
                 @JsonProperty("capacity") int capacity,
                 @JsonProperty("location") Location location,
                 @JsonProperty("hours") @Size(min = 7, max = 7) List<Hours> hours)
    {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.capacity = capacity;
        this.location = location;
        this.hours.addAll(hours);
    }

    public List<Hours> getHours() {
        Collections.sort(this.hours);
        return this.hours;
    }

    public void changeHours(Hours h) {
        Collections.sort(this.hours);
        this.hours.set(h.getDayEnum().ordinal(), h);
    }

    @JsonIgnore
    public List<Account> getManagers() {
        return managers;
    }

    public void addManager(Account admin) {
        managers.add(admin);
    }

    public void removeManager(Account admin) {
        managers.remove(admin);
    }
}
