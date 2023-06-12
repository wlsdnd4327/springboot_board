package org.koreait.commons;

import jakarta.servlet.http.HttpSession;
import org.koreait.commons.constants.Role;
import org.koreait.entities.MemberEntity;
import org.koreait.models.member.MemberInfo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MemberUtil {

    HttpSession session;

    /**
     * 로그인 여부 확인
     */
    public boolean isLogin(){
        return getMember() != null;
    }

    public boolean isInstructor(){
        return isLogin() && getMember().getRole() == Role.INSTRUCTOR;
    }

    public boolean isAdmin(){
        return isLogin() && getMember().getRole() == Role.ADMIN;
    }

    public MemberInfo getMember(){
        MemberInfo memberInfo = (MemberInfo) session.getAttribute("memberInfo");
        return memberInfo;
    }

    public MemberEntity getEntity(){

        if(isLogin()){
            MemberEntity member = new ModelMapper().map(getMember(), MemberEntity.class);
            return member;
        }

        return null;
    }
}
