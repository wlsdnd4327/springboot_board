package org.koreait.controllers.boards;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.koreait.commons.CommonException;
import org.koreait.controllers.admin.BoardForm;
import org.koreait.entities.Board;
import org.koreait.entities.Post;
import org.koreait.models.board.config.BoardConfigInfoService;
import org.koreait.models.post.PostInfoService;
import org.koreait.models.post.PostSaveService;
import org.koreait.models.post.UpdateHitService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class PostController {

    private final BoardConfigInfoService boardConfigInfoService;
    private final PostInfoService infoService;
    private final PostSaveService saveService;
    private final HttpServletResponse response;
    private final UpdateHitService updateHitService;

    private Board board;    //게시판 설정

    /**
     * 게시글 목록
     * @param bId
     * @return
     */
    @GetMapping("/list/{bId}")
    public String list(@PathVariable String bId, Model model){
        commonProcess(bId, "list", model, "게시글 목록");

        return "board/list";
    }

    /**
     * 게시글 작성
     * @param bId
     * @return
     */
    @GetMapping("/write/{bId}")
    public String write(@PathVariable String bId, @ModelAttribute PostForm postForm, Model model){
        commonProcess(bId, "write", model, "게시글 작성");
        postForm.setBId(bId);

        return "board/write";
    }

    /**
     * 게시글 수정
     * @param id
     * @return
     */
    @GetMapping("/{id}/update")
    public String update(@PathVariable Long id, Model model){
        Post post = infoService.get(id, "update");
        Board board = post.getBoard();
        commonProcess(board.getBId(), "update", model, "게시글 수정");

        PostForm postForm = new ModelMapper().map(post, PostForm.class);
        model.addAttribute("postForm", postForm);

        return "board/update";
    }

    @PostMapping("/save")
    public String save(@Valid PostForm postForm, Errors errors, Model model){
        Long id = postForm.getId();
        String mode = id == null ? "write" : "update";
        commonProcess(postForm.getBId(), mode, model, mode !=null && mode.equals("update") ? "게시판 수정" : "게시판 등록");

        if(errors.hasErrors()){
            return "board/" + mode;
        }
        saveService.save(postForm);

        return "redirect:/board/view/" + postForm.getId(); //글 작성 후 뷰로 이동
    }

    /**
     * 게시글 보기
     * @param id
     * @return
     */
    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, Model model){
        Post post = infoService.get(id);
        Board board = post.getBoard();
        commonProcess(board.getBId(), "view", model, "게시글 보기");

        model.addAttribute("post", post);
        model.addAttribute("board", board);

        updateHitService.update(id);    //게시글 조회수 업데이트

        return "board/view";
    }

    private void commonProcess(String bId, String action, Model model, String title){
        /**
         * 1. bId로 게시판 설정 조회
         * 2. action - write, update : 공통 script, css
         *           - 에디터 사용 -> 에디터 스크립트 추가, 미사용시 추가x
         *           - update : 본인이 작성한 글만 수정(관리자는 다 가능)
         *           - 회원  : 회원번호
         */
        board = boardConfigInfoService.get(bId, action);
        List<String> addCss = new ArrayList<>();
        List<String> addScript = new ArrayList<>();

        //공통 css 추가
        addCss.add("board/style");

        //글 작성, 수정시 필요한 script 추가
        if(action.equals("write") || action.equals("update")){
            if(board.isUseEditor()){    //에디터 사용하는 경우
                addScript.add("ckeditor/ckeditor");
            }
            addScript.add("board/form");
        }

        //공통 필요 속성 추가
        model.addAttribute("board", board); //게시판 설정
        model.addAttribute("addCss", addCss); //css 설정
        model.addAttribute("addScript", addScript); //script 설정

        //타이틀 추가
        model.addAttribute("title", title);
    }

    //예외 발생 처리
    @ExceptionHandler(CommonException.class)
    public String errorHandler(CommonException e, Model model){
        e.printStackTrace();

        String message = e.getMessage();
        HttpStatus status = e.getStatus();
        response.setStatus(status.value()); //기본 응답 상태코드

        String script = String.format("alert('%s');history.back();", message);
        model.addAttribute("script", script);

        return "commons/execute_script";
    }
}
