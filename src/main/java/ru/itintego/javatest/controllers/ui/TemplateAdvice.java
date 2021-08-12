package ru.itintego.javatest.controllers.ui;


import org.slf4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.itintego.javatest.services.SecurityHelper;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Objects;

@ControllerAdvice
public class TemplateAdvice {

    private SecurityHelper securityHelper;
    private final Logger logger;

    public TemplateAdvice(Logger logger) {
        this.logger = logger;
    }

    @ModelAttribute
    public void addDefaultAttributes(HttpServletRequest request, Model model) {
        securityHelper = new SecurityHelper(SecurityContextHolder.getContext());
        String requestURI = request.getRequestURI();
        String parent = href(requestURI);
        logger.info("parent {}", parent);
        model.addAttribute("isLoggedIn", securityHelper.isAuthenticated()
                && !securityHelper.isAnonymous());
        if (!Objects.equals(securityHelper.username(), "anonymousUser"))
            model.addAttribute("username", securityHelper.username());
        if (!requestURI.equals("/auth")) model.addAttribute("parent", parent);
        model.addAttribute("isAdminOrSuper", securityHelper.isAdminOrSuper());
        model.addAttribute("header", " ");
    }

    private String href(String requestUri) {
        URI uri = URI.create(requestUri);
        URI parent = uri.getPath().endsWith("/") ? uri.resolve("..") : uri.resolve(".");
        return parent.getPath();
    }
}
