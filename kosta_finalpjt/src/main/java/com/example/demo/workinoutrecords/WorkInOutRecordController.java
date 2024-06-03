package com.example.demo.workinoutrecords;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.users.Users;


@Controller
@RequestMapping("/auth/record")
public class WorkInOutRecordController {
	@Autowired
	private WorkInOutRecordService service;
	
	//개인
	//출퇴근 기록 페이지로 이동하기
	@GetMapping("/my")
	public String myrecord(String Members,ModelMap map) {
		//출근기록x
		boolean flag = false;
		ArrayList<WorkInOutRecordDto> list = service.selectByDate(Members);
		//출근기록O
		if(!list.isEmpty()) {
			flag = true; 
		}
		map.addAttribute("flag", flag);
		map.addAttribute("num",list.get(0).getMemberid());
		return "record/my";
	}
	//출근하기
	@ResponseBody
	@PostMapping("/in")
	public void workin(String Members) {
		Users s = new Users(Members,"","","",0);
		service.save(new WorkInOutRecordDto(0, s, null, null, "출근"));
	}
	//퇴근하기
	@ResponseBody
	@PostMapping("/out")
	public void workout(String Members, int memberid) {
		System.out.println("퇴근기록 확인용");
		System.out.println(Members);
		System.out.println(service.select(memberid));
	}
	
	//관리자
}
