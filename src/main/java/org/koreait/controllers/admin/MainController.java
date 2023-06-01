package org.koreait.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("AdminMainController")
@RequestMapping("/admin")
public class MainController {

    @GetMapping
    public String index(){
        return "admin/index";
    }
}
