package com.example.demo.mariadb.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface virtual_users_dao extends JpaRepository<virtual_users, Integer> {
  virtual_users_dto findByBox (String box);
}
