package io.github.mendjoy.gymJourneyAPI.domain;

import io.github.mendjoy.gymJourneyAPI.dto.user.UserDto;
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
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    private String name;

    private String phone;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
               joinColumns = @JoinColumn(name = "user_id"),
               inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    private Boolean verified = false;

    private String token;

    @Column(name = "expiration_token")
    private LocalDateTime expirationToken;

    @Column(nullable = false)
    private Boolean active;

    public User() {
    }

    public User(String email, String name, String phone, LocalDate birthDate, String password, List<Role> role, Boolean verified, String token, LocalDateTime expirationToken, Boolean active) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.birthDate = birthDate;
        this.password = password;
        this.roles = role;
        this.verified = verified;
        this.token = token;
        this.expirationToken = expirationToken;
        this.active = active;
    }

    public Long getId() {
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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
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

    public void update(UserDto userDto){
        if (userDto.name() != null && !userDto.name().isBlank()) {
            this.name = userDto.name();
        }

        if (userDto.phone() != null && !userDto.phone().isBlank()) {
            this.phone = userDto.phone();
        }

        if (userDto.birthDate() != null) {
            this.birthDate = userDto.birthDate();
        }
    }

}
