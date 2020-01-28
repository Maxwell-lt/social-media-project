package maxwell_lt.socialmediaproject.service;

import maxwell_lt.socialmediaproject.entity.Post;
import maxwell_lt.socialmediaproject.entity.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class PostService extends AbstractService {
    public PostService() {
        super();
    }

    public void createPost(Post post) {
        em.getTransaction().begin();
        em.persist(post);
        em.getTransaction().commit();
    }

    public Optional<Post> getPostById(int id) {
        Post post = em.find(Post.class, id);
        return Optional.ofNullable(post);
    }

    public Collection<Post> getPostsByUserId(int id) {
        User user = em.find(User.class, id);
        if (user != null) {
            return user.getPosts();
        } else {
            return new ArrayList<>();
        }
    }
}