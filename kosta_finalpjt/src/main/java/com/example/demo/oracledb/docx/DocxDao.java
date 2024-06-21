package com.example.demo.oracledb.docx;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.oracledb.users.Users;

import jakarta.transaction.Transactional;

@Repository
public interface DocxDao extends JpaRepository<Docx, Integer> {
	//writer 검색
	List<Docx> findDistinctByWriter (Users writer);
	//formtype으로 검색 num 값으로 정렬후 출력
	List<Docx> findByFormtypeOrderByFormnumDesc(String formtype);
	//title like 검색
	List<Docx> findByTitleLike(String title);
	//docxkey로 검색해서 그 문서안에 전체 시니어 검색하는 메서드
	List<Docx> findByDocxkeyAndFormtype(int docxkey, String formtype);
	//docxkey값을 기준으로 중복되는 문서 정리해주는 메서드
	List<Docx> findDistinctByDocxkey(int docxkey);
	//중복제거 해서 전체 리스트 가져오기
	List<Docx> findByDocxorder(int num);
	//Senior이름으로 검색
	List<Docx> findBySenior(String senior);
	
	// docxkey 값이 같은 문서를 전부 삭제하는 메서드
    @Modifying
    @Transactional
    @Query("DELETE FROM Docx d WHERE d.docxkey = :docxkey")
    void deleteByDocxkey(int docxkey);
	
	
}
