package org.koreait.dtos.front;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IndexForm {

    private int page = 0;
    private int limit = 6; //한페이지당 최대 글 수
}
