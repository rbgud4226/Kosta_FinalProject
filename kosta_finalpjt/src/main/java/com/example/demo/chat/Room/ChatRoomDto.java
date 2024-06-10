package com.example.demo.chat.Room;

import java.util.List;

import com.example.demo.chat.Message.Message;
import com.example.demo.chat.RoomUser.RoomUser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDto {
	private String chatroomid;
	private String name;
	private String roomName; 
	private String roomType; 
	private List<Message> chats;
	private List<RoomUser> roomUsers;
	private boolean status; 
}
