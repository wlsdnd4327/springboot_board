package org.koreait.controllers.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FindPwForm {
    @NotBlank
    private String memberId;
    @NotBlank @Email
    private String email;
}
