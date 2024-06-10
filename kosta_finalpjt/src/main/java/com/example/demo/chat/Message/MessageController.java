package com.example.demo.chat.Message;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
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
	private MessageDao messagedao;

	@Value("${spring.servlet.multipart.location}")
	private String path;

	@MessageMapping("/{roomId}")
	@SendTo("/room/{roomId}")
	public List<Message> sendMessage(@Payload MessageDto chatMessage, @DestinationVariable String roomId) {
		if (chatMessage.getType().equals("INVITE")) {
			String msg = chatRoomService.inviteUserToChatRoom(roomId, chatMessage.getNewuserId());
			chatMessage.setContent(msg);
			messageService.save(chatMessage, roomId);
			List<Message> list = messagedao.findByRoom_Chatroomid(roomId);
			list.get(0).setNewuserId(msg);
			return list;
		} else if (chatMessage.getType().equals("FILE")) {
			String wpath = "http://localhost:8081/files/" + chatMessage.getFileName();
			chatMessage.setFileRoot(wpath);
			chatMessage.setFileId(UUID.randomUUID().toString());
			chatMessage.setContent("FILE");
		}
		messageService.save(chatMessage, roomId);
		List<Message> list = messagedao.findByRoom_Chatroomid(roomId);
		return list;
	}

	@GetMapping("auth/message/room/{roomId}")
	@ResponseBody
	public List<Message> getMessages(@PathVariable String roomId) {
		return messagedao.findByRoom_Chatroomid(roomId);
	}

	@PostMapping("/auth/upload")
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
