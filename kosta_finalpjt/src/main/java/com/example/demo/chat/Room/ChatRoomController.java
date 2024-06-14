package com.example.demo.chat.Room;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth/chat")
public class ChatRoomController {
	@Autowired
	private ChatRoomService chatRoomService;

	@GetMapping("/starttest")
	public String chatStart() {
		return "/chat/chatindex";
	}

	@GetMapping("/room/{roomId}")
	public String getChatRoomByRoomId(@PathVariable String roomId, HttpSession session, ModelMap map) {
		String userId1 = (String) session.getAttribute("loginId");
		map.addAttribute("roomId", roomId);
		map.addAttribute("userId1", userId1);
		return "/chat/chatstart";
	}

	@GetMapping("/room")
	public String getChatRoom(@RequestParam List<String> userIds, HttpSession session, ModelMap map) {
		String userId1 = (String) session.getAttribute("loginId");
		if (!userIds.contains(userId1)) {
			userIds.add(userId1);
		}
		ChatRoomDto chatRoomDto = chatRoomService.createChatRoom(userIds);
		map.addAttribute("roomId", chatRoomDto.getChatroomid());
		map.addAttribute("userId1", userId1);
		return "/chat/chatstart";
	}

	@GetMapping("/rooms/{userId}")
	public String getChatRoomsByRoomId(@PathVariable String userId, HttpSession session, ModelMap map) {
		String userId1 = (String) session.getAttribute("loginId");
		ArrayList<ChatRoomDto> cr = chatRoomService.getChatRoomsListByName(userId, userId1);
		map.addAttribute("chatRooms", cr);
		map.addAttribute("userId1", userId1);
		return "/chat/chatroomlist";
	}

	@GetMapping("/rooms")
	public String getChatRooms(@RequestParam String user, HttpSession session, ModelMap map) {
		String userId1 = (String) session.getAttribute("loginId");
		ArrayList<ChatRoomDto> cr = chatRoomService.getChatRoomsListByName(user, userId1);
		map.addAttribute("chatRooms", cr);
		map.addAttribute("user", user);
		return "/chat/chatroomlist";
	}

	@GetMapping("/rooms/out")
	public String getOutRooms(@RequestParam String chatroomid, String userId) {
		chatRoomService.getOutChatRoom(chatroomid, userId);
		return "/member/list";
	}

	@GetMapping("/rooms/invite/{chatroomid}")
	@ResponseBody
	public String InviteChatRoom(@PathVariable String chatroomid, String newuserId, ModelMap map) {
		String mes = chatRoomService.inviteUserToChatRoom(chatroomid, newuserId);
		map.addAttribute("inviteMessage", mes);
		return "/auth/chat/room/" + chatroomid;
	}
	
	@PostMapping("/rooms/edit")
	public String editRoomName(@RequestParam String chatroomid, String newRoomName, ModelMap map) {
		chatRoomService.editChatRoomName(chatroomid, newRoomName);
		return "/chat/chatroomlist";
	}
}
