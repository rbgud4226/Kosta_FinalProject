package com.example.demo.chat.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.chat.Room.ChatRoom;
import com.example.demo.chat.Room.ChatRoomDao;

@Service
public class MessageService {
	@Autowired
	private MessageDao messagedao;

	@Autowired
	private ChatRoomDao chatroomdao;


	public Message save(MessageDto message, String roomId) {
		ChatRoom chatroom = chatroomdao.findByChatroomid(roomId);
		  if (chatroom == null) {
		        throw new NullPointerException("없는방 " + roomId);
		    }
		Message ms = new Message(message.getId(), chatroom, message.getContent(), message.getSendDate(), message.getSender(), message.getType(), message.getNewuserId(), message.getFileName(), message.getFileId(), message.getFileRoot());
		String mess = ms.getContent().replaceAll("(?:\r\n|\r|\n)", "<br>");
		ms.setContent(mess);
		return messagedao.save(ms);
	}
}
