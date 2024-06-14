package com.example.demo.members;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.depts.Depts;
import com.example.demo.depts.Joblvs;
import com.example.demo.users.Users;
import com.example.demo.users.UsersDao;

/*
 * ==================================================================
 * 추가 및 전체수정
 * id로 검색
 * 이름으로 검색
 * 전체 검색
 * 부서번호로 검색
 * 직급으로 검색
 * ==================================================================
*/

@Service
public class MembersService {
	@Autowired
	private MembersDao mdao;

	@Autowired
	private UsersDao udao;

	public MembersDto save(MembersDto dto) {
		Members m = mdao.save(new Members(dto.getUserid(), dto.getMemberid(), dto.getBirthdt(), dto.getEmail(),
				dto.getCpnum(), dto.getAddress(), dto.getMemberimgnm(), dto.getHiredt(), dto.getLeavedt(),
				dto.getDeptid(), dto.getJoblvid(), dto.getMgrid(), null));
		return new MembersDto(m.getUserid(), m.getMemberid(), m.getBirthdt(), m.getEmail(), m.getCpnum(),
				m.getAddress(), m.getMemberimgnm(), m.getHiredt(), m.getLeavedt(), m.getDeptid(), m.getJoblvid(),
				m.getMgrid(), null, null);
	}

//	public MembersDto update(MembersDto dto) {
//		mdao.update(dto.getMemberid(), dto.getBirthdt(), dto.getEmail(), dto.getCpnum(), dto.getAddress(), dto.getMemberimgnm(), dto.getHiredt(), dto.getLeavedt(), dto.getDeptid(), dto.getJoblvid(), dto.getMgrid());
//		return getByMemberId(dto.getMemberid());
//	}

	public MembersDto getByMemberId(int memberid) {
		Members m = mdao.findById(memberid).orElse(null);
		if (m == null) {
			return null;
		}
		return new MembersDto(m.getUserid(), m.getMemberid(), m.getBirthdt(), m.getEmail(), m.getCpnum(),
				m.getAddress(), m.getMemberimgnm(), m.getHiredt(), m.getLeavedt(), m.getDeptid(), m.getJoblvid(),
				m.getMgrid(), null, null);
	}

	public MembersDto getByuserId(String userid) {
		Members m = mdao.findByUserid(new Users(userid, "", "", "", 0, null));
		if (m == null) {
			return null;
		}
		return new MembersDto(m.getUserid(), m.getMemberid(), m.getBirthdt(), m.getEmail(), m.getCpnum(),
				m.getAddress(), m.getMemberimgnm(), m.getHiredt(), m.getLeavedt(), m.getDeptid(), m.getJoblvid(),
				m.getMgrid(), null, null);
	}

	public MembersDto getByuserNm(Users userid) {
		Members m = mdao.findByUserid(userid);
		if (m == null) {
			return null;
		}
		return new MembersDto(m.getUserid(), m.getMemberid(), m.getBirthdt(), m.getEmail(), m.getCpnum(),
				m.getAddress(), m.getMemberimgnm(), m.getHiredt(), m.getLeavedt(), m.getDeptid(), m.getJoblvid(),
				m.getMgrid(), null, null);
	}

	public ArrayList<MembersDto> getAll() {
		List<Members> l = mdao.findAll();
		ArrayList<MembersDto> list = new ArrayList<>();
		for (Members m : l) {
			list.add(new MembersDto(m.getUserid(), m.getMemberid(), m.getBirthdt(), m.getEmail(), m.getCpnum(),
					m.getAddress(), m.getMemberimgnm(), m.getHiredt(), m.getLeavedt(), m.getDeptid(), m.getJoblvid(),
					m.getMgrid(), null, null));
		}
		return list;
	}

	public ArrayList<MembersDto> getByDeptNm(String deptnm) {
		List<Members> l = mdao.findByDeptid(new Depts(Integer.parseInt(deptnm), null, null));
		ArrayList<MembersDto> list = new ArrayList<>();
		for (Members m : l) {
			list.add(new MembersDto(m.getUserid(), m.getMemberid(), m.getBirthdt(), m.getEmail(), m.getCpnum(),
					m.getAddress(), m.getMemberimgnm(), m.getHiredt(), m.getLeavedt(), m.getDeptid(), m.getJoblvid(),
					m.getMgrid(), null, null));
		}
		return list;
	}

	public ArrayList<MembersDto> getByJobLv(int joblvid) {
		List<Members> l = mdao.findByJoblvid(new Joblvs(0, joblvid, ""));
		ArrayList<MembersDto> list = new ArrayList<>();
		for (Members m : l) {
			list.add(new MembersDto(m.getUserid(), m.getMemberid(), m.getBirthdt(), m.getEmail(), m.getCpnum(),
					m.getAddress(), m.getMemberimgnm(), m.getHiredt(), m.getLeavedt(), m.getDeptid(), m.getJoblvid(),
					m.getMgrid(), null, null));
		}
		return list;
	}

}
