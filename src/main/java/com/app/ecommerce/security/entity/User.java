package com.app.ecommerce.security.entity;

import com.app.ecommerce.entity.Address;
import com.app.ecommerce.security.enums.Role;
import com.app.ecommerce.security.validator.Password;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.lang.Nullable;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Integer id;
    @NonNull
    private String firstname;
    @NonNull
    private String lastname;

    @NonNull
    //@Length(min = 5, max = 50, message = "Min 5 characters and max 50")
    private String username;

    @Email
    @NonNull
    private String email;

    @NonNull
    @NotBlank(message = "New password is mandatory")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;
    
    @Nullable
    @OneToMany(fetch = FetchType.LAZY , cascade = {CascadeType.PERSIST , CascadeType.REMOVE ,CascadeType.MERGE})
    @JsonManagedReference
    private List<Address> addresses;


    private LocalDateTime timeRegisterToken;
    private String registerToken;

    private boolean active;

    private String resetPasswordToken;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }


    public String getEmail() {
        return email;
    }

    @Override
    public String getUsername() {
        return username;
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
    
}