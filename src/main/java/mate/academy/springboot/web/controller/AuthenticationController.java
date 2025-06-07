package mate.academy.springboot.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.web.dto.UserRegistrationRequestDto;
import mate.academy.springboot.web.dto.UserResponseDto;
import mate.academy.springboot.web.exception.RegistrationException;
import mate.academy.springboot.web.service.UserService;
import mate.academy.springboot.web.swagger.annotations.BadRequestApiResponse;
import mate.academy.springboot.web.swagger.annotations.InternalServerErrorApiResponse;
import mate.academy.springboot.web.swagger.annotations.OkApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;

    @PostMapping("/registeration")
    @BadRequestApiResponse
    @OkApiResponse
    @InternalServerErrorApiResponse
    @Operation(summary = "Register a new user", description = "Creates a new user")
    public UserResponseDto register(@Valid @RequestBody UserRegistrationRequestDto request)
            throws RegistrationException {
        return userService.register(request);
    }
}
