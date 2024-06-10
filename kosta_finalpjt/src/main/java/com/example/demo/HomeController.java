package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.charts.ChartsService;
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
	public String home() {
		return "index";
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
	public void adminHome() {

	}

	@RequestMapping("/index_emp")
	public void empHome(ModelMap map) {
		map.addAttribute("list", chartsService.getAll());
	}

}
