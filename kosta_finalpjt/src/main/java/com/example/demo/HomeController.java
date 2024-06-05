package com.example.demo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.charts.ChartsService;
import com.example.demo.depts.DeptsDto;
import com.example.demo.depts.DeptsService;
import com.example.demo.members.MembersDto;
import com.example.demo.members.MembersService;
import com.example.demo.users.Users;
import com.example.demo.users.UsersDto;
import com.example.demo.users.UsersService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	@Autowired
	private UsersService uservice;

	@Autowired
	private MembersService mservice;

	@Autowired
	private DeptsService dservice;

	@Autowired
	private ChartsService chartsService;
	
	@Value("${spring.servlet.multipart.location}")
	private String path; // >> 기본 경로

	private String dirName = "memberimg/";

	@RequestMapping("/")
	public String home() {
		return "index";
	}

	@GetMapping("/user/userjoin")
	public String userjoinform() {
		return "user/userjoin";
	}

	@PostMapping("/user/userjoin")
	public String userjoin(UsersDto dto) {
		dto.setAprov(0);
		uservice.save(dto);
		return "redirect:/";
	}

	@PostMapping("/user/useredit")
	public String useredit(HttpSession session, String pwd) {
		String loginId = (String) session.getAttribute("loginId");
		UsersDto udto = uservice.getById(loginId);
		udto.setPwd(pwd);
		uservice.save(udto);
		return "redirect:/user/userinfo?id=" + loginId;
	}

	@GetMapping("/loginform")
	public String loginform() {
		return "user/userlogin";
	}

	@GetMapping("/auth/login")
	public String authlogin() {
		return "/loginform";
	}

	@GetMapping("/auth/logout")
	public String authlogout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

	@GetMapping("/user/userinfo")
	public String myinfo(String id, ModelMap map) {
		map.addAttribute("user", uservice.getById(id));
		return "user/userinfo";
	}

	@RequestMapping("/index_admin")
	public void adminHome() {

	}

	@GetMapping("/admin/user/userlist")
	public String userlist(ModelMap map) {
		ArrayList<UsersDto> ulist = uservice.getAll();

		map.addAttribute("ulist", ulist);
		System.out.println(ulist);
		return "user/userlist";
	}

	@PostMapping("/admin/user/getdeptby")
	public ModelAndView getuserby(String val, int type) {
		UsersDto udto = null;
		if (type == 1) {
			udto = uservice.getById(val);
		} else if (type == 2) {
			udto = uservice.getByUsernm(val);
		}
		ModelAndView mav = new ModelAndView("user/userlist");
		mav.addObject("type", type);
		mav.addObject("val", val);
		mav.addObject("ulist", udto);
		return mav;
	}
	
	@GetMapping("/member/memberaprov")
	public String memberaprov(String userid) {
		UsersDto udto = uservice.getById(userid);
		udto.setAprov(1);
		uservice.save(udto);
		return "redirect:/member/memberlist";
	}
	
	@GetMapping("/member/memberlist")
	public String memberlist(ModelMap map) {
		ArrayList<MembersDto> mlist = mservice.getAll();
		map.addAttribute("mlist", mlist);
		return "member/memberlist";
	}

	@PostMapping("/member/getdeptby")
	public ModelAndView getmemberby(String val, int type) {
		ArrayList<MembersDto> mlist = new ArrayList<MembersDto>();
		if (type == 1) {
			mlist = mservice.getByDeptNm(val);
		} else if (type == 2) {
			UsersDto udto = uservice.getByUsernm(val);
			if (udto == null) {
				mlist.add(null);
			} else {
				mlist.add(mservice.getByuserNm(new Users(udto.getId(), "", "", "", 0)));
			}
			System.out.println(mlist);
		} else if (type == 3) {
			mlist = mservice.getByJobLv(Integer.parseInt(val));
		}
		ModelAndView mav = new ModelAndView("member/memberlist");
		mav.addObject("type", type);
		mav.addObject("val", val);
		mav.addObject("mlist", mlist);
		return mav;
	}

	@GetMapping("/member/memberinfo")
	public String memberinfo(String id, ModelMap map) {
		
		map.addAttribute("member", mservice.getByuserId(id));
		map.addAttribute("userid", id);
		System.out.println(mservice.getByuserId(id));
		return "member/memberinfo";
	}
	
	@GetMapping("/read-img")
	public ResponseEntity<byte[]> read_img(String fname) {
		ResponseEntity<byte[]> result = null;
		File f = new File(path + dirName + fname);
		HttpHeaders header = new HttpHeaders();
		try {
			header.add("Content-Type", Files.probeContentType(f.toPath()));
			result = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(f), header, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	@PostMapping("/member/memberadd")
	public String memberadd(HttpSession session, MembersDto dto) {
		System.out.println(dto.getJoblv());
    //		mservice.save(dto);
		mservice.save(dto.getMemberid());
		
		String oname = dto.getMemberimgf().getOriginalFilename(); // >>파일 원본 이름
		System.out.println("oname:" + oname);
		String f1 = oname.substring(oname.lastIndexOf(".")); // >>뒤에서부터 특정 문자를 처음 만나는 위치를 찾아서 그 문자부터 문자열을 잘라냄
		System.out.println("f1:" + f1);
		String f2 = oname.substring(oname.lastIndexOf(".") + 1, oname.length());
		System.out.println("f2:" + f2);
		String f3 = oname.substring(0, oname.lastIndexOf("."));
		System.out.println("f3:" + f3);
		// 업로드 파일명을 글번호.확장자
//		String fname = idto.getNum() + f1;  // >>업로드된 파일 이름을 글 번호로 바꿈, 파일이 중복되어 기존 파일이 지워지는 것을 방지하기 위해
		String fname = f3 + " (" + dto.getUserid().getUsernm() + ")." + f2;
		System.out.println(fname);
		// >>업로드 위치
		File newFile = new File(path + dirName + fname);
		try {
			dto.getMemberimgf().transferTo(newFile); // >>멀티파트로 올라온 파일을 복사
			dto.setMemberimgnm(newFile.getName()); // >>생성한 파일의 이름을 객체 ibto의 fname에 저장
//			mservice.save(dto); // update 동작. fname값 수정
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.setAttribute("loginId", (String) session.getAttribute("loginId"));
		session.setAttribute("type", (String) session.getAttribute("type"));
		return "redirect:/member/memberinfo?id="+session.getAttribute("loginId");
	}

	@RequestMapping("/index_emp")
	public void empHome(ModelMap map) {
		map.addAttribute("list", chartsService.getAll());
	}

	@ResponseBody
	@GetMapping("/user/idcheck")
	public Map idcheck(String id) {
		Map map = new HashMap();
		UsersDto udto = uservice.getById(id);
		boolean flag = false;
		if (udto == null) {
			flag = true;
		}
		map.put("flag", flag);
		return map;
	}

	@GetMapping("/corp/deptlist")
	public String deptlist(ModelMap map) {
		ArrayList<DeptsDto> dlist = dservice.getAll();
		map.addAttribute("dlist", dlist);
		System.out.println(dlist);
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
