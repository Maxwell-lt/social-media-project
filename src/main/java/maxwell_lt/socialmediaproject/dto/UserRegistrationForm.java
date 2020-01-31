package maxwell_lt.socialmediaproject.dto;

import maxwell_lt.socialmediaproject.validator.PasswordStrength;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.StringJoiner;

public class UserRegistrationForm {

    @NotNull
    @NotEmpty
    @Size(max = 50)
    private String username;

    @NotNull
    @NotEmpty
    @Size(max = 254)
    @Email
    private String email;

    @NotNull
    @NotEmpty
    @Size(max = 48)
    @PasswordStrength
    private String rawPassword;

    public UserRegistrationForm() {
    }

    public UserRegistrationForm(String username, String email, String rawPassword) {
        this.username = username;
        this.email = email;
        this.rawPassword = rawPassword;
    }

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

    public String getRawPassword() {
        return rawPassword;
    }

    public void setRawPassword(String rawPassword) {
        this.rawPassword = rawPassword;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UserRegistrationForm.class.getSimpleName() + "[", "]")
                .add("username='" + username + "'")
                .add("email='" + email + "'")
                .add("rawPassword='" + rawPassword + "'")
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRegistrationForm userRegistrationForm = (UserRegistrationForm) o;
        return username.equals(userRegistrationForm.username) &&
                email.equals(userRegistrationForm.email) &&
                rawPassword.equals(userRegistrationForm.rawPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email, rawPassword);
    }
}
