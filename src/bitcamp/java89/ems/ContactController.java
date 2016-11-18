package bitcamp.java89.ems;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class ContactController {
  private Scanner keyScan;
  private ArrayList<Contact> list;
  private boolean changed;
  private String filename = "contactv1.5.data";

  public ContactController(Scanner keyScan) throws Exception {
    list = new ArrayList<Contact>();
    this.keyScan = keyScan;
    this.load();
  }

  public boolean isChanged() {
    return changed;
  }

  @SuppressWarnings("unchecked")
  private void load() {
    FileInputStream in0 = null;
    ObjectInputStream in = null;
    try {
      in0 = new FileInputStream(this.filename);
      in = new ObjectInputStream(in0);
      list = (ArrayList<Contact>)in.readObject();
    } catch (EOFException e) {
    } catch (Exception e) {
      System.out.println("데이터 로딩 중 오류 발생!");
    } finally {
      try {
        in.close();
        in0.close();
      } catch (Exception e) {
      }
    }
  }

  public void service() {
    loop : while(true) {
      System.out.print("연락처관리> ");
      String order = keyScan.nextLine().toLowerCase();
      try {
        switch (order) {
        case "add": this.doAdd(); break;
        case "list": this.doList(); break;
        case "view": this.doView(); break;
        case "delete": this.doDelete(); break;
        //case "update": this.doUpdate(); break;
        case "main": break loop;
        default: 
          System.out.println("올바른 명령어가 아닙니다. 다시 입력하세요.");
        }
      } catch (IndexOutOfBoundsException e) {
        System.out.println("인덱스가 유효하지 않습니다.");
      } catch (Exception e) {
        System.out.println("인덱스 값이 잘못되었거나, 실행 중 오류가 발생했습니다.");
        e.printStackTrace();
      } // try ~ catch
    } // while
  }

  private void doAdd() throws Exception {
    while (true) {
      Contact contact = new Contact();
      System.out.print("이름?(예:홍길동) ");
      contact.setName(this.keyScan.nextLine());
      
      System.out.print("직위?(예:대리) ");
      contact.setPosition(this.keyScan.nextLine());
      
      System.out.print("전화?(예:010-1111-2222) ");
      contact.setTel(this.keyScan.nextLine());
      
      System.out.print("이메일?(예:hong@test.com) ");
      contact.setEmail(this.keyScan.nextLine());
      
      boolean save = true;
      if (existEmail(contact.getEmail())) {
        System.out.print("같은 이메일이 존재합니다. 저장하시겠습니까?(y/n) ");
        if (!keyScan.nextLine().toLowerCase().equals("y")) {
          save = false;
          System.out.println("저장을 취소하였습니다.");
        }
      }
      
      if (save) {
        list.add(contact);
        changed = true;
      }
      
      System.out.print("계속 입력하시겠습니까(y/n)? ");
      if (!this.keyScan.nextLine().equals("y"))
        break;
    }
  }

  private boolean existEmail(String email) {
    for (Contact contact : list) {
      if (contact.getEmail().toLowerCase().equals(email.toLowerCase())) {
        return true;
      }
    }
    return false;
  }
  
  
  
  private void doList() {
    for (Contact contact : list) {
      System.out.printf("%s,%s,%s,%s\n",
        contact.getName(),
        contact.getPosition(),
        contact.getTel(),
        contact.getEmail());
    }
  }

  private void doView() {
    System.out.print("이름? ");
    String name = this.keyScan.nextLine();
    
    for (Contact contact : list) {
      if (contact.getName().equals(name)) {
        System.out.println("-------------------------");
        System.out.printf("이름: %s\n", contact.getName());
        System.out.printf("직위: %s\n", contact.getPosition());
        System.out.printf("전화: %s\n", contact.getTel());
        System.out.printf("이메일: %s\n", contact.getEmail());
      }
    }
  }
  
  private void doDelete() {
    System.out.print("이름? ");
    String name = keyScan.nextLine();
    
    ArrayList<Contact> deleteList = searchByName(name);
    
    if (deleteList.size() == 0) {
      System.out.println("입력한 이름이 등록된 파일이 없습니다.");
      return;
    }
    
    System.out.println("-----------------------------");
    
    for (int i = 0; i < deleteList.size(); i++) {
      Contact contact = deleteList.get(i);
      System.out.printf("%d. %s(%s)\n", (i + 1), contact.getName(), contact.getEmail());
    }
    System.out.println("-----------------------------");
    
    System.out.print("삭제할 연락처의 번호? ");
    int deleteNo = Integer.parseInt(keyScan.nextLine());
    
    while (deleteNo < 1 || deleteNo > deleteList.size()) {
      System.out.print("유효하지 않은 번호입니다. 삭제할 연락처의 번호? ");
      deleteNo = Integer.parseInt(keyScan.nextLine());
    }
    
    list.remove(deleteList.get(deleteNo - 1));
    changed = true;
    System.out.println("삭제하였습니다.");
  }
  
  private ArrayList<Contact> searchByName(String name) {
    ArrayList<Contact> searchList = new ArrayList<>();
    
    for (Contact contact : list) {
      if (contact.getName().toLowerCase().equals(name.toLowerCase())) {
        searchList.add(contact);
      }
    }
    return searchList;
  }
  
  /*
  private void doDelete() {
    System.out.print("이름? ");
    String name = keyScan.nextLine();
    
    for (int i = list.size()-1; i >= 0; i--) {
      if (list.get(i).getName().equals(name)) {
        list.remove(i);
      }
    }
    
    changed = true;
    System.out.println("삭제하였습니다.");
  }
  private void doDelete() {
    System.out.print("이름? ");
    String name = keyScan.nextLine();
    
    ArrayList<Contact> deleteList = new ArrayList<Contact>();
    
    for (Contact contact : list) {
      if (contact.getName().equals(name)) {
        deleteList.add(contact);
      }
    }
    
    for (Contact contact : deleteList) {
      list.remove(contact);
    }
    
    changed = true;
    System.out.println("삭제하였습니다.");
  }
  private void doUpdate() {
    System.out.print("변경할 강의실 인덱스? ");
    int index = Integer.parseInt(this.keyScan.nextLine());
    Contact oldContact = list.get(index);
    Contact update = new Contact();
    update.name = oldContact.name;
    System.out.printf("위치?(%s) ", oldContact.name);
    update.location = this.keyScan.nextLine();

    System.out.printf("면적?(%s) ", oldContact.location);
    update.area = this.keyScan.nextLine();

    System.out.printf("이용시간?(%s) ", oldContact.usabletime);
    update.usabletime = this.keyScan.nextLine();

    while(true) {
      try {
        System.out.printf("수용인원?(%d) ", oldContact.people);
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
      changed = true;
    } else {
      System.out.println("변경을 취소하였습니다.");
    }
  }

  public void save() throws Exception {
    FileOutputStream out = new FileOutputStream(this.filename);
    ObjectOutputStream out2 = new ObjectOutputStream(out);
    out2.writeObject(list);
    out2.close();
    changed = false;
    System.out.println("저장하였습니다.");
  }*/
}