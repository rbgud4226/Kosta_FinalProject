package com.example.demo.users;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/*
 * ==================================================================
 * 추가 및 전체수정
 * id로 검색
 * aprov으로 검색
 * 전체 검색
 * id로 삭제
 * ==================================================================
*/

@Service
public class UsersService {
	@Autowired
	private UsersDao dao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public UsersDto save(UsersDto dto) {
		Users u = dao.save(new Users(dto.getId(), dto.getUsernm(), passwordEncoder.encode(dto.getPwd()), dto.getType(),
				dto.getAprov()));
		return new UsersDto(u.getId(), u.getUsernm(), u.getPwd(), u.getType(), u.getAprov());
	}

	public UsersDto getById(String id) {
		Users u = dao.findById(id).orElse(null);
		if (u == null) {
			return null;
		}
		return new UsersDto(u.getId(), u.getUsernm(), u.getPwd(), u.getType(), u.getAprov());
	}

//	public UsersDto getByUsernm(String usernm) {
//		Users u = dao.findByName(usernm);
//		if (u == null) {
//			return null;
//		}
//		return new UsersDto(u.getId(), u.getUsernm(), u.getPwd(), u.getType(), u.getAprov());
//	}

	public ArrayList<UsersDto> getByAprov(int aprov) {
		List<Users> l = dao.findByAprov(aprov);
		ArrayList<UsersDto> list = new ArrayList<UsersDto>();
		for (Users u : l) {
			list.add(new UsersDto(u.getId(), u.getUsernm(), u.getPwd(), u.getType(), u.getAprov()));
		}
		return list;
	}

	public ArrayList<UsersDto> getAll() {
		List<Users> l = dao.findAll();
		ArrayList<UsersDto> list = new ArrayList<UsersDto>();
		for (Users u : l) {
			list.add(new UsersDto(u.getId(), u.getUsernm(), u.getPwd(), u.getType(), u.getAprov()));
		}
		return list;
	}

	public void delMember(String id) {
		dao.deleteById(id);
	}

}
