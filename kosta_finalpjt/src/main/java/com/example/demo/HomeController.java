package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.charts.ChartsService;
import com.example.demo.members.MembersDto;
import com.example.demo.members.MembersService;
import com.example.demo.users.UsersService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	@Autowired
	private UsersService uservice;

	@Autowired
	private MembersService mservice;

	@Autowired
	private ChartsService chartsService;

	@RequestMapping("/")
	public String home(HttpSession session) {
		String type = (String) session.getAttribute("type");
		String indexPath = "";
		if (type == "admin") {
			indexPath = "/index_admin";
		} else if (type == "emp") {
			indexPath = "/index_emp";
		} else {
			indexPath = "/index";
		}
//		System.out.println(type);
		return indexPath;
	}

	@GetMapping("/loginform")
	public String loginform() {
		return "user/userlogin";
	}

	@PostMapping("/loginerror")
	public String loginerror(HttpServletRequest request, String loginFailMsg) {
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

	@RequestMapping("/index_admin")
	public void adminHome(HttpSession session, ModelMap map) {
		map.addAttribute("usernm", uservice.getById((String) session.getAttribute("loginId")));
		MembersDto mdto = mservice.getByuserId((String) session.getAttribute("loginId"));
		if (mdto != null) {
			if (mdto.getMemberimgnm() == "") {
				session.setAttribute("memberimgnm", "");
			} else {
				session.setAttribute("memberimgnm", mdto.getMemberimgnm());
			}
			if (mdto.getDeptid() == null) {
				session.setAttribute("deptnm", "미등록 상태");
			} else {
				session.setAttribute("deptnm", mdto.getDeptid().getDeptnm());
			}
			if (mdto.getJoblvid() == null) {
				session.setAttribute("joblvnm", "미등록 상태");
			} else {
				session.setAttribute("joblvnm", mdto.getJoblvid().getJoblvnm());
			}
		}
	}

	@RequestMapping("/index_emp")
	public void empHome(HttpSession session, ModelMap map) {
		String loginid = (String) session.getAttribute("loginId");
		map.addAttribute("usernm", uservice.getById(loginid));
		MembersDto mdto = mservice.getByuserId((String) session.getAttribute("loginId"));
		if (mdto != null) {
			if (mdto.getMemberimgnm() == "") {
				session.setAttribute("memberimgnm", "");
			} else {
				session.setAttribute("memberimgnm", mdto.getMemberimgnm());
			}
			if (mdto.getDeptid() == null) {
				session.setAttribute("deptnm", "미등록 상태");
			} else {
				session.setAttribute("deptnm", mdto.getDeptid().getDeptnm());
			}
			if (mdto.getJoblvid() == null) {
				session.setAttribute("joblvnm", "미등록 상태");
			} else {
				session.setAttribute("joblvnm", mdto.getJoblvid().getJoblvnm());
			}
		}
		map.addAttribute("list", chartsService.getbyUsers(loginid));
	}

}
