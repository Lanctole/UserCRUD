package lanctole.service;

import lanctole.dto.CreateOrUpdateUserDto;
import lanctole.dto.UserDto;
import lanctole.enums.EventType;
import lanctole.event.UserEvent;
import lanctole.exception.EmailAlreadyExistsException;
import lanctole.exception.UserServiceException;
import lanctole.mapper.UserMapper;
import lanctole.model.User;
import lanctole.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ApplicationEventPublisher eventPublisher;


    @Transactional(readOnly = true)
    public List<UserDto> getAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserDto getById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserServiceException(id));
    }

    @Transactional
    public UserDto create(CreateOrUpdateUserDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException(dto.getEmail());
        }

        User user = userMapper.toEntity(dto);
        User saved = userRepository.save(user);
        eventPublisher.publishEvent(new UserEvent(saved.getEmail(), EventType.USER_CREATED));
        return userMapper.toDto(saved);
    }

    @Transactional
    public UserDto update(Long id, CreateOrUpdateUserDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserServiceException(id));

        if (userRepository.existsByEmail(dto.getEmail()) && !dto.getEmail().equals(user.getEmail())) {
            throw new EmailAlreadyExistsException(dto.getEmail());
        }

        userMapper.updateEntity(user, dto);
        User updated = userRepository.save(user);
        return userMapper.toDto(updated);
    }

    @Transactional
    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserServiceException(id));
        userRepository.deleteById(id);
        eventPublisher.publishEvent(new UserEvent(user.getEmail(), EventType.USER_DELETED));
    }
}
