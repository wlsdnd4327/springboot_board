package org.koreait.controllers.member;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FindForm {

    @NotNull
    private String memberNm;

    @NotNull
    private String email;

    @NotNull
    private String memberId;
}
