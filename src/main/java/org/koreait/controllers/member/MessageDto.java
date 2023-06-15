package org.koreait.controllers.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;
@Data
@AllArgsConstructor
public class MessageDto {

    private String message; // 사용자에게 전달할 메시지
    private String redirectUri; // 리다이렉트 URI
    private RequestMethod method; // Http 요청 메서드
    private Map<String,Object> data; // 화면으로 전달할 데이터
}
