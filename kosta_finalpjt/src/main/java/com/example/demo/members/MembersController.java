package com.example.demo.members;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth/member")
public class MembersController {
	@Autowired
	private MembersService service;

	@GetMapping("/add")
	public String addform() {
		return "member/add";
	}

	@PostMapping("/add")
	public String add(MembersDto dto) {
		service.save(dto);
		return "redirect:/index_emp";
	}
	
	@GetMapping("/list")
	public String del(ModelMap map) {
		map.addAttribute("list", service.getAll());
		return "member/list";
	}
	
//	@GetMapping("/getbydeptid")
//	public String getbydeptid(int deptid, ModelMap map) {
//		map.addAttribute("list", service.getByDeptId(deptid));
//		return "member/list";
//	}
	
//	@GetMapping("/getbyjoblv")
//	public String getbyjoblv(int joblv, ModelMap map) {
//		map.addAttribute("list", service.getByJobLv(joblv));
//		return "member/list";
//	}
	
	@GetMapping("/getbyid")
	public String getbyid(int memberid, ModelMap map) {
		map.addAttribute("m", service.getByMemberId(memberid));
		return "member/detail";
	}
	
//	@GetMapping("/getbynm")
//	public String getbynm(String membernm, ModelMap map) {
//		map.addAttribute("m", service.getByMemberNm(membernm));
//		return "member/detail";
//	}
	
}
