package bitcamp.java89.ems.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class EduAppClient {
  public static void main(String[] args) {
    Scanner keyScan = new Scanner(System.in);
    System.out.print("서버주소? ");
    String serverAddr = keyScan.nextLine();
    
    try (
        Socket socket = new Socket(serverAddr, 8888);
        Scanner in = new Scanner(new BufferedInputStream(socket.getInputStream()));
        PrintStream out = new PrintStream(
            new BufferedOutputStream(socket.getOutputStream()), true)) {
      
      while (true) {
        // 서버가 보낸 데이터를 읽는다. 빈 줄을 입력 받을 때까지.
        boolean firstLine = true;
        while (true) {
          String message = in.nextLine();
          if (message.length() == 0) {
            break;
          }
          System.out.printf("%s%s", ((firstLine)?"":"\n"), message);
          firstLine = false;
        }
        
        // 사용자로부터 명령을 입력 받아 출력한다.
        String command = keyScan.nextLine();
        out.println(command);
        
        if (command.toLowerCase().equals("quit")) {
          break;
        }
      }
      
    } catch (Exception e) {
      e.printStackTrace();
      
    } finally {
      keyScan.close();
    }
  }
}

/* contact/add?name=홍길동&position=대리&tel=111-1111&email=hong@test.com
 * contact/list
 * contact/view?name=홍길동
 * contact/delete?email=hong@test.com
 * contact/update?name=이성복&position=사원&tel=123-4567&email=hong@test.com
 * 
 * classroom/add?name=자바강의실&location=302호&area=100m^2&time=08:00~22:00&people=30&aircon=true&projector=true
 * classroom/list
 * classroom/view?name=자바강의실
 * classroom/delete?name=자바강의실
 * classroom/update?name=자바강의실&location=301호&area=200m^2&time=09:00~21:00&people=400&aircon=false&projector=false
 * 
 * student/add?userId=eomjinyoung&password=1234&name=엄진영&email=eomjinyoung@test.com&tel=010-1234-1234
 * student/list
 * student/view?name=엄진영
 * student/delete?name=엄진영
 * student/update?userId=312&password=dz&name=엄진영&email=zzz&tel=3120
 * 
 * curriculum/add?name=강죄명&introduce=소개&target=123&document=교재명
 * curriculum/list
 * curriculum/view?name=강죄명
 * curriculum/delete?target=123
 * curriculum/update?name=zz&introduce=zzz&target=123&document=zzzzz
 * 
 * textbook/add?title=책제목&author=저자&press=출판사&page=100&price=10000&dayofissue=test
 * textbook/list
 * textbook/view?title=책제목
 * textbook/delete?title=책제목
 * textbook/update?title=책제목&author=zz&press=zzz&page=999&price=9999&dayofissue=zzzzz
 */