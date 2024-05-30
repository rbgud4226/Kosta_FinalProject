package com.example.demo.members;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembersDao extends JpaRepository<Members, Integer> {
//	Members findByName(String userid);

//	ArrayList<Members> findByDeptId(int deptid);
//
//	ArrayList<Members> findByJoblv(int joblv);
}
