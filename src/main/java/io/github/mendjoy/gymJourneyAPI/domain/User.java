package io.github.mendjoy.gymJourneyAPI.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "gym_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String name;
    private String phone;
    @Column(name = "birth_date")
    private LocalDate birthDate;

    public User() {
    }

    public User(String email, String name, String phone, LocalDate birthDate){
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.birthDate = birthDate;
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
}
