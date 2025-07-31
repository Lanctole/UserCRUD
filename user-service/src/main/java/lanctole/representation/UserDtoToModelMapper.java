package lanctole.representation;

import lanctole.controller.UserController;
import lanctole.dto.UserDto;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UserDtoToModelMapper implements RepresentationModelAssembler<UserDto, UserModel> {

    @Override
    public UserModel toModel(UserDto dto) {
        UserModel model = new UserModel();
        model.setId(dto.getId());
        model.setName(dto.getName());
        model.setEmail(dto.getEmail());
        model.setAge(dto.getAge());
        model.setCreatedAt(dto.getCreatedAt());

        model.add(linkTo(methodOn(UserController.class).getUserById(dto.getId())).withSelfRel());
        model.add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("all-users"));

        return model;
    }
}
