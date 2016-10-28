package bitcamp.java89.ems;
import java.util.Scanner;
public class ClassroomController {
  Scanner keyScan;
  Classroom[] classrooms = new Classroom[100];
  Classroom classroom = new Classroom();
  int length = 0;

  public ClassroomController(Scanner keyScan) {
    this.keyScan = keyScan;
  }

  public void add() {
    while (length < this.length) {
      System.out.print("강의실명?(예:세미나실) ");
      classroom.name = this.keyScan.nextLine();

      System.out.print("위치?(예:602호) ");
      classroom.location = this.keyScan.nextLine();

      System.out.print("면적?(예:100m^2) ");
      classroom.area = this.keyScan.nextLine();

      System.out.print("이용시간?(예:09:00 ~ 21:00) ");
      classroom.usabletime = this.keyScan.nextLine();

      System.out.print("수용인원?(예:100) ");
      classroom.people = Integer.parseInt(this.keyScan.nextLine());

      System.out.print("에어컨(y/n)? ");
      classroom.aconditioner =
      (this.keyScan.nextLine().equals("y")) ? true : false;

      System.out.print("프로젝터(y/n)? ");
      classroom.projector =
      (this.keyScan.nextLine().equals("y")) ? true : false;

      classrooms[length++] = classroom;

      System.out.print("계속 입력하시겠습니까(y/n)? ");
      if (!this.keyScan.nextLine().equals("y"))
        break;
    }
  }

  public void list() {
    for (int i = 0; i < length; i++) {
      classroom = classrooms[i];
      System.out.printf("%s,%s,%s,%s,%d,%s,%s\n",
        this.classroom.name,
        this.classroom.location,
        this.classroom.area,
        this.classroom.usabletime,
        this.classroom.people,
        ((this.classroom.aconditioner)?"yes":"no"),
        ((this.classroom.projector)?"yes":"no"));
    }
  }

  public void view() {
    System.out.print("강의실명? ");
    String name = this.keyScan.nextLine();
    System.out.println("----------------------------");
    for (int i = 0; i < this.length; i++) {
      if (name.equals(this.classrooms[i].name)) {
        classroom = classrooms[i];
        System.out.printf("강의실명: %s\n", this.classroom.name);
        System.out.printf("위치: %s\n", this.classroom.location);
        System.out.printf("면적: %s\n", this.classroom.area);
        System.out.printf("이용시간: %s\n", this.classroom.usabletime);
        System.out.printf("수용인원: %d명\n", this.classroom.people);
        System.out.printf("에어컨: %s\n", ((this.classroom.aconditioner)?"yes":"no"));
        System.out.printf("프로젝터: %s\n", ((this.classroom.projector)?"yes":"no"));
        break;
      }
    }
  }
}
