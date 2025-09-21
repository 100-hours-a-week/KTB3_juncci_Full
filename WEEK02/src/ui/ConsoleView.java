package ui;

public class ConsoleView {

    // 메인 메뉴
    public void showMainMenu() {
        System.out.println("\n=== 도서관 시스템 ===");
        System.out.println("1. 사용자");
        System.out.println("2. 관리자");
        System.out.println("0. 종료");
        System.out.println("===============");
        System.out.print("선택: ");
    }

    public void showInvalidInput() {
        System.out.println("잘못된 입력입니다.");
    }

    public void showExit() {
        System.out.println("프로그램을 종료합니다.");
    }

    //사용자 메뉴
    public void showUserMenu(int ignored) { 
        System.out.println("\n<<< 사용자 메뉴 >>>");
        System.out.println("1. 대여");
        System.out.println("2. 반납");
        System.out.println("3. 내 대여 현황");
        System.out.println("4. 대출 가능 목록");
        System.out.println("0. 메인으로 돌아가기");
        System.out.print("선택: ");
    }

    //관리자 메뉴
    public void showAdminMenu(int ignored) {
        System.out.println("\n[[[ 관리자 메뉴 ]]]");
        System.out.println("1. 책 등록");
        System.out.println("2. 책 삭제");
        System.out.println("3. 책 수정");
        System.out.println("4. 전체 책 목록");
        System.out.println("0. 메인으로 돌아가기");
        System.out.print("선택: ");
    }
}
