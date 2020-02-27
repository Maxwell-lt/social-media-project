package maxwell_lt.socialmediaproject.modelassembler;

import maxwell_lt.socialmediaproject.entity.Post;
import maxwell_lt.socialmediaproject.view.AdministrationController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PostModelAssembler implements RepresentationModelAssembler<Post, EntityModel<Post>> {

    @Override
    public EntityModel<Post> toModel(Post post) {
        return new EntityModel<>(post,
                linkTo(methodOn(AdministrationController.class).getPostDetails(post.getId())).withSelfRel(),
                linkTo(methodOn(AdministrationController.class).getUserDetails(post.getUser().getId())).withRel("user"),
                linkTo(methodOn(AdministrationController.class).getAllPosts()).withRel("posts"));
    }

    @Override
    public CollectionModel<EntityModel<Post>> toCollectionModel(Iterable<? extends Post> posts) {
        return new CollectionModel<>(
                StreamSupport.stream(posts.spliterator(), false)
                        .map(this::toModel)
                        .collect(Collectors.toList()),
                linkTo(methodOn(AdministrationController.class).getAllPosts()).withSelfRel()
        );
    }
}
