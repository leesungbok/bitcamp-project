package bitcamp.java89.ems.server.controller;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import bitcamp.java89.ems.server.vo.Contact;

public class ContactController {
  private Scanner in;
  private PrintStream out;
  private ArrayList<Contact> list;
  private boolean changed;
  private String filename = "contact-v1.6.data";

  public ContactController(Scanner in, PrintStream out) throws Exception {
    this.in = in;
    this.out = out;
    list = new ArrayList<Contact>();
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
      out.println("연락처 데이터 로딩 중 오류 발생!");
    } finally {
      try {
        in.close();
        in0.close();
      } catch (Exception e) {
      }
    }
  }

  public boolean service() {
    while(true) {
      // 클라이언트로 데이터 출력
      out.println("연락처관리> ");
      out.println(); // 보내는 데이터의 끝을 의미
      
      // 클라이언트가 보낸 데이터 읽기
      String[] commands = in.nextLine().split("\\?");
      try {
        switch (commands[0]) {
        case "add": this.doAdd(commands[1]); break;
        case "list": this.doList(); break;
        case "view": this.doView(commands[1]); break;
        case "delete": this.doDelete(commands[1]); break;
        case "update": this.doUpdate(commands[1]); break;
        case "main": return true;
        case "quit": return false;
        default: 
          out.println("올바른 명령어가 아닙니다. 다시 입력하세요.");
        }
      } catch (IndexOutOfBoundsException e) {
        out.println("인덱스가 유효하지 않습니다.");
      } catch (Exception e) {
        out.println("인덱스 값이 잘못되었거나, 실행 중 오류가 발생했습니다.");
        e.printStackTrace();
      } // try ~ catch
    } // while
  }
  
  // 클라이언트에서 보낸 데이터 형식
  // add?name=홍길동&position=대리&tel=111-1111&email=hong@test.com
  private void doAdd(String params) throws Exception {
    String[] values = params.split("&");
    HashMap<String, String> paramMap = new HashMap<>();
    
    for (String value : values) {
      String[] kv = value.split("=");
      paramMap.put(kv[0], kv[1]);
    }
    
    Contact contact = new Contact();
    contact.setName(paramMap.get("name"));
    contact.setPosition(paramMap.get("position"));
    contact.setTel(paramMap.get("tel"));
    contact.setEmail(paramMap.get("email"));

    if (existEmail(contact.getEmail())) {
      out.println("같은 이메일이 존재합니다. 등록을 취소합니다.");
      return ;
    }
    
    list.add(contact);
    changed = true;
    out.println("등록하였습니다.");
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
      out.printf("%s,%s,%s,%s\n",
        contact.getName(),
        contact.getPosition(),
        contact.getTel(),
        contact.getEmail());
    }
  }

  // => view?name=홍길동
  private void doView(String params) {
    String[] values = params.split("=");
    for (Contact contact : list) {
      if (contact.getName().equals(values[1])) {
        out.println("-------------------------");
        out.printf("이름: %s\n", contact.getName());
        out.printf("직위: %s\n", contact.getPosition());
        out.printf("전화: %s\n", contact.getTel());
        out.printf("이메일: %s\n", contact.getEmail());
      }
    }
    out.println("-------------------------");
  }

  // => delete?email=hong@test.com
  private void doDelete(String params) { // 마지막 버전
    String[] values = params.split("=");
    for (Contact contact : list) {
      if (contact.getEmail().equals(values[1])) {
        list.remove(contact);
        changed = true;
        out.println("삭제하였습니다.");
        break;
      }
    }
  }

  // => update?name=홍길동&position=대리&tel=111-1111&email=hong@test.com
  private void doUpdate(String params) {
    String[] values = params.split("&");
    HashMap<String,String> paramMap = new HashMap<>();
    
    for (String value : values) {
      String[] kv = value.split("=");
      paramMap.put(kv[0], kv[1]);
    }
    
    for (Contact contact : list) {
      if (contact.getEmail().toLowerCase().equals(paramMap.get("email"))) {
        contact.setName(paramMap.get("name"));
        contact.setPosition(paramMap.get("position"));
        contact.setTel(paramMap.get("tel"));
        out.println("변경하였습니다.");
        changed = true;
        return;
      }
    }
    out.println("이메일을 찾지 못했습니다.");
  }
  
  public void save() throws Exception {
    FileOutputStream out = new FileOutputStream(this.filename);
    ObjectOutputStream out2 = new ObjectOutputStream(out);
    out2.writeObject(list);
    out2.close();
    changed = false;
  }
}