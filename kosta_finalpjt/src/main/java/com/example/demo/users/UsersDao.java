package com.example.demo.users;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * ==================================================================
 * aprov 검색 추가
 * ==================================================================
*/

@Repository
public interface UsersDao extends JpaRepository<Users, String> {
	ArrayList<Users> findByAprov(int aprov);
}
