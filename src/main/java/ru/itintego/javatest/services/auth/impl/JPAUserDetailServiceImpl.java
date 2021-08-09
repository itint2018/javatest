package ru.itintego.javatest.services.auth.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itintego.javatest.repositories.UserRepository;
import ru.itintego.javatest.services.auth.JPAUserDetailService;

@Service
public class JPAUserDetailServiceImpl implements JPAUserDetailService {
    private final UserRepository userRepository;

    public JPAUserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return new JPAUserDetails(userRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("Can't find user")));
    }
}
