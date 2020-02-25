package maxwell_lt.socialmediaproject.service;

import maxwell_lt.socialmediaproject.entity.Post;
import maxwell_lt.socialmediaproject.entity.User;
import maxwell_lt.socialmediaproject.exception.PostNotFoundException;
import maxwell_lt.socialmediaproject.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

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

    public Post getPostById(int id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    public Collection<Post> getPostsByUser(User user) {
        return postRepository.findAllByUser(user);
    }

    public Page<Post> getPostsByUser(User user, Pageable pageable) {
        return postRepository.findByUser(user, pageable);
    }

    public Page<Post> getPostsAsPage(PageRequest page) {
        return postRepository.findAll(page);
    }

    public Page<Post> getPostsAsPageByPopularity(PageRequest page) {
        return postRepository.findAllOrderByPopularity(page);
    }

    public Page<Post> getPostsAsPageByUserByPopularity(User user, PageRequest page) {
        return postRepository.findAllByUserOrderByPopularity(user, page);
    }

    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    @Transactional
    public void deletePost(int postId) {
        postRepository.deletePostById(postId);
    }
}