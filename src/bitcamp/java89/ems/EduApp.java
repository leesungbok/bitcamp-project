package bitcamp.java89.ems;
import java.util.Scanner;
public class EduApp {
  public static void main(String[] args) {
    System.out.println("비트캠프 관리시스템에 오신걸 환영합니다.");
    Scanner keyScan = new Scanner(System.in);

    loop : while(true) {
      System.out.print("명령> ");
      ClassroomController.keyScan = keyScan;
      String order = keyScan.nextLine().toLowerCase();
      switch (order) {
        case "add": ClassroomController.add(); break;
        case "list": ClassroomController.list(); break;
        case "view": ClassroomController.view(); break;
        case "quit":
          System.out.println("Bye");
          break loop;
        default :
          System.out.println("올바른 명령어가 아닙니다. 다시 입력하세요.");
      }
    }
  }
}
