package com.app.ecommerce.security.service;

import com.app.ecommerce.security.dto.AuthenticationRequest;
import com.app.ecommerce.security.dto.AuthenticationResponse;
import com.app.ecommerce.security.dto.Message;
import com.app.ecommerce.security.dto.RegisterRequest;
import com.app.ecommerce.security.entity.Token;
import com.app.ecommerce.security.entity.User;
import com.app.ecommerce.security.enums.Role;
import com.app.ecommerce.security.enums.TokenType;
import com.app.ecommerce.security.repository.TokenRepository;
import com.app.ecommerce.security.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public String register(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .active(false)
                .timeRegisterToken(LocalDateTime.now())
                .registerToken(RandomStringUtils.randomAlphabetic(30))
                .build();
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser, jwtToken);
        AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
        return user.getRegisterToken();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        System.out.println(user.isActive());
        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public boolean existsByEmail(String email){
        return repository.existsByEmail(email);
    }


    public ResponseEntity verifyRegister(User user) {
        if(user.isActive()){
            return new ResponseEntity(new Message("account has already active"), HttpStatus.BAD_REQUEST);
        }
        if(Duration.between(user.getTimeRegisterToken(), LocalDateTime.now()).toMinutes()>5){
            return new ResponseEntity(new Message("code time out"), HttpStatus.BAD_REQUEST);
        }

        user.setActive(true);
        user.setRegisterToken(null);
        repository.save(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity updateResetPasswordToken(String token, String email) throws ChangeSetPersister.NotFoundException {
        var user = repository.findByEmail(email).orElseThrow();
        if (user != null) {
            user.setResetPasswordToken(token);
            repository.save(user);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(new Message("email has not existed"), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity updatePassword(User user, String newPassword) {
        if(newPassword==null || newPassword==""){
            return new ResponseEntity(new Message("password is required"), HttpStatus.BAD_REQUEST);
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        user.setResetPasswordToken(null);
        repository.save(user);
        return new ResponseEntity(new Message("password updated"), HttpStatus.OK);
    }

    public User getByResetPasswordToken(String token) {
        return repository.findByResetPasswordToken(token);
    }

    public User getByRegisterToken(String token) {
        return repository.findByRegisterToken(token);
    }

    public void deleteById(int id){
        repository.deleteById(id);
    }
}
