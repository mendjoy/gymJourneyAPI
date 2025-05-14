package io.github.mendjoy.gymJourneyAPI.entity.user;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String name;
    private String phone;

    @Column(name = "birth_date")
    private LocalDate birthDate;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public User() {
    }

    public User(String email, String name, String phone, LocalDate birthDate, String password, UserRole role ){
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.birthDate = birthDate;
        this.password = password;
        this.role = role;
    }

    public User(Integer id, String email, String name, String password, UserRole role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public void setBirth_date(LocalDate birth_date) {
        this.birthDate = birthDate;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(role == UserRole.ADMIN){
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        }else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
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

    public void setUsername(String username) {
        this.email = username;
    }

}

