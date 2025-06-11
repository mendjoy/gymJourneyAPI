package io.github.mendjoy.gymJourneyAPI.dto.user;

import jakarta.validation.constraints.NotBlank;

public class UserPasswordUpdateDTO {

    @NotBlank(message = "A senha atual é obrigatória.")
    private String currentPassword;

    @NotBlank(message = "A nova senha é obrigatória.")
    private String newPassword;

    @NotBlank(message = "A confirmação da nova senha é obrigatória.")
    private String confirmNewPassword;

    public UserPasswordUpdateDTO() {
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }
}
