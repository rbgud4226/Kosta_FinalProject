package com.example.demo.docx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.members.Members;

@Controller
@RequestMapping("/auth/docx")
public class DocxController {

	@Autowired
	private DocxService service;
//	@Autowired
//	private TokenProvider provider;
//	@Autowired
//	private AuthenticationManagerBuilder abuilder;

	@GetMapping("/add")
	public String addForm() {
		return "docx/list";
	}
	
	@GetMapping("/addreport")
	public String reportForm() {
		return "docx/addreport";
	}

	@PostMapping("/addreport")
	public String addreport(DocxDto dto) {
		service.save(dto);
		return "redirect:/index";
	}
	
	@GetMapping("/addmeet")
	public String meetForm() {
		return "docx/addmeet";
	}

	@PostMapping("/addmeet")
	public String addmeet(DocxDto dto) {
		service.save(dto);
		return "redirect:/index";
	}
	
	@GetMapping("/addvacation")
	public String vacationForm() {
		return "docx/addvacation";
	}

	@PostMapping("/addvacation")
	public String addvacation(DocxDto dto) {
		service.save(dto);
		return "redirect:/index";
	}

	@GetMapping("/edit")
	public ModelAndView editForm(int num) {
		DocxDto d = service.getDocx(num);
		ModelAndView mav = new ModelAndView();
		if (d.getFormtype().equals("report")) {
			mav.setViewName("docx/editreport");
		} else if (d.getFormtype().equals("meet")) {
			mav.setViewName("docx/editmeet");
		} else if (d.getFormtype().equals("vacation")) {
			mav.setViewName("docx/editvacation");
		}
		mav.addObject("d", d);
		return mav;
	}

	@PostMapping("/editreport")
	public String editReport(DocxDto dto) {
		service.save(dto);
		return "redirect:/docx/list";
	}

	@PostMapping("/editmeet")
	public String editMeeting(DocxDto dto) {
		service.save(dto);
		return "redirect:/docxlist";
	}

	@PostMapping("/editvacation")
	public String editVacation(DocxDto dto) {
		service.save(dto);
		return "redirect:/docxlist";
	}

	@RequestMapping("/list")
	public String list(ModelMap map) {
		map.addAttribute("list", service.getAll());
		return "docx/list";
	}

	@RequestMapping("/writerlist")
	public String writerList(Members writer, ModelMap map) {
		map.addAttribute("wlist", service.getByWriter(writer));
		return "docx/list";
	}

	@RequestMapping("/titlelist")
	public String titleList(String title, ModelMap map) {
		map.addAttribute("wlist", service.getByTitle(title));
		return "docx/list";
	}

	@PostMapping("/deldocx")
	public void deldocx(int num) {
		service.delDocx(num);
	}
}
