package com.example.demo.chat.RoomUser;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.chat.Room.ChatRoom;
import com.example.demo.users.Users;

@Service
public class RoomUserService {
	@Autowired
	private RoomUserDao roomuserdao;

	public void save(Users user, ChatRoom chatroom) {
		if (!roomuserdao.findByChatRoomAndUser(chatroom, user).isPresent()) {
			roomuserdao.save(new RoomUser(null, chatroom, user));
		}

	}

	public ArrayList<RoomUserDto> findRoom(String chatroomid) {
		List<RoomUser> l = roomuserdao.findByChatRoom_Chatroomid(chatroomid);
		ArrayList<RoomUserDto> list = new ArrayList<>();
		for (RoomUser ru : l) {
			list.add(new RoomUserDto(ru.getId(), ru.getChatRoom(), ru.getUser()));
		}
		return list;
	}

}
