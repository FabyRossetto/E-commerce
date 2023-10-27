package com.app.ecommerce.security.controller;

import com.app.ecommerce.security.dto.*;
import com.app.ecommerce.security.entity.User;
import com.app.ecommerce.security.service.AuthService;
import com.app.ecommerce.security.service.MailService;
import com.app.ecommerce.security.utils.TemplateSendMail;
import com.app.ecommerce.security.utils.Utility;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
@CrossOrigin(exposedHeaders = "Access-Control-Allow-Origin")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService service;
    private final MailService senderService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody @Valid RegisterRequest request,
            HttpServletRequest urlRequest
    ) {
        System.out.println(urlRequest);
        if (StringUtils.isBlank(request.getEmail())) {
            return new ResponseEntity(new Message("email is required"), HttpStatus.BAD_REQUEST);
        }
        if (StringUtils.isBlank(request.getPassword())) {
            return new ResponseEntity(new Message("password is required"), HttpStatus.BAD_REQUEST);
        }
        if (StringUtils.isBlank(request.getFirstname())) {
            return new ResponseEntity(new Message("firstname is required"), HttpStatus.BAD_REQUEST);
        }
        if (StringUtils.isBlank(request.getLastname())) {
            return new ResponseEntity(new Message("lastname is required"), HttpStatus.BAD_REQUEST);
            }else {
            String token = service.register(request);

            String registerLink = Utility.getSiteURL(urlRequest) + "/auth/verify?token=" + token;

            String subject = "Here's the link to register";

            String content = TemplateSendMail.getContent(registerLink, "Confirm Account", "First, you need to confirm your account.");

            senderService.sendEmail(request.getEmail(), subject, content);
            return new ResponseEntity(new Message("Register success. Please check email verify!"), HttpStatus.OK);
        }
    }

    @GetMapping("/verify")
    public String verifyUser(HttpServletRequest request){
        String token = request.getParameter("token");
        User user = service.getByRegisterToken(token);

        if(user.isActive()){
            return TemplateSendMail.getError("Account has already active!!!", Utility.getSiteURL(request) + "/", "Ecommerce");
        }
        if(Duration.between(user.getTimeRegisterToken(), LocalDateTime.now()).toMinutes()>5){
            service.deleteById(user.getId());
            return TemplateSendMail.getError("Authentication timeout. Please register again!", Utility.getSiteURL(request) + "/auth/register", "Register");
        }
        if (user == null) {
            return TemplateSendMail.getError("Token wrong!!!", Utility.getSiteURL(request) + "/register", "Ecommerce Registration");
        }

        service.verifyRegister(user);
        return TemplateSendMail.getSuccess(Utility.getSiteURL(request) + "/login");
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<?> forgotPassword(HttpServletRequest request,
                                            @Valid
                                            @RequestBody ForgotPassRequest forgot){
        String token = RandomStringUtils.randomAlphabetic(30);

        String email = forgot.getEmail();

        try {
            service.updateResetPasswordToken(token, email);
            String resetPasswordLink = Utility.getSiteURL(request) + "auth/resetPassword?token=" + token;

            String subject = "Here's the link to reset your password";

            String content = TemplateSendMail.getContent(resetPasswordLink, "Reset Password", "You are asking for a password reset.");

            senderService.sendEmail(email, subject, content);
        } catch (RuntimeException | ChangeSetPersister.NotFoundException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(new Message("Token reset password send success. Please check email!"));
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestParam("token") String token,
                                           @RequestBody ResetPassRequest forgot) {

        User user = service.getByResetPasswordToken(token);
        String password = forgot.getPassword();

        if (user == null) {
            return ResponseEntity.ok(new Message("wrong token"));
        }
        service.updatePassword(user, password);
        return ResponseEntity.ok(new Message("Reset password success"));
    }


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        try {
            if (StringUtils.isBlank(request.getEmail())) {
                return new ResponseEntity(new Message("email is required"), HttpStatus.BAD_REQUEST);
            }
            if (StringUtils.isBlank(request.getPassword())) {
                return new ResponseEntity(new Message("password is required"), HttpStatus.BAD_REQUEST);
            }
            else {
                return ResponseEntity.ok(service.authenticate(request));
            }
        }
        catch (BadCredentialsException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorRegisterResponse handlePasswordValidationException(MethodArgumentNotValidException e) {

        return ErrorRegisterResponse.builder()
                .message(String.join(",", e.getBindingResult().getFieldError().getDefaultMessage()))
                .build();

    }
}