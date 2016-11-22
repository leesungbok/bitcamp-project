package bitcamp.java89.ems.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
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

    while (true) {
      processRequest(ss.accept());
    }
  }

  private static void processRequest(Socket socket) {
    try {
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
  
        String order = in.nextLine().toLowerCase();
  
        boolean running = true;
        switch (order) {
          case "menu": doMenu(); break;
          case "go 1": running = classroomController.service(); break;
          case "go 5": running = contactController.service(); break;
          case "save": doSave(); break;
          case "quit": doQuit(); break loop;
          default:
           out.println("올바른 명령어가 아닙니다. 다시 입력하세요.");
        }
  
        if (!running) {
          doQuit();
          break;
        }
      } // while
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {in.close();} catch (Exception e) {}
      try {out.close();} catch (Exception e) {}
      try {socket.close();} catch (Exception e) {}
    }
  }

  static void doMenu() {
    out.println("[메뉴]");
    out.println("1. 강의실 관리");
    out.println("5. 연락처 관리");
    out.println("메뉴 이동은 'go 메뉴번호'를 입력하세요");
    out.println("[명령]");
    out.println("save   데이터 저장");
    out.println("quit   프로그램 종료");
  }

  static void doQuit() {
    boolean changed = classroomController.isChanged();
    boolean changed2 = contactController.isChanged();
    if (changed || changed2) {
      doSave();
    }
    System.out.println("클라이언트 연결 종료!");
  }

  static void doSave() {
    try {
      classroomController.save();
    } catch (Exception e) {
      System.out.println("강의실 정보 저장 중에 오류가 발생했습니다.");
      e.printStackTrace();
    }

    try {
      contactController.save();
    } catch (Exception e) {
      System.out.println("연락처 정보 저장 중에 오류가 발생했습니다.");
      e.printStackTrace();
    }
  }
}
