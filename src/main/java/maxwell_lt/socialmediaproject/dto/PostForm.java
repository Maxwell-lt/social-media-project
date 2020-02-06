package maxwell_lt.socialmediaproject.dto;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.StringJoiner;

public class PostForm {

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 100)
    private String title;

    private MultipartFile image;

    @NotNull
    @NotEmpty
    @Size(max = 65535)
    private String text;

    public PostForm(@NotNull @NotEmpty @Size(min = 1, max = 100) String title,
                    MultipartFile image,
                    @NotNull @NotEmpty @Size(max = 65535) String text) {
        this.title = title;
        this.image = image;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PostForm.class.getSimpleName() + "[", "]")
                .add("title='" + title + "'")
                .add("image=" + image)
                .add("text='" + text + "'")
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostForm postForm = (PostForm) o;
        return getTitle().equals(postForm.getTitle()) &&
                Objects.equals(getImage(), postForm.getImage()) &&
                getText().equals(postForm.getText());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getImage(), getText());
    }
}
