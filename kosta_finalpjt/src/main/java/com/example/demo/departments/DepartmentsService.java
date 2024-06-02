package com.example.demo.departments;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.members.Members;

@Service
public class DepartmentsService {
	@Autowired
	private DepartmentsDao dao;

	public DepartmentsDto save(DepartmentsDto dto) {
		Departments d = dao.save(new Departments(dto.getDeptid(), dto.getDeptnm(), dto.getMgrid()));
		return new DepartmentsDto(d.getDeptid(), d.getDeptnm(), d.getMgrid());
	}

	private DepartmentsDto getByDeptId(int deptid) {
		Departments d = dao.findById(deptid).orElse(null);
		if (d == null) {
			return null;
		}
		return new DepartmentsDto(d.getDeptid(), d.getDeptnm(), d.getMgrid());
	}

	private DepartmentsDto getByDeptNm(String deptnm) {
		Departments d = dao.findByDeptnm(deptnm);
		if (d == null) {
			return null;
		}
		return new DepartmentsDto(d.getDeptid(), d.getDeptnm(), d.getMgrid());
	}

	private DepartmentsDto getByMgrId(Members mgrid) {
		Departments d = dao.findByMgrid(mgrid);
		if (d == null) {
			return null;
		}
		return new DepartmentsDto(d.getDeptid(), d.getDeptnm(), d.getMgrid());
	}

	private ArrayList<DepartmentsDto> getAll() {
		List<Departments> l = dao.findAll();
		ArrayList<DepartmentsDto> list = new ArrayList<DepartmentsDto>();
		for(Departments d : l) {
			list.add(new DepartmentsDto(d.getDeptid(), d.getDeptnm(), d.getMgrid()));
		}
		return list;
	}
	
	private void delDepartments(int deptid) {
		dao.deleteById(deptid);
	}
}
