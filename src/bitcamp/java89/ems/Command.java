package bitcamp.java89.ems;
import java.util.Scanner;
public class Command {
  static Scanner keyScan = new Scanner(System.in);
  static Classroom[] classrooms = new Classroom[100];
  static Classroom classroom = new Classroom();
  static int length = 0;

  static void add() {
    while (length < classrooms.length) {
      System.out.print("강의실명?(예:세미나실) ");
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
      classroom.aconditioner =
      (keyScan.nextLine().equals("y")) ? true : false;

      System.out.print("프로젝터(y/n)? ");
      classroom.projector =
      (keyScan.nextLine().equals("y")) ? true : false;

      classrooms[length++] = classroom;

      System.out.print("계속 입력하시겠습니까(y/n)? ");
      if (!keyScan.nextLine().equals("y"))
        break;
    }
  }

  static void list() {
    for (int i = 0; i < length; i++) {
      classroom = classrooms[i];
      System.out.printf("%s,%s,%s,%s,%d,%s,%s\n",
        classroom.name,
        classroom.location,
        classroom.area,
        classroom.usabletime,
        classroom.people,
        ((classroom.aconditioner)?"yes":"no"),
        ((classroom.projector)?"yes":"no"));
    }
  }

  static void view() {
    System.out.print("강의실명? ");
    String name = keyScan.nextLine();
    System.out.println("----------------------------");
    for (int i = 0; i < length; i++) {
      if (name.equals(classrooms[i].name)) {
        classroom = classrooms[i];
        System.out.printf("강의실명: %s\n", classroom.name);
        System.out.printf("위치: %s\n", classroom.location);
        System.out.printf("면적: %s\n", classroom.area);
        System.out.printf("이용시간: %s\n", classroom.usabletime);
        System.out.printf("수용인원: %d명\n", classroom.people);
        System.out.printf("에어컨: %s\n", ((classroom.aconditioner)?"yes":"no"));
        System.out.printf("프로젝터: %s\n", ((classroom.projector)?"yes":"no"));
        break;
      }
    }
  }
}
