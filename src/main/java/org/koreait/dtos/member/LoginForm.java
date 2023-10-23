package org.koreait.dtos.member;

import lombok.Data;


@Data
public class LoginForm {

    private String memberId;

    private String memberPw;

    private boolean saveId;

    private String redirectUrl;
}
