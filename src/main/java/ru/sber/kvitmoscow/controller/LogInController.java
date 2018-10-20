package ru.sber.kvitmoscow.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sber.kvitmoscow.service.UserService;

@Controller
@RequestMapping("/login")
public class LogInController {
    @Autowired
    UserService userService;

    @GetMapping
    public String index(Model m){
        m.addAttribute("title", "Авторизация");
        return "login";
    }
}
