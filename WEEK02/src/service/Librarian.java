package service;
import domain.Library;
import domain.Book;
import domain.User;
public class Librarian extends User {

    public Librarian(String name) {
        super(name);
    }

    public void addBook(Library library, Book book) { //책 추가
        library.addBook(book);
    }

    public void removeBook(Library library, String id) { //책 삭제
        library.removeBook(id);
    }

    public void updateBook(Library library, String id, String title, String author) { //책 업데이트
        library.updateBook(id, title, author);
    }

    public void viewAllBooks(Library library) { //책 열거
        library.listAllBooks();
    }
}
