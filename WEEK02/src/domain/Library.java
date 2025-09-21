package domain;
import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book) { //책 추가
        if (findBookById(book.getId()) != null) {
            System.out.println("이미 존재하는 ID입니다.");
            return;
        }
        books.add(book);
        System.out.println("등록 완료");
    }

    public void removeBook(String id) { //책 제거
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

    public void updateBook(String id, String title, String author) { //책 업데이트
        Book book = findBookById(id);
        if (book != null) {
            book.title = title;
            book.author = author;
            System.out.println("업데이트 완료");
        } else {
            System.out.println("해당 ID의 책 없음");
        }
    }

    public Book findBookById(String id) { //책 찾기
        return books.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void listAvailableBooks() { //이용 가능한 책
        books.stream().filter(b -> !b.isBorrowed()).forEach(Book::introduce);
    }

    public void listAllBooks() { //모든 책 열거
        books.forEach(Book::introduce);
    }

    public void seedData() { //더미 데이터
        addBook(new Novel("N001", "소설1", "저자A", "장르1"));
        addBook(new Comic("C001", "코믹스1", "저자B", "일러스트1"));
        addBook(new Magazine("M001", "메거진1", "저자C", 1));
    }
}
