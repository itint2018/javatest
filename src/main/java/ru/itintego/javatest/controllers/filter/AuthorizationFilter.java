package ru.itintego.javatest.controllers.filter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import ru.itintego.javatest.models.User;
import ru.itintego.javatest.repositories.UserRepository;
import ru.itintego.javatest.services.UserDetailsImpl;

import javax.persistence.EntityNotFoundException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Component
public class AuthorizationFilter extends GenericFilterBean {

    public static final List<String> ALLOWED_ROUTES = List.of("/auth", "/api/login", "/js/app.js", "/js/md5_min.js", "/aut_registration.php");

    private final UserRepository userRepository;

    public AuthorizationFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest servletRequest1 = (HttpServletRequest) servletRequest;
        HttpServletResponse servletResponse1 = (HttpServletResponse) servletResponse;
        String requestURI = servletRequest1.getRequestURI();
        if (!ALLOWED_ROUTES.contains(requestURI)) {
            Cookie[] cookies = servletRequest1.getCookies();
            try {
                for (Cookie cookie : cookies) {
                    if (Objects.equals(cookie.getName(), "idSession")) {
                        Optional<User> bySession = userRepository.findBySession(cookie.getValue());
                        User o = bySession.orElseThrow(() -> new NullPointerException(""));
                        cookie.setMaxAge(300);
                        cookie.setPath("/");
                        servletResponse1.addCookie(cookie);
                        UserDetailsImpl userDetails = new UserDetailsImpl(o);
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                }
            } catch (NullPointerException | EntityNotFoundException e) {
                servletResponse1.sendRedirect("/auth?" + "uri=" + requestURI);
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
