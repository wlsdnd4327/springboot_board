package org.koreait.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping
    public String index(Model model){
        commonProcess(model,"메인페이지");
        return "front/index";
    }

    private void commonProcess(Model model, String title){
        model.addAttribute("addCss","style2");
        model.addAttribute("title",title);
    }
}
