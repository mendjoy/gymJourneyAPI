package io.github.mendjoy.gymJourneyAPI.domain;

import io.github.mendjoy.gymJourneyAPI.domain.enums.UserRole;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "gym_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String email;

    private String name;

    private String phone;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole userRole = UserRole.USER;

    private Boolean verified = false;

    private String token;

    @Column(name = "expiration_token")
    private LocalDateTime expirationToken;

    private Boolean ativo;

    public User() {
    }

    public User(String email, String name, String phone, LocalDate birthDate, String password, UserRole userRole, Boolean verified, String token, LocalDateTime expirationToken) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.birthDate = birthDate;
        this.password = password;
        this.userRole = userRole;
        this.verified = verified;
        this.token = token;
        this.expirationToken = expirationToken;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpirationToken() {
        return expirationToken;
    }

    public void setExpirationToken(LocalDateTime expirationToken) {
        this.expirationToken = expirationToken;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ROLE_" + this.userRole.name());
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return email;
    }

}
