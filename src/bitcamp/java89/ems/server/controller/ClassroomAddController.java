package bitcamp.java89.ems.server.controller;

import java.io.PrintStream;
import java.util.HashMap;

import bitcamp.java89.ems.server.Command;
import bitcamp.java89.ems.server.dao.ClassroomDao;
import bitcamp.java89.ems.server.vo.Classroom;

public class ClassroomAddController implements Command {
  private ClassroomDao classRoomDao;

  public ClassroomAddController() {
    classRoomDao = ClassroomDao.getInstance();
  }
  
  public void service(HashMap<String,String> paramMap, PrintStream out) {
    if (classRoomDao.existName(paramMap.get("name"))) {
      out.println("같은 강의실이 존재합니다. 등록을 취소합니다.");
      return ;
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
    
    classRoomDao.insert(classroom);
    out.println("등록하였습니다.");
  }
}