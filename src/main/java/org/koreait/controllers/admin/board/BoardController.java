package org.koreait.controllers.admin.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.koreait.commons.CommonException;
import org.koreait.commons.utils.MenuDetail;
import org.koreait.commons.utils.Menus;
import org.koreait.dtos.admin.board.BoardCount;
import org.koreait.dtos.admin.board.BoardForm;
import org.koreait.entities.board.Board;
import org.koreait.services.board.BoardConfigDeleteService;
import org.koreait.services.board.BoardConfigInfoService;
import org.koreait.services.board.BoardConfigListService;
import org.koreait.services.board.BoardConfigSaveService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardConfigSaveService configSaveService;
    private final BoardConfigInfoService configInfoService;
    private final BoardConfigListService configListService;
    private final BoardConfigDeleteService configDeleteService;
    private final HttpServletRequest request;

    /**
     * 게시판 목록
     * @return
     */
    @GetMapping
    public String index(@ModelAttribute BoardCount boardCount, Model model){
        commonProcess(model, "게시판 목록");

        BoardCount boardcount = new BoardCount();
        Page<Board> data = configListService.gets(boardcount);
        model.addAttribute("items", data.getContent());

        return "admin/board/index";
    }

    /**
     * 게시판 등록
     * @return
     */
    @GetMapping("/register")
    public String register(@ModelAttribute BoardForm boardForm, Model model){
        commonProcess(model, "게시판 등록");

        return "admin/board/form";
    }

    /**
     * 게시판 수정
     * @param bId
     * @param model
     * @return
     */
    @GetMapping("/{bId}/update")
    public String update(@PathVariable String bId, Model model){
        commonProcess(model, "게시판 수정");

        Board board = configInfoService.get(bId, true);
        BoardForm boardForm = new ModelMapper().map(board, BoardForm.class);
        boardForm.setMode("update");

        model.addAttribute("boardForm", boardForm);

        return "admin/board/form";
    }

    @GetMapping("/delete/{bId}")
    public String delete(@PathVariable String bId, Model model){
        commonProcess(model, "게시글 삭제");
        configDeleteService.delete(bId);

        //삭제 완료 후 게시판 목록으로 이동
        return "redirect:/admin/board";
    }

    @PostMapping("/save")
    public String save(@Valid BoardForm boardForm, Errors errors, Model model){
        String mode = boardForm.getMode();
        commonProcess(model, mode !=null && mode.equals("update") ? "게시판 수정" : "게시판 등록");

        try{
            configSaveService.save(boardForm, errors);
        }catch (CommonException e){
            errors.reject("BoardConfigError", e.getMessage());
        }

        if(errors.hasErrors()){
            return "admin/board/form";
        }

        return "redirect:/admin/board"; //게시판 등록 후 목록으로 이동
    }

    private void commonProcess(Model model, String title){
        String URI = request.getRequestURI();

        // 서브 메뉴 처리
        String subMenuCode = Menus.getSubMenuCode(request);
        subMenuCode = title.equals("게시판 수정") ? "register" : subMenuCode;
        model.addAttribute("subMenuCode", subMenuCode);

        List<MenuDetail> submenus = Menus.gets("board");
        model.addAttribute("submenus", submenus);

        model.addAttribute("pageTitle", title);
        model.addAttribute("title", title);
        model.addAttribute("menuCode", "board");
    }
}
