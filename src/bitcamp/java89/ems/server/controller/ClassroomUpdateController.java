package bitcamp.java89.ems.server.controller;

import java.io.PrintStream;
import java.util.HashMap;

import bitcamp.java89.ems.server.Command;
import bitcamp.java89.ems.server.dao.ClassroomDao;
import bitcamp.java89.ems.server.vo.Classroom;

public class ClassroomUpdateController implements Command {
  private ClassroomDao classRoomDao;

  public ClassroomUpdateController() {
    classRoomDao = ClassroomDao.getInstance();
  }
  
  public void service(HashMap<String,String> paramMap, PrintStream out) {
    if (!classRoomDao.existName(paramMap.get("name"))) {
      out.println("해당 강의실을 찾지 못했습니다.");
      return;
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
    
    classRoomDao.update(classroom);
    out.println("변경하였습니다.");
  }
}