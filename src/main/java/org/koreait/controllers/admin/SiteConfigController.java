package org.koreait.controllers.admin;

import org.koreait.commons.CommonFunc;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/config")
public class SiteConfigController implements CommonFunc {
    @GetMapping
    public String config(@ModelAttribute SiteConfigData siteConfigData, Model model){
        commonProcess(model);
        model.addAttribute("siteConfigData",siteConfigData);
        return "admin/config";
    }

    @PostMapping
    public String configPs(Model model){
        commonProcess(model);
        return "admin/config";
    }

    @Override
    public void commonProcess(Model model) {
        String title = "사이트 설정";
        model.addAttribute("title",title);
    }
}
