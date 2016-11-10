package bitcamp.java89.ems;

public class LinkedList<T> {
  Box<T> head;
  Box<T> tail;
  int length;

  public LinkedList() {
    head = new Box<T>();
    tail = head;
  }

  private class Box<T> {
    T value;
    Box<T> next;
    public Box() {}
    public Box(T value) {
      this.value = value;
    }
    @Override
    public String toString() {
      return "Box(" + this.value + ")";
    }
  }

  public void add(T value) {
    tail.value = value;
    tail.next = new Box<T>();
    tail = tail.next;
    length++;
  }

  public T get(int index) {
    if (index < 0 || index >= length) {
      throw new IndexOutOfBoundsException("인덱스가 유효하지 않습니다.");
    }
    Box<T> currentBox = head;
    for (int i = 0; i < index; i++) {
      currentBox = currentBox.next;
    }
    return currentBox.value;
  }

  public T set(int index, T newValue) {
    if (index < 0 || index >= length) {
      throw new IndexOutOfBoundsException("인덱스가 유효하지 않습니다.");
    }
    Box<T> currentBox = head;
    for (int i = 0; i < index; i++) {
      currentBox = currentBox.next;
    }
    T oldValue = currentBox.value;
    currentBox.value = newValue;
    return oldValue;
  }

  public T remove(int index) {
    if (index < 0 || index >= length) {
      throw new IndexOutOfBoundsException("인덱스가 유효하지 않습니다.");
    }
    T oldValue = null;
    if (index == 0) {
      oldValue = head.value;
      head = head.next;
    } else {
      Box<T> currentBox = head;
      for (int i = 0; i < (index - 1); i++) {
        currentBox = currentBox.next;
      }
      oldValue = currentBox.next.value;
      currentBox.next = currentBox.next.next;
    }
    length--;
    return oldValue;
  }

  public int size() {
    return length;
  }
}
