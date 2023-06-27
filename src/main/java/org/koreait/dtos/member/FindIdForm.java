package org.koreait.dtos.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FindIdForm {

    @NotBlank
    private String memberNm;
    @NotBlank @Email
    private String email;
}
