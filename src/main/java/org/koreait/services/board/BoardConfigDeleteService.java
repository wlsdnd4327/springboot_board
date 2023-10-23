package org.koreait.services.board;

import lombok.RequiredArgsConstructor;
import org.koreait.entities.board.Board;
import org.koreait.exceptions.board.BoardConfigNotExistException;
import org.koreait.repositories.BoardRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardConfigDeleteService {

    private final BoardRepository boardRepository;

    public void delete(String bId){
        Board board = boardRepository.findById(bId).orElseThrow(BoardConfigNotExistException::new);
        boardRepository.delete(board);
    }
}
