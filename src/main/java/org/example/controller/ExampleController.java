package org.example.controller;

import org.example.model.Page;
import org.springframework.core.SpringVersion;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExampleController {

    private static final String SPRING_VERSION = SpringVersion.getVersion();

    @GetMapping("/")
    public String home(Model model) {
        Page page = Page.builder()
                .title("Welcome")
                .description("Welcome in Spring " + SPRING_VERSION + " with JTE !")
                .build();
        model.addAttribute("page", page);

        return "home";
    }

    @GetMapping("/example")
    public String example(Model model) {
        Page page = Page.builder()
                .title("Hello")
                .description("Prints Hello")
                .build();
        model.addAttribute("page", page);

        return "example";
    }

}
