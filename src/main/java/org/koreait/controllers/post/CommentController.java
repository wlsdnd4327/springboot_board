package org.koreait.controllers.post;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.koreait.commons.utils.MemberUtil;
import org.koreait.dtos.post.CommentForm;
import org.koreait.entities.member.MemberEntity;
import org.koreait.entities.post.Comment;
import org.koreait.exceptions.board.BoardNotAllowAccessException;
import org.koreait.services.post.comment.CommentDeleteService;
import org.koreait.services.post.comment.CommentInfoService;
import org.koreait.services.post.comment.CommentSaveService;
import org.koreait.services.post.PostInfoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board/comment")
public class CommentController {

    private final CommentSaveService saveService;
    private final CommentDeleteService deleteService;
    private final PostInfoService infoService;
    private final MemberUtil memberUtil;
    private final CommentInfoService commentInfoService;

    @ResponseBody
    @GetMapping("/ajax/{id}")
    public String ajax_view(@PathVariable Long id) {
        Comment comment = commentInfoService.get(id);
        return comment.getContent();
    }

    @ResponseBody
    @PostMapping("/ajax_update")
    public void ajax_update(Long id, String content) {
        saveService.update(id, content);
    }

    @PostMapping
    public String save(@Valid CommentForm form, Errors errors, Model model) {
        ResourceBundle bundle = ResourceBundle.getBundle("messages.validations");
        if (errors.hasErrors()) {
            String message = errors.getAllErrors().stream().map(o -> bundle.getString(o.getCode())).collect(Collectors.joining("\\n"));
            throw new IllegalArgumentException(message);
        }

        saveService.save(form);

        /** 댓글 작성 및 수정이 완료 되면 새로고침 */
        model.addAttribute("script", "parent.location.reload();");

        return "commons/execute_script";
    }

    @GetMapping("/{id}/update")
    public String update(@PathVariable Long id, Model model){
        Comment comment = commentInfoService.get(id);
        updateDeletePossibleCheck(comment);

        CommentForm commentForm = new ModelMapper().map(comment, CommentForm.class);
        model.addAttribute("commentForm", commentForm);

        return "commons/execute_script";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model){
        Comment comment = commentInfoService.get(id);
        updateDeletePossibleCheck(comment);

        deleteService.delete(id);
        model.addAttribute("script", "parent.location.reload();");
        return  "commons/execute_script";
    }

    public void updateDeletePossibleCheck(Comment comment){
        if(memberUtil.isAdmin()){   //관리자
            return;
        }

        MemberEntity member = comment.getMember();
        if(!memberUtil.isLogin() || (memberUtil.isLogin() && memberUtil.getMember().getMemberNo() != comment.getMember().getMemberNo())){ //글 작성한 회원만
            throw new BoardNotAllowAccessException();
        }

    }

    @ExceptionHandler(Exception.class)
    public String errorHandler(Exception e, Model model) {
        String script = String.format("alert('%s');", e.getMessage());
        model.addAttribute("script", script);
        e.printStackTrace();
        return "commons/execute_script";
    }
}
