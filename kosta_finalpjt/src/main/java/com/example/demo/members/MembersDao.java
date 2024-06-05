package com.example.demo.members;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.depts.Depts;
import com.example.demo.users.Users;

import jakarta.transaction.Transactional;

@Repository
public interface MembersDao extends JpaRepository<Members, Integer> {
	Members findByUserid(Users userid);

	ArrayList<Members> findByDeptid(Depts deptid);

	ArrayList<Members> findByJoblv(int joblv);

	@Transactional
	@Modifying
	@Query(value = "update members set address=null where memberid=:memberid", nativeQuery = true)
	void update(@Param("memberid") int memberid);
}
