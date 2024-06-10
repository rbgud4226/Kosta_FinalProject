package com.example.demo.chat.RoomUser;

import com.example.demo.chat.Room.ChatRoom;
import com.example.demo.users.Users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoomUserDto {
	private Long id;
	private ChatRoom chatRoom;
	private Users user;
}
