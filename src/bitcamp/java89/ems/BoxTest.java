package bitcamp.java89.ems;
import java.util.Scanner;

public class BoxTest  {
  static Box head;
  static Box tail;
  static int length;
  static Scanner keyScan;

  static {
    head = new Box();
    tail = head;
    length = 0;
    keyScan = new Scanner(System.in);
  }

  public static void main(String[] args) {
    loop:
    while (true) {
      System.out.print("명령> ");
      String command = keyScan.nextLine();
      switch (command) {
      case "add": doAdd(); break;
      case "list": doList(); break;
      case "get": doGet(); break;
      case "delete": doDelete(); break;
      case "quit": System.out.println("Good Bye~");
        break loop;
      default: System.out.println("명령어가 없습니다");
      }
    }
  }

  static void doAdd() {
    System.out.print("입력할 값? ");
    int value = Integer.parseInt(keyScan.nextLine());
    tail.value = value;
    tail.next = new Box();
    tail = tail.next;
    length++;
  }

  static void doList() {
    Box cursor = head;
    while (cursor != null && cursor != tail) {
      System.out.println(cursor.value);
      cursor = cursor.next;
    }
  }

  static void doGet() {
    System.out.print("인덱스? ");
    int index = Integer.parseInt(keyScan.nextLine());

    if (index < 0 || index >= length) {
      System.out.println("인덱스가 유효하지 않습니다.");
      return;
    }

    Box cursor = head;
    for (int i = 0; i < index; i++)
      cursor = cursor.next;
    System.out.println(cursor.value);
  }

  static void doDelete() {
    System.out.print("삭제할 값의 인덱스? ");
    int index = Integer.parseInt(keyScan.nextLine());

    if (index < 0 || index >= length) {
      System.out.println("인덱스가 유효하지 않습니다.");
      return;
    }

    if (index == 0) {
      head = head.next;
      length--;
      return;
    }

    Box cursor = head;
    for (int i = 0; i < (index - 1); i++)
      cursor = cursor.next;
    cursor.next = cursor.next.next;
    length--;
  }
}
