package com.example.demo.depts;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.members.MembersDto;
import com.example.demo.members.MembersService;

@Controller
public class DeptsController {

	@Autowired
	private DeptsService dservice;
	
	@Autowired
	private MembersService mservice;

	@GetMapping("/corp/deptlist")
	public String deptlist(ModelMap map) {
		ArrayList<DeptsDto> dlist = dservice.getAll();
		map.addAttribute("dlist", dlist);
//		System.out.println(dlist);
		return "corp/deptlist";
	}

	@GetMapping("/admin/corp/deptadd")
	public String deptaddform() {
		return "corp/deptadd";
	}

	@PostMapping("/admin/corp/deptadd")
	public String deptadd(DeptsDto dto) {
		dservice.save(dto);
		return "redirect:/corp/deptlist";
	}

	@GetMapping("/corp/deptinfo")
	public String deptdinfo(int deptid, ModelMap map) {
		map.addAttribute("d", dservice.getByDeptId(deptid));
		return "corp/deptinfo";
	}

	@PostMapping("/admin/corp/deptedit")
	public String deptedit(DeptsDto dto) {
		dservice.save(dto);
		return "redirect:/corp/deptinfo?deptid=" + dto.getDeptid();
	}

	@PostMapping("/corp/getdeptby")
	public ModelAndView getdeptby(String val, int type) {
		ArrayList<DeptsDto> dlist = null;
		if (type == 1) {
			dlist = dservice.getByDeptNm(val);
		} else if (type == 2) {
			MembersDto mdto = mservice.getByuserId(val);
			dlist = dservice.getByMgrId(mdto.getMemberid());
		}
		ModelAndView mav = new ModelAndView("corp/deptlist");
		mav.addObject("type", type);
		mav.addObject("val", val);
		mav.addObject("dlist", dlist);
		return mav;
	}

}
