package bitcamp.java89.ems.server.controller;

import java.io.PrintStream;
import java.util.HashMap;

import bitcamp.java89.ems.server.AbstractCommand;
import bitcamp.java89.ems.server.dao.ClassroomDao;
import bitcamp.java89.ems.server.vo.Classroom;

public class ClassroomAddController extends AbstractCommand {
  ClassroomDao classroomDao;
  public void setClassroomDao(ClassroomDao classroomDao) {
    this.classroomDao = classroomDao;
  }
  @Override
  protected void doResponse(HashMap<String,String> paramMap, PrintStream out) throws Exception {    
    if (classroomDao.existName(paramMap.get("name"))) {
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
    
    classroomDao.insert(classroom);
    out.println("등록하였습니다.");
  }
  @Override
  public String getCommandString() {
    return "classroom/add";
  }
}