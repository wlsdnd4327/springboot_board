package org.koreait.commons.interceptors;

import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.koreait.services.SiteConfigInfoService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

/**
 * 사이트 공통 설정
 */
@Component("siteConf")
@RequiredArgsConstructor
public class SiteInterceptor implements HandlerInterceptor {

    private final SiteConfigInfoService infoService;
    private final HttpServletRequest request;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Map<String,String> siteConfigs = infoService.get("siteConfig", new TypeReference<Map<String, String>>() {});
        request.setAttribute("siteConfig",siteConfigs);

        return true;
    }

    public String get(String name){
        Map<String,String> siteConfigs = (Map<String, String>) request.getAttribute("siteConfig");
        String value = siteConfigs == null ? "" : siteConfigs.get(name); // key로 value값 조회
        return value;
    }
}
