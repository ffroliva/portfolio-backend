package br.com.ffroliva.portfolio.service.impl;

import br.com.ffroliva.portfolio.model.User;
import br.com.ffroliva.portfolio.model.UserPrincipal;
import br.com.ffroliva.portfolio.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail)
            throws UsernameNotFoundException {
        // Let people login with either username or email
        User user = userRepository
                .findByUsernameOrEmail(usernameOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail)
                );

        return UserPrincipal.create(user);
    }

    // This method is used by JWTAuthenticationFilter
    @Transactional
    public UserDetails loadUserById(Integer id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(
                () -> new UsernameNotFoundException("User not found with id : " + id)
        );
        user.getUserRoles()
                .stream()
                .forEach(userRole -> log.debug("User Role: " + userRole.getId().getRole()));
        return UserPrincipal.create(user);
    }
}
