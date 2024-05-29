package com.example.demo.auth;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.demo.member.MemberDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class TokenProvider {
	private final long expiredTime = 1000 * 60 * 60 * 1L;
	Key key = Keys.hmacShaKeyFor("asdfdfghjhgukjhljkdyhywsrgegsrthtyjguil;yujrthyjfhgkeargaegthaegahukgjhlsfghdghg"
			.getBytes(StandardCharsets.UTF_8));

	@Autowired
	private final TokenUserDetailsService tokenUserDetailsService;

	public String getToken(MemberDto dto) {
		Date now = new Date();
		return Jwts.builder().setSubject(dto.getId()).setHeader(createHeader()).setClaims(createClaims(dto))
				.setExpiration(new Date(now.getTime() + expiredTime)).signWith(key, SignatureAlgorithm.HS256).compact();
	}

	public Map<String, Object> createHeader() {
		Map<String, Object> map = new HashMap<>();
		map.put("typ", "JWT");
		map.put("alg", "HS256");
		map.put("regDate", System.currentTimeMillis());
		return map;
	}

	public Map<String, Object> createClaims(MemberDto dto) {
		Map<String, Object> map = new HashMap();
		map.put("username", dto.getId());
		map.put("roles", dto.getType());
		return map;
	}

	private Claims getClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}

	public String getUserName(String token) {
		return (String) getClaims(token).get("username");
	}

	public String getRoles(String token) {
		return (String) getClaims(token).get("roles");
	}

	public String resolveToken(HttpServletRequest request) {
		return request.getHeader("auth_token");
	}

	public boolean validateToken(String token) {
		try {
			Claims claims = getClaims(token);
			return !claims.getExpiration().before(new Date());
		} catch (Exception e) {
			return false;
		}
	}

	public Authentication getAuthenticatiln(String token) {
		UserDetails user = tokenUserDetailsService.loadUserByUsername(getUserName(token));
		return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
	}
}
