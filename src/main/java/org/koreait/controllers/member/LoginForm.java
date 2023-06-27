package org.koreait.controllers.member;

import lombok.Data;


@Data
public class LoginForm {

    private String memberId;

    private String memberPw;

    private boolean saveId;
}
