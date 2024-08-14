package project.project.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterDTO {
    @NotBlank(message = "Username is empty")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 symbols long")
    private String username;

    @NotBlank
    @Email(regexp = ".*@.*")
    private String email;

    @NotBlank
    @Size(min = 6, max = 30, message = "Password must be between 6 and 30 symbols long")
    private String password;

    @NotBlank
    @Size(min = 6, max = 30, message = "Password must be between 6 and 230 symbols long")
    private String confirmPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
