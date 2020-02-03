package maxwell_lt.socialmediaproject.utilities;

import javafx.geometry.Pos;
import maxwell_lt.socialmediaproject.dto.PostDto;
import maxwell_lt.socialmediaproject.dto.PostForm;
import maxwell_lt.socialmediaproject.entity.Post;
import maxwell_lt.socialmediaproject.entity.User;
import maxwell_lt.socialmediaproject.service.FileService;
import maxwell_lt.socialmediaproject.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

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

        String imageId = fileService.createImageFileAndThumbnail(postForm.getImage());
        postEntity.setImageId(imageId);

        return postEntity;
    }
}
