package com.spring.player.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class Main {

    @GetMapping("/admin")
    public String myAdmin(){
        return "I am Admin";
    }

    @GetMapping("/adminormanger")
    public String adminOrManager(){
        return "Iam Admin || Manger";
    }

    @GetMapping("adminmangeruser")
    public String adminOrMangerOrUser(){
        return "I am Admin || Manger || User";
    }
}
