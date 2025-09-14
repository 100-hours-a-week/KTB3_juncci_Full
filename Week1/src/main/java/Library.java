import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        if (findBookById(book.getId()) != null) {
            System.out.println("이미 존재하는 ID입니다.");
            return;
        }
        books.add(book);
        System.out.println("등록 완료");
    }

    public void removeBook(String id) {
        Book book = findBookById(id);
        if (book != null) {
            if (book.isBorrowed()) {
                System.out.println("삭제 불가: 대여 중인 책");
            } else {
                books.remove(book);
                System.out.println("삭제 완료");
            }
        } else {
            System.out.println("해당 ID의 책 없음");
        }
    }

    public void updateBook(String id, String title, String author) {
        Book book = findBookById(id);
        if (book != null) {
            book.title = title;
            book.author = author;
            System.out.println("업데이트 완료");
        } else {
            System.out.println("해당 ID의 책 없음");
        }
    }

    public Book findBookById(String id) {
        return books.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void listAvailableBooks() {
        books.stream().filter(b -> !b.isBorrowed()).forEach(Book::introduce);
    }

    public void listAllBooks() {
        books.forEach(Book::introduce);
    }

    // 초기 데이터
    public void seedData() {
        addBook(new Novel("N001", "해리포터", "J.K.Rowling", "Fantasy"));
        addBook(new Comic("C001", "원피스", "Oda", "Oda Eiichiro"));
        addBook(new Magazine("M001", "TIME", "편집부", 202));
    }
}
