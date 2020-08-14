package com.inLine.model;

import com.fasterxml.jackson.annotation.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "accounts")
public class Account implements UserDetails {
    @Id
    @GeneratedValue
    @Column(name = "account_id")
    private int id;
    @Column(name = "account_access_level", columnDefinition = "enum('USER', 'ADMIN')")
    private String level;
    @Column(name = "email")
    private String email;
    @Column(name = "phone_number")
    private long phoneNumber;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "account_password")
    private String password;
    @JsonIgnore
    @ManyToMany(mappedBy = "managers", fetch = FetchType.LAZY)
    private List<Store> managedStores = new ArrayList<>();

    private enum Access {
        ADMIN, USER
    }

    public Account(@JsonProperty("account_type") Access level,
                   @JsonProperty("email") String email,
                   @JsonProperty("phone") long phoneNumber,
                   @JsonProperty("first_name") String firstName,
                   @JsonProperty("last_name") String lastName,
                   @JsonProperty("password") String password) {
        this.level = level.toString();
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public Account() {
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        Set<SimpleGrantedAuthority> permissions = new HashSet<>();
        permissions.add(new SimpleGrantedAuthority("ROLE_" + level.toUpperCase()));
        return permissions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getGrantedAuthorities();
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }
    @JsonIgnore
    @Override
    public String getUsername() {
        return email;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setEncodedPassword(PasswordEncoder passwordEncoder) {
        this.password=passwordEncoder.encode(password);
    }

    public List<Store> getManagedStores() {
        return managedStores;
    }

    public void setManagedStores(List<Store> managedStores) {
        this.managedStores = managedStores;
    }

    public void addStore(Store s) {
        managedStores.add(s);
    }

    public void removeStore(Store s){
        managedStores.remove(s);
    }
}
