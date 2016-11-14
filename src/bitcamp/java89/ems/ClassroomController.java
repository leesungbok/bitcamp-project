package bitcamp.java89.ems;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.DataInputStream;
import java.lang.Exception;

public class ClassroomController {
  private Scanner keyScan;
  private ArrayList<Classroom> list;
  private FileOutputStream out = null;
  private DataOutputStream out2 = null;
  private FileInputStream in = null;
  private DataInputStream in2 = null;
  private int count;

  public ClassroomController(Scanner keyScan) throws Exception {
    list = new ArrayList<Classroom>();
    File file = new File("lib/");
    File[] files = file.listFiles();
    for (File name : files) {
      Classroom classroom = new Classroom();
      in = new FileInputStream(name.getPath());
      in2 = new DataInputStream(in);
      classroom.name = in2.readUTF();
      classroom.location = in2.readUTF();
      classroom.area = in2.readUTF();
      classroom.usabletime = in2.readUTF();
      classroom.people = in2.readInt();
      classroom.aconditioner = in2.readBoolean();
      classroom.projector = in2.readBoolean();
      list.add(classroom);
      in2.close();
    }
    this.keyScan = keyScan;
  }

  public void service() {
    loop : while(true) {
      System.out.print("강의실관리> ");
      String order = keyScan.nextLine().toLowerCase();
      try {
        switch (order) {
        case "add": this.doAdd(); break;
        case "list": this.doList(); break;
        case "view": this.doView(); break;
        case "delete": this.doDelete(); break;
        case "update": this.doUpdate(); break;
        case "main": break loop;
        default: 
          System.out.println("올바른 명령어가 아닙니다. 다시 입력하세요.");
        }
      } catch (IndexOutOfBoundsException e) {
        System.out.println("인덱스가 유효하지 않습니다.");
      } catch (Exception e) {
        System.out.println("인덱스 값이 잘못되었거나, 실행 중 오류가 발생했습니다.");
      } // try ~ catch
    } // while
  }

  private void doAdd() throws Exception {
    while (true) {
      count = 1;
      Classroom classroom = new Classroom();
      System.out.print("강의실명?(예:세미나실) ");
      classroom.name = this.keyScan.nextLine();
      

      System.out.print("위치?(예:602호) ");
      classroom.location = this.keyScan.nextLine();
      

      System.out.print("면적?(예:100m^2) ");
      classroom.area = this.keyScan.nextLine();
      

      System.out.print("이용시간?(예:09:00 ~ 21:00) ");
      classroom.usabletime = this.keyScan.nextLine();
     

      while(true) {
        try {
          System.out.print("수용인원?(예:100) ");
          classroom.people = Integer.parseInt(this.keyScan.nextLine());
          break;
        } catch (Exception e) {
          System.out.println("정수 값을 입력하세요.");
        }
      }

      System.out.print("에어컨(y/n)? ");
      classroom.aconditioner =
      (this.keyScan.nextLine().equals("y")) ? true : false;
      

      System.out.print("프로젝터(y/n)? ");
      classroom.projector =
      (this.keyScan.nextLine().equals("y")) ? true : false;
      list.add(classroom);

      System.out.print("계속 입력하시겠습니까(y/n)? ");
      if (!this.keyScan.nextLine().equals("y"))
        break;
    }
  }

  private void doList() {
    for (Classroom classroom : list) {
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

  private void doView() {
    System.out.print("강의실 인덱스? ");
    int index = Integer.parseInt(this.keyScan.nextLine());
    Classroom classroom = list.get(index);
    System.out.printf("강의실명: %s\n", classroom.name);
    System.out.printf("위치: %s\n", classroom.location);
    System.out.printf("면적: %s\n", classroom.area);
    System.out.printf("이용시간: %s\n", classroom.usabletime);
    System.out.printf("수용인원: %d명\n", classroom.people);
    System.out.printf("에어컨: %s\n",
    ((classroom.aconditioner)?"yes":"no"));
    System.out.printf("프로젝터: %s\n",
    ((classroom.projector)?"yes":"no"));
  }

  private void doDelete() {
    Classroom classroom = new Classroom();
    System.out.print("삭제할 강의실 인덱스? ");
    int index = Integer.parseInt(keyScan.nextLine());
    list.remove(index);
  }

  private void doUpdate() {
    System.out.print("변경할 강의실 인덱스? ");
    int index = Integer.parseInt(this.keyScan.nextLine());
    Classroom oldClassroom = list.get(index);
    Classroom update = new Classroom();
    update.name = oldClassroom.name;
    System.out.printf("위치?(%s) ", oldClassroom.name);
    update.location = this.keyScan.nextLine();

    System.out.printf("면적?(%s) ", oldClassroom.location);
    update.area = this.keyScan.nextLine();

    System.out.printf("이용시간?(%s) ", oldClassroom.usabletime);
    update.usabletime = this.keyScan.nextLine();

    while(true) {
      try {
        System.out.printf("수용인원?(%d) ", oldClassroom.people);
        update.people = Integer.parseInt(this.keyScan.nextLine());
        break;
      } catch (Exception e) {
        System.out.println("정수 값을 입력하세요.");
      }
    }

    System.out.print("에어컨(y/n)? ");
    update.aconditioner =
      (this.keyScan.nextLine().equals("y")) ? true : false;

    System.out.print("프로젝터(y/n)? ");
    update.projector =
      (this.keyScan.nextLine().equals("y")) ? true : false;

    System.out.print("저장하시겠습니까(y/n)? ");

    if (this.keyScan.nextLine().toLowerCase().equals("y")) {
      list.set(index, update);
      System.out.println("저장하였습니다.");
      count = 1;
    } else {
      System.out.println("변경을 취소하였습니다.");
    }
  }

  public void doSave() throws Exception {
    for (Classroom classroom : list) {
      out = new FileOutputStream("lib/" + classroom.name + ".data");
      out2 = new DataOutputStream(out);
      out2.writeUTF(classroom.name);
      out2.writeUTF(classroom.location);
      out2.writeUTF(classroom.area);
      out2.writeUTF(classroom.usabletime);
      out2.writeInt(classroom.people);
      out2.writeBoolean(classroom.aconditioner);
      out2.writeBoolean(classroom.projector);
      out2.close();
    }
    System.out.println("저장하였습니다.");
    count = 0;
  }

  public boolean doQuit() throws Exception {
    if (count != 1) {
      System.out.println("Good bye!");
      return true;
    } else {
      System.out.println("학생 정보가 변경되었습니다.");
      System.out.print("그래도 종료하시겠습니까?(y/n) ");
      while(true) {
        String exit = keyScan.nextLine().toLowerCase();
        switch (exit) {
          case "y":
            System.out.println("학생 정보가 변경된 것을 취소하고 종료합니다.");
            System.out.println("Good bye!");
            return true;
          case "n":
            return false;
          default:
            System.out.println("올바른 명령어가 아닙니다. 다시 입력하세요.");
            System.out.print("그래도 종료하시겠습니까?(y/n) ");
            break;
        }
      }
    }
  }
}