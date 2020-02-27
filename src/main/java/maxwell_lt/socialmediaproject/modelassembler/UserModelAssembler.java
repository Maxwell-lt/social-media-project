package maxwell_lt.socialmediaproject.modelassembler;

import maxwell_lt.socialmediaproject.entity.User;
import maxwell_lt.socialmediaproject.view.AdministrationController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {
    @Override
    public EntityModel<User> toModel(User user) {
        return new EntityModel<>(user,
                linkTo(methodOn(AdministrationController.class).getUserDetails(user.getId())).withSelfRel(),
                linkTo(methodOn(AdministrationController.class).getAllUsers()).withRel("users"));
    }

    @Override
    public CollectionModel<EntityModel<User>> toCollectionModel(Iterable<? extends User> users) {
        return new CollectionModel<>(
                StreamSupport.stream(users.spliterator(), false)
                        .map(this::toModel)
                        .collect(Collectors.toList()),
                linkTo(methodOn(AdministrationController.class).getAllUsers()).withSelfRel()
        );
    }
}
