package bitcamp.java89.ems;

public class Classrom {
  String name, location, area, usabletime;
  int people;
  boolean aconditioner, projector;

  public Classrom() {}

  public Classrom(String name, String location, String usabletime) {
    this.name = name;
    this.location = location;
    this.usabletime = usabletime;
  }
}
