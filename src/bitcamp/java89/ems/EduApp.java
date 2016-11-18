package bitcamp.java89.ems;

import java.util.Scanner;

public class EduApp {
    static Scanner keyScan = new Scanner(System.in);
    static ClassroomController classroomController;
    static ContactController contactController;

  public static void main(String[] args) throws Exception {
    System.out.println("비트캠프 관리시스템에 오신걸 환영합니다.");
    classroomController = new ClassroomController(keyScan);
    contactController = new ContactController(keyScan);
    
    loop : while(true) {
      System.out.print("명령> ");
      String order = keyScan.nextLine().toLowerCase();
      switch (order) {
        case "menu": doMenu(); break;
        case "go 1": classroomController.service(); break;
        case "go 5": contactController.service(); break;
        case "save": doSave(); break;
        case "quit": 
          if (doQuit())
            break loop;
          break;
        default :
          System.out.println("올바른 명령어가 아닙니다. 다시 입력하세요.");
      }
    }
  }

  static void doMenu() {
    System.out.println("[메뉴]");
    System.out.println("1. 강의실 관리");
    System.out.println("5. 연락처 관리");
    System.out.println("[명령]");
    System.out.println("save   데이터 저장");
    System.out.println("quit   프로그램 종료");
    System.out.println("메뉴 이동은 'go 메뉴번호'를 입력하세요");
  }

  static boolean doQuit() {
    boolean changed = classroomController.isChanged();
    if (changed) {
      System.out.print("학생 정보가 변경되었습니다. 그래도 종료하시겠습니까?(y/n) ");
      if (!keyScan.nextLine().toLowerCase().equals("y"))
        return false;
      System.out.println("학생 정보가 변경된 것을 취소하고 종료합니다.");
    }
    System.out.println("Good bye!");
    return true;
  }

  static void doSave() {
    try {
      classroomController.save();
    } catch (Exception e) {
      System.out.println("파일 저장 중에 오류가 발생했습니다.");
      e.printStackTrace();
    }
  }
}
