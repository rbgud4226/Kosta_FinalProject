package com.example.demo.mariadb.mail;

import com.example.demo.mariadb.users.virtual_users;
import com.example.demo.mariadb.users.virtual_users_dao;
import com.example.demo.mariadb.users.virtual_users_dto;
import com.example.demo.mariadb.users.virtual_users_service;
import jakarta.mail.*;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.search.MessageIDTerm;
import jakarta.mail.search.SearchTerm;
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

    JavaMailSender emailSender = mailSenderFactory.getSender(user.getEmail(), user.getPassword());

    try {
      MimeMessage message = emailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true);

      helper.setSubject(title);
      helper.setText(content);
      helper.setFrom(receivd_user.getEmail());
//      helper.setCc(ref_user.getEmail());
      // set to 에 문자열 사용 가능
      helper.setTo(user.getEmail());

      // 파일 첨부
//      File file = new File("");
//      FileItem fileItem = new DiskFileItem("mainFile", Files.probeContentType(file.toPath()),
//          false, file.getName(), (int) file.length(), file.getParentFile());

      emailSender.send(message);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }

  public ArrayList<Map<String, Object>> recieveMail(String mail, String password){
    Properties properties = new Properties();
    properties.put("mail.imap.host", "192.168.0.9");
    properties.put("mail.imap.port", "143");
    properties.put("mail.imap.ssl.enable", "false");
    properties.put("mail.store.protocol","imap");
    ArrayList<Map<String, Object>> maillist = new ArrayList<>();
    Session session = Session.getDefaultInstance(properties);

    try{
      Store store = session.getStore("imap");
      store.connect(mail, password);

      Folder inbox = store.getFolder("INBOX");
      inbox.open(Folder.READ_WRITE);

      Message[] messages = inbox.getMessages();
      for(Message message : messages){
        Map<String, Object> rmail = new HashMap<>();
        rmail.put("Message-ID",message.getHeader("Message-ID")[0]);
        rmail.put("Subject", message.getSubject());
        rmail.put("From", message.getFrom()[0]);
        rmail.put("To", message.getAllRecipients()[0]);
        rmail.put("Date", message.getSentDate());
        rmail.put("Content", message.getContent());
        maillist.add(rmail);
      }

      inbox.close();
      store.close();
    } catch (NoSuchProviderException e) {
      e.printStackTrace();
    } catch (MessagingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return maillist;
  }

  public Map<String, Object> selectMail(String mail, String password, String messageID){
    Properties properties = new Properties();
    properties.put("mail.imap.host", "192.168.0.9");
    properties.put("mail.imap.port", "143");
    properties.put("mail.imap.ssl.enable", "false");
    properties.put("mail.store.protocol","imap");
    Session session = Session.getDefaultInstance(properties);
    Map<String, Object> rmail = new HashMap<>();

    try{
      Store store = session.getStore("imap");
      store.connect(mail, password);

      Folder inbox = store.getFolder("INBOX");
      inbox.open(Folder.READ_WRITE);

      SearchTerm searchTerm = new MessageIDTerm(messageID);

      Message[] message = inbox.search(searchTerm);
      rmail.put("Message-ID",message[0].getHeader("Message-ID")[0]);
      rmail.put("Subject", message[0].getSubject());
      rmail.put("From", message[0].getFrom()[0]);
      rmail.put("To", message[0].getAllRecipients()[0]);
      rmail.put("Date", message[0].getSentDate());
      rmail.put("Content", message[0].getContent());

      inbox.close();
      store.close();
    } catch (NoSuchProviderException e) {
      e.printStackTrace();
    } catch (MessagingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return rmail;
  }

  public void delMail(String mail, String password, String messageID){
    Properties properties = new Properties();
    properties.put("mail.imap.host", "192.168.0.9");
    properties.put("mail.imap.port", "143");
    properties.put("mail.imap.ssl.enable", "false");
    properties.put("mail.store.protocol","imap");
    Session session = Session.getDefaultInstance(properties);
    Map<String, Object> rmail = new HashMap<>();

    try{
      Store store = session.getStore("imap");
      store.connect(mail, password);

      Folder inbox = store.getFolder("INBOX");
      inbox.open(Folder.READ_WRITE);

      SearchTerm searchTerm = new MessageIDTerm(messageID);

      Message[] messages = inbox.search(searchTerm);

      for(Message message : messages) {
        System.out.println("Deleting message with subject : " + message);
        message.setFlag(Flags.Flag.DELETED, true);
      }

      inbox.close();
      store.close();
    } catch (NoSuchProviderException e) {
      e.printStackTrace();
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }
}
