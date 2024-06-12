package com.example.demo.docx;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.members.Members;
import com.example.demo.members.MembersDao;
import com.example.demo.members.MembersDto;
import com.example.demo.users.Users;

@Service
public class DocxService {
	@Autowired
	private DocxDao dao;
	@Autowired
	private MembersDao mdao;


	// 문서 작성 , 수정
	public DocxDto save(DocxDto dto , String senior , int i, int dkey) {
		Docx d = dao.save(new Docx(dto.getFormnum(), dto.getWriter(),senior,dto.getStartdt(), dto.getEnddt(),
				dto.getTitle(), dto.getContent(), dto.getNote(), dto.getTaskclasf(), dto.getTaskplan(),
				dto.getTaskprocs(), dto.getTaskprocsres(), dto.getDeptandmeetloc(), dto.getDayoffclasf(),
				dto.getParticipant(), dto.getFormtype(), dto.getAprovdoc(),i,dto.getStatus(),dkey));

		return new DocxDto(d.getFormnum(), d.getWriter(),d.getSenior() , d.getStartdt(), d.getEnddt(), d.getTitle(),
				d.getContent(), d.getNote(), d.getTaskclasf(), d.getTaskplan(), d.getTaskprocs(), d.getTaskprocsres(),
				d.getDeptandmeetloc(), d.getDayoffclasf(), d.getParticipant(), d.getFormtype(), d.getAprovdoc(),d.getDocxorder(),d.getStatus(),d.getDocxkey());
	}

	// 문서 번호로 검색
	public DocxDto getDocx(int formnum) {
		Docx d = dao.findById(formnum).orElse(null);
		if (d == null) {
			return null;
		}
		return new DocxDto(d.getFormnum(), d.getWriter(), null,d.getStartdt(), d.getEnddt(), d.getTitle(),
				d.getContent(), d.getNote(), d.getTaskclasf(), d.getTaskplan(), d.getTaskprocs(), d.getTaskprocsres(),
				d.getDeptandmeetloc(), d.getDayoffclasf(), d.getParticipant(), d.getFormtype(), d.getAprovdoc(),d.getDocxorder(),d.getStatus(),d.getDocxkey());
	}

	// 문서 전체 검색
	public ArrayList<DocxDto> getAll() {
			List<Docx> l = dao.findByDocxorder(0);
			ArrayList<DocxDto> list = new ArrayList<DocxDto>();
			for (Docx d : l) {
				list.add(new DocxDto(d.getFormnum(), d.getWriter(), null ,d.getStartdt(), d.getEnddt(),
						d.getTitle(), d.getContent(), d.getNote(), d.getTaskclasf(), d.getTaskplan(), d.getTaskprocs(),
						d.getTaskprocsres(), d.getDeptandmeetloc(), d.getDayoffclasf(), d.getParticipant(), d.getFormtype(),
						d.getAprovdoc(),d.getDocxorder(),d.getStatus(),d.getDocxkey()));
			}
			return list;
	}
	
	//findByDistinctByDocxkey
//	public ArrayList<DocxDto> getDistinctList(int docxkey){
//		List<Docx> l = dao.findDistinctByDocxkey(docxkey);
//		ArrayList<DocxDto> list = new ArrayList<DocxDto>();
//		for (Docx d : l) {
//			list.add(new DocxDto(d.getFormnum(), d.getWriter(), null ,d.getStartdt(), d.getEnddt(),
//					d.getTitle(), d.getContent(), d.getNote(), d.getTaskclasf(), d.getTaskplan(), d.getTaskprocs(),
//					d.getTaskprocsres(), d.getDeptandmeetloc(), d.getDayoffclasf(), d.getParticipant(), d.getFormtype(),
//					d.getAprovdoc(),d.getDocxorder(),d.getStatus(),d.getDocxkey()));
//		}
//		return list;
//	}
	
	
	
	//멤버리스트 뽑기
	public ArrayList<MembersDto> getMemAll() {
		List<Members> l = mdao.findAll();
		ArrayList<MembersDto> mlist = new ArrayList<MembersDto>();
		for (Members m : l) {
			mlist.add(new MembersDto(m.getUserid(),m.getMemberid(),m.getBirthdt(),m.getEmail(),m.getCpnum(),m.getAddress(),m.getMemberimgnm(),m.getHiredt(),m.getLeavedt(),m.getDeptid(),m.getJoblv(),null,null));
		}
		return mlist;
	}

