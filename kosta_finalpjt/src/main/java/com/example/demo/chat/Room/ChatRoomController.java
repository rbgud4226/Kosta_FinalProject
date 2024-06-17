package com.example.demo.chat.Room;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;

@Controller
public class ChatRoomController {
	@Autowired
	private ChatRoomService chatRoomService;

	@Autowired
	private ChatRoomNameService chatRoomNameService;

	@GetMapping("/chat/chatroom/{roomId}")
	public String getChatRoomByRoomId(@PathVariable String roomId, HttpSession session, ModelMap map) {
		String userId1 = (String) session.getAttribute("loginId");
		map.addAttribute("roomId", roomId);
		map.addAttribute("userId1", userId1);
		return "/chat/bootchat";
	}

	@GetMapping("/chat/chatroom")
	public String getChatRoom(@RequestParam List<String> userIds, HttpSession session, ModelMap map) {
		String userId1 = (String) session.getAttribute("loginId");
		if (!userIds.contains(userId1)) {
			userIds.add(userId1);
		}
		ChatRoomDto chatRoomDto = chatRoomService.createChatRoom(userIds);
		map.addAttribute("roomId", chatRoomDto.getChatroomid());
		map.addAttribute("userId1", userId1);
		return "/chat/bootchat";
	}

	@GetMapping("/chat/chatrooms/{userId}")
	public String getChatRoomsByRoomId(@PathVariable String userId, HttpSession session, ModelMap map) {
		String userId1 = (String) session.getAttribute("loginId");
		ArrayList<ChatRoomDto> cr = chatRoomService.getChatRoomsListByName(userId, userId1);
		map.addAttribute("chatRooms", cr);
		map.addAttribute("userId1", userId1);
		return "/chat/bootchat";
	}

	@GetMapping("/chat/chatrooms/loadrooms/{userId}")
	@ResponseBody
	public ArrayList<ChatRoomDto> getPersonalChatRooms(@PathVariable String userId) {
		ArrayList<ChatRoomDto> cr = chatRoomService.getAllChatRooms(userId);
		for (ChatRoomDto chatRoom : cr) {
			ArrayList<ChatRoomNameDto> roomNamesDto = chatRoomNameService.getChatRoomNames(chatRoom.getChatroomid());
			List<ChatRoomName> roomNames = new ArrayList<>();
			for (ChatRoomNameDto dto : roomNamesDto) {
				if (dto.getHost().equals(userId)) {
					roomNames.add(new ChatRoomName(dto.getId(), dto.getRoom(),dto.getHost(), dto.getRoomName()));   
	            }
			}
			chatRoom.setChatRoomNames(roomNames);
		}
		return cr;
	}

	@GetMapping("/chat/chatrooms/loadrooms/search/{userId}")
	@ResponseBody
	public ArrayList<ChatRoomDto> getChatRoomsSearch(@PathVariable String userId, HttpSession session) {
		String loginId = (String) session.getAttribute("loginId");
		ArrayList<ChatRoomDto> cr = chatRoomService.getChatRoomsListByName(userId, loginId);
		return cr;
	}

	@GetMapping("/chat/chatrooms/loadrooms/searchroom/{chatroomid}/{userId1}")
	@ResponseBody
	public ChatRoomDto getChatRooms(@PathVariable String chatroomid, @PathVariable String userId1) {
		 ChatRoomDto cr = chatRoomService.getChatRoomsByChatRoomId(chatroomid);
		    ArrayList<ChatRoomNameDto> roomNamesDto = chatRoomNameService.getChatRoomNames(cr.getChatroomid());
		    List<ChatRoomName> roomNames = new ArrayList<>();
		    for (ChatRoomNameDto dto : roomNamesDto) {
		        if (dto.getHost().equals(userId1)) {
		            roomNames.add(new ChatRoomName(dto.getId(), dto.getRoom(), dto.getHost(), dto.getRoomName()));
		        }
		    }
		    cr.setChatRoomNames(roomNames);
		    return cr;
	}

	@GetMapping("/chat/chatrooms/out")
	public String getOutRooms(@RequestParam String chatroomid, String userId) {
		chatRoomService.getOutChatRoom(chatroomid, userId);
		return "redirect:/chat/chatrooms/" + userId;
	}

	@GetMapping("/chat/chatrooms/invite/{chatroomid}")
	@ResponseBody
	public String InviteChatRoom(@PathVariable String chatroomid, String newuserId, ModelMap map) {
		String mes = chatRoomService.inviteUserToChatRoom(chatroomid, newuserId);
		map.addAttribute("inviteMessage", mes);
		return "/chat/chatroom/" + chatroomid;
	}

	@PostMapping("/chat/chatrooms/edit")
	public String editRoomName(@RequestParam String chatroomid, String newRoomName, String userId1) {
		chatRoomService.editChatRoomName(chatroomid, newRoomName, userId1);
		return "/chat/bootchat";
	}
}
