package com.example.demo.docx;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.members.Members;

@Service
public class DocxService {
	private DocxDao dao;

	// 문서 작성 , 수정
	public DocxDto save(DocxDto dto) {
		Docx d = dao.save(new Docx(dto.getFormnum(), dto.getWriter(), dto.getSenior(), dto.getStartdt(), dto.getEnddt(),
				dto.getTitle(), dto.getContent(), dto.getNote(), dto.getTaskclasf(), dto.getTaskplan(),
				dto.getTaskprocs(), dto.getTaskprocsres(), dto.getDeptandmeetloc(), dto.getDayoffclasf(),
				dto.getParticipant(), dto.getFormtype(), dto.getAprovdoc()));

		return new DocxDto(d.getFormnum(), d.getWriter(), d.getSenior(), d.getStartdt(), d.getEnddt(), d.getTitle(),
				d.getContent(), d.getNote(), d.getTaskclasf(), d.getTaskplan(), d.getTaskprocs(), d.getTaskprocsres(),
				d.getDeptandmeetloc(), d.getDayoffclasf(), d.getParticipant(), d.getFormtype(), d.getAprovdoc());
	}

	// 문서 번호로 검색
	public DocxDto getDocx(int formnum) {
		Docx d = dao.findById(formnum).orElse(null);
		if (d == null) {
			return null;
		}
		return new DocxDto(d.getFormnum(), d.getWriter(), d.getSenior(), d.getStartdt(), d.getEnddt(), d.getTitle(),
				d.getContent(), d.getNote(), d.getTaskclasf(), d.getTaskplan(), d.getTaskprocs(), d.getTaskprocsres(),
				d.getDeptandmeetloc(), d.getDayoffclasf(), d.getParticipant(), d.getFormtype(), d.getAprovdoc());
	}

	// 문서 전체 검색
	public ArrayList<DocxDto> getAll() {
		List<Docx> l = dao.findAll();
		ArrayList<DocxDto> list = new ArrayList<DocxDto>();
		for (Docx d : l) {
			list.add(new DocxDto(d.getFormnum(), d.getWriter(), d.getSenior(), d.getStartdt(), d.getEnddt(),
					d.getTitle(), d.getContent(), d.getNote(), d.getTaskclasf(), d.getTaskplan(), d.getTaskprocs(),
					d.getTaskprocsres(), d.getDeptandmeetloc(), d.getDayoffclasf(), d.getParticipant(), d.getFormtype(),
					d.getAprovdoc()));
		}
		return list;
	}

	// 문서 작성자 검색
	public ArrayList<DocxDto> getByWriter(Members writer) {
		List<Docx> l = dao.findByWriter(writer);
		if (l == null) {
			return null;
		}
		ArrayList<DocxDto> list = new ArrayList<DocxDto>();
		for (Docx d : l) {
			list.add(new DocxDto(d.getFormnum(), d.getWriter(), d.getSenior(), d.getStartdt(), d.getEnddt(),
					d.getTitle(), d.getContent(), d.getNote(), d.getTaskclasf(), d.getTaskplan(), d.getTaskprocs(),
					d.getTaskprocsres(), d.getDeptandmeetloc(), d.getDayoffclasf(), d.getParticipant(), d.getFormtype(),
					d.getAprovdoc()));
		}
		return list;
	}

	// 문서 제목으로 검색 Like문
	public ArrayList<DocxDto> getByTitle(String title) {
		List<Docx> l = dao.findByTitleLike("%" + title + "%");
		if (l == null) {
			return null;
		}
		ArrayList<DocxDto> list = new ArrayList<DocxDto>();
		for (Docx d : l) {
			list.add(new DocxDto(d.getFormnum(), d.getWriter(), d.getSenior(), d.getStartdt(), d.getEnddt(),
					d.getTitle(), d.getContent(), d.getNote(), d.getTaskclasf(), d.getTaskplan(), d.getTaskprocs(),
					d.getTaskprocsres(), d.getDeptandmeetloc(), d.getDayoffclasf(), d.getParticipant(), d.getFormtype(),
					d.getAprovdoc()));
		}
		return list;
	}

	// 문서 삭제
	public void delDocx(int formnum) {
		dao.deleteById(formnum);
	}
	
	//회의록  수정
	public void editDocx(int num, DocxDto dto) {
		Docx d = dao.findById(num).orElse(null);
		if(d != null) {
			
		}
	}

}
