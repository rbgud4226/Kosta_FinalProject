package com.example.demo.oracledb.docx;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.demo.oracledb.members.Members;
import com.example.demo.oracledb.members.MembersDao;
import com.example.demo.oracledb.members.MembersDto;
import com.example.demo.oracledb.users.Users;

@Service
public class DocxService {
	@Autowired
	private DocxDao dao;
	@Autowired
	private MembersDao mdao;

	// 문서 작성 , 수정
	public DocxDto save(DocxDto dto, String senior,String participant  ,int i, int dkey) {
		Docx d = dao.save(
				new Docx(dto.getFormnum(), dto.getWriter(), senior, dto.getStartdt(), dto.getEnddt(), dto.getTitle(),
						dto.getContent(), dto.getNote(), dto.getTaskclasf(), dto.getTaskplan(), dto.getTaskprocs(),
						dto.getTaskprocsres(), dto.getDeptandmeetloc(), dto.getDayoffclasf(), dto.getParticipant(),
						dto.getFormtype(), dto.getAprovdoc(), i, dto.getStatus(), dkey, dto.getOrderloc()));

		return new DocxDto(d.getFormnum(), d.getWriter(), d.getSenior(), d.getStartdt(), d.getEnddt(), d.getTitle(),
				d.getContent(), d.getNote(), d.getTaskclasf(), d.getTaskplan(), d.getTaskprocs(), d.getTaskprocsres(),
				d.getDeptandmeetloc(), d.getDayoffclasf(), participant, d.getFormtype(), d.getAprovdoc(),
				d.getDocxorder(), d.getStatus(), d.getDocxkey(), d.getOrderloc());
	}

	// 문서 번호로 검색
	public DocxDto getDocx(int formnum) {
		Docx d = dao.findById(formnum).orElse(null);
		if (d == null) {
			return null;
		}
		return new DocxDto(d.getFormnum(), d.getWriter(), null, d.getStartdt(), d.getEnddt(), d.getTitle(),
				d.getContent(), d.getNote(), d.getTaskclasf(), d.getTaskplan(), d.getTaskprocs(), d.getTaskprocsres(),
				d.getDeptandmeetloc(), d.getDayoffclasf(), d.getParticipant(), d.getFormtype(), d.getAprovdoc(),
				d.getDocxorder(), d.getStatus(), d.getDocxkey(), d.getOrderloc());
	}

	// 결재과정 처리 메서드
	public void approveDocx(int docxkey, String formtype) {
		int test = docxkey;
		System.out.println("docx키 확인 : : " + docxkey);
		System.out.println("formtype 확인 : :-" + formtype);
		System.out.println("docxkey 타입 확인 : :-" + test);
		List<Docx> docxlist = dao.findByDocxkeyAndFormtype(docxkey, formtype);
		System.out.println("문서 가져오는지 확인 : : : " + docxlist);
		int cnt = docxlist.get(0).getOrderloc() + 1;
		for (int i = 0; i < docxlist.size(); i++) {
			Docx d = docxlist.get(i);
			d.setOrderloc(cnt);
			dao.save(d);
			if (i == docxlist.size() - 1) {
				if (d.getOrderloc() > d.getDocxorder()) {
					for (Docx doc : docxlist) {
						doc.setStatus(2);
						dao.save(doc);
					}
				}
			}
		}
	}

	// 문서 전체 검색
	public Page<DocxDto> getAll(int page, int size) {
		PageRequest pageable = PageRequest.of(page,size);
		Page<Docx> docxPage = dao.findByDocxorder(0,pageable);
		// Docx 객체를 DocxDto로 변환
		return docxPage.map(docx -> new DocxDto(
				docx.getFormnum(),
				docx.getWriter(),
	            null,
	            docx.getStartdt(),
	            docx.getEnddt(),
	            docx.getTitle(),
	            docx.getContent(),
	            docx.getNote(),
	            docx.getTaskclasf(),
	            docx.getTaskplan(),
	            docx.getTaskprocs(),
	            docx.getTaskprocsres(),
	            docx.getDeptandmeetloc(),
	            docx.getDayoffclasf(),
	            docx.getParticipant(),
	            docx.getFormtype(),
	            docx.getAprovdoc(),
	            docx.getDocxorder(),
	            docx.getStatus(),
	            docx.getDocxkey(),
	            docx.getOrderloc()
				));
	}

