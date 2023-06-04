package org.koreait.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 회원가입 & 로그인 및 스프링 시큐리티 구현 시 코드 추가 예정.
 */

@Component
@RequiredArgsConstructor
public class AwareAuditingImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.empty();
    }
}