package com.example.demo.mariadb.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class virtual_users_service {
  @Autowired
  private virtual_users_dao dao;

  public void save(String id) {
    String password = id + "$6$";
    String mail = id+"@mail.yserver.iptime.org";
    Random r = new Random();
    for(int i=0; i<16; i++){
      password += "" + r.nextInt(10);
    }
//    virtual_users user = dao.save(new virtual_users(0, 1, password, mail, id));
//    System.out.println("메일생성확인 : " + user);
  }

  public virtual_users_dto get (String box){
    return dao.findByBox(box);
  }
}
