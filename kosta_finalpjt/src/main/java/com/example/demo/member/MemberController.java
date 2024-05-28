package com.example.demo.member;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.auth.TokenProvider;

@RestController
@CrossOrigin(origins = "*")
public class MemberController {
	@Autowired
	private MemberService service;

	@Autowired
	private TokenProvider tokenProvider;

	@Autowired
	private AuthenticationManagerBuilder ambuilder;

	@PostMapping("/join")
	public Map join(MemberDto dto) {
		boolean flag = true;
		try {
			service.save(dto);
		} catch (Exception e) {
			flag = false;
			System.out.println(e);
		}
		Map map = new HashMap();
		map.put("flag", flag);
		return map;
	}

	@PostMapping("/login")
	public Map login(String id, String pwd) {
		UsernamePasswordAuthenticationToken upauthtoken = new UsernamePasswordAuthenticationToken(id, pwd);
		Authentication auth = ambuilder.getObject().authenticate(upauthtoken);
		boolean flag = auth.isAuthenticated();
		System.out.println("인증결과:" + flag);
		Map map = new HashMap();
		if (flag) {
			String token = tokenProvider.getToken(service.getMember(id));
			map.put("token", token);
			map.put("id", id);
			String type = tokenProvider.getRoles(token);
			map.put("type", type);
		}
		map.put("flag", flag);
		return map;
	}

	@GetMapping("/auth/member")
	public Map myinfo() {
		Map map = new HashMap();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		MemberDto mdto = service.getMember(id);
		map.put("mdto", mdto);
		return map;
	}
}
