package com.example.demo.member;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
	@Autowired
	private MemberDao dao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public MemberDto save(MemberDto dto) {
		Member m = dao.save(new Member(dto.getId(), passwordEncoder.encode(dto.getPwd()), dto.getName(), dto.getEmail(),
				dto.getType()));
		return new MemberDto(m.getId(), m.getPwd(), m.getName(), m.getEmail(), m.getType());
	}

	public MemberDto getMember(String id) {
		Member m = dao.findById(id).orElse(null);
		if (m == null) {
			return null;
		}
		return new MemberDto(m.getId(), m.getPwd(), m.getName(), m.getEmail(), m.getType());
	}

	public ArrayList<MemberDto> getAll() {
		List<Member> l = dao.findAll();
		ArrayList<MemberDto> list = new ArrayList<>();
		for (Member m : l) {
			list.add(new MemberDto(m.getId(), m.getPwd(), m.getName(), m.getEmail(), m.getType()));
		}
		return list;
	}

	public void delMember(String id) {
		dao.deleteById(id);
	}

	public ArrayList<MemberDto> getByType(String type) {
		List<Member> l = dao.findByType(type);
		ArrayList<MemberDto> list = new ArrayList<>();
		for (Member m : l) {
			list.add(new MemberDto(m.getId(), m.getPwd(), m.getName(), m.getEmail(), m.getType()));
		}
		return list;
	}
}
