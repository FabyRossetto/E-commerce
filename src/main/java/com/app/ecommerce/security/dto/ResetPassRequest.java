package com.app.ecommerce.security.dto;

import com.app.ecommerce.security.validator.Password;
import lombok.Data;

@Data
public class ResetPassRequest {
    @Password
    private String password;
}
