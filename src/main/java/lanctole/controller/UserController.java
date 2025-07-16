package lanctole.controller;

import jakarta.validation.Valid;
import lanctole.dto.CreateOrUpdateUserDto;
import lanctole.dto.UserDto;
import lanctole.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable("id") Long id) {
        return userService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@Valid @RequestBody CreateOrUpdateUserDto createOrUpdateUserDto) {
        return userService.create(createOrUpdateUserDto);
    }

    @PutMapping("/{id}")
    public UserDto updateUser(@Valid @PathVariable("id") Long id, @RequestBody CreateOrUpdateUserDto createOrUpdateUserDto) {
        return userService.update(id, createOrUpdateUserDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
    }
}
