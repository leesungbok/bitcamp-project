package bitcamp.java89.ems;
import java.util.Scanner;
public class EduApp {
  public static void main(String[] args) {
    System.out.println("비트캠프 관리시스템에 오신걸 환영합니다.");
    Scanner keyScan = new Scanner(System.in);
    //Command command = new Command();

    command : while(true) {
      System.out.print("명령> ");
      String order = keyScan.nextLine().toLowerCase();
      switch (order) {
        case "add": Command.add(); break;
        case "list": Command.list(); break;
        case "view": Command.view(); break;
        case "quit":
          System.out.println("Bye");
          break command;
        default :
          System.out.println("올바른 명령어가 아닙니다. 다시 입력하세요.");
      }
    }
  }
}
