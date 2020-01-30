package maxwell_lt.socialmediaproject.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.StringJoiner;

public class UserDTO {

    @NotNull
    @NotEmpty
    @Size(max = 50)
    private String username;
    @NotNull
    @NotEmpty
    @Size(max = 254)
    private String email;
    @NotNull
    @NotEmpty
    @Size(max = 48)
    private String rawPassword;

    public UserDTO() {
    }

    public UserDTO(String username, String email, String rawPassword) {
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
        return new StringJoiner(", ", UserDTO.class.getSimpleName() + "[", "]")
                .add("username='" + username + "'")
                .add("email='" + email + "'")
                .add("rawPassword='" + rawPassword + "'")
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return username.equals(userDTO.username) &&
                email.equals(userDTO.email) &&
                rawPassword.equals(userDTO.rawPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email, rawPassword);
    }
}
