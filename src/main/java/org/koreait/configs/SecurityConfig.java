package org.koreait.configs;

import jakarta.servlet.http.HttpServletResponse;
import org.koreait.services.member.LoginFailureHandler;
import org.koreait.services.member.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig{

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.formLogin(f->f
                        .loginPage("/member/login")
                        .usernameParameter("memberId")
                        .passwordParameter("memberPw")
                        .successHandler(new LoginSuccessHandler())
                        .failureHandler(new LoginFailureHandler())
                ).logout(f->f
                        .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                        .logoutSuccessUrl("/member/login")
                );

        http.authorizeHttpRequests(f->f
                .requestMatchers("/mypage/**").authenticated() // 회원 전용
//                .requestMatchers("/instructor/**").hasAuthority("INSTRUCTOR")// 강사 전용 - 강의등록 페이지
//                .requestMatchers("/admin/**", "/instructor/**").hasAuthority("ADMIN") // 관리자 전용 -> 개발 중 disabled
                .anyRequest().permitAll() // 그 외의 모든 페이지 접근 가능.
        );

        http.exceptionHandling(f->f
                .authenticationEntryPoint((req,resp,e)->{
                    String uri = req.getRequestURI();
                    if(uri.indexOf("/admin") != -1){
                        resp.sendError(HttpServletResponse.SC_UNAUTHORIZED,"NOT AUTHORIZED");
                    }else{
                        String redirectUrl = req.getContextPath() + "/member/login";
                        resp.sendRedirect(redirectUrl);
                    }
                })
        );

        http.headers(f-> f
                .frameOptions(d->d
                        .sameOrigin())
        );

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return w->w.ignoring().requestMatchers("/css/**","/js/**","/images/**","/errors/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
