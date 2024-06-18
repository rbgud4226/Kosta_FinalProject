package com.example.demo.chat.Message;

import java.util.ArrayList;
import java.util.List;

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
	
	
	public ArrayList<MessageDto> getMessageByRoomId(String roomId){
		List<Message> l = messagedao.findByRoom_ChatroomidOrderByIdAsc(roomId);
		ArrayList<MessageDto> list = new ArrayList<>();
		for(Message m : l) {
			list.add(new MessageDto(m.getId(),m.getRoom(),m.getContent(), m.getSendDate(), m.getSender(), m.getType(), m.getNewuserId(), m.getFileName(), m.getFileId(),m.getFileRoot()));
		}
		return list;
	}
	
	public String getRecentMessageByRoomId(String roomId) {
		List<Message> l = messagedao.findByRoom_ChatroomidOrderByIdAsc(roomId);
		String recentMsg = "";
		for(int i=0; i<l.size(); i++) {
			recentMsg = l.get(l.size()-1).getContent();
		}
		recentMsg = recentMsg.replaceAll("<br>", " ");
		if(recentMsg.length() >= 13) {
			recentMsg = recentMsg.substring(0, 12) + "..."; 
		}
		return recentMsg;
	}
}
