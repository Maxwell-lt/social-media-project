package maxwell_lt.socialmediaproject.utilities;

import maxwell_lt.socialmediaproject.dto.PostForm;
import maxwell_lt.socialmediaproject.entity.Post;
import maxwell_lt.socialmediaproject.entity.User;
import maxwell_lt.socialmediaproject.service.FileService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;

@Service
public class PostUtil {

    private FileService fileService;
    private Logger logger;

    @Autowired
    public PostUtil(FileService fileService) {
        this.fileService = fileService;
        logger = LogManager.getLogger();
    }

    public Post createPostFromPostFormAndUser(PostForm postForm, User user) {
        Post postEntity = new Post();

        postEntity.setTitle(postForm.getTitle());
        postEntity.setText(postForm.getText());
        postEntity.setUser(user);

        postEntity.setTimestamp(Timestamp.from(Instant.now()));

        if (!postForm.getImage().isEmpty()) {
            String imageId = null;
            try {
                imageId = fileService.createImageFileAndThumbnail(postForm.getImage());
            } catch (IOException e) {
                logger.error("Could not save image file", e);
            }
            postEntity.setImageId(imageId);
        }

        return postEntity;
    }

    public static Sort getSortFromParam(String sortParam) {
        if (sortParam.equals("new")) {
            return Sort.by("timestamp").descending();
        }
        if (sortParam.equals("old")) {
            return Sort.by("timestamp").ascending();
        }
        if (sortParam.equals("popular")) {
            return Sort.unsorted();
        }
        return Sort.unsorted();
    }
}
