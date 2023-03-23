package com.linker.springlinker.controllers;

import com.linker.springlinker.models.User;
import com.linker.springlinker.services.AppUserDetailsService;
import com.linker.springlinker.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    @Autowired
    AuthService authService;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    AppUserDetailsService appUserDetailsService;

    @GetMapping("/login")
    public String login(@RequestParam(required = false) boolean error, Model model) {
        if(authService.checkAuthentication()){
            return "redirect:";
        }
        if (error) {
            model.addAttribute("log_error", "Wrong username or password");
        }
        return "login";
    }

    @GetMapping("/registration")
    public String registration() {
        if(authService.checkAuthentication()){
            return "redirect:";
        }
        return "registration";
    }

    @PostMapping("/register")
    public String addUser(@RequestBody MultiValueMap<String, String> formData, Model model){
        User user = new User();
        user.setUsername(formData.getFirst("username"));
        user.setPassword(formData.getFirst("password"));
        String result = appUserDetailsService.createUser(user);
        if(result != "Saved"){
            model.addAttribute("reg_error", result);
            return "registration";
        }
        UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(formData.getFirst("username"), formData.getFirst("password"));
        Authentication authResult = authenticationProvider.authenticate(authRequest);
        SecurityContextHolder.getContext().setAuthentication(authResult);
        return "redirect:";
    }

    @GetMapping("/logout")
    public String logout() {
        SecurityContextHolder.clearContext();
        return "login";
    }
}
