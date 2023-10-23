package org.koreait.controllers.front;

import lombok.RequiredArgsConstructor;
import org.koreait.dtos.front.IndexForm;
import org.koreait.entities.post.Post;
import org.koreait.services.front.IndexListService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class MainController {

    private final IndexListService listService;

    @GetMapping
    public String index(Model model, @ModelAttribute IndexForm indexForm){
        Page<Post> data = listService.gets(indexForm);
        model.addAttribute("items", data.getContent());
        commonProcess(model,"메인페이지");
        return "front/index";
    }

    private void commonProcess(Model model, String title){
        model.addAttribute("title",title);
    }
}
