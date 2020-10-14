package com.inLine.model;

import com.fasterxml.jackson.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

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
    @ManyToMany(targetEntity = Account.class,
                cascade = CascadeType.PERSIST,
                fetch = FetchType.LAZY)
    @JoinTable(name = "ownership",
               joinColumns = @JoinColumn(name = "store_id"),
               inverseJoinColumns = @JoinColumn(name = "account_id"))
    @JsonIgnore
    private List<@Valid Account> managers = new ArrayList<>();

    //TODO: Validate
    //TODO: when a Store gets deleted, you want its queue to be deleted as well. --> Stare is parent of UserStatus
    @Schema(description = "List of all queued \"Waiting\" user states to this Store, aka, the queue")
    @OneToMany(mappedBy = "store",
               cascade = CascadeType.PERSIST,
               orphanRemoval = true) //Owning side
    @Where(clause = "user_state = 'WAITING' AND active")
    @JsonIgnore
    private List<UserStatus> queue = new ArrayList<>();

    //TODO: Validate
    //TODO: when a Store gets deleted, you want its shoppingQueue to be deleted as well. --> Store is parent of UserStatus
    @Schema(description = "List of all queued \"Shopping\" user states to this Store, aka, the shoppers")
    @OneToMany(mappedBy = "store",
               cascade = CascadeType.PERSIST,
               orphanRemoval = true) //Owning side
    @Where(clause = "user_state = 'SHOPPING' AND active")
    @JsonIgnore
    private List<UserStatus> shoppers = new ArrayList<>();

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

    public void addUserStatus(UserStatus us) {
        this.queue.add(us);
    }

    public UserStatus enqueue(Account user) {
        try{
            if(!user.getStatus().getState().equals("IDLE")) return null;
        } catch (NullPointerException e) {}
        UserStatus us = new UserStatus(UserStatus.Status.WAITING, user, this);
        //user.addUserStateToHistory(us);
        this.queue.add(us);
        return us;
    }

    public List<UserStatus> dequeue(Account user) {
        List<UserStatus> statusList = new ArrayList<>();
        if(!validUser(user, "WAITING")) return statusList;
        UserStatus us = user.getStatus();
        us.makeInactive();
        statusList.add(us);
        us = new UserStatus(UserStatus.Status.IDLE, user, this);
        statusList.add(us);
        return statusList;
    }

    @JsonIgnore
    public Account getNextUserOnQueue() {
        return this.queue.get(0).getUser();
    }

    public List<UserStatus> enShop(Account user) {
        List<UserStatus> statusList = new ArrayList<>();
        if(!validUser(user, "WAITING")) return statusList;
        if(!user.equals(getNextUserOnQueue())) return statusList;
        UserStatus us = user.getStatus();
        us.makeInactive();
        statusList.add(us);
        us = new UserStatus(UserStatus.Status.SHOPPING, user, this);
        statusList.add(us);
        return statusList;
    }

    public UserStatus deShop(Account user) {
        if(!validUser(user, "SHOPPING")) return null;
        if(this.shoppers.remove(user.getStatus())) {
            UserStatus us = new UserStatus(UserStatus.Status.IDLE, user, this);
            user.addUserStateToHistory(us);
            this.shoppers.add(us);
            return us;
        }
        return null;
    }

    private boolean validUser(Account user, String state) {
        if (user == null) return false;
        if(user.getStatus() == null) return true;
        return user.getStatus().getState().equals(state) && user.getStatus().getStore().equals(this);
    }

    @JsonProperty("queue_size")
    public int getQueueSize() {
        return this.queue.size();
    }

    @JsonProperty("amount_of_shoppers")
    public int getShoppersSize() {
        return this.shoppers.size();
    }

    public int getPlaceInQueue(Account user) {
        if(!validUser(user, "WAITING")) return -1;
        return this.queue.indexOf(user.getStatus()) + 1;
    }

    @Override
    public int hashCode() {
        return this.id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Store)) return false;
        return this.id == ((Store) obj).id;
    }
}


