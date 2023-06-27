package org.koreait.controllers.admin;

import lombok.RequiredArgsConstructor;
import org.koreait.dtos.admin.SiteConfigData;
import org.koreait.services.SiteConfigDeleteService;
import org.koreait.services.SiteConfigInfoService;
import org.koreait.services.SiteConfigSaveService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/config")
@RequiredArgsConstructor
public class SiteConfigController{

    private final SiteConfigSaveService saveService;
    private final SiteConfigInfoService infoService;
    private final SiteConfigDeleteService deleteService;
    private String configId = "siteConfig";
    @GetMapping
    public String config(Model model){
        commonProcess(model, "사이트 설정");
        SiteConfigData data = infoService.get(configId, SiteConfigData.class);
        model.addAttribute("siteConfigData",data == null ? new SiteConfigData() : data);
        model.addAttribute("addCss", new String("config"));
        return "admin/config";
    }

    @PostMapping
    public String configPs(SiteConfigData siteConfigdata, Model model){
        commonProcess(model,"사이트 설정");
        saveService.save(configId,siteConfigdata);
        model.addAttribute("message","설정이 저장되었습니다");
        model.addAttribute("addCss", new String("config"));
        return "admin/config";
    }

    private void commonProcess(Model model, String title) {
        model.addAttribute("addCss","style2");
        model.addAttribute("title",title);
    }
}
