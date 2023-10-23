package org.koreait.restcontrollers;

import lombok.RequiredArgsConstructor;
import org.koreait.services.flie.FileDeleteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class FileDeleteController {
    private final FileDeleteService deleteService;

    @GetMapping("/file/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {

        deleteService.delete(id);

        String script = String.format("if(typeof parent.fileDeleteCallback == 'function') parent.fileDeleteCallback(%d);", id);
        model.addAttribute("script", script);

        return "commons/execute_script";
    }
}
