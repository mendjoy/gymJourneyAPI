package io.github.mendjoy.gymJourneyAPI.dto.user;


import io.github.mendjoy.gymJourneyAPI.dto.role.RoleDto;

import java.time.LocalDate;
import java.util.List;

public class UserDto {

    private Long id;
    private String email;
    private String name;
    private String phone;
    private LocalDate birthDate;
    private List<RoleDto> roles;

    public UserDto() {
    }

    public UserDto(Long id, String email, String name, String phone, LocalDate birthDate, List<RoleDto> roles) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.birthDate = birthDate;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDto> roles) {
        this.roles = roles;
    }
}
