package com.example.demo.users;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Users {
	@Id
	private String id;
	private String usernm;
	private String pwd;
	private String type;
	private int aprov;
}
