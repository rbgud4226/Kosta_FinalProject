# KOSTA FINAL PROJECT<br/>(웹 풀스택 개발)

## 목차
* [프로젝트 개요](#프로젝트-개요)
* [프로젝트 소개](#프로젝트-소개)
* [상세 기능](#상세-기능)
* [프로젝트 ERD](#프로젝트-ERD)
* [프로젝트 흐름도](#프로젝트-흐름도)
* [담당영역 화면](#담당영역-화면)

## 프로젝트 개요

> #### 프로젝트 목적 : `java`기반의 `Spring Boot` 프레임워크를 활용한 웹 풀스택 개발

> #### 프로젝트 주제 : `HRD&GROUPWARE SYSTEM` 개발
> #### 프로젝트 목표
 * 사내 사용자가 각기 별개의 작업 환경에서 통합된 하나의 프로젝트를 동시에 수행할 수 있도록 도움을 주는 소프트웨어를 제작
 * 관리자와 사원의 영역을 나누어 업무를 정형화함으로써 업무 생산성 향상을 목적에 둠

> #### 프로젝트 기간 : 2024.05.24 ~ 2024.06.26 (22 일), 2024.07.04 ~ 2024.07.14(React 적용)
> #### 개발 구성원 : `BE/FE` 5명 (각 담당 영역의 BE/FE 작업을 진행)

> #### 개발 담당 영역
 * `공지게시판, 실시간 메신저, AWS베포` 영역
 
 |이름|담당 영역|
 |:---|:---|
 |최완민(팀장)|`USERS`, `MEMBERS`, `WORK&EDU`|
 |진하경|`WORK_RECORD`|

 * `GROUPWARE SYSTEM` 영역
 
 |이름|담당 영역|
 |:---|:---|
 |백승훈|`DOCX`|
 |정윤석|`MAIL`,`SCHEDULER`|
 |황규형|`CHAT`|

> #### 기술 스택

 |영역|기술|
 |:---|:---|
 |Front-end|`html`, `CSS`, `javascript`, `thymeleaf`, `JQuery`|
 |Back-end |`Java`, `Spring Boot`, `JPA`, `maven`, `Spring Security`, `Websocket`, `Spring Batch`, `Scheduler`, `JDBC`, `Lombok`|
 |DB|`Oracle`, `MariaDB` |
 |IDE|`Eclipse`, `Intellij`, `Oracle Sql Developer`|
 |SCM|`Git&Github`|
 |SEVER|`Ubuntu`|
 |ETC.|`Notion`, `KakaoTalk`, `Linux`, `Oracle VMware`|

## 프로젝트 소개
> #### 관리 시스템 정의
![kosta_final_pjt_ppt_ver_fianl_1](https://github.com/choiwanmin/kosta_final_pjt/assets/111493653/4eea3baf-dee1-4870-a23b-0be259766d20)

> #### 협업 시스템 정의
![kosta_final_pjt_ppt_ver_fianl_2](https://github.com/choiwanmin/kosta_final_pjt/assets/111493653/1251d3cb-8b0b-4156-887f-bc72f0a11aac)

## 상세 기능
![kosta_final_pjt_ppt_ver_fianl_detail](https://github.com/choiwanmin/kosta_final_pjt/assets/111493653/d0703273-192e-4ec7-a438-5878009a7adc)
## 프로젝트 ERD
![파이널erd](https://github.com/user-attachments/assets/cf7108cb-3928-4efe-bade-31c4395794d5)

## 프로젝트 흐름도
![kosta_final_pjt_ppt_ver_fianl_flow](https://github.com/choiwanmin/kosta_final_pjt/assets/111493653/ea5a340a-7cc2-4041-86d2-10a8f2ea69bb)

## 담당영역 화면
> #### 채팅방 화면
![채팅화면](https://github.com/user-attachments/assets/601596ec-e4bb-4d22-b960-fb7dad3ff624)
<br/>
채팅방 이름변경<br/>
![채팅방 이름변경](https://github.com/user-attachments/assets/d45393da-80ca-4779-96cb-7f89cbc61a01)

> #### 채팅방 초대 및 생성 화면
모달창을 통해 채팅방 생성 및 사용자 초대 기능 제공
![초대및생성](https://github.com/user-attachments/assets/cd8aed73-4b49-40ce-9112-1054d3ea111a)


> #### 단체방 화면
![단체방](https://github.com/user-attachments/assets/484ee636-58bb-42cb-821e-61d4a402da14)


> #### 사용자 정보 화면
![사용자정보](https://github.com/user-attachments/assets/8f166a27-c83a-4c4f-b75b-4e2edb76e43d)


> #### 파일업로드
![업로드](https://github.com/user-attachments/assets/d86e48e0-91b3-46ca-8052-03704a1e1e43)
![업로드결과](https://github.com/user-attachments/assets/427910ff-7597-4f75-a441-81e470ac502e)


> #### 공지게시판 리스트(전체부서, 내부서)
자신이 작성한 공지만 삭제 가능, 전체/내 부서로 나눠서 리스트 출력
전체공지<br/>
![공지리스트](https://github.com/user-attachments/assets/ca80e62c-685b-4a63-af16-47d9c0114956)<br/>
내 부서 공지<br/>
![공지리스트2](https://github.com/user-attachments/assets/fb117b18-eb90-498c-8ce5-ab491d5a479a)


> #### 공지 작성
![공지작성](https://github.com/user-attachments/assets/e0f7f299-8e1f-4733-8803-b6584243388e)
