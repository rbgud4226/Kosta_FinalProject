package com.example.demo.docx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/auth/docx")
public class DocxController {

	@Autowired
	private DocxService service;

	@GetMapping("/add")
	public String addForm() {
		return "docx/list";
	}
	
	//작성폼 불러올때 멤버 리스트 데이터 가져오기
	@GetMapping("/addreport")
	public String reportForm(ModelMap map) {
		map.addAttribute("mlist",service.getMemAll());
		
		return "docx/addreport";
	}

		
	@PostMapping("/addreport")
	public String addreport(DocxDto d) {
		System.out.println(d.getSenior());
		int dkey = service.findByFormtypeDesc(d.getFormtype());
		dkey += 1;
		System.out.println("key값 있는지 확인 ::" +dkey);
		String[] s = d.getSenior().split(",");
		for(int j=0; j<s.length; j++) {
			service.save(d,s[j],j,dkey);
		}
		return "redirect:/auth/docx/list";
	}
	
	
	@GetMapping("/addmeet")
	public String meetForm() {
		return "docx/addmeet";
	}

//	@PostMapping("/addmeet")
//	public String addmeet(DocxDto dto) {
//		service.save(dto);
//		return "redirect:/index";
//	}
	
	@GetMapping("/addvacation")
	public String vacationForm() {
		return "docx/addvacation";
	}

//	@PostMapping("/addvacation")
//	public String addvacation(DocxDto dto,ModelMap map) {
//		service.save(dto);
//	
//		return "redirect:/index";
//	}

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

//	@PostMapping("/editreport")
//	public String editReport(DocxDto dto) {
//		service.save(dto);
//		return "redirect:/docx/list";
//	}
//
//	@PostMapping("/editmeet")
//	public String editMeeting(DocxDto dto) {
//		service.save(dto);
//		return "redirect:/docxlist";
//	}
//
//	@PostMapping("/editvacation")
//	public String editVacation(DocxDto dto) {
//		service.save(dto);
//		return "redirect:/docxlist";
//	}

	@GetMapping("/list")
	public String list(ModelMap map) {
		map.addAttribute("list", service.getAll());
		return "docx/list";
	}
	 @PostMapping("/list")
	    public String list(ModelMap map,
	                              @RequestParam(value = "searchType", required = false) String searchType,
	                              @RequestParam(value = "searchValue", required = false) String searchValue) {
		 if(searchType.equals("title")) {
			 map.addAttribute("list", service.getByTitle(searchType, searchValue));
		 }else if(searchType.equals("writer")) {
			 map.addAttribute("list", service.getByWriter(searchType, searchValue));
		 }
	        
	        return "docx/list";
	    }

//	@RequestMapping("/writerlist")
//	public String writerList(String writer, ModelMap map) {
//		map.addAttribute("wlist", service.getByWriter(writer));
//		return "docx/list";
//	}

//	@RequestMapping("/titlelist")
//	public String titleList(String title,ModelMap map) {
//		map.addAttribute("wlist", service.getByTitle(title));
//		return "docx/list";
//	}
	
//	@RequestMapping("/seniorlist")
//	public String seniorList(String senior,ModelMap map) {
//		map.addAttribute("wlist", service.getBySenior(senior));
//		return "docx/list";
//	}
	
	@GetMapping("/getdocx")
	public String get(int formnum, int docxkey,String formtype,ModelMap map) {
		map.addAttribute("d", service.getDocx(formnum));
		map.addAttribute("docx", service.findByDocxKeyAndFormType(docxkey, formtype));
		//service에 findByDocxKeyAndFormType 관련 기능 추가해서 여기서 map에 담기
		return "docx/detail";
	}

	@RequestMapping("/deldocx")
	public String deldocx(int formnum) {
		service.delDocx(formnum);
		return "redirect:/auth/docx/list";
	}
}
