package com.example.demo.members;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.depts.Depts;
import com.example.demo.users.Users;

@Repository
public interface MembersDao extends JpaRepository<Members, Integer> {
	Members findByUserid(Users userid);
	ArrayList<Members> findByDeptid(Depts deptid);
	ArrayList<Members> findByJoblv(int joblv);
}
