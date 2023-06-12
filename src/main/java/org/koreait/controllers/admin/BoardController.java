package org.koreait.controllers.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/board")
@RequiredArgsConstructor
public class BoardController {

    /**
     * 게시판 목록
     * @return
     */
    @GetMapping
    public String index(Model model){
        commonProcess(model, "게시판 목록");
        model.addAttribute("addCss", new String[]{"config", "submenu"});
        return "admin/board/index";
    }

    /**
     * 게시판 등록
     * @return
     */
    @GetMapping("/register")
    public String register(Model model){
        commonProcess(model, "게시판 등록");
        model.addAttribute("addCss", new String[]{"config", "submenu"});
        return "admin/board/form";
    }

    @GetMapping("/{bId}/update")
    public String update(@PathVariable String bId, Model model){
        commonProcess(model, "게시판 수정");
        return "admin/board/form";
    }

    private void commonProcess(Model model, String title){
        model.addAttribute("pageTitle", title);
        model.addAttribute("title", title);
    }
}
