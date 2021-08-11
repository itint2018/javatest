package ru.itintego.javatest.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itintego.javatest.repositories.UserRepository;

@Service
public class UserServiceDetailImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserServiceDetailImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserDetailsImpl(userRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found")));
    }
}
