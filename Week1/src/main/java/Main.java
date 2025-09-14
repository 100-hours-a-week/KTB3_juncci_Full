import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        library.seedData(); // 초기 3권 등록
        Scanner sc = new Scanner(System.in);

        System.out.print("이름 입력: ");
        String name = sc.nextLine();
        System.out.println("1. 사용자  2. 관리자");
        int role = sc.nextInt();
        sc.nextLine(); // 개행 제거

        if (role == 1) {
            User user = new User(name);
            runUserMenu(sc, user, library);
        } else {
            Librarian librarian = new Librarian(name);
            runAdminMenu(sc, librarian, library);
        }
    }

    private static void runUserMenu(Scanner sc, User user, Library library) {
        while (true) {
            System.out.println("\n[사용자 메뉴] 1.대출 2.반납 3.대여현황 4.대출가능목록 0.종료");
            int sel = sc.nextInt(); sc.nextLine();
            if (sel == 0) break;
            switch (sel) {
                case 1 -> {
                    library.listAvailableBooks();
                    System.out.print("대출할 책 ID: ");
                    user.borrowBook(library, sc.nextLine());
                }
                case 2 -> {
                    user.viewBorrowedBooks();
                    System.out.print("반납할 책 ID: ");
                    user.returnBook(library, sc.nextLine());
                }
                case 3 -> user.viewBorrowedBooks();
                case 4 -> library.listAvailableBooks();
            }
        }
    }

    private static void runAdminMenu(Scanner sc, Librarian librarian, Library library) {
        while (true) {
            System.out.println("\n[관리자 메뉴] 1.등록 2.삭제 3.수정 4.전체목록 0.종료");
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
                        case "Novel" -> librarian.addBook(library, new Novel(id, title, author, "Fantasy"));
                        case "Comic" -> librarian.addBook(library, new Comic(id, title, author, "Unknown"));
                        case "Magazine" -> librarian.addBook(library, new Magazine(id, title, author, 1));
                        default -> System.out.println("잘못된 종류");
                    }
                }
                case 2 -> {
                    System.out.print("삭제할 책 ID: ");
                    librarian.removeBook(library, sc.nextLine());
                }
                case 3 -> {
                    System.out.print("수정할 책 ID: ");
                    String id = sc.nextLine();
                    System.out.print("새 제목: ");
                    String title = sc.nextLine();
                    System.out.print("새 저자: ");
                    String author = sc.nextLine();
                    librarian.updateBook(library, id, title, author);
                }
                case 4 -> librarian.viewAllBooks(library);
            }
        }
    }
}
