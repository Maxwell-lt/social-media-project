package maxwell_lt.socialmediaproject.utilities;

import maxwell_lt.socialmediaproject.dto.PostForm;
import maxwell_lt.socialmediaproject.entity.Post;
import maxwell_lt.socialmediaproject.entity.User;
import maxwell_lt.socialmediaproject.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
public class PostUtil {

    private FileService fileService;

    @Autowired
    public PostUtil(FileService fileService) {
        this.fileService = fileService;
    }

    public Post createPostFromPostFormAndUser(PostForm postForm, User user) {
        Post postEntity = new Post();

        postEntity.setTitle(postForm.getTitle());
        postEntity.setText(postForm.getText());
        postEntity.setUser(user);

        postEntity.setTimestamp(Timestamp.from(Instant.now()));
        postEntity.setDeleted(false);

        if (!postForm.getImage().isEmpty()) {
            String imageId = fileService.createImageFileAndThumbnail(postForm.getImage());
            postEntity.setImageId(imageId);
        }

        return postEntity;
    }
}