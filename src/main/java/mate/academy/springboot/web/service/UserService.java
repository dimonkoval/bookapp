package mate.academy.springboot.web.service;

import mate.academy.springboot.web.dto.UserRegistrationRequestDto;
import mate.academy.springboot.web.dto.UserResponseDto;
import mate.academy.springboot.web.exception.RegistrationException;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto request) throws RegistrationException;
}

