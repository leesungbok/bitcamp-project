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

import bitcamp.java89.ems.server.vo.Classroom;

public class ClassroomController {
  private Scanner in;
  private PrintStream out;
  private ArrayList<Classroom> list;
  private boolean changed;
  private String filename = "classroom-v1.6.data";

  public ClassroomController(Scanner in, PrintStream out) throws Exception {
    this.in = in;
    this.out = out;
    list = new ArrayList<Classroom>();
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
      list = (ArrayList<Classroom>)in.readObject();
    } catch (EOFException e) {
    } catch (Exception e) {
      System.out.println("강의실 데이터 로딩 중 오류 발생!");
    } finally {
      try {
        in.close();
        in0.close();
      } catch (Exception e) {}
    }
  }

  public void service() {
    loop : while(true) {
      out.println("강의실관리> ");
      out.println();
      String[] commands = in.nextLine().split("\\?");
      try {
        switch (commands[0].toLowerCase()) {
        case "add": this.doAdd(commands[1]); break;
        case "list": this.doList(); break;
        case "view": this.doView(commands[1]); break;
        case "delete": this.doDelete(commands[1]); break;
        case "update": this.doUpdate(commands[1]); break;
        case "main": break loop;
        default: 
          out.println("올바른 명령어가 아닙니다. 다시 입력하세요.");
        }
      } catch (IndexOutOfBoundsException e) {
        out.println("인덱스가 유효하지 않습니다.");
      } catch (Exception e) {
        out.println("인덱스 값이 잘못되었거나, 실행 중 오류가 발생했습니다.");
      } // try ~ catch
    } // while
  }
  
  //add?name=자바강의실&location=302호&area=100m^2&time=08:00~22:00&people=30&aircon=true&projector=true
  private void doAdd(String params) throws Exception {
    String[] values = params.split("&");
    HashMap<String, String> paramMap = new HashMap<>();
    
    for (String value : values) {
      String[] kv = value.split("=");
      paramMap.put(kv[0], kv[1]);
    }
    
    Classroom classroom = new Classroom();
    classroom.setName(paramMap.get("name"));
    classroom.setLocation(paramMap.get("location"));
    classroom.setArea(paramMap.get("area"));
    classroom.setTime(paramMap.get("time"));
    classroom.setPeople(Integer.parseInt(paramMap.get("people")));
    classroom.setAircon(
        (paramMap.get("aircon").toLowerCase().equals("true")) ? true : false);
    classroom.setProjector(
        (paramMap.get("projector").toLowerCase().equals("true")) ? true : false);
    
    list.add(classroom);
    changed = true;
    out.println("등록하였습니다.");
  }

  private void doList() {
    for (Classroom classroom : list) {
      out.printf("%s,%s,%s,%s,%d,%s,%s\n",
        classroom.getName(),
        classroom.getLocation(),
        classroom.getArea(),
        classroom.getTime(),
        classroom.getPeople(),
        ((classroom.isAircon())?"yes":"no"),
        ((classroom.isProjector())?"yes":"no"));
    }
  }
  
  //view?name=자바강의실
  private void doView(String params) {
    String[] values = params.split("=");
    for (Classroom classroom : list) {
      if (classroom.getName().equals(values[1])) {
        out.println("-----------------------------------");
        out.printf("강의실명: %s\n", classroom.getName());
        out.printf("위치: %s\n", classroom.getLocation());
        out.printf("면적: %s\n", classroom.getArea());
        out.printf("이용시간: %s\n", classroom.getTime());
        out.printf("수용인원: %d명\n", classroom.getPeople());
        out.printf("에어컨: %s\n",
        ((classroom.isAircon())?"yes":"no"));
        out.printf("프로젝터: %s\n",
        ((classroom.isProjector())?"yes":"no"));
      }
      out.println("-----------------------------------");
    }
  }
  
  //delete?name=자바강의실
  private void doDelete(String params) {
    String[] values = params.split("=");
    for (Classroom classroom : list) {
      if (classroom.getName().toLowerCase().equals(values[1])) {
        list.remove(classroom);
        out.println("삭제하였습니다.");
        changed = true;
        break;
      }
    }
  }
  
//update?name=자바강의실&location=301호&area=200m^2&time=09:00~21:00&people=400&aircon=false&projector=false
  private void doUpdate(String params) {
    String[] values = params.split("&");
    HashMap<String,String> paramMap = new HashMap<>();
    
    for (String value : values) {
      String[] kv = value.split("=");
      paramMap.put(kv[0], kv[1]);
    }
    
    for (Classroom classroom : list) {
      if (classroom.getName().toLowerCase().equals(paramMap.get("name"))) {
        classroom.setLocation(paramMap.get("location"));
        classroom.setArea(paramMap.get("area"));
        classroom.setTime(paramMap.get("time"));
        classroom.setPeople(Integer.parseInt(paramMap.get("people")));
        classroom.setAircon(
            (paramMap.get("aircon").toLowerCase().equals("true")) ? true : false);
        classroom.setProjector(
            (paramMap.get("projector").toLowerCase().equals("true")) ? true : false);
        out.println("변경하였습니다.");
        changed = true;
        return;
      }
    }
    out.println("해당 강의실을 찾지 못했습니다.");
  }

  public void save() throws Exception {
    FileOutputStream out = new FileOutputStream(this.filename);
    ObjectOutputStream out2 = new ObjectOutputStream(out);
    out2.writeObject(list);
    out2.close();
    changed = false;
  }
}