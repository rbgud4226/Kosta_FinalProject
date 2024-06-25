package com.example.demo.mariadb.mail;

import com.example.demo.mariadb.users.virtual_users;
import com.example.demo.mariadb.users.virtual_users_dto;
import com.example.demo.mariadb.users.virtual_users_service;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
  @ResponseBody
  public void maillist(HttpSession session, ModelMap map){
    String loginid = (String) session.getAttribute("loginId");
    virtual_users user = virtualservice.getbybox(loginid);
    Message[] mailbox = service.recieveMail("test@mail.yserver.iptime.org", "1234");
    try {
      for(Message message : mailbox){
        map.addAttribute("Subject", message.getSubject());
        map.addAttribute("From", message.getFrom()[0]);
        map.addAttribute("To", message.getAllRecipients()[0]);
        map.addAttribute("Date", message.getSentDate());
        map.addAttribute("Content", message.getContent());
      }
    } catch (MessagingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
