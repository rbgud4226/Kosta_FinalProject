package com.example.demo.oracledb.chat.Message;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.oracledb.chat.Room.ChatRoom;
import com.example.demo.oracledb.chat.Room.ChatRoomDao;
import com.example.demo.oracledb.chat.Room.ChatRoomService;
import com.example.demo.oracledb.users.UsersService;

@Service
public class MessageService {
	@Autowired
	private MessageDao messagedao;

	@Autowired
	private ChatRoomDao chatroomdao;

	@Autowired
	@Lazy
	private ChatRoomService chatRoomService;
	
	@Autowired
	private UsersService usersService;

	@Value("${spring.servlet.multipart.location}")
	private String path;

	public Message save(MessageDto message, String roomId) {
		ChatRoom chatroom = chatroomdao.findByChatroomid(roomId);
		  if (chatroom == null) {
		        throw new NullPointerException("없는방 " + roomId);
		    }
		Message ms = new Message(message.getId(), chatroom, message.getContent(), message.getSendDate(), message.getSender(), message.getType(), message.getFileName(), message.getFileId(), message.getFileRoot(), message.getPartid());
		String mess = ms.getContent().replaceAll("(?:\r\n|\r|\n)", "<br>");
		ms.setContent(mess);
		return messagedao.save(ms);
	}
	
	
	public ArrayList<MessageDto> getMessageByRoomId(String roomId){
		List<Message> l = messagedao.findByRoom_ChatroomidOrderByIdAsc(roomId);
		ArrayList<MessageDto> list = new ArrayList<>();
		for(Message m : l) {
			list.add(new MessageDto(m.getId(),m.getRoom(),m.getContent(), m.getSendDate(), m.getSender(), m.getType(), m.getFileName(), m.getFileId(),m.getFileRoot(),m.getPartid()));
		}
		return list;
	}
	
	public String getRecentMessageByRoomId(String roomId) {
		List<Message> l = messagedao.findByRoom_ChatroomidOrderByIdAsc(roomId);
		String recentMsg = "";
		for(int i=0; i<l.size(); i++) {
			recentMsg = l.get(l.size()-1).getContent();
		}
		if(recentMsg == null) {
			recentMsg = "";
		}
		recentMsg = recentMsg.replaceAll("<br>", " ");
		if(recentMsg.length() >= 13) {
			recentMsg = recentMsg.substring(0, 12) + "..."; 
		}
		return recentMsg;
	}
	
	public void outTypeMessage(MessageDto chatMessage, String roomId) {
		String osg = chatRoomService.getOutChatRoom(roomId, chatMessage.getSender());
		String partN = usersService.getById2(chatMessage.getSender()).getUsernm();
		chatMessage.setContent(osg);
		chatMessage.setPartid(partN);
	}
	
	public void fileTypeMessage(MessageDto chatMessage, String roomId) {
		String wpath = "http://localhost:8081/files/" + chatMessage.getFileName();
        chatMessage.setFileRoot(wpath);
        chatMessage.setFileId(UUID.randomUUID().toString());
        chatMessage.setContent("FILE");
	}
	
	public Map<String, String> FileuploadMethod(MultipartFile file){
		try {
			String originalFilename = file.getOriginalFilename();
			String fileRoot = path + "/" + originalFilename;
			File newFile = new File(fileRoot);
			file.transferTo(newFile);
			Map<String, String> response = new HashMap<>();
			String wpath = "http://localhost:8081/files/" + originalFilename;
			response.put("fileName", originalFilename);
			response.put("fileRoot", wpath);
			return response;
		} catch (Exception e) {
			throw new RuntimeException("파일 업로드 실패: " + e.getMessage());
		}
	}
}
