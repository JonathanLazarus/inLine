package com.inLine.model;

import com.fasterxml.jackson.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.Target;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;

@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account implements UserDetails {
    @Id @GeneratedValue
    @Column(name = "account_id")
    @Getter @Setter
    @Schema(hidden = true)
    private int id;


    @Schema(description = "Account type.",
            required = true,
            allowableValues = {"ADMIN", "USER"},
            example = "USER",
            name = "account_type")
    @NotBlank @Getter @Setter
    @Column(name = "account_access_level", columnDefinition = "enum('USER', 'ADMIN')")
    private String level;

    @Schema(description = "Email of account holder, doubles as username.",
            example = "dylan123@email.com",
            name = "email",
            required = true)
    @NotBlank @Email
    @Getter @Setter
    @Column(name = "email")
    private String email;

    @Schema(description = "Phone number",
            type = "long",
            example = "3125098776",
            name = "phone",
            required = true)
    @Digits(integer = 10, fraction = 0)
    @Getter @Setter
    @Positive
    @Column(name = "phone_number")
    private long phoneNumber;

    @Schema(description = "First name of the account holder.",
            example = "Dylan",
            name = "first_name",
            required = true)
    @NotBlank
    @Getter @Setter
    @Column(name = "first_name")
    private String firstName;

    @Schema(description = "Last name of account holder.",
            example = "Pratt",
            name = "last_name",
            required = true)
    @NotBlank
    @Getter @Setter
    @Column(name = "last_name")
    private String lastName;

    @Schema(description = "Account password.",
            example = "Pa$$word123",
            name = "password",
            required = true)
    @NotBlank
    @Setter
    @Column(name = "account_password")
    private String password;

    @JsonIgnore
    @Getter @Setter
    @ManyToMany(mappedBy = "managers", fetch = FetchType.LAZY)
    private List<Store> managedStores = new ArrayList<>();

    //TODO: when an account is deleted, you also want to remove its associated UserStatuses --> Account is parent of UserStatus
    @JsonIgnore
    @Getter @Setter
    @OneToMany(mappedBy = "user",
               cascade = CascadeType.ALL,
               orphanRemoval = true) //Owning side
    @Where(clause = "NOT active")
    private List<UserStatus> history = new ArrayList<>();

    @OneToMany(mappedBy = "user",
              cascade = CascadeType.ALL,
              orphanRemoval = true)
    @Where(clause = "active")
    @JsonIgnore
    private List<UserStatus> status;

    private enum Access {
        ADMIN, USER
    }

    public Account(@JsonProperty("account_type") Access level,
                   @JsonProperty("email") String email,
                   @JsonProperty("phone") long phoneNumber,
                   @JsonProperty("first_name") String firstName,
                   @JsonProperty("last_name") String lastName,
                   @JsonProperty("password")  String password)
    {
        this.level = level.toString();
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        Set<SimpleGrantedAuthority> permissions = new HashSet<>();
        permissions.add(new SimpleGrantedAuthority("ROLE_" + level.toUpperCase()));
        return permissions;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getGrantedAuthorities();
    }

    @JsonIgnore
    @Override
    public String getPassword() { return password; }

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

    public void addStore(Store s) {
        managedStores.add(s);
    }

    public void removeStore(Store s){
        managedStores.remove(s);
    }

    public void addUserStateToHistory(UserStatus status) {
        this.history.add(status);
    }

    @JsonIgnore
    public UserStatus getStatus() {
        return this.status.isEmpty() ? null : this.status.get(0);
    }

    public void setStatus(UserStatus us) {
        this.status.add(0, us);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null) return false;
      if (!(o instanceof UserStatus)) return false;
      return this.id == ((UserStatus) o).getId();
    }

    @Override
    public int hashCode() {
      return this.id;
    }
}
