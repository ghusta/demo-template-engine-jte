package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {

    /**
     * Injects object 'contextPath' into {@link org.springframework.ui.Model}.
     */
    @ModelAttribute("contextPath")
    public String contextPath(HttpServletRequest request) {
        return request.getContextPath();
    }

}