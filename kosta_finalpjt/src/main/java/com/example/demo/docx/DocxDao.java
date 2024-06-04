package com.example.demo.docx;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.members.Members;

@Repository
public interface DocxDao extends JpaRepository<Docx, Integer> {
	ArrayList<Docx> findByWriter (Members writer);
	
	ArrayList<Docx> findByTitleLike (String title);
	
}
