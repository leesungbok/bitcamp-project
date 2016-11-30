package bitcamp.java89.ems.server.controller;

import java.io.PrintStream;
import java.util.ArrayList;

import bitcamp.java89.ems.server.annotation.Component;
import bitcamp.java89.ems.server.annotation.RequestMapping;
import bitcamp.java89.ems.server.annotation.RequestParam;
import bitcamp.java89.ems.server.dao.ClassroomDao;
import bitcamp.java89.ems.server.vo.Classroom;

@Component
public class ClassroomController {
  ClassroomDao classroomDao;

  public void setClassroomDao(ClassroomDao classroomDao) {
    this.classroomDao = classroomDao;
  }
  //classroom/add?name=자바강의실&location=302호&area=100m^2&time=08:00~22:00&people=30&aircon=true&projector=true
  @RequestMapping(value = "classroom/add")
  public void add(
      @RequestParam("name") String name,
      @RequestParam("location") String location,
      @RequestParam("area") String area,
      @RequestParam("time") String time,
      @RequestParam("people") int people,
      @RequestParam("aircon") boolean aircon,
      @RequestParam("projector") boolean projector,
      PrintStream out) throws Exception {
    if (classroomDao.existName(name)) {
      out.println("같은 강의실이 존재합니다. 등록을 취소합니다.");
      return;
    }

    Classroom classroom = new Classroom();
    classroom.setName(name);
    classroom.setLocation(location);
    classroom.setArea(area);
    classroom.setTime(time);
    classroom.setPeople(people);
    classroom.setAircon(aircon);
    classroom.setProjector(projector);

    classroomDao.insert(classroom);
    out.println("등록하였습니다.");
  }

  @RequestMapping(value = "classroom/delete")
  public void delete(@RequestParam("name") String name, PrintStream out) throws Exception {
    if (!classroomDao.existName(name)) {
      out.println("해당 강의실을 찾지 못했습니다.");
      return;
    }

    classroomDao.delete(name);
    out.println("삭제하였습니다.");
  }

  @RequestMapping(value = "classroom/list")
  public void list(PrintStream out) throws Exception {
    ArrayList<Classroom> list = classroomDao.getList();
    if (list.size() == 0) {
      out.println("강의실 데이터가 없습니다.");
      return;
    }
    for (Classroom classroom : list) {
      out.printf("%s,%s,%s,%s,%d,%s,%s\n", classroom.getName(), classroom.getLocation(), classroom.getArea(),
          classroom.getTime(), classroom.getPeople(), ((classroom.isAircon()) ? "yes" : "no"),
          ((classroom.isProjector()) ? "yes" : "no"));
    }
  }

  @RequestMapping(value = "classroom/update")
  public void update(
      @RequestParam("name") String name,
      @RequestParam("location") String location,
      @RequestParam("area") String area,
      @RequestParam("time") String time,
      @RequestParam("people") int people,
      @RequestParam("aircon") boolean aircon,
      @RequestParam("projector") boolean projector,
      PrintStream out) throws Exception {
    if (!classroomDao.existName(name)) {
      out.println("해당 강의실을 찾지 못했습니다.");
      return;
    }

    Classroom classroom = new Classroom();
    classroom.setName(name);
    classroom.setLocation(location);
    classroom.setArea(area);
    classroom.setTime(time);
    classroom.setPeople(people);
    classroom.setAircon(aircon);
    classroom.setProjector(projector);

    classroomDao.update(classroom);
    out.println("변경하였습니다.");
  }

  @RequestMapping(value = "classroom/view")
  public void view(@RequestParam("name") String name, PrintStream out) throws Exception {
    ArrayList<Classroom> list = classroomDao.getListByName(name);

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
      out.printf("에어컨: %s\n", ((classroom.isAircon()) ? "yes" : "no"));
      out.printf("프로젝터: %s\n", ((classroom.isProjector()) ? "yes" : "no"));
    }
    out.println("-----------------------------------");
  }
}