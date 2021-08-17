package ru.itintego.javatest.controllers.ui;


import org.slf4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.itintego.javatest.services.SecurityHelper;
import ru.itintego.javatest.services.UserDetailsImpl;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Objects;

@ControllerAdvice
public class TemplateAdvice {

    private final Logger logger;
    private final UserDetailsService userDetailsService;

    public TemplateAdvice(Logger logger, UserDetailsService userDetailsService) {
        this.logger = logger;
        this.userDetailsService = userDetailsService;
    }

    @ModelAttribute
    public void addDefaultAttributes(HttpServletRequest request, Model model) {
        SecurityHelper securityHelper = new SecurityHelper(SecurityContextHolder.getContext());
        String requestURI = request.getRequestURI();
        String parent = href(requestURI);
        logger.info("parent {}", parent);
        model.addAttribute("isLoggedIn", securityHelper.isAuthenticated()
                && !securityHelper.isAnonymous());
        if (!Objects.equals(securityHelper.username(), "anonymousUser")) {
            UserDetailsImpl attributeValue = (UserDetailsImpl) userDetailsService.loadUserByUsername(securityHelper.username());
            String fullName = attributeValue.getUser().getLastName() + " " + attributeValue.getUser().getFirstName();
            model.addAttribute("username", fullName);
        }
        if (!parent.equals("/") && !requestURI.equals("/auth")) model.addAttribute("parent", parent);
        model.addAttribute("isAdminOrSuper", securityHelper.isAdminOrSuper());
        model.addAttribute("isAdmin", securityHelper.isAdmin());
        model.addAttribute("isManager", securityHelper.isManager());
        model.addAttribute("isEmployee", securityHelper.isEmployee());
        model.addAttribute("isManagerOrEmployee", securityHelper.isManagerOrEmployee());
        model.addAttribute("header", " ");
    }

    private String href(String requestUri) {
        URI uri = URI.create(requestUri);
        URI parent = uri.getPath().endsWith("/") ? uri.resolve("..") : uri.resolve(".");
        return parent.getPath();
    }
}
