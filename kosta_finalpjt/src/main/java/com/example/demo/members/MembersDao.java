package com.example.demo.members;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.departments.Departments;

@Repository
public interface MembersDao extends JpaRepository<Members, Integer> {
	ArrayList<Members> findByDeptid(Departments deptid);
	ArrayList<Members> findByJoblv(int joblv);
}
