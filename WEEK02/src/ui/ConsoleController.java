package ui;

import java.util.Scanner;
import domain.Library;
import domain.User;
import domain.Novel;
import domain.Comic;
import domain.Magazine;
import domain.Librarian;

public class ConsoleController {

    private final Library library;
    private final ConsoleView view;
    private final Scanner sc;

    public ConsoleController(Library library, ConsoleView view, Scanner sc) {
        this.library = library;
        this.view = view;
        this.sc = sc;
    }

    public void start() {
        while (true) {
            view.showMainMenu();
            String input = sc.nextLine();
            int code;

            try {
                code = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                view.showInvalidInput();
                continue;
            }

            UserRole role = UserRole.fromCode(code);

            if (role == UserRole.EXIT) {
                view.showExit();
                break;
            }
            if (role == null) {
                view.showInvalidInput();
                continue;
            }

            switch (role) {
                case USER -> runUser();
                case ADMIN -> runAdmin();
            }
        }
    }

    private void runUser() {
        System.out.print("사용자 이름 입력: ");
        User user = new User(sc.nextLine());

        SessionTimerTask timer = new SessionTimerTask(5); // 5초 제한
        timer.start();

        while (!timer.isExpired()) {
            view.showUserMenu(0);

            String input = sc.nextLine();
            if (timer.isExpired()) break;

            int sel;
            try {
                sel = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                if (timer.isExpired()) break;
                view.showInvalidInput();
                continue;
            }

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
                default -> view.showInvalidInput();
            }
        }

        timer.stop();
    }

    private void runAdmin() {
        System.out.print("관리자 이름 입력: ");
        Librarian librarian = new Librarian(sc.nextLine());

        SessionTimerTask timer = new SessionTimerTask(5); // 5초 제한
        timer.start();

        while (!timer.isExpired()) {
            view.showAdminMenu(0);

            String input = sc.nextLine();
            if (timer.isExpired()) break;

            int sel;
            try {
                sel = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                if (timer.isExpired()) break;
                view.showInvalidInput();
                continue;
            }

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
                        default -> view.showInvalidInput();
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
                default -> view.showInvalidInput();
            }
        }

        timer.stop();
    }
}
