package com.example.demo.oracledb.notice;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth/notice")
public class NoticeController {
	@Autowired
	private NoticeService noticeService;
	
	//공지 저장
	@PostMapping("/addNotice")
	public String addNotice(NoticeDto dto) {
		noticeService.save(dto);
		return "redirect:/auth/docx/list";
	}
	
	//공지 전체 출력
	@GetMapping("/list")
	public ArrayList<NoticeDto> allNoticeList() {
		ArrayList<NoticeDto> list = noticeService.getAllNotice();
		return list;
	}
}
