package com.example.demo.users;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.depts.Depts;
import com.example.demo.depts.Joblvs;
import com.example.demo.members.MembersDto;

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
				dto.getAprov(), dto.getRoomUsers()));
		return new UsersDto(u.getId(), u.getUsernm(), u.getPwd(), u.getType(), u.getAprov(),
				new MembersDto(u, 0, null, null, null, null, null, null, null, null, null, null, null, null), u.getRoomUsers());
	}

	public void update(UsersDto dto) {
		dao.update(dto.getId(), dto.getUsernm(), dto.getType(), dto.getAprov());
	}

	public UsersDto getById(String id) {
		Users u = dao.findById(id).orElse(null);
		if (u == null) {
			return null;
		}
		return new UsersDto(u.getId(), u.getUsernm(), u.getPwd(), u.getType(), u.getAprov(),
				new MembersDto(u, 0, null, null, null, null, null, null, null, null, null, null, null, null), u.getRoomUsers());
	}

	public UsersDto getByUsernm(String usernm) {
		Users u = dao.findByUsernm(usernm);
		if (u == null) {
			return null;
		}
		return new UsersDto(u.getId(), u.getUsernm(), u.getPwd(), u.getType(), u.getAprov(),
				new MembersDto(u, 0, null, null, null, null, null, null, null, null, null, null, null, null), u.getRoomUsers());
	}

	public ArrayList<UsersDto> getByAprov(int aprov) {
		List<Users> l = dao.findByAprov(aprov);
		ArrayList<UsersDto> list = new ArrayList<UsersDto>();
		for (Users u : l) {
			list.add(new UsersDto(u.getId(), u.getUsernm(), u.getPwd(), u.getType(), u.getAprov(),
					new MembersDto(u, 0, null, null, null, null, null, null, null, null, null, null, null, null),
					u.getRoomUsers()));
		}
		return list;
	}

	public ArrayList<UsersDto> getByIdLike(String id) {
		List<Users> l = dao.findByIdLike("%" + id + "%");
		ArrayList<UsersDto> list = new ArrayList<UsersDto>();
		for (Users u : l) {
			list.add(new UsersDto(u.getId(), u.getUsernm(), u.getPwd(), u.getType(), u.getAprov(),
					new MembersDto(u, 0, null, null, null, null, null, null, null, null, null, null, null, null),
					u.getRoomUsers()));
		}
		return list;
	}

	public ArrayList<UsersDto> getByUsernmLike(String usernm) {
		List<Users> l = dao.findByUsernmLike("%" + usernm + "%");
		ArrayList<UsersDto> list = new ArrayList<UsersDto>();
		for (Users u : l) {
			list.add(new UsersDto(u.getId(), u.getUsernm(), u.getPwd(), u.getType(), u.getAprov(),
					new MembersDto(u, 0, null, null, null, null, null, null, null, null, null, null, null, null),
					u.getRoomUsers()));
		}
		return list;
	}

	public ArrayList<UsersDto> getAll() {
		List<Users> l = dao.findAll();
		ArrayList<UsersDto> list = new ArrayList<UsersDto>();
		for (Users u : l) {
			list.add(new UsersDto(u.getId(), u.getUsernm(), u.getPwd(), u.getType(), u.getAprov(),
					new MembersDto(u, 0, null, null, null, null, null, null, null, null, null, null, null, null),
					u.getRoomUsers()));
		}
		return list;
	}

	public ArrayList<UsersDto> getbyDepid(int deptid) {
		List<Users> l = dao.findAll();
		ArrayList<UsersDto> list = new ArrayList<UsersDto>();
		for (Users u : l) {
			list.add(new UsersDto(
					u.getId(), u.getUsernm(), u.getPwd(), u.getType(), u.getAprov(), new MembersDto(u, 0, null, null,
							null, null, null, null, null, new Depts(deptid, null, null), null, null, null, null),
					u.getRoomUsers()));
		}
		return list;
	}

	public ArrayList<UsersDto> getbyJoblv(int joblv) {
		List<Users> l = dao.findAll();
		ArrayList<UsersDto> list = new ArrayList<UsersDto>();
		for (Users u : l) {
			list.add(new UsersDto(u.getId(), u.getUsernm(), u.getPwd(), u.getType(), u.getAprov(),
					new MembersDto(u, 0, null, null, null, null, null, null, null, null, new Joblvs(0, joblv, ""), null, null, null),
					u.getRoomUsers()));
		}
		return list;
	}

	public void delMember(String id) {
		dao.deleteById(id);
	}

	// 채팅용 임시
	public Users getById2(String id) {
		Users u = dao.findById(id).orElse(null);
		return u;
	}

}
