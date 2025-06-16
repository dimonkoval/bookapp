package mate.academy.springboot.web.service.impl;

import lombok.RequiredArgsConstructor;
import mate.academy.springboot.web.mapper.UserMapper;
import mate.academy.springboot.web.model.User;
import mate.academy.springboot.web.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmailFetchRoles(email).orElseThrow(() ->
                new UsernameNotFoundException("Can't find user with email " + email));
        return userMapper.convertToUserDetails(user);
    }
}
