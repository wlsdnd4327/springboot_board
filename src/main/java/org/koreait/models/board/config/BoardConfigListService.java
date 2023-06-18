package org.koreait.models.board.config;

import lombok.RequiredArgsConstructor;
import org.koreait.controllers.admin.BoardCount;
import org.koreait.entities.Board;
import org.koreait.repositories.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import static org.springframework.data.domain.Sort.Order.desc;

/**
 * 게시판 설정 목록
 */
@Service
@RequiredArgsConstructor
public class BoardConfigListService {

    private final BoardRepository boardRepository;

    public Page<Board> gets(BoardCount boardCount){
        int page = boardCount.getPage();
        int limit = boardCount.getLimit();
        page = page < 1 ?  1: page;
        limit = limit < 1 ? 20 : limit;

        Pageable pageable = PageRequest.of(page -1, limit, Sort.by(desc("createdAt")));
        Page<Board> data = boardRepository.findAll(pageable);

        return data;
    }
}
