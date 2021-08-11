package ru.itintego.javatest.controllers.filter;

import org.slf4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.itintego.javatest.models.User;
import ru.itintego.javatest.repositories.UserRepository;
import ru.itintego.javatest.services.UserDetailsImpl;

import javax.persistence.EntityNotFoundException;
import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Component
public class AuthorizationFilter implements Filter {

    public static final List<String> ALLOWED_ROUTES = List.of("/auth", "/api/login", "/js/app.js", "/js/md5_min.js");

    private final Logger logger;
    private final UserRepository userRepository;

    public AuthorizationFilter(Logger logger, UserRepository userRepository) {
        this.logger = logger;
        this.userRepository = userRepository;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest servletRequest1 = (HttpServletRequest) servletRequest;
        HttpServletResponse servletResponse1 = (HttpServletResponse) servletResponse;
        String requestURI = servletRequest1.getRequestURI();
        logger.info(requestURI);
        if (!ALLOWED_ROUTES.contains(requestURI)) {
            Cookie[] cookies = servletRequest1.getCookies();
            try {
                for (Cookie cookie : cookies) {
                    logger.info("request cookies {}={}", cookie.getName(), cookie.getValue());
                    if (Objects.equals(cookie.getName(), "idSession")) {

                        Optional<User> bySession = userRepository.findBySession(cookie.getValue());
                        User o = bySession.orElseThrow(() -> new NullPointerException(""));
                        logger.info("User {}", o);
                        cookie.setMaxAge(300);
                        cookie.setPath("/");
                        servletResponse1.addCookie(cookie);
                        UserDetailsImpl userDetails = new UserDetailsImpl(bySession.get());
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                }
            } catch (NullPointerException | EntityNotFoundException e) {
                logger.info("null pointer exception");
                servletResponse1.sendRedirect("/auth?" + "uri=" + requestURI);
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
