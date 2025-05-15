package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.Locale;

@ControllerAdvice
public class GlobalModelAttributes {

    /**
     * Injects object 'contextPath' into {@link org.springframework.ui.Model}.
     */
    @ModelAttribute("contextPath")
    public String contextPath(HttpServletRequest request) {
        return request.getContextPath();
    }

    /**
     * Injects object 'locale' into {@link org.springframework.ui.Model}.
     *
     * @see RequestContextUtils#getLocale(HttpServletRequest)
     */
    @ModelAttribute("locale")
    public Locale currentLocale(HttpServletRequest request) {
        return RequestContextUtils.getLocale(request);
    }

}