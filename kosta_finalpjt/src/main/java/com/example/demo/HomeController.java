package com.example.demo;

import java.util.HashMap;
import java.util.Map;

import com.example.demo.charts.ChartsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.users.UsersDto;
import com.example.demo.users.UsersService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	@Autowired
	private UsersService service;
	@Autowired
	private ChartsService chartsService;

	@RequestMapping("/")
	public String home() {
		return "index";
	}

	@GetMapping("/join")
	public String joinform() {
		return "user/join";
	}

	@PostMapping("/join")
	public String join(UsersDto dto) {
		service.save(dto);
		return "redirect:/";
	}

	@GetMapping("/loginform")
	public String loginform() {
		return "user/login";
	}

	@GetMapping("/auth/login")
	public void authlogin() {

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

	@ResponseBody
	@GetMapping("/idcheck")
	public Map idcheck(String id) {
		Map map = new HashMap();
		UsersDto udto = service.getById(id);
		boolean flag = false;
		if (udto == null) {
			flag = true;
		}
		map.put("flag", flag);
		return map;
	}

}
