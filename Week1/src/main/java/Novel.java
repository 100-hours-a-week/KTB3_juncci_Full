public class Novel extends Book {
    private String genre;

    public Novel(String id, String title, String author, String genre) {
        super(id, title, author, "Novel");
        this.genre = genre;
    }

    @Override
    public void preview() {
        System.out.println("소설 미리보기: 장르 = " + genre);
    }
}
