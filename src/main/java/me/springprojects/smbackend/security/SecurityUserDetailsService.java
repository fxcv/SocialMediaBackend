package me.springprojects.smbackend.security;

import lombok.AllArgsConstructor;
import me.springprojects.smbackend.entities.User;
import me.springprojects.smbackend.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {

    private final UserRepository securityUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> securityUser = securityUserRepository.findUserByUsername(username);

        return securityUser.map(user -> new SecurityUserDetails(user, passwordEncoder))
                           .orElseThrow(() -> new UsernameNotFoundException("Username not found."));
    }
}
