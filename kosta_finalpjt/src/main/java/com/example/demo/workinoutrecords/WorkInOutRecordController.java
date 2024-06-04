package com.example.demo.workinoutrecords;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
			map.addAttribute("num",list.get(0).getMemberid());
		}
		map.addAttribute("flag", flag);
		return "record/my";
	}
//	G
	//출근하기
	@ResponseBody
	@PostMapping("/in")
	public Map workin(String Members) {
		Users s = new Users(Members,"","","",0);
		String type = "출근";
		
	    //지각 체크
        LocalTime currentTime = LocalTime.now();
        LocalTime targetTime = LocalTime.of(9, 0);

        // 현재 시간이 9시 이전인지 확인
        if (currentTime.isAfter(targetTime)) {
        	type="지각";
        }			
		service.save(new WorkInOutRecordDto(0, s, null, null, type));
		Map map = new HashMap<>();
		map.put("state", type);
		return map;
	}
	//퇴근하기
	@ResponseBody
	@PostMapping("/out")
	public void workout(String Members, int memberid) {
		WorkInOutRecordDto w = service.select(memberid);
		//퇴근시간 기록
        w.setWorkoutdt(new Date());
        
		String type = "정상근무";
	    //근무 시간
        LocalTime currentTime = LocalTime.now();
        LocalTime targetTime = LocalTime.of(18, 30);
        LocalTime targetTime2 = LocalTime.of(17, 50);
        if (currentTime.isAfter(targetTime)) {
        	type="추가근무";
        }else if(currentTime.isBefore(targetTime2)) {
        	type="조기퇴근";
        }

        if(w.getState().equals("지각")) {
        	type="지각";
        }
        w.setState(type);
        service.save(w);
        
	}
	//내 근태기록 확인하기
	@GetMapping("/getmonth")
	public Map myrecord(String Members,int cnt) {
        // 현재 날짜 가져오기
        LocalDate currentDate = LocalDate.now();
        // 원하는 달 가져오기
        int currentMonth = currentDate.getMonthValue()+cnt;
        int currentYear = currentDate.getYear();
        
        
	}
	
	
	
	//관리자
}
