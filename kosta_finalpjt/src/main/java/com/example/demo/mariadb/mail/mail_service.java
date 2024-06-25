package com.example.demo.mariadb.mail;

import com.example.demo.mariadb.users.virtual_users;
import com.example.demo.mariadb.users.virtual_users_dao;
import com.example.demo.mariadb.users.virtual_users_dto;
import com.example.demo.mariadb.users.virtual_users_service;
import jakarta.mail.*;
import jakarta.mail.internet.MimeMessage;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Service
public class mail_service {
  @Autowired
  private virtual_users_service service;

  @Autowired
  private mailSenderFactory mailSenderFactory;

  public void sendMail(String loginid, String title, String content, String ref, String receiver){
    virtual_users user = service.getbybox(loginid);
    virtual_users receivd_user = service.getbybox(receiver);
    virtual_users ref_user = service.getbybox(ref);

    JavaMailSender emailSender = mailSenderFactory.getSender("youn@mail.yserver.iptime.org", "1234");

    try {
      MimeMessage message = emailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true);

      helper.setSubject("title");
      helper.setText("content");
//      helper.setCc(ref_user.getEmail());
      helper.setFrom("youn@mail.yserver.iptime.org");

      // 파일 첨부
//      File file = new File("");
//      FileItem fileItem = new DiskFileItem("mainFile", Files.probeContentType(file.toPath()),
//          false, file.getName(), (int) file.length(), file.getParentFile());

      // set to 에 문자열 사용 가능
      helper.setTo("test@mail.yserver.iptime.org");
      emailSender.send(message);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }

  public Message[] recieveMail(String mail, String password){
    Properties properties = new Properties();
    properties.put("mail.imap.host", "192.168.0.9");
    properties.put("mail.imap.port", "993");
    properties.put("mail.imap.ssl.enable", "true");

    Session session = Session.getDefaultInstance(properties);
    Message[] messages = null;
    try{
      Store store = session.getStore("imap");
      store.connect("test@mail.yserver.iptime.org", "1234");

      Folder inbox = store.getFolder("INBOX");
      inbox.open(Folder.READ_ONLY);

      messages = inbox.getMessages();

      inbox.close(false);
      store.close();
    } catch (NoSuchProviderException e) {
      e.printStackTrace();
    } catch (MessagingException e) {
      e.printStackTrace();
    }

    return messages;
  }
}