	// 멤버리스트 뽑기
	public ArrayList<MembersDto> getMemAll() {
		List<Members> l = mdao.findAll();
		ArrayList<MembersDto> mlist = new ArrayList<MembersDto>();
		for (Members m : l) {
			mlist.add(new MembersDto(m.getUserid(), m.getMemberid(), m.getBirthdt(), m.getEmail(), m.getCpnum(),
					m.getAddress(), m.getMemberimgnm(), m.getHiredt(), m.getLeavedt(), m.getDeptid(), m.getJoblvid(),
					m.getMgrid(), null, null));
		}
		return mlist;
	}

	// 내가 작성한 문서만 출력
	public ArrayList<DocxDto> getMyList(String writer) {
		List<Docx> l = dao.findDistinctByWriter(new Users(writer, null, null, null, 0, null));
		Set<String> seenTitles = new LinkedHashSet<>(); // 중복 제거를 위해 LinkedHashSet 사용
		l.sort(Comparator.comparingInt(Docx::getFormnum).reversed());
		ArrayList<DocxDto> mylist = new ArrayList<DocxDto>();
		for (Docx d : l) {
//			seenTitles.add(d.getTitle())는 title이 seenTitles에 이미 존재하는지 여부를 확인한다.
//			title이 처음 등장하는 경우에만 true를 반환하고, DocxDto 리스트에 추가.
			if (seenTitles.add(d.getTitle())) {
				mylist.add(new DocxDto(d.getFormnum(), d.getWriter(), null, d.getStartdt(), d.getEnddt(), d.getTitle(),
						d.getContent(), d.getNote(), d.getTaskclasf(), d.getTaskplan(), d.getTaskprocs(),
						d.getTaskprocsres(), d.getDeptandmeetloc(), d.getDayoffclasf(), d.getParticipant(),
						d.getFormtype(), d.getAprovdoc(), d.getDocxorder(), d.getStatus(), d.getDocxkey(),
						d.getOrderloc()));
			}
		}
		return mylist;
	}

	// 문서 작성자 검색
	public ArrayList<DocxDto> getByWriter(String searchType, String searchValue) {
		List<Docx> l = dao.findDistinctByWriter(new Users(searchValue, null, null, null, 0, null));
		Set<String> seenTitles = new LinkedHashSet<>(); // 중복 제거를 위해 LinkedHashSet 사용
		if (l == null) {
			return null;
		}
		ArrayList<DocxDto> list = new ArrayList<DocxDto>();
		l.sort(Comparator.comparingInt(Docx::getFormnum).reversed());
		for (Docx d : l) {
			if (seenTitles.add(d.getTitle())) {
				list.add(new DocxDto(d.getFormnum(), d.getWriter(), null, d.getStartdt(), d.getEnddt(), d.getTitle(),
						d.getContent(), d.getNote(), d.getTaskclasf(), d.getTaskplan(), d.getTaskprocs(),
						d.getTaskprocsres(), d.getDeptandmeetloc(), d.getDayoffclasf(), d.getParticipant(),
						d.getFormtype(), d.getAprovdoc(), d.getDocxorder(), d.getStatus(), d.getDocxkey(),
						d.getOrderloc()));
			}
		}
		return list;
	}

	// 문서 제목으로 검색 Like문
	public ArrayList<DocxDto> getByTitle(String searchType, String searchValue) {
		List<Docx> l = dao.findByTitleLike("%" + searchValue + "%");
		Set<String> seenTitles = new LinkedHashSet<>(); // 중복 제거를 위해 LinkedHashSet 사용
		if (l == null) {
			return null;
		}
		ArrayList<DocxDto> list = new ArrayList<DocxDto>();
		l.sort(Comparator.comparingInt(Docx::getFormnum).reversed());
		for (Docx d : l) {
			if (seenTitles.add(d.getTitle())) {
				list.add(new DocxDto(d.getFormnum(), d.getWriter(), null, d.getStartdt(), d.getEnddt(), d.getTitle(),
						d.getContent(), d.getNote(), d.getTaskclasf(), d.getTaskplan(), d.getTaskprocs(),
						d.getTaskprocsres(), d.getDeptandmeetloc(), d.getDayoffclasf(), d.getParticipant(),
						d.getFormtype(), d.getAprovdoc(), d.getDocxorder(), d.getStatus(), d.getDocxkey(),
						d.getOrderloc()));
			}
		}
		return list;
	}

