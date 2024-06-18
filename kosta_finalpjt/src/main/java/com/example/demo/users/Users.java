package com.example.demo.users;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.chat.RoomUser.RoomUser;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

public class Users {
	@Id
	private String id;
	private String usernm;
	private String pwd;
	private String type;
	private int aprov;

	@OneToMany(mappedBy = "roomuser", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JsonIgnore
	private List<RoomUser> roomUsers = new ArrayList<RoomUser>();
}
