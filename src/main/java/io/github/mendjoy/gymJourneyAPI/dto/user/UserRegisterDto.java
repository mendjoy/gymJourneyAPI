package io.github.mendjoy.gymJourneyAPI.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record UserRegisterDto(

        Integer id,

        @NotBlank(message = "O email do usuário deve ser informado!")
        @Email(message = "O email deve ser válido!")
        String email,

        @NotBlank(message = "O nome deve ser informado!")
        String name,

        String phone,

        LocalDate birthDate,

        @NotBlank(message = "A senha deve ser informada!")
        String password

) {}