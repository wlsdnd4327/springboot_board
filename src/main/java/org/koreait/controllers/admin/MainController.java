package org.koreait.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("AdminMainController")
@RequestMapping("/admin")
public class MainController {

    @GetMapping
    public String index(Model model){
        commonProcess(model, "관리자 메인");
        return "admin/index";
    }

    private void commonProcess(Model model, String title) {
        model.addAttribute("addCss","style2");
        model.addAttribute("title",title);
    }
}
