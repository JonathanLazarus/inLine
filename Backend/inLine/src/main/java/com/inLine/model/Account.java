package com.inLine.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "accounts")
public class Account implements UserDetails {

    @Id
    @GeneratedValue
    @Column(name = "account_id")
    private int id;
    @Column(name = "account_access_level")
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

    @ManyToMany(mappedBy = "managers", fetch = FetchType.LAZY)
    //@JsonManagedReference
    private List<Store> managedStores;

    public Account(@JsonProperty("account_type") String level,
                   @JsonProperty("email") String email,
                   @JsonProperty("phone") long phoneNumber,
                   @JsonProperty("first_name") String firstName,
                   @JsonProperty("last_name") String lastName,
                   @JsonProperty("password") String password) {
        this.level = level;
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
        return level.toString();
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
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getGrantedAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

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
}
