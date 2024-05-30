package com.example.demo.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.users.Users;
import com.example.demo.users.UsersDao;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UsersDao dao;

	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Users u = dao.findById(id).orElseThrow(() -> new UsernameNotFoundException("not found id:" + id));
		System.out.println("security service: " + u);
		return new MyUserDetails(u);
	}

}
