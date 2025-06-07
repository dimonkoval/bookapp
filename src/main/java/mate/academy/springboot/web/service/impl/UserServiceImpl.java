package mate.academy.springboot.web.service.impl;

import lombok.RequiredArgsConstructor;
import mate.academy.springboot.web.dto.UserRegistrationRequestDto;
import mate.academy.springboot.web.dto.UserResponseDto;
import mate.academy.springboot.web.exception.RegistrationException;
import mate.academy.springboot.web.mapper.UserMapper;
import mate.academy.springboot.web.model.User;
import mate.academy.springboot.web.repository.UserRepository;
import mate.academy.springboot.web.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto request)
            throws RegistrationException {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RegistrationException("User with email "
                    + request.getEmail() + " already exist.");
        }

        User user = userMapper.toEntity(request);
        userRepository.save(user);
        return userMapper.toDto(user);
    }
}
