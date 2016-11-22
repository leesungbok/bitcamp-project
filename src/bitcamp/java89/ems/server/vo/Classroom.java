package bitcamp.java89.ems.server.vo;

import java.io.Serializable;

public class Classroom implements Serializable {
  private static final long serialVersionUID = 1L;
  
  private String name;

  private String location;

  private String area;

  private String time;
  private int people;
  private boolean aircon, projector;

  public Classroom() {}

  public Classroom(String name, String location, String time) {
    this.name = name;
    this.location = location;
    this.time = time;
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

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public int getPeople() {
    return people;
  }

  public void setPeople(int people) {
    this.people = people;
  }

  public boolean isAircon() {
    return aircon;
  }

  public void setAircon(boolean aircon) {
    this.aircon = aircon;
  }

  public boolean isProjector() {
    return projector;
  }

  public void setProjector(boolean projector) {
    this.projector = projector;
  }

  @Override
  public String toString() {
    return "Classroom [name=" + name + ", location=" + location + ", area=" + area + ", time=" + time
        + ", people=" + people + ", aircon=" + aircon + ", projector=" + projector + "]";
  }
}
