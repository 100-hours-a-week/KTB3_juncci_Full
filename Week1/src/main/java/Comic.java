public class Comic extends Book {
    private String illustrator;

    public Comic(String id, String title, String author, String illustrator) {
        super(id, title, author, "Comic");
        this.illustrator = illustrator;
    }

    @Override
    public void preview() {
        System.out.println("만화 미리보기: 그림 작가 = " + illustrator);
    }
}
