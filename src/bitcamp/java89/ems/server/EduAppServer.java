package bitcamp.java89.ems.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import bitcamp.java89.ems.server.controller.ClassroomController;
import bitcamp.java89.ems.server.controller.ContactController;

public class EduAppServer {
  static PrintStream out;
  static Scanner in;
  static Scanner keyScan = new Scanner(System.in);
  static ClassroomController classroomController;
  static ContactController contactController;

  public static void main(String[] args) throws Exception {
    ServerSocket ss = new ServerSocket(8888);
    System.out.println("서버 실행중 ...");
    Socket socket = ss.accept();
    in = new Scanner(
        new BufferedInputStream(socket.getInputStream()));
    out = new PrintStream(
        new BufferedOutputStream(socket.getOutputStream()), true);
    
    classroomController = new ClassroomController(in, out);
    contactController = new ContactController(in, out);

    out.println("비트캠프 관리시스템에 오신걸 환영합니다.");
    
    loop : while(true) {
      // 클라이언트에게 데이터를 전송한다.
      out.println("명령>");
      out.println(); // 빈 줄은 보내는 데이터의 끝을 의미한다.
      
      // 클라이언트로부터 명령을 읽는다.
      String order = in.nextLine().toLowerCase();
      switch (order) {
        case "menu": doMenu(); break;
        case "go 1": classroomController.service(); break;
        case "go 5": contactController.service(); break;
        case "save": doSave(); break;
        case "quit": 
          if (doQuit())
            break loop;
          break;
        default:
         out.println("올바른 명령어가 아닙니다. 다시 입력하세요.");
      }
    } // while
    
    in.close();
    out.close();
    socket.close();
    ss.close();
  }

  static void doMenu() {
    out.println("[메뉴]");
    out.println("1. 강의실 관리");
    out.println("5. 연락처 관리");
    out.println("[명령]");
    out.println("save   데이터 저장");
    out.println("quit   프로그램 종료");
    out.println("메뉴 이동은 'go 메뉴번호'를 입력하세요");
  }

  static boolean doQuit() {
    boolean changed = classroomController.isChanged();
    if (changed) {
      out.print("학생 정보가 변경되었습니다. 그래도 종료하시겠습니까?(y/n) ");
      if (!keyScan.nextLine().toLowerCase().equals("y"))
        return false;
      out.println("학생 정보가 변경된 것을 취소하고 종료합니다.");
    }
    out.println("Good bye!");
    return true;
  }

  static void doSave() {
    try {
      classroomController.save();
      contactController.save();
      out.println("저장하였습니다.");
    } catch (Exception e) {
      out.println("파일 저장 중에 오류가 발생했습니다.");
      e.printStackTrace();
    }
  }
}
