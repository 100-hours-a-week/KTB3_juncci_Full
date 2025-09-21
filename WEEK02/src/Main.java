import ui.ConsoleController;
import ui.ConsoleView;
import domain.Library;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        // 도서관 초기 데이터 세팅
        Library library = new Library();
        library.seedData();

      

        // 콘솔 UI 구성 요소 준비
        ConsoleView view = new ConsoleView();
        Scanner sc = new Scanner(System.in);

        // 컨트롤러 실행
        ConsoleController controller = new ConsoleController(library, view, sc);
        controller.start();
    }
}
