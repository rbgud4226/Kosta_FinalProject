package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import reactor.netty.http.server.HttpServerRequest;

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

	@PostMapping("/member/memberadd")
	public String memberadd(HttpSession session, MembersDto dto) {
//		System.out.println(dto.getJoblv());
		mservice.save(dto);
		//mservice.update(dto.getMemberid());
		session.setAttribute("loginId", (String) session.getAttribute("loginId"));
		session.setAttribute("type", (String) session.getAttribute("type"));
		return "redirect:/member/memberinfo?id="+session.getAttribute("loginId");
	}

	@RequestMapping("/index_emp")
	public void empHome(ModelMap map) {
		map.addAttribute("list", chartsService.getAll());
	}

	@ResponseBody
	@GetMapping("/idcheck")
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
