package com.example.demo.oracledb.docx;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

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

	//보고서 작성양식
	@PostMapping("/addreport")
	public String addreport(DocxDto d) {
		System.out.println("시니어값 테스트22 : " + d.getSenior());
		String senior = d.getSenior();
		System.out.println("시니어값 테스트 : " + senior);
		if(senior != null) {
			int dkey = service.findByFormtypeDesc(d.getFormtype());
			dkey += 1;
			String[] s = d.getSenior().split(",");
			for(int j=0; j<s.length; j++) {
				service.save(d,s[j],null,j,dkey);
			}
		}
		return "redirect:/auth/docx/list";
	}
	
	//휴가서류 작성양식
		@PostMapping("/addvacation")
		public String addvacation(DocxDto d) {
			System.out.println(d.getSenior());
			int dkey = service.findByFormtypeDesc(d.getFormtype());
			dkey += 1;
			System.out.println("key값 있는지 확인 ::" +dkey);
			String[] s = d.getSenior().split(",");
			for(int j=0; j<s.length; j++) {
				service.save(d,s[j],null,j,dkey);
			}
			return "redirect:/auth/docx/list";
		}
		
		//회의록 작성양식
		@PostMapping("/addmeeting")
		public String addmeeting(DocxDto d) {
			String senior = d.getSenior();
			int dkey = service.findByFormtypeDesc(d.getFormtype());
			dkey += 1;
			if(senior != null) {
				String[] s = d.getSenior().split(",");
				for(int j=0; j<s.length; j++) {
					service.save(d,null,d.getParticipant(),j,dkey);
				}
			}
			return "redirect:/auth/docx/list";
		}


	//전체문서 리스트 출력
	@GetMapping("/list")
	public Page<DocxDto> list(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
		return service.getAll(page, size);
	}
	
	//결재걸린 문서 리스트 출력
	@GetMapping("/slist")
	public String slist(ModelMap map, HttpSession session) {
		String loginId = (String)session.getAttribute("loginId");
		map.addAttribute("list",service.SelectedList(loginId));
		return "docx/slist";
	}
	
	
	//내가 작성한 문서만 출력
	@GetMapping("/mylist")
	public String myList(ModelMap map, String writer) {
		map.addAttribute("mylist", service.getMyList(writer));
		return "docx/mylist";
	}
	
	//검색 컨트롤러
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

	@GetMapping("/getdocx")
	public String get(int formnum, int docxkey,String formtype,ModelMap map,HttpSession session) {
		boolean flag = false;
		String loginid = (String) session.getAttribute("loginId");
		List<DocxDto> l = service.findByDocxKeyTypeSenior(docxkey, formtype);
		for(DocxDto d : l) {
			if(d.getOrderloc()==d.getDocxorder() && d.getSenior().equals(loginid)) {
				flag = true;
				break;
			}
		}
		map.addAttribute("d", service.getDocx(formnum));
		map.addAttribute("docx", l);
		map.addAttribute("flag",flag);
		System.out.println( "현재 문서의 정보 출력 : "+service.findByDocxKeyTypeSenior(docxkey, formtype));
		return "docx/detail";
	}
	
	//결재처리 메서드
	
	@PostMapping("/approve")
	public String approveDocx(int docxkey, String formtype) {
		service.approveDocx(docxkey,formtype);
		return "redirect:/auth/docx/list";
	}
	
	@RequestMapping("/deldocx")
	public String deldocx(int docxkey) {
		service.delDocx(docxkey);
		return "redirect:/auth/docx/list";
	}
	

}
