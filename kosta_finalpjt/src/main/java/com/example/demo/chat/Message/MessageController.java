package com.example.demo.chat.Message;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.chat.Room.ChatRoomService;

@Controller
public class MessageController {
	@Autowired
	private MessageService messageService;

	@Autowired
	private ChatRoomService chatRoomService;
	
	@Autowired
    private SimpMessagingTemplate messagingTemplate;

	@Value("${spring.servlet.multipart.location}")
	private String path;
	
	@MessageMapping("/chat/message/{roomId}")
	public void sendMessage(@Payload MessageDto chatMessage, @DestinationVariable String roomId) {
		if(chatMessage.getType().equals("OUT")) {
			String osg = chatRoomService.getOutChatRoom(roomId, chatMessage.getSender());
			chatMessage.setContent(osg);
			messageService.save(chatMessage, roomId);
			ArrayList<MessageDto> list = messageService.getMessageByRoomId(roomId);
	        messagingTemplate.convertAndSend("/room/" + roomId, list);
	        messagingTemplate.convertAndSend("/recent/update", roomId);
		}else if (chatMessage.getType().equals("INVITE")) {
	        String msg = chatRoomService.inviteUserToChatRoom(roomId, chatMessage.getNewuserId());
	        chatMessage.setContent(msg);
	        messageService.save(chatMessage, roomId);
	        ArrayList<MessageDto> list = messageService.getMessageByRoomId(roomId);
	        list.get(0).setNewuserId(msg);
	        messagingTemplate.convertAndSend("/room/" + roomId, list);
	        messagingTemplate.convertAndSend("/recent/update", roomId);
	    } else if (chatMessage.getType().equals("FILE")) {
	        String wpath = "http://localhost:8081/files/" + chatMessage.getFileName();
	        chatMessage.setFileRoot(wpath);
	        chatMessage.setFileId(UUID.randomUUID().toString());
	        chatMessage.setContent("FILE");
	        messageService.save(chatMessage, roomId);
	        ArrayList<MessageDto> list = messageService.getMessageByRoomId(roomId);
	        messagingTemplate.convertAndSend("/room/" + roomId, list);
	        messagingTemplate.convertAndSend("/recent/update", roomId);
	    } else {
	        messageService.save(chatMessage, roomId);
	        ArrayList<MessageDto> list = messageService.getMessageByRoomId(roomId);
	        messagingTemplate.convertAndSend("/room/" + roomId, list);
	        messagingTemplate.convertAndSend("/recent/update", roomId);
	    }
	}
	
	@GetMapping("/chat/message/room/{roomId}")
	@ResponseBody
	public ArrayList<MessageDto> getMessages(@PathVariable String roomId) {
		ArrayList<MessageDto> list = messageService.getMessageByRoomId(roomId);
		return list;
	}

	@PostMapping("/chat/message/upload")
	@ResponseBody
	public Map<String, String> FileUpload(@RequestParam("file") MultipartFile file) {
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
