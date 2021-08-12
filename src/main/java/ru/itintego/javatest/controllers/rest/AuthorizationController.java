package ru.itintego.javatest.controllers.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.itintego.javatest.dto.AuthorizationRequest;
import ru.itintego.javatest.dto.AuthorizationResponse;
import ru.itintego.javatest.models.User;
import ru.itintego.javatest.repositories.UserRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping({"/api/login", "/aut_registration.php"})
public class AuthorizationController {

    private final UserRepository userRepository;

    public AuthorizationController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping
    public AuthorizationResponse login() {
        return new AuthorizationResponse("TEST");
    }

    @PostMapping
    public AuthorizationResponse login(@RequestBody AuthorizationRequest authorizationRequest, HttpServletResponse httpServletResponse) {
        Optional<User> user = userRepository.findByLoginAndPassword(authorizationRequest.getUsername(), authorizationRequest.getPassword());
        User user1 = user.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
        UUID cookieValue = UUID.randomUUID();
        Cookie cookie = new Cookie("idSession", cookieValue.toString());
        cookie.setMaxAge(300);
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);
        user1.setSession(cookieValue.toString());
        userRepository.save(user1);
        return new AuthorizationResponse(cookieValue.toString());
    }
}