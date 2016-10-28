package bitcamp.java89.ems;
import java.util.Scanner;
public class EduApp {
  public static void main(String[] args) {
    System.out.println("비트캠프 관리시스템에 오신걸 환영합니다.");

    Scanner keyScan = new Scanner(System.in);
    Classrom classroom = new Classrom();

    System.out.print("호실명?(예:세미나실) ");
    classroom.name = keyScan.nextLine();

    System.out.print("위치?(예:602호) ");
    classroom.location = keyScan.nextLine();

    System.out.print("면적?(예:100m^2) ");
    classroom.area = keyScan.nextLine();

    System.out.print("이용시간?(예:09:00 ~ 21:00) ");
    classroom.usabletime = keyScan.nextLine();

    System.out.print("수용인원?(예:100) ");
    classroom.people = Integer.parseInt(keyScan.nextLine());

    System.out.print("에어컨(y/n)? ");
    classroom.aconditioner = (keyScan.nextLine().equals("y")) ? true : false;

    System.out.print("프로젝터(y/n)? ");
    classroom.projector = (keyScan.nextLine().equals("y")) ? true : false;

    System.out.printf("호실명: %s\n", classroom.name);
    System.out.printf("위치: %s\n", classroom.location);
    System.out.printf("면적: %s\n", classroom.area);
    System.out.printf("이용시간: %s\n", classroom.usabletime);
    System.out.printf("수용인원: %d\n명", classroom.people);
    System.out.printf("에어컨: %b\n", classroom.aconditioner);
    System.out.printf("프로젝터: %b\n", classroom.projector);
  }
}