	// 문서 작성자 검색
	public ArrayList<DocxDto> getByWriter(String searchType, String searchValue) {
		List<Docx> l = dao.findByWriter(new Users(searchValue,null,null,null,0));
		if (l == null) {
			return null;
		}
		ArrayList<DocxDto> list = new ArrayList<DocxDto>();
		for (Docx d : l) {
			list.add(new DocxDto(d.getFormnum(), d.getWriter(),null,d.getStartdt(), d.getEnddt(),
					d.getTitle(), d.getContent(), d.getNote(), d.getTaskclasf(), d.getTaskplan(), d.getTaskprocs(),
					d.getTaskprocsres(), d.getDeptandmeetloc(), d.getDayoffclasf(), d.getParticipant(), d.getFormtype(),
					d.getAprovdoc(),d.getDocxorder(),d.getStatus(),d.getDocxkey()));
		}
		return list;
	}

	// 문서 제목으로 검색 Like문
	public ArrayList<DocxDto> getByTitle(String searchType, String searchValue) {
		List<Docx> l = dao.findByTitleLike("%" + searchValue + "%");
		if (l == null) {
			return null;
		}
		ArrayList<DocxDto> list = new ArrayList<DocxDto>();
		for (Docx d : l) {
			list.add(new DocxDto(d.getFormnum(), d.getWriter(),null ,d.getStartdt(), d.getEnddt(),
					d.getTitle(), d.getContent(), d.getNote(), d.getTaskclasf(), d.getTaskplan(), d.getTaskprocs(),
					d.getTaskprocsres(), d.getDeptandmeetloc(), d.getDayoffclasf(), d.getParticipant(), d.getFormtype(),
					d.getAprovdoc(),d.getDocxorder(),d.getStatus(),d.getDocxkey()));
		}
		return list;
	}

	
	
	public int findByFormtypeDesc (String formtype) {
		List<Docx> l = dao.findByFormtypeOrderByFormnumDesc(formtype);
		ArrayList<DocxDto> list = new ArrayList<DocxDto>();
		for(Docx d : l) {
			list.add(new DocxDto(d.getFormnum(), d.getWriter(),null ,d.getStartdt(), d.getEnddt(),
					d.getTitle(), d.getContent(), d.getNote(), d.getTaskclasf(), d.getTaskplan(), d.getTaskprocs(),
					d.getTaskprocsres(), d.getDeptandmeetloc(), d.getDayoffclasf(), d.getParticipant(), d.getFormtype(),
					d.getAprovdoc(),d.getDocxorder(),d.getStatus(),d.getDocxkey()));
		}
		if (!list.isEmpty()) {
	        DocxDto firstDocxDto = list.get(0);
	        return firstDocxDto.getDocxkey();
	    } else {
	        // 리스트가 비어있을 경우에 대한 처리
	        return -1; // 예를 들어 -1을 반환하거나 다른 오류 코드를 반환합니다.
	    }
	}
	
	//findByDocxKeyAndFormType(int docxkey , String formtype) 서류종류와 문서유일키를 합쳐서 찾아오기
	public String findByDocxKeyAndFormType(int docxkey, String formtype) {
		List<Docx> l = dao.findByDocxkeyAndFormtype(docxkey, formtype);
		ArrayList<DocxDto> list = new ArrayList<DocxDto>();
		for(Docx d : l) {
			list.add(new DocxDto(d.getFormnum(), d.getWriter(),d.getSenior() ,d.getStartdt(), d.getEnddt(),
					d.getTitle(), d.getContent(), d.getNote(), d.getTaskclasf(), d.getTaskplan(), d.getTaskprocs(),
					d.getTaskprocsres(), d.getDeptandmeetloc(), d.getDayoffclasf(), d.getParticipant(), d.getFormtype(),
					d.getAprovdoc(),d.getDocxorder(),d.getStatus(),d.getDocxkey()));
		}
		
		if(!list.isEmpty()) {
			DocxDto dto = list.get(0);
			System.out.println(dto.getDocxkey()+"__"+dto.getFormtype()+"__"+dto.getSenior());
			return dto.getFormtype()+"-"+dto.getDocxkey();
		}else {
			return "리스트가 비었습니다";
		}
		
	}
	
	// senior 출력 문서 ,11일 미완료 
//	public List<DocxDto> findByDocxKeyTypeSenior(int docxkey, String formtype) {
//		List<Docx> l = dao.findByDocxkeyAndFormtype(docxkey, formtype);
//		ArrayList<DocxDto> list = new ArrayList<DocxDto>();
//		for(Docx d : l) {
//			list.add(new DocxDto(d.getFormnum(), d.getWriter(),d.getSenior() ,d.getStartdt(), d.getEnddt(),
//					d.getTitle(), d.getContent(), d.getNote(), d.getTaskclasf(), d.getTaskplan(), d.getTaskprocs(),
//					d.getTaskprocsres(), d.getDeptandmeetloc(), d.getDayoffclasf(), d.getParticipant(), d.getFormtype(),
//					d.getAprovdoc(),d.getDocxorder(),d.getStatus(),d.getDocxkey()));
//			
//		}
//		
//	}
	

	
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
