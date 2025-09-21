package domain;

public class Comic extends Book {
    private String illustrator;

    public Comic(String id, String title, String author, String illustrator) {
        super(id, title, author, "Comic");
        this.illustrator = illustrator;
    }

    @Override
    public void preview() { //상속 메소드
        System.out.println("만화 미리보기: 그림작가 = " + illustrator);
    }
}
