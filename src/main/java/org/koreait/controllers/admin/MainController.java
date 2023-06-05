package org.koreait.controllers.admin;

import org.koreait.commons.CommonFunc;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("AdminMainController")
@RequestMapping("/admin")
public class MainController implements CommonFunc {

    @GetMapping
    public String index(Model model){
        commonProcess(model);
        return "admin/index";
    }

    @Override
    public void commonProcess(Model model) {
        String title = "관리자 메인";
        model.addAttribute("title",title);
    }
}
