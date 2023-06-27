package org.koreait.controllers.member;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PwResetForm {
    @NotBlank
    private String memberPw;
    @NotBlank
    private String memberPwRe;
}
