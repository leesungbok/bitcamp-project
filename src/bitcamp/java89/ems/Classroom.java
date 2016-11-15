package bitcamp.java89.ems;

import java.io.Serializable;

public class Classroom implements Serializable {
  private static final long serialVersionUID = 1L;
  
  protected String name, location, area, usabletime;
  protected int people;
  protected boolean aconditioner, projector;

  public Classroom() {}

  public Classroom(String name, String location, String usabletime) {
    this.name = name;
    this.location = location;
    this.usabletime = usabletime;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getArea() {
    return area;
  }

  public void setArea(String area) {
    this.area = area;
  }

  public String getUsabletime() {
    return usabletime;
  }

  public void setUsabletime(String usabletime) {
    this.usabletime = usabletime;
  }

  public int getPeople() {
    return people;
  }

  public void setPeople(int people) {
    this.people = people;
  }

  public boolean isAconditioner() {
    return aconditioner;
  }

  public void setAconditioner(boolean aconditioner) {
    this.aconditioner = aconditioner;
  }

  public boolean isProjector() {
    return projector;
  }

  public void setProjector(boolean projector) {
    this.projector = projector;
  }

  @Override
  public String toString() {
    return "Classroom [name=" + name + ", location=" + location + ", area=" + area + ", usabletime=" + usabletime
        + ", people=" + people + ", aconditioner=" + aconditioner + ", projector=" + projector + "]";
  }
}
