package org.koreait.controllers.post;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.koreait.commons.CommonException;
import org.koreait.commons.utils.MemberUtil;
import org.koreait.dtos.post.PostForm;
import org.koreait.dtos.post.PostSearch;
import org.koreait.entities.board.Board;
import org.koreait.entities.member.MemberEntity;
import org.koreait.entities.post.Post;
import org.koreait.exceptions.board.BoardNotAllowAccessException;
import org.koreait.repositories.PostRepository;
import org.koreait.services.board.BoardConfigInfoService;
import org.koreait.services.post.*;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
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
    private final MemberUtil memberUtil;
    private final PostListService listService;
    private final PostDeleteService deleteService;
    private final PostRepository repository;
    private Board board;    //게시판 설정

    /**
     * 게시글 목록
     * @param bId
     * @return
     */
    @GetMapping("/list/{bId}")
    public String list(@PathVariable String bId, Model model, @RequestParam(value = "page", defaultValue = "0") int page, @ModelAttribute PostSearch postSearch){
        commonProcess(bId, "list", model, "게시글 목록");
        postSearch.setBid(bId);
        Page<Post> data = listService.gets(postSearch);
        model.addAttribute("items", data.getContent());

        //페이징 처리
        Page<Post> paging = listService.getList(page);
        model.addAttribute("paging", paging);

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

        if(memberUtil.isLogin()){
            postForm.setPoster(memberUtil.getMember().getMemberNm());
        }

        /*  글 쓰기시에 로그인 여부 체크 - 로그인 하지 않은 경우 로그인 페이지 이동 */
        if (!memberUtil.isLogin()) {
            return "redirect:/member/login?redirectUrl=/board/write/" + bId;
        }

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

        //수정,삭제 권한체크
        updateDeletePossibleCheck(post);

        PostForm postForm = new ModelMapper().map(post, PostForm.class);
        postForm.setNotice(post.isNotice());
        model.addAttribute("postForm", postForm);

        return "board/update";
    }

    @PostMapping("/save")
    public String save(@Valid PostForm postForm, Errors errors, boolean isNotice, Model model){
        postForm.setNotice(isNotice);
        Long id = postForm.getId();
        String mode = id == null ? "write" : "update";
        commonProcess(postForm.getBId(), mode, model, mode !=null && mode.equals("update") ? "게시글 수정" : "게시글 등록");

        if(errors.hasErrors()){
            return "board/" + mode;
        }

        saveService.save(postForm);

        //return "board/" + mode;
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

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model){
        Post post = infoService.get(id, "update");
        board = post.getBoard();
        commonProcess(board.getBId(), "update", model, "게시글 삭제");

        //삭제 권한 체크
        updateDeletePossibleCheck(post);

        //삭제처리
        deleteService.delete(id);

        //삭제 완료 후 게시글 목록으로 이동
        return "redirect:/board/list/" + board.getBId();
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
            addScript.add("board/fileManger");
            addScript.add("board/form");
        } else if (action.equals("view")) {
            addScript.add("board/comment");
        }

        //공통 필요 속성 추가
        model.addAttribute("board", board); //게시판 설정
        model.addAttribute("addCss", addCss); //css 설정
        model.addAttribute("addScript", addScript); //script 설정

        //타이틀 추가
        model.addAttribute("title", title);
    }

    /**
     * 수정 권한 체크
     * 회원 : 작성한 회원만
     * 관리자 : 모두 가능
     * @param post
     */
    public void updateDeletePossibleCheck(Post post){
        if(memberUtil.isAdmin()){   //관리자
            return;
        }

        MemberEntity member = post.getMember();
        if(!memberUtil.isLogin() || (memberUtil.isLogin() && memberUtil.getMember().getMemberNo() != post.getMember().getMemberNo())){ //글 작성한 회원만
            throw new BoardNotAllowAccessException();
        }

    }

    public void updateDeletePossibleCheck(Long id){
        Post post = infoService.get(id, "update");
        updateDeletePossibleCheck(post);

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
