package maxwell_lt.socialmediaproject.utilities;

import maxwell_lt.socialmediaproject.dto.CommentForm;
import maxwell_lt.socialmediaproject.entity.Comment;
import maxwell_lt.socialmediaproject.entity.User;
import maxwell_lt.socialmediaproject.service.FileService;
import maxwell_lt.socialmediaproject.service.PostService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;

@Service
public class CommentUtil {

    private PostService postService;
    private FileService fileService;
    private Logger logger;

    @Autowired
    public CommentUtil(PostService postService, FileService fileService) {
        this.postService = postService;
        this.fileService = fileService;
        logger = LogManager.getLogger();
    }

    public Comment createCommentFromCommentFormAndUser(CommentForm commentForm, User user) {
        Comment commentEntity = new Comment();

        commentEntity.setText(commentForm.getText());
        postService.getPostById(commentForm.getPostId()).ifPresent(commentEntity::setPost);
        commentEntity.setUser(user);

        commentEntity.setTimestamp(Timestamp.from(Instant.now()));

        if (!commentForm.getImage().isEmpty()) {
            String imageId = null;
            try {
                imageId = fileService.createImageFileAndThumbnail(commentForm.getImage());
            } catch (IOException e) {
                logger.error("Could not save image file", e);
            }
            commentEntity.setImageId(imageId);
        }

        return commentEntity;
    }
}
