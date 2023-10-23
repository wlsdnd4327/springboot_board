package org.koreait.services.board;

import lombok.RequiredArgsConstructor;
import org.koreait.commons.constants.Role;
import org.koreait.commons.utils.MemberUtil;
import org.koreait.entities.board.Board;
import org.koreait.exceptions.board.BoardConfigNotExistException;
import org.koreait.exceptions.board.BoardNotAllowAccessException;
import org.koreait.repositories.BoardRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardConfigInfoService {

    private final BoardRepository boardRepository;
    private MemberUtil memberUtil;

    public Board get(String bId, String location){
        return get(bId, false, location);
    }

    /**
     * 게시판 설정 조회
     * @param bId
     * @param isAdmin   :true - 권한 체크x
     *                  :false - 권한 체크, location으로 목록, 보기, 글쓰기, 답글, 댓글
     * @param location : 기능 위치(list, view, reply, comment)
     * @return
     */
    public Board get(String bId, boolean isAdmin, String location){
        Board board = boardRepository.findById(bId).orElseThrow(BoardConfigNotExistException::new);

        if(!isAdmin){   //권한체크
            accessCheck(board, location);
        }

        return board;
    }

    public Board get(String bId, boolean isAdmin){  //관리자일 경우 location x
        return get(bId, isAdmin, null);
    }

    /**
     * 접근 권한 체크
     * @param board
     */
    private void accessCheck(Board board, String location){

        //use - false : 모든 항목 접근 불가(관리자 제외)
        if(!board.isUse() && !memberUtil.isAdmin()){
            throw new BoardNotAllowAccessException();
        }

        Role role = Role.ALL;
        if(location.equals("list")){    //목록 접근 권한
            role  = board.getListAccessRole();

        } else if (location.equals("view")){    //게시글 접근 권한
            role = board.getViewAccessRole();

        } else if (location.equals("write")){   //  글쓰기 권한
            role = board.getWriteAccessRole();

        } else if (location.equals("comment")) {    //댓글 권한
            role = board.getCommentAccessRole();
        }

        if(role == Role.MEMBER && !memberUtil.isLogin() || role == Role.ADMIN && !memberUtil.isAdmin()){    //접근권한이 없을 경우 예외발생
            throw new BoardNotAllowAccessException();
        }
    }
}
