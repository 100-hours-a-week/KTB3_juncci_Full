public class Librarian extends User {

    public Librarian(String name) {
        super(name);
    }

    public void addBook(Library library, Book book) {
        library.addBook(book);
    }

    public void removeBook(Library library, String id) {
        library.removeBook(id);
    }

    public void updateBook(Library library, String id, String title, String author) {
        library.updateBook(id, title, author);
    }

    public void viewAllBooks(Library library) {
        library.listAllBooks();
    }
}
