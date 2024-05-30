package com.example.demo.members;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.users.Users;

@Repository
public interface MembersDao extends JpaRepository<Members, Integer> {
//	Members findByName(String userid);

//	ArrayList<Members> findByDeptId(int deptid);
//
//	ArrayList<Members> findByJoblv(int joblv);
}
