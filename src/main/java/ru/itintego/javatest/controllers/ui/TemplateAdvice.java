package ru.itintego.javatest.controllers.ui;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.itintego.javatest.services.SecurityHelper;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class TemplateAdvice {

    private SecurityHelper securityHelper;

    @ModelAttribute
    public void addDefaultAttributes(HttpServletRequest request, Model model) {
        securityHelper = new SecurityHelper(SecurityContextHolder.getContext());
        model.addAttribute("isLoggedIn", securityHelper.isAuthenticated()
                && !securityHelper.isAnonymous());
        model.addAttribute("username", securityHelper.username());
        model.addAttribute("isAdminOrSuper", securityHelper.isAdminOrSuper());
    }
}
