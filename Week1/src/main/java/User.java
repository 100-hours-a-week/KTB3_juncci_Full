import java.util.ArrayList;
import java.util.List;

public class User {
    protected String name;
    protected List<Book> borrowedBooks = new ArrayList<>();

    public User(String name) {
        this.name = name;
    }

    public void borrowBook(Library library, String bookId) {
        Book book = library.findBookById(bookId);
        if (book != null && !book.isBorrowed()) {
            book.borrow();
            borrowedBooks.add(book);
        } else {
            System.out.println("대여 불가: 존재하지 않거나 이미 대여 중");
        }
    }

    public void returnBook(Library library, String bookId) {
        Book book = borrowedBooks.stream()
                .filter(b -> b.getId().equals(bookId))
                .findFirst()
                .orElse(null);
        if (book != null) {
            book.returnBook();
            borrowedBooks.remove(book);
        } else {
            System.out.println("반납 불가: 대여 목록에 없음");
        }
    }

    public void viewBorrowedBooks() {
        if (borrowedBooks.isEmpty()) {
            System.out.println("대여 중인 책이 없습니다.");
        } else {
            borrowedBooks.forEach(Book::introduce);
        }
    }
}
