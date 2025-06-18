package mate.academy.springboot.web.mapper;

import mate.academy.springboot.web.dto.UserRegistrationRequestDto;
import mate.academy.springboot.web.dto.UserResponseDto;
import mate.academy.springboot.web.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    User toEntity(UserRegistrationRequestDto dto);

    UserResponseDto toDto(User user);

    default org.springframework.security.core.userdetails.User convertToUserDetails(User user) {
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.isEnabled(),
                user.isAccountNonExpired(),
                user.isCredentialsNonExpired(),
                user.isAccountNonLocked(),
                user.getAuthorities()
        );
    }
}
