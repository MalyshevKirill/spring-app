package com.linker.springlinker.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;

@Controller
public class HomeController {
    @GetMapping(value = {"/"})
    public String home(Model model) {

        return "redirect:my-links";
    }
}
