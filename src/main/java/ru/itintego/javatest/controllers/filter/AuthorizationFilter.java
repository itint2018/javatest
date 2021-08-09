package ru.itintego.javatest.controllers.filter;

import org.slf4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import ru.itintego.javatest.services.auth.JPAUserDetailService;
import ru.itintego.javatest.services.auth.JwtService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.springframework.util.StringUtils.hasText;

@Component
public class AuthorizationFilter extends GenericFilterBean {

    private final JwtService jwtService;
    private final JPAUserDetailService jpaUserDetailService;
    private final Logger logger;

    private static final String AUTHORIZATION = "Authorization";

    public AuthorizationFilter(JwtService jwtService, JPAUserDetailService jpaUserDetailService, Logger logger) {
        this.jwtService = jwtService;
        this.jpaUserDetailService = jpaUserDetailService;
        this.logger = logger;
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = getTokenFromRequest((HttpServletRequest) servletRequest);
        if (token != null && jwtService.validateToken(token)) {
            String userLogin = jwtService.getLoginFromToken(token);
            var customUserDetails = jpaUserDetailService.loadUserByUsername(userLogin);
            logger.info("Getting {} from user {}", ((HttpServletRequest) servletRequest).getRequestURI(), customUserDetails.getUsername());
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader(AUTHORIZATION);
        if (hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}
