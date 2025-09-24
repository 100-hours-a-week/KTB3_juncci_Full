# Library Service – Java 콘솔 도서 대여 시스템

Java 콘솔 환경에서 동작하는 **도서 대여 서비스**입니다.
사용자(User)와 관리자(Librarian)가 각자의 역할로 대출/반납/관리 기능을 수행합니다. 이번 과제에서는 **“로그인 시간 제한”을 비동기(스레드)로 구현**해, 입력 대기 중에도 세션이 만료되면 **즉시 메인 메뉴로 복귀**하도록 설계/개선했습니다.

---

## 요구 사항 요약

### 역할 구분

* **사용자(User)**: 책 **대출**, **반납**, **내 대여 현황 조회**
* **관리자(Librarian)**: 책 **등록/삭제/수정**, **전체 목록 조회**


## 클래스 설계

### 핵심 엔티티

* `Book` (abstract): 공통 속성/행동 보유 (`borrow()`, `returnBook()`, `introduce()`, `preview()`(abstract))
* `Novel`, `Comic`, `Magazine`: `Book` 상속, **종류별 `preview()` 구현**
* `Library`: 전체 도서 **등록/삭제/수정/검색/목록/더미 데이터 주입**
* `User`: 도서 **대출/반납/내 대출 목록 조회**
* `Librarian`(extends `User`): **관리자 전용 CRUD** 위임(`Library` 사용)

  ### 추가된 엔티티
* `ConsoleView`: 메뉴/메시지 출력
* `ConsoleController`: **흐름 제어**, 입력 처리, **세션 타이머** 연동
* `UserRole`(enum): 메뉴 역할 선택(매직 넘버 제거용)
* `SessionTimerTask`: **비동기 세션 만료 타이머 스레드**


---

## 폴더/패키지 구조

```
src/
 ├─ app/
 │   └─ Main.java
 ├─ domain/
 │   ├─ Book.java
 │   ├─ Novel.java
 │   ├─ Comic.java
 │   ├─ Magazine.java
 │   ├─ User.java
 │   ├─ Librarian.java
 │   └─ Library.java
 ├─ service/
 └─ ui/
     ├─ ConsoleController.java
     ├─ ConsoleView.java
     ├─ UserRole.java
     └─ SessionTimerTask.java   ← 비동기 세션 타이머
```

---

## 비동기(스레드) 도입 – 설계 의도와 변화

### 왜 비동기가 필요했나?

* 콘솔 앱의 `Scanner.nextLine()`은 **블로킹 I/O**입니다.
* 동기 방식에서는 **입력 대기 중엔 아무 것도 못 함** → 세션 제한(로그인 시간 제한)을 적용해도 **입력 끝나야** 만료가 반영됨.
* **요구**: “**입력 대기 중이라도** 시간이 지나면 바로 **세션 만료 → 메인 메뉴 복귀**”.

### 설계 목표

* **입력 대기**와 **세션 타이머**를 **동시에** 수행 → **비동기(스레드)** 필요.
* 타이머는 **1초마다 남은 시간 출력**, 종료 시 **만료 플래그** 세팅.

### 구현 요약

#### 1) `SessionTimerTask` (비동기 타이머)

```java
package ui;

public class SessionTimerTask implements Runnable {
    private final int timeoutSeconds;
    private volatile boolean expired = false; // 스레드 간 가시성 보장
    private Thread thread;

    public SessionTimerTask(int timeoutSeconds) {
        this.timeoutSeconds = timeoutSeconds;
    }

    public void start() {
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        expired = true;
        if (thread != null) thread.interrupt();
    }

    public boolean isExpired() {
        return expired;
    }

    @Override
    public void run() {
        try {
            for (int i = timeoutSeconds; i > 0; i--) {
                if (expired) return;
                System.out.println("[시스템] 남은 시간: " + i + "초");
                Thread.sleep(1000);
            }
            System.out.println("[시스템] 세션이 만료되었습니다. 메인 메뉴로 돌아갑니다.");
            expired = true;
        } catch (InterruptedException ignored) {}
    }
}
```

#### 2) `ConsoleController`에서 사용

```java
SessionTimerTask timer = new SessionTimerTask(5); // 사용자 5초, 관리자 15초 등
timer.start();

while (!timer.isExpired()) {
    view.showUserMenu(0);             // 메뉴 출력 (남은 시간은 타이머가 별도로 출력)
    String input = sc.nextLine();     // 입력 대기(블로킹)
    if (timer.isExpired()) break;     // ⬅ 입력 직후 만료되었으면 조용히 탈출

    // 숫자 파싱 & 메뉴 처리
}

timer.stop(); // 사용자 종료 시 타이머 종료
```

### 효과

* **메인 스레드**: 입력을 기다리는 동안에도
* **타이머 스레드**: 1초마다 카운트다운 & 만료 시 즉시 복귀
* 결과적으로 **입력 중에도 시간 만료가 바로 반영**되는 “비동기 UX” 달성

---

## 1주차 피드백 반영

### 1) 매직 넘버 → `enum`으로 치환 (과제 1주차 피드백 반영)

기존:

```java
int code = ...;
if (code == 1) { /* User */ }
else if (code == 2) { /* Admin */ }
else if (code == 0) { /* Exit */ }
```

개선:

```java
public enum UserRole {
    USER(1), ADMIN(2), EXIT(0);
    private final int code;
    UserRole(int code) { this.code = code; }
    public static UserRole fromCode(int code) {
        for (UserRole r : values()) if (r.code == code) return r;
        return null;
    }
}
```

컨트롤러:

```java
int code = Integer.parseInt(input);
UserRole role = UserRole.fromCode(code);
```

→ 가독성/유지보수성 향상, 잘못된 값 처리 일관성 확보

### 2) **메인에 몰아넣은 코드 정리** → 패키지/레이어 분리 (과제 1주차 피드백 반영)

* `app`(엔트리 포인트), `domain`(도메인 모델), `service`(역할/유즈케이스), `ui`(입출력 & 제어)로 분리
* 의존 방향: `app → ui → service → domain` 단방향
* 효과: 모듈성 향상, 테스트/확장 용이

### 3) 입력 처리 일관화

* `Scanner.nextInt()` ↔ `nextLine()` 섞임으로 인한 버퍼 꼬임 방지
* **모든 입력을 `nextLine()`으로 받고 `Integer.parseInt()`** 로 숫자 변환
* 잘못된 입력은 `try-catch`로 사용자 친화 메시지 출력

---

## 비동기 적용 전/후 비교



---

## 비동기 전/후 차이 설명

- **비동기 적용 전**
  - 로그인 시간 제한을 넣으려고 해도,  
    `Scanner.nextLine()` 입력이 끝날 때까지 **메인 스레드가 블로킹**됨.  
  - 따라서 사용자가 엔터를 치기 전까지 타이머가 실행되지 않아,  
    “세션 만료” 메시지가 입력 완료 후에만 출력되는 문제가 있었음.  
  - 즉, **시간 제한 기능이 실제로는 동작하지 않는 상태**였음.  

- **비동기 적용 후**
  - 입력을 받는 스레드와 **타이머 스레드(Thread)** 를 분리.  
  - 타이머 스레드는 1초마다 남은 시간을 출력하고,  
    제한 시간이 지나면 **expired 플래그를 true**로 변경.  
  - 메인 스레드는 입력을 기다리더라도,  
    타이머가 만료되면 즉시 **메인 메뉴로 복귀** 가능.  
  - “입출력과 무관하게 세션 시간이 흐른다”는 실제 로그인 만료 UX 구현.  

---



