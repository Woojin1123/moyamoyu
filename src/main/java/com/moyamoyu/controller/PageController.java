package com.moyamoyu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class PageController {
    @GetMapping("/{path:[^\\\\.]*}")
    public String indexPage() {
        return "forward:/";
    }
    @GetMapping("/main")
    public String mainPage(){
        return "main";
    }

}
