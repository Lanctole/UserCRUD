package lanctole.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lanctole.dto.CreateOrUpdateUserDto;
import lanctole.representation.UserDtoToModelMapper;
import lanctole.representation.UserModel;
import lanctole.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "Управление пользователями")
public class UserController {
    private final UserService userService;
    private final UserDtoToModelMapper mapper;

    @GetMapping
    @Operation(summary = "Получить список всех пользователей")
    public CollectionModel<UserModel> getAllUsers() {
        return mapper.toCollectionModel(userService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить пользователя по ID")
    public UserModel getUserById(@PathVariable("id") Long id) {
        return mapper.toModel(userService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создать нового пользователя")
    public UserModel createUser(@Valid @RequestBody CreateOrUpdateUserDto createOrUpdateUserDto) {
        return mapper.toModel(userService.create(createOrUpdateUserDto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить пользователя по ID")
    public UserModel updateUser(@Valid @PathVariable("id") Long id, @RequestBody CreateOrUpdateUserDto createOrUpdateUserDto) {
        return mapper.toModel(userService.update(id, createOrUpdateUserDto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удалить пользователя по ID")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
    }
}
