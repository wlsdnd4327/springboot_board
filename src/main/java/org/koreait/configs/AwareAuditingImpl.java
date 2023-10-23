package org.koreait.configs;

import lombok.RequiredArgsConstructor;
import org.koreait.commons.utils.MemberUtil;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AwareAuditingImpl implements AuditorAware<String> {

    private final MemberUtil memberUtil;

    @Override
    public Optional<String> getCurrentAuditor() {

        String memberId = memberUtil.isLogin() ? memberUtil.getMember().getMemberId() : null;

        return Optional.ofNullable(memberId);   //null값을 허용
    }
}
