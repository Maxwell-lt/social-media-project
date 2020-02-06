package maxwell_lt.socialmediaproject.dto;

import maxwell_lt.socialmediaproject.validator.PostExist;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.StringJoiner;

public class CommentForm {

    @NotNull
    @NotEmpty
    @PostExist
    private int postId;

    @NotNull
    @NotEmpty
    @Size(max = 5000)
    private String text;

    private MultipartFile image;

    public CommentForm(@NotNull @NotEmpty int postId,
                       @NotNull @NotEmpty @Size(max = 5000) String text,
                       MultipartFile image) {
        this.text = text;
        this.postId = postId;
        this.image = image;
    }

    public CommentForm() {

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CommentForm.class.getSimpleName() + "[", "]")
                .add("text='" + text + "'")
                .add("postId=" + postId)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentForm that = (CommentForm) o;
        return getPostId() == that.getPostId() &&
                getText().equals(that.getText());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getText(), getPostId());
    }
}
