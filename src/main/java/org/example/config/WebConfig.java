package org.example.config;

import gg.jte.CodeResolver;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.DirectoryCodeResolver;
import gg.jte.springframework.boot.autoconfigure.JteConfigurationException;
import gg.jte.springframework.boot.autoconfigure.JteProperties;
import gg.jte.springframework.boot.autoconfigure.JteViewResolver;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.FileSystems;
import java.nio.file.Paths;

@Configuration(proxyBeanMethods = false)
@EnableWebMvc
@ComponentScan(basePackages = "org.example.controller")
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve Static Resources with Spring
        // See: https://www.baeldung.com/spring-mvc-static-resources#resource-http-request-handler
        registry.addResourceHandler("/third-party-libs/**")
                .addResourceLocations("/third-party-libs/");
    }

    // look into gg.jte.springframework.boot.autoconfigure.JteAutoConfiguration

    @Bean
    public JteProperties jteProperties() {
        JteProperties jteProperties = new JteProperties();
        // modify props by setters
        jteProperties.setUsePrecompiledTemplates(false);
        jteProperties.setDevelopmentMode(true);
        return jteProperties;
    }

    @Bean
    public TemplateEngine jteTemplateEngine(JteProperties jteProperties) {
        if (jteProperties.isDevelopmentMode() && jteProperties.usePreCompiledTemplates()) {
            throw new JteConfigurationException("You can't use development mode and precompiledTemplates together");
        }
        if (jteProperties.usePreCompiledTemplates()) {
            // Templates will need to be compiled by the maven/gradle build task
            return TemplateEngine.createPrecompiled(ContentType.Html);
        }
        if (jteProperties.isDevelopmentMode()) {
            // Here, a jte file watcher will recompile the jte templates upon file save (the web browser will auto-refresh)
            // If using IntelliJ, use Ctrl-F9 to trigger an auto-refresh when editing non-jte files.
            String[] split = jteProperties.getTemplateLocation().split("/");
            CodeResolver codeResolver = new DirectoryCodeResolver(FileSystems.getDefault().getPath("", split));
            TemplateEngine templateEngine = TemplateEngine.create(codeResolver, Paths.get("jte-classes"), ContentType.Html, getClass().getClassLoader());
            templateEngine.setHtmlCommentsPreserved(true);
            templateEngine.setTrimControlStructures(true);
            return templateEngine;
        }
        throw new JteConfigurationException("You need to either set gg.jte.usePrecompiledTemplates or gg.jte.developmentMode to true ");
    }

    @Bean
    public JteViewResolver jteViewResolver(TemplateEngine templateEngine, JteProperties jteProperties) {
        return new JteViewResolver(templateEngine, jteProperties);
    }

    /**
     * Add Context Path to All Views Automatically.
     *
     * @see InterceptorRegistry#addInterceptor(HandlerInterceptor)
     */
    private static class ContextPathInterceptor implements HandlerInterceptor {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
            request.setAttribute("contextPath", request.getContextPath());
            return true;
        }
    }

}
