package bitcamp.java89.ems.server.controller;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import bitcamp.java89.ems.server.AbstractCommand;
import bitcamp.java89.ems.server.dao.ClassroomDao;
import bitcamp.java89.ems.server.vo.Classroom;

public class ClassroomListController extends AbstractCommand {
  ClassroomDao classroomDao;
  public void setClassroomDao(ClassroomDao classroomDao) {
    this.classroomDao = classroomDao;
  }
  @Override
  protected void doResponse(HashMap<String,String> paramMap, PrintStream out) throws Exception {
    ArrayList<Classroom> list = classroomDao.getList();
    if (list.size() == 0) {
      out.println("강의실 데이터가 없습니다.");
      return;
    }
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
  @Override
  public String getCommandString() {
    return "classroom/list";
  }
}