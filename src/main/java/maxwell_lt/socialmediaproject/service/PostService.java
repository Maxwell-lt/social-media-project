package maxwell_lt.socialmediaproject.service;

import maxwell_lt.socialmediaproject.entity.Post;
import maxwell_lt.socialmediaproject.entity.User;
import maxwell_lt.socialmediaproject.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class PostService {

    private PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public int createPost(Post post) {
        postRepository.save(post);
        return post.getId();
    }

    public Optional<Post> getPostById(int id) {
        return postRepository.findById(id);
    }

    public Collection<Post> getPostsByUser(User user) {
        return postRepository.findAllByUser(user);
    }

    public Page<Post> getPostsAsPage(PageRequest page) {
        return postRepository.findAll(page);

    }
}