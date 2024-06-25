package com.example.demo.mariadb.mail;

import com.example.demo.mariadb.users.virtual_users;
import com.example.demo.mariadb.users.virtual_users_service;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@Controller
@RequestMapping("/mail")
public class mail_controller {
  @Autowired
  private mail_service service;

  @Autowired
  private virtual_users_service virtualservice;


  @PostMapping("/sendmail")
  public void sendmail(HttpSession session, @RequestParam String title, @RequestParam String content,
                       @RequestParam String ref, @RequestParam String receiver){
    String loginId = (String) session.getAttribute("loginId");

    service.sendMail("testaaa","title_test", "test_content", "testbbb", "testccc");
  }

  @GetMapping("/list")
  public void maillist(HttpSession session, ModelMap map){
    String loginid = (String) session.getAttribute("loginId");
    virtual_users user = virtualservice.getbybox(loginid);
    ArrayList<Map<String, Object>> list = service.recieveMail("test@mail.yserver.iptime.org", "1234");
    for(Map m : list){
      System.out.println("===============================");
      System.out.println(m.get("Subject"));
      System.out.println(m.get("From"));
      System.out.println(m.get("To"));
      System.out.println(m.get("Date"));
      System.out.println(m.get("Content"));
      System.out.println("===============================");
    }
  }

}
