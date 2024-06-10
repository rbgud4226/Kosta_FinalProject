package com.example.demo.users;

import java.util.List;

import com.example.demo.chat.RoomUser.RoomUser;
import com.example.demo.members.MembersDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class UsersDto {
	private String id;
	private String usernm;
	private String pwd;
	private String type;
	private int aprov;
	private MembersDto memberdto;
	private List<RoomUser> roomUsers;
}
