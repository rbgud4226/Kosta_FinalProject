package com.example.demo.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.member.Member;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TokenUserDetails implements UserDetails {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private final Member m;

	public TokenUserDetails(Member m) {
		this.m = m;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		List<GrantedAuthority> list = new ArrayList<>();
		list.add(new SimpleGrantedAuthority(m.getType()));
		return list;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return m.getPwd();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return m.getId();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
