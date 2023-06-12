# 스프링 부트 프로젝트
- 강의 신청, 강의 후기 및 관련 정보 커뮤니티.

## 프로젝트 기간
- 2023.06.01 ~

## 기술 스택

## WireFrame

## ERD

## Trouble Shooting

### 구현 내용
- 스프링 부트 기본 설정 완료
- 관리자 페이지
  - 레이아웃 / 아웃라인
  - style.css
  - 사이트 설정 저장 기능 구현중

기본 설정
- 사이트 설정 저장 / 수정 / 조회 / 삭제 기능 구현 & interceptor 구현.
- BaseEntity & BaseMemberEntity 추가
- Regex 이넘 클래스 : 정규표현식을 이넘 객체에 저장.

회원 관련 기능 구현
- 회원가입 기능
  - 회원 정보 추가/ 수정
- 로그인 기능
  - 회원 정보 조회(UserDetails & UserDetailsService 구현) - spring 시큐리티
    - List<GrantedAuthority> authorities
      - 시큐리티 인가 기능에서 사용하는 값.(회원의 권한 확인)
      - .requestMatchers("/admin/**").hasAuthority("ADMIN") // 사용 예시
    - MemberUtil : 회원 인가관련 편의 기능.
  - 로그인 성공 및 실패 시 세션처리 및 이동 페이지 Handling - spring 시큐리티
  - 아이디 저장 기능 추가 - 쿠키 애너테이션
- 회원탈퇴 기능
  - 회원 정보 삭제
  - 회원 탈퇴 확인 js 추가(정말로 탈퇴하시겠습니까?)
- 회원 아이디 & 비밀번호 찾기 기능
  - repository에 추가.
  - MySql index로 조회하는 법 복습...