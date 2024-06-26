package com.example.demo.mariadb.mail;

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

  @PostMapping("/sendmail")
  public void sendmail(HttpSession session, @RequestParam String title, @RequestParam String content,
                       @RequestParam String ref, @RequestParam String receiver){
    String loginId = (String) session.getAttribute("loginId");
    System.out.println("메일발신자 : " + loginId);
    service.sendMail(loginId,title, content, ref, receiver);
  }

  @GetMapping("/list")
  @ResponseBody
  public ArrayList<Map<String, Object>> maillist(HttpSession session, ModelMap map){
    String loginid = (String) session.getAttribute("loginId");
    ArrayList<Map<String, Object>> list = service.recieveMail(loginid);
    for(Map m : list){
      System.out.println("===============================");
      System.out.println(m.get("Subject"));
      System.out.println(m.get("From"));
      System.out.println(m.get("To"));
      System.out.println(m.get("Date"));
      System.out.println(m.get("Content"));
      System.out.println("===============================");
    }
    return list;
  }

  @GetMapping("/mail")
  @ResponseBody
  public Map<String, Object> maildetail(HttpSession session, @RequestParam String messageId){
    String loginId = (String) session.getAttribute("loginId");
    return service.selectMail(loginId, messageId);
  }

  @RequestMapping("/del")
  public void delmail(HttpSession session, @RequestParam String messageId){
    String loginId = (String) session.getAttribute("loginId");
    service.delMail(loginId, messageId);
  }

}
