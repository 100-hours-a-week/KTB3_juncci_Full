package org.example;

public class Magazine extends Book {
    private int issueNumber;

    public Magazine(String id, String title, String author, int issueNumber) {
        super(id, title, author, "Magazine");
        this.issueNumber = issueNumber;
    }

    @Override
    public void preview() { //상속 메소드
        System.out.println("잡지 미리보기: " + issueNumber + "호 특집 기사!");
    }
}
