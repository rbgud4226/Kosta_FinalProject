package com.example.demo.departments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.members.Members;

@Repository
public interface DepartmentsDao extends JpaRepository<Departments, Integer> {
	Departments findByDeptnm(String deptnm);
	Departments findByMgrid(Members mgrid);
}
