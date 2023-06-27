package org.koreait.services.board;

import lombok.RequiredArgsConstructor;
import org.koreait.commons.constants.Role;
import org.koreait.dtos.admin.board.BoardForm;
import org.koreait.entities.board.Board;
import org.koreait.exceptions.board.DuplicateBoardConfigException;
import org.koreait.repositories.BoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

/**
 * 게시판 설정, 추가
 */
@Service
@RequiredArgsConstructor
public class BoardConfigSaveService {
    private final BoardRepository boardRepository;

    public void save(BoardForm boardForm){  //단위 테스트시 오류 발생하기 때문에 추가
        save(boardForm, null);
    }

    public void save(BoardForm boardForm, Errors errors){

        if(errors!= null && errors.hasErrors()){ //에러가 있을 경우 하단 로직 실행x
            return;
        }

        /**
         * 게시판 설정 조회
         * 게시판 등록 모드인 경우 중복 여부 체크
         */
        String bId = boardForm.getBId();
        Board board = boardRepository.findById(bId).orElseGet(Board::new);

        String mode = boardForm.getMode();
        if((mode == null || !mode.equals("update")) && board.getBId() != null){ //게시판 등록 -> 중복 여부 체크
            throw new DuplicateBoardConfigException();
        }

        board.setBId(bId);
        board.setBName(boardForm.getBName());
        board.setUse(boardForm.isUse());
        board.setRowsOfPage(boardForm.getRowsOfPage());
        board.setShowViewList(boardForm.isShowViewList());
        board.setCategory(boardForm.getCategory());
        board.setListAccessRole(Role.valueOf(boardForm.getListAccessRole()));
        board.setViewAccessRole(Role.valueOf(boardForm.getViewAccessRole()));
        board.setWriteAccessRole(Role.valueOf(boardForm.getWriteAccessRole()));
        board.setReplyAccessRole(Role.valueOf(boardForm.getReplyAccessRole()));
        board.setCommentAccessRole(Role.valueOf(boardForm.getCommentAccessRole()));
        board.setUseEditor(boardForm.isUseEditor());
        board.setUseAttachFile(boardForm.isUseAttachFile());
        board.setUseAttachImage(boardForm.isUseAttachImage());
        board.setUseReply(boardForm.isUseReply());
        board.setUseComment(boardForm.isUseComment());

        boardRepository.saveAndFlush(board);
    }
}
