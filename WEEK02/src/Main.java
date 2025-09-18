import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        library.seedData();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== 도서관 시스템 ===");
            System.out.println("1. 사용자");
            System.out.println("2. 관리자");
            System.out.println("0. 종료");
            System.out.println("===============");
            System.out.print("선택: ");
            int role = sc.nextInt();
            sc.nextLine();

            if (role == 0) {
                System.out.println("프로그램을 종료합니다.");
                break;
            }

            switch (role) {
                case 1 -> runUserProcess(sc, library);
                case 2 -> runAdminProcess(sc, library);
                default -> System.out.println("잘못된 선택입니다.");
            }
        }
    }

    private static void runUserProcess(Scanner sc, Library library) {
        System.out.print("사용자 이름 입력: ");
        User user = new User(sc.nextLine());

        while (true) {
            System.out.println("\n<<< 도서관 시스템 >>>");
            System.out.println("1. 대여");
            System.out.println("2. 반납");
            System.out.println("3. 내 대여 현황");
            System.out.println("4. 대출 가능 목록");
            System.out.println("0. 메인으로 돌아가기");
            System.out.println("<<<<<<<>>>>>>>");
            System.out.print("선택: ");
            int sel = sc.nextInt(); sc.nextLine();
            if (sel == 0) break;

            switch (sel) {
                case 1 -> {
                    library.listAvailableBooks();
                    System.out.print("대여할 책 ID: ");
                    user.borrowBook(library, sc.nextLine());
                }
                case 2 -> {
                    user.viewBorrowedBooks();
                    System.out.print("반납할 책 ID: ");
                    user.returnBook(library, sc.nextLine());
                }
                case 3 -> user.viewBorrowedBooks();
                case 4 -> library.listAvailableBooks();
                default -> System.out.println("잘못된 입력입니다.");
            }
        }
    }

    private static void runAdminProcess(Scanner sc, Library library) {
        System.out.print("관리자 이름 입력: ");
        Librarian librarian = new Librarian(sc.nextLine());

        while (true) {
            System.out.println("\n[[[ 관리자 시스템 ]]]");
            System.out.println("1. 책 등록");
            System.out.println("2. 책 삭제");
            System.out.println("3. 책 수정");
            System.out.println("4. 전체 책 목록");
            System.out.println("0. 메인으로 돌아가기");
            System.out.println("[[[[[[[]]]]]]]");
            System.out.print("선택: ");
            int sel = sc.nextInt(); sc.nextLine();
            if (sel == 0) break;

            switch (sel) {
                case 1 -> {
                    System.out.print("종류(Novel/Comic/Magazine): ");
                    String type = sc.nextLine();
                    System.out.print("ID: ");
                    String id = sc.nextLine();
                    System.out.print("제목: ");
                    String title = sc.nextLine();
                    System.out.print("저자: ");
                    String author = sc.nextLine();

                    switch (type) {
                        case "Novel" ->
                                librarian.addBook(library, new Novel(id, title, author, "Fantasy"));
                        case "Comic" ->
                                librarian.addBook(library, new Comic(id, title, author, "Unknown"));
                        case "Magazine" ->
                                librarian.addBook(library, new Magazine(id, title, author, 1));
                        default -> System.out.println("잘못된 종류입니다.");
                    }
                }
                case 2 -> {
                    library.listAvailableBooks();
                    System.out.print("삭제할 책 ID: ");
                    librarian.removeBook(library, sc.nextLine());
                }
                case 3 -> {
                    library.listAvailableBooks();
                    System.out.print("수정할 책 ID: ");
                    String id = sc.nextLine();
                    System.out.print("새 제목: ");
                    String title = sc.nextLine();
                    System.out.print("새 저자: ");
                    String author = sc.nextLine();
                    librarian.updateBook(library, id, title, author);
                }
                case 4 -> librarian.viewAllBooks(library);
                default -> System.out.println("잘못된 입력입니다.");
            }
        }
    }
}
