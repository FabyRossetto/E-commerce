package com.app.ecommerce.security.dto;

import com.app.ecommerce.security.entity.Token;
import com.app.ecommerce.security.enums.Role;
import com.app.ecommerce.security.validator.Password;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class UserDto {
    private String firstname;
    private String lastname;

    @Email
    private String email;
    @Password
    @NonNull
    @NotBlank(message = "New password is mandatory")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;
}
