package org.koreait.commons.utils;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.koreait.commons.constants.Role;
import org.koreait.entities.member.MemberEntity;
import org.koreait.dtos.member.MemberInfo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class MemberUtil {

    private final HttpSession session;

    /**
     * 로그인 여부 확인
     */
    public boolean isLogin(){
        return getMember() != null;
    }

    /**
     * 관리자 여부
     */
    public boolean isAdmin(){
        return isLogin() && getMember().getRole() == Role.ADMIN;
    }

    public MemberInfo getMember(){
        MemberInfo memberInfo = (MemberInfo) session.getAttribute("memberInfo");
        return memberInfo;
    }

    public boolean isMine(long memberNo) {
        if (isLogin() || isAdmin()) {
            return memberNo == getMember().getMemberNo();
        }
        return false;
    }

    public MemberEntity getEntity(){
        if(isLogin()){
            MemberEntity member = new ModelMapper().map(getMember(), MemberEntity.class);
            return member;
        }

        return null;
    }
}
