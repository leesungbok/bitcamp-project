package bitcamp.java89.ems;
import java.util.Scanner;
public class EduApp {
  public static void main(String[] args) {
    System.out.println("비트캠프 관리시스템에 오신걸 환영합니다.");

    Scanner keyScan = new Scanner(System.in);
    Classroom[] classrooms = new Classroom[100];
    int length = 0;
    for (int i = 0; i < classrooms.length; i++) {
      classrooms[i] = new Classroom();

      System.out.print("호실명?(예:세미나실) ");
      classrooms[i].name = keyScan.nextLine();

      System.out.print("위치?(예:602호) ");
      classrooms[i].location = keyScan.nextLine();

      System.out.print("면적?(예:100m^2) ");
      classrooms[i].area = keyScan.nextLine();

      System.out.print("이용시간?(예:09:00 ~ 21:00) ");
      classrooms[i].usabletime = keyScan.nextLine();

      System.out.print("수용인원?(예:100) ");
      classrooms[i].people = Integer.parseInt(keyScan.nextLine());

      System.out.print("에어컨(y/n)? ");
      classrooms[i].aconditioner = (keyScan.nextLine().equals("y")) ? true : false;

      System.out.print("프로젝터(y/n)? ");
      classrooms[i].projector = (keyScan.nextLine().equals("y")) ? true : false;

      printClassroomList(classrooms, length++);

      System.out.print("계속 입력하시겠습니까(y/n)? ");
      if (!keyScan.nextLine().equals("y"))
        break;
    }
  }

  static void printClassroomList(Classroom[] classrooms, int length) {
    Classroom classroom = null;
    for (int i = 0; i <= length; i++) {
      classroom = classrooms[i];
      System.out.printf("호실명: %s\n", classroom.name);
      System.out.printf("위치: %s\n", classroom.location);
      System.out.printf("면적: %s\n", classroom.area);
      System.out.printf("이용시간: %s\n", classroom.usabletime);
      System.out.printf("수용인원: %d\n명", classroom.people);
      System.out.printf("에어컨: %b\n", ((classroom.aconditioner)?"yes":"no"));
      System.out.printf("프로젝터: %b\n", ((classroom.projector)?"yes":"no"));
    }
  }
}
