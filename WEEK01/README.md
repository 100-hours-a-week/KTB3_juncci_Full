# Library Service

Java 콘솔 환경에서 동작하는 **도서 대여 서비스**입니다.  
사용자와 관리자가 각자의 역할을 통해 책 대여/반납/관리 기능이 있습니다.

---

## 설계 개요

### 요구 사항
- **역할 구분**
  - **사용자(User)** : 책 대출, 반납, 내 대여 현황 조회
  - **관리자(Librarian)** : 책 등록/삭제/수정, 전체 책 목록 관리
- **초기 데이터**
  - 프로그램 실행 시 `Novel`, `Comic`, `Magazine` 3권이 자동 등록됩니다.
- **대여 흐름**
  - 프로그램 시작 → **사용자 / 관리자** 선택 → 각 메뉴 실행

### 예외 처리
| 구분 | 상황 | 처리 방식 |
|------|------|-----------|
| 대여 | 이미 대여 중인 책 | “이미 대여 중입니다.” 출력 후 대여 불가 |
| 반납 | 대여하지 않은 책 ID 입력 | “대여 목록에 없음” 출력 후 반납 불가 |
| 관리자 | 존재하지 않는 책 ID 수정/삭제 | “해당 ID의 책 없음” 출력 |
| 관리자 | 대여 중인 책 삭제 시도 | “삭제 불가: 대여 중인 책” 출력 |
| 등록 | 중복 ID 입력 | “이미 존재하는 ID입니다.” 출력 |

---

## 클래스 관계
<img width="2163" height="1314" alt="image" src="https://github.com/user-attachments/assets/cc74f634-5a09-4480-88c4-465412e7577a" />


**주요 관계**
- `Book` : 추상 클래스, 모든 책의 공통 속성 및 기능 정의
- `Novel`, `Comic`, `Magazine` : `Book`을 상속, 각 종류별 `preview()` 구현
- `User` : 사용자, 책 대여/반납/조회 기능
- `Librarian` : `User` 상속 → 관리자 전용 CRUD 기능 추가
- `Library` : 전체 도서 컬렉션 관리 (검색, 목록, 초기 데이터)

---

## 폴더 구조
<img width="426" height="360" alt="image" src="https://github.com/user-attachments/assets/6ddb6d42-69d3-4c05-a1c8-69cad9809f59" />

---

## 실행 흐름

**프로그램 시작**  
   - `1. 사용자` / `2. 관리자` / `0. 종료` 중 선택

**사용자 선택 시**
   - 사용자 이름 입력
   - 메뉴
     - `1. 대출` → 대출 가능한 책 목록(ID 포함) → ID 입력
     - `2. 반납` → 내 대여 목록 → ID 입력
     - `3. 내 대여 현황` → 내가 빌린 책 출력
     - `4. 대출 가능 목록` → 현재 대여 가능 도서 출력
     - `0. 메인으로 돌아가기`

**관리자 선택 시**
   - 관리자 이름 입력
   - 메뉴
     - `1. 책 등록` → 종류/ID/제목/저자 입력
     - `2. 책 삭제` → 삭제할 책 ID 입력
     - `3. 책 수정` → 수정할 책 ID 및 새 제목/저자 입력
     - `4. 전체 책 목록` → 전체 도서 출력
     - `0. 메인으로 돌아가기`

---
## 실행 화면

| 구분 | 화면 | 설명 |
|------|------|------|
| **사용자 첫 화면** | <img src="https://github.com/user-attachments/assets/6da9f5ff-9eec-4ad4-a9ea-6b55d0e0a4d6" width="140"/> | 사용자/관리자 선택 후 사용자 모드 진입 |
| **대여** | <img src="https://github.com/user-attachments/assets/569ec797-a1f9-4bd7-ad28-ee2e1557fd74" width="250"/> | 대출 가능한 책 목록(ID 포함)에서 선택 후 대여 |
| **반납** | <img src="https://github.com/user-attachments/assets/9653f4ab-e454-4d62-aada-cba3d01f870b" width="210"/> | 내 대여 목록 중 반납할 책 ID 입력 |
| **대여 현황** | <img src="https://github.com/user-attachments/assets/1bfcca00-31a3-43c8-9efd-380bebff2486" width="230"/> | 현재 내가 빌린 책 목록 조회 |
| **대여 가능 목록** | <img src="https://github.com/user-attachments/assets/6b725bde-9199-4ed2-bba6-ce84fc5fac86" width="260"/> | 현재 대출 가능한 모든 도서 목록 |



## 실행 화면 – 관리자 모드

| 구분 | 화면 | 설명 |
|------|------|------|
| **첫 화면** | <img src="https://github.com/user-attachments/assets/c6e97684-6c79-43f5-8837-f1ba14ab6b2a" width="140"/> | 프로그램 시작 후 **관리자 모드** 진입 화면 |
| **책 등록** | <img src="https://github.com/user-attachments/assets/eafaede5-0f47-4f26-8f87-33006caac1c0" width="180"/>| 책 등록 메뉴 선택 후 정보 입력 |
| **책 등록 확인** | <img src="https://github.com/user-attachments/assets/d60fd16f-acb0-417a-8d9b-5d12eff858ef" width="220"/>| 책 등록 완료 및 목록 반영 |
| **책 삭제** | <img src="https://github.com/user-attachments/assets/c8ece6ea-6907-421c-9b4e-dcc6b2521b90" width="210"/>| 삭제할 책 ID 입력 |
| **책 삭제 확인** | <img src="https://github.com/user-attachments/assets/13a3d709-5c58-453e-b5fb-51ac963dd26d" width="210"/> | 삭제 완료 및 결과 출력 |
| **책 수정** | <img src="https://github.com/user-attachments/assets/dc3adaf9-b43f-4a5d-a71b-584e679b9ef8" width="210"/>| 수정할 책 ID 및 새 제목/저자 입력 |
| **책 수정 확인** | <img src="https://github.com/user-attachments/assets/45b8122a-390a-434b-8b91-6e0d8bac156c" width="220"/>| 수정 완료 및 결과 출력 |



## 참고 자료

| 문법 | 설명 | 공식 문서 |
|------|------|-----------|
| **람다식 (`->`)** | 매개변수를 받아 단일 메서드를 간결하게 표현하는 문법.<br/>예: `list.forEach(item -> System.out.println(item));` | [Lambda Expressions (Java Tutorials)](https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html) |
| **메서드 참조 (`::`)** | 기존에 정의된 메서드를 이름으로 직접 참조하여 호출하는 문법.<br/>람다식이 오직 메서드 호출만 할 때 더 간결하게 사용.<br/>예: `list.forEach(System.out::println);` | [Method References (Java Tutorials)](https://docs.oracle.com/javase/tutorial/java/javaOO/methodreferences.html) |







