package bitcamp.java89.ems.server.controller;

import java.io.PrintStream;
import java.util.HashMap;

import bitcamp.java89.ems.server.Command;
import bitcamp.java89.ems.server.dao.ClassroomDao;

public class ClassroomDeleteController implements Command {
  private ClassroomDao classRoomDao;

  public ClassroomDeleteController() {
    classRoomDao = ClassroomDao.getInstance();
  }
  
  public void service(HashMap<String,String> paramMap, PrintStream out) {
    if (!classRoomDao.existName(paramMap.get("name"))) {
      out.println("해당 강의실을 찾지 못했습니다.");
      return;
    }
    
    classRoomDao.delete(paramMap.get("name"));
    out.println("삭제하였습니다.");
  }
}