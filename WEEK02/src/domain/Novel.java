package domain;

public class Novel extends Book {
    private String genre;

    public Novel(String id, String title, String author, String genre) {
        super(id, title, author, "Novel");
        this.genre = genre;
    }

    @Override
    public void preview() { //상속 메소드
        System.out.println("소설 미리보기: 장르 = " + genre);
    }
}
