package ringo.cms.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.v3.oas.annotations.Hidden;

@Controller
@Hidden
public class HomeController {

    @Hidden
    @GetMapping("/")
    public String home() {
        return "home";
    }
}
