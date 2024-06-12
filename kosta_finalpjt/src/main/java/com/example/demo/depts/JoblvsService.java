package com.example.demo.depts;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JoblvsService {
	
	@Autowired
	private JoblvsDao dao;
	
	public JoblvsDto save(JoblvsDto dto) {
		Joblvs j = dao.save(new Joblvs(dto.getJoblvidx(), dto.getJoblvid(), dto.getJoblvnm()));
		if(j == null) {
			return null;
		}
		return new JoblvsDto(j.getJoblvidx(), j.getJoblvid(), j.getJoblvnm());
	}
	
	public ArrayList<JoblvsDto> getByjoblvId(int joblvid) {
		List<Joblvs> l = dao.findByJoblvid(joblvid);
		ArrayList<JoblvsDto> list = new ArrayList<JoblvsDto>();
		for (Joblvs j : l) {
			list.add(new JoblvsDto(j.getJoblvidx(), j.getJoblvid(), j.getJoblvnm()));
		}
		return list;
	}
	
	public ArrayList<JoblvsDto> getByjoblvnmLike(String joblvnm) {
		List<Joblvs> l = dao.findByJoblvnmLike(joblvnm);
		ArrayList<JoblvsDto> list = new ArrayList<JoblvsDto>();
		for (Joblvs j : l) {
			list.add(new JoblvsDto(j.getJoblvidx(), j.getJoblvid(), j.getJoblvnm()));
		}
		return list;
	}
	
	public ArrayList<JoblvsDto> getAll() {
		List<Joblvs> l = dao.findAll();
		ArrayList<JoblvsDto> list = new ArrayList<JoblvsDto>();
		for (Joblvs j : l) {
			list.add(new JoblvsDto(j.getJoblvidx(), j.getJoblvid(), j.getJoblvnm()));
		}
		return list;
	}
	
	public void delJoblvs(int joblvidx) {
		dao.deleteById(joblvidx);
	}
}
