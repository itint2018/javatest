package ru.itintego.javatest.controllers.rest;

import org.slf4j.Logger;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itintego.javatest.dto.AuthorizationRequest;
import ru.itintego.javatest.dto.AuthorizationResponse;
import ru.itintego.javatest.repositories.UserRepository;
import ru.itintego.javatest.services.auth.JwtService;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/")
public class AuthorizationController {
    private final Logger logger;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthorizationController(Logger logger, UserRepository userRepository, JwtService jwtService) {
        this.logger = logger;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @PostMapping("login")
    public AuthorizationResponse login(@RequestBody AuthorizationRequest request) {
        logger.info("Receive next auth request {}", request);
        String token = jwtService.generateToken(userRepository.findByLoginAndPassword(request.getLogin(),
                        request.getPassword())
                .orElseThrow(() -> new EntityNotFoundException("User not found"))
                .getLogin());
        return new AuthorizationResponse(token);
    }
}
