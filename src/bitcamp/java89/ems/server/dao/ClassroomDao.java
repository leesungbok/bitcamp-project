package bitcamp.java89.ems.server.dao;

import java.util.ArrayList;

import bitcamp.java89.ems.server.vo.Classroom;

public class ClassroomDao extends AbstractDao<Classroom> {
  static ClassroomDao obj;

  public ClassroomDao() throws Exception {
    this.setFilename("classroom-v1.9.data");
    try {this.load();} catch (Exception e) {}
  }

  public synchronized void insert(Classroom classRoom) {
    list.add(classRoom);
    try {this.save();} catch (Exception e) {e.printStackTrace();}
  }

  public ArrayList<Classroom> getList() {
    return this.list;
  }

  public ArrayList<Classroom> getListByName(String name) {
    ArrayList<Classroom> results = new ArrayList<>();
    for (Classroom classRoom : list) {
      if (classRoom.getName().equals(name)) {
        results.add(classRoom);
      }
    }
    return results;
  }

  public synchronized void update (Classroom classRoom) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getName().equals(classRoom.getName())) {
        list.set(i, classRoom);
      }
    }
    try {this.save();} catch (Exception e) {e.printStackTrace();}
  }

  public synchronized void delete(String name) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getName().equals(name)) {
        list.remove(i);
      }
    }
    try {this.save();} catch (Exception e) {e.printStackTrace();}
  }

  public boolean existName(String name) {
    for (Classroom classRoom : list) {
      if (classRoom.getName().toLowerCase().equals(name.toLowerCase())) {
        return true;
      }
    }
    return false;
  }
}