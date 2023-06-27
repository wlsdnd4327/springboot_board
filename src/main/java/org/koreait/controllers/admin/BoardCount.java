package org.koreait.controllers.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 페이지당 개시글 수 설정
 */
@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardCount {
    private int page = 1;

    private int limit = 20;
}
