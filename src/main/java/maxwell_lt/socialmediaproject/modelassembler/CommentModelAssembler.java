package maxwell_lt.socialmediaproject.modelassembler;

import maxwell_lt.socialmediaproject.entity.Comment;
import maxwell_lt.socialmediaproject.view.AdministrationController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class CommentModelAssembler implements RepresentationModelAssembler<Comment, EntityModel<Comment>> {
    @Override
    public EntityModel<Comment> toModel(Comment comment) {
        return new EntityModel<>(comment,
                linkTo(methodOn(AdministrationController.class).getCommentDetails(comment.getId())).withSelfRel(),
                linkTo(methodOn(AdministrationController.class).getUserDetails(comment.getUser().getId())).withRel("user"),
                linkTo(methodOn(AdministrationController.class).getAllComments()).withRel("comments"));
    }

    @Override
    public CollectionModel<EntityModel<Comment>> toCollectionModel(Iterable<? extends Comment> comments) {
        return new CollectionModel<>(
                StreamSupport.stream(comments.spliterator(), false)
                        .map(this::toModel)
                        .collect(Collectors.toList()),
                linkTo(methodOn(AdministrationController.class).getAllComments()).withSelfRel()
        );
    }
}
