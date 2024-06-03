package com.example.demo.depts;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.members.Members;

@Repository
public interface DeptsDao extends JpaRepository<Depts, Integer> {
	ArrayList<Depts> findByDeptnmContains(String deptnm);
	ArrayList<Depts> findByMgridContains(Members mgrid);
}
