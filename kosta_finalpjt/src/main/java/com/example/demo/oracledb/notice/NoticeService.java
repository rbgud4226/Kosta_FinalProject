package com.example.demo.oracledb.notice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoticeService {
	@Autowired
	private NoticeDao ndao;
	
	//공지 작성, 수정
	public Notice save(NoticeDto dto) {
		Notice notice = ndao.save(new Notice(dto.getId(), dto.getWriter(), dto.getStartdt(), dto.getEnddt(), dto.getTitle(), dto.getContent(), dto.getFormtype()));
		return notice;
	}
	
	//전체 공지 리스트 출력
	public ArrayList<NoticeDto> getAllNotice(){
		List<Notice> l =  ndao.findAll();
		ArrayList<NoticeDto> list = new ArrayList<>();
		for(Notice n : l) {
			list.add(new NoticeDto(n.getId(), n.getWriter(), n.getStartdt(), n.getEnddt(), n.getTitle(), n.getContent(), n.getFormtype()));
		}
		return list;
	}
}
