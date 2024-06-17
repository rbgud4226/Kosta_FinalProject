package com.example.demo.chat.Room;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatRoomNameService {
	@Autowired
	private ChatRoomNameDao chatRoomNameDao;

	public ChatRoomName save(ChatRoomName chatRoomName) {
		return chatRoomNameDao.save(chatRoomName);
	}
	
	public ArrayList<ChatRoomNameDto> getChatRoomNames(String chatroomid){
		List<ChatRoomName> l = chatRoomNameDao.findByRoom_chatroomid(chatroomid);
		ArrayList<ChatRoomNameDto> list = new ArrayList<>();
		for(ChatRoomName crn : l) {
			list.add(new ChatRoomNameDto(crn.getId(), crn.getRoom(),crn.getHost(), crn.getRoomName()));
		}
		return list;
	}
}