	public int findByFormtypeDesc(String formtype) {
		List<Docx> l = dao.findByFormtypeOrderByFormnumDesc(formtype);
		ArrayList<DocxDto> list = new ArrayList<DocxDto>();
		for (Docx d : l) {
			list.add(new DocxDto(d.getFormnum(), d.getWriter(), null, d.getStartdt(), d.getEnddt(), d.getTitle(),
					d.getContent(), d.getNote(), d.getTaskclasf(), d.getTaskplan(), d.getTaskprocs(),
					d.getTaskprocsres(), d.getDeptandmeetloc(), d.getDayoffclasf(), d.getParticipant(), d.getFormtype(),
					d.getAprovdoc(), d.getDocxorder(), d.getStatus(), d.getDocxkey(), d.getOrderloc()));
		}
		if (!list.isEmpty()) {
			DocxDto firstDocxDto = list.get(0);
			return firstDocxDto.getDocxkey();
		} else {
			// 리스트가 비어있을 경우에 대한 처리
			return -1; // 예를 들어 -1을 반환하거나 다른 오류 코드를 반환합니다.
		}
	}

	// findByDocxKeyAndFormType(int docxkey , String formtype) 서류종류와 문서유일키를 합쳐서 찾아오기
	public String findByDocxKeyAndFormType(int docxkey, String formtype) {
		List<Docx> l = dao.findByDocxkeyAndFormtype(docxkey, formtype);
		ArrayList<DocxDto> list = new ArrayList<DocxDto>();
		for (Docx d : l) {
			list.add(new DocxDto(d.getFormnum(), d.getWriter(), d.getSenior(), d.getStartdt(), d.getEnddt(),
					d.getTitle(), d.getContent(), d.getNote(), d.getTaskclasf(), d.getTaskplan(), d.getTaskprocs(),
					d.getTaskprocsres(), d.getDeptandmeetloc(), d.getDayoffclasf(), d.getParticipant(), d.getFormtype(),
					d.getAprovdoc(), d.getDocxorder(), d.getStatus(), d.getDocxkey(), d.getOrderloc()));
		}

		if (!list.isEmpty()) {
			DocxDto dto = list.get(0);
			System.out.println(dto.getDocxkey() + "__" + dto.getFormtype());
			return dto.getFormtype() + "-" + dto.getDocxkey();
		} else {
			return "리스트가 비었습니다";
		}

	}

	// senior 출력 문서
	public List<DocxDto> findByDocxKeyTypeSenior(int docxkey, String formtype) {
		List<Docx> l = dao.findByDocxkeyAndFormtype(docxkey, formtype);
		ArrayList<DocxDto> list = new ArrayList<DocxDto>();
		for (Docx d : l) {
			list.add(new DocxDto(d.getFormnum(), d.getWriter(), d.getSenior(), d.getStartdt(), d.getEnddt(),
					d.getTitle(), d.getContent(), d.getNote(), d.getTaskclasf(), d.getTaskplan(), d.getTaskprocs(),
					d.getTaskprocsres(), d.getDeptandmeetloc(), d.getDayoffclasf(), d.getParticipant(), d.getFormtype(),
					d.getAprovdoc(), d.getDocxorder(), d.getStatus(), d.getDocxkey(), d.getOrderloc()));

		}
		return list;
	}

	// 문서 삭제
	public void delDocx(int docxkey) {
		dao.deleteByDocxkey(docxkey);
	}

	public ArrayList<DocxDto> SelectedList(String id) {
		List<Docx> l = dao.findBySenior(id);
		ArrayList<DocxDto> list = new ArrayList<DocxDto>();
		for (Docx d : l) {
			list.add(new DocxDto(d.getFormnum(), d.getWriter(), null, d.getStartdt(), d.getEnddt(), d.getTitle(),
					d.getContent(), d.getNote(), d.getTaskclasf(), d.getTaskplan(), d.getTaskprocs(),
					d.getTaskprocsres(), d.getDeptandmeetloc(), d.getDayoffclasf(), d.getParticipant(), d.getFormtype(),
					d.getAprovdoc(), d.getDocxorder(), d.getStatus(), d.getDocxkey(), d.getOrderloc()));
		}
		return list;
	}

}
