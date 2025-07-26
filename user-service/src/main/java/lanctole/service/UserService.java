package lanctole.service;

import lanctole.dto.CreateOrUpdateUserDto;
import lanctole.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto create(CreateOrUpdateUserDto user);

    UserDto getById(Long id);

    List<UserDto> getAll();

    UserDto update(Long id, CreateOrUpdateUserDto dto);

    void delete(Long id);
}
