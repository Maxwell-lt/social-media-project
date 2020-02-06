package maxwell_lt.socialmediaproject.utilities;

import maxwell_lt.socialmediaproject.dto.CommentForm;
import maxwell_lt.socialmediaproject.entity.Comment;
import maxwell_lt.socialmediaproject.entity.User;
import maxwell_lt.socialmediaproject.service.FileService;
import maxwell_lt.socialmediaproject.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
public class CommentUtil {

    private PostService postService;
    private FileService fileService;

    @Autowired
    public CommentUtil(PostService postService, FileService fileService) {
        this.postService = postService;
        this.fileService = fileService;
    }

    public Comment createCommentFromCommentFormAndUser(CommentForm commentForm, User user) {
        Comment commentEntity = new Comment();

        commentEntity.setText(commentForm.getText());
        commentEntity.setPost(postService.getPostById(commentForm.getPostId()).get());
        commentEntity.setUser(user);

        commentEntity.setTimestamp(Timestamp.from(Instant.now()));

        if (!commentForm.getImage().isEmpty()) {
            String imageId = fileService.createImageFileAndThumbnail(commentForm.getImage());
            commentEntity.setImageId(imageId);
        }

        return commentEntity;
    }
}
