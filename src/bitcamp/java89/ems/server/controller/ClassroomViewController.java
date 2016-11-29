package bitcamp.java89.ems.server.controller;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import bitcamp.java89.ems.server.AbstractCommand;
import bitcamp.java89.ems.server.dao.ClassroomDao;
import bitcamp.java89.ems.server.vo.Classroom;

public class ClassroomViewController extends AbstractCommand {
  ClassroomDao classroomDao;
  public void setClassroomDao(ClassroomDao classroomDao) {
    this.classroomDao = classroomDao;
  }
  @Override
  protected void doResponse(HashMap<String,String> paramMap, PrintStream out) throws Exception {
    ArrayList<Classroom> list = classroomDao.getListByName(paramMap.get("name"));
    
    if (list.size() == 0) {
      out.println("해당 강의실 정보를 찾지 못했습니다.");
      return;
    }
    
    for (Classroom classroom : list) {
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

  @Override
  public String getCommandString() {
    return "classroom/view";
  }
}