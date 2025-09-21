package domain;
public abstract class Book {
    protected String id;
    protected String title;
    protected String author;
    protected String category;
    protected boolean isBorrowed;

    public Book(String id, String title, String author, String category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.isBorrowed = false;
    }

    public void borrow() { //책 대여
        if (isBorrowed) {
            System.out.println("이미 대여 중입니다.");
        } else {
            isBorrowed = true;
            System.out.println(title + " 대여 완료");
        }
    }

    public void returnBook() { //책 반납
        if (!isBorrowed) {
            System.out.println("대여 중이 아닙니다.");
        } else {
            isBorrowed = false;
            System.out.println(title + " 반납 완료");
        }
    }

    public void introduce() { //책 현황
        System.out.printf("ID:%s | [%s] %s / %s | 상태:%s%n",
                id, category, title, author,
                isBorrowed ? "대여중" : "대여가능");
    }

    public abstract void preview(); //상속용 메소드


    public String getId() { return id; }
    public String getTitle() { return title; }
    public boolean isBorrowed() { return isBorrowed; }
}

