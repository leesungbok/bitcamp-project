package bitcamp.java89.ems.server.dao;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import bitcamp.java89.ems.server.vo.Contact;

public class ContactDao {
  static ContactDao obj;
  private String filename = "contact-v1.7.data";
  private ArrayList<Contact> list;
  
  private ContactDao() {
    this.load();
  }
  
  public static ContactDao getInstance() {
    if (obj == null) {
      obj = new ContactDao();
    }
    return obj;
  }

  @SuppressWarnings("unchecked")
  private void load() {
    FileInputStream in0 = null;
    ObjectInputStream in = null;
    try {
      in0 = new FileInputStream(this.filename);
      in = new ObjectInputStream(in0);
      list = (ArrayList<Contact>)in.readObject();
    } catch (EOFException e) {
    } catch (Exception e) {
      System.out.println("연락처 데이터 로딩 중 오류 발생!");
      list = new ArrayList<Contact>();
    } finally {
      try {
        in.close();
        in0.close();
      } catch (Exception e) {
      }
    }
  }
  
  public synchronized void insert(Contact contact) {
    list.add(contact);
    try {this.save();} catch (Exception e) {e.printStackTrace();}
  }
  
  public ArrayList<Contact> getList() {
    return this.list;
  }

  public ArrayList<Contact> getListByName(String name) {
    ArrayList<Contact> results = new ArrayList<>();
    for (Contact contact : list) {
      if (contact.getName().equals(name)) {
        results.add(contact);
      }
    }
    return results;
  }
  
  public synchronized void update (Contact contact) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getEmail().equals(contact.getEmail())) {
        list.set(i, contact);
      }
    }
    try {this.save();} catch (Exception e) {e.printStackTrace();}
  }
  
  public synchronized void delete(String email) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getEmail().equals(email)) {
        list.remove(i);
      }
    }
    try {this.save();} catch (Exception e) {e.printStackTrace();}
  }
  
  public boolean existEmail(String email) {
    for (Contact contact : list) {
      if (contact.getEmail().toLowerCase().equals(email.toLowerCase())) {
        return true;
      }
    }
    return false;
  }

  public void save() throws Exception {
    FileOutputStream out = new FileOutputStream(this.filename);
    ObjectOutputStream out2 = new ObjectOutputStream(out);
    out2.writeObject(list);
    out2.close();
  }
}