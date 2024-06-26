package com.example.demo;

import com.example.demo.mariadb.mail.mail_service;
import com.example.demo.oracledb.charts.ChartsService;
import com.example.demo.oracledb.depts.DeptsService;
import com.example.demo.oracledb.members.MembersDto;
import com.example.demo.oracledb.members.MembersService;
import com.example.demo.oracledb.users.UsersDto;
import com.example.demo.oracledb.users.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
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

	@RequestMapping("/")
	public String home(HttpSession session) {
		String loginId = (String) session.getAttribute("loginId");
		String type = (String) session.getAttribute("type");
		String indexPath = "";
		if (loginId == null) {
			indexPath = "user/userlogin";
		} else {
			if (type == "admin") {
				indexPath = "/index_admin";
			} else if (type == "emp") {
				indexPath = "/index_emp";
			} else {
				indexPath = "/index";
			}
		}
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
		String loginid = (String) session.getAttribute("loginId");
		session.setAttribute("usernm", uservice.getById(loginid).getUsernm());
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
				if (mdto.getDeptid().getMgrid() != null
						&& mdto.getDeptid().getMgrid().getMemberid() == mdto.getMemberid()) {
					session.setAttribute("mgr_deptid", mdto.getDeptid().getDeptid());
				}
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
		session.setAttribute("usernm", uservice.getById(loginid).getUsernm());
		UsersDto udto = uservice.getById((String) session.getAttribute("loginId"));
		MembersDto mdto = mservice.getByuserId(udto.getId());
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
				if (mdto.getDeptid().getMgrid() != null
						&& mdto.getDeptid().getMgrid().getMemberid() == mdto.getMemberid()) {
					session.setAttribute("mgr_deptid", mdto.getDeptid().getDeptid());
				}
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
