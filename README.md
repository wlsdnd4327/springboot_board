# 스프링 부트 프로젝트
- 강의 신청, 강의 후기 및 관련 정보 커뮤니티.

## 프로젝트 기간
- 2023.06.01 ~

## 기술 스택

## WireFrame

## ERD

## Trouble Shooting

### Day01 2023.06.01
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
  - 로그인 성공 및 실패 시 세션처리 및 이동 페이지 Handling - spring 시큐리티
  - 아이디 저장 기능 추가 - 쿠키 애너테이션