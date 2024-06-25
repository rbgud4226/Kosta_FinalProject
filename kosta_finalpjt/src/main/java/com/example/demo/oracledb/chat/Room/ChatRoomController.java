package com.example.demo.oracledb.chat.Room;

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

	@GetMapping("/chat/chatroom/{roomId}")
	public String getChatRoomByRoomId(@PathVariable String roomId, HttpSession session, ModelMap map) {
		ModelMap chatRoomInfo = chatRoomService.chatRoomByRoomIdMethod(roomId, session);
		map.addAttribute("partId", chatRoomInfo.get("partId"));
		map.addAttribute("roomId", chatRoomInfo.get("roomId"));
		map.addAttribute("userId1", chatRoomInfo.get("userId1"));
		return "chat/bootchat";
	}

	@GetMapping("/chat/chatroom")
	public String createChatRoom(@RequestParam(name = "userid") List<String> userid, HttpSession session, ModelMap map) {
		ModelMap chatRoomInfo = chatRoomService.createChatRoomByUserList(userid, session);
		map.addAttribute("partId", chatRoomInfo.get("partId"));
		map.addAttribute("roomId", chatRoomInfo.get("roomId"));
		map.addAttribute("userId1", chatRoomInfo.get("userId1"));
		return "chat/bootchat";
	}

	@GetMapping("/chat/chatrooms/{userid}")
	public String getChatRoomsByUserId(@PathVariable String userid, HttpSession session, ModelMap map) {
		ModelMap chatRoomInfo = chatRoomService.chatroomsByUserId(userid, session);
		map.addAttribute("partId", chatRoomInfo.get("partId"));
		map.addAttribute("chatRooms", chatRoomInfo.get("chatRooms"));
		map.addAttribute("userId1", chatRoomInfo.get("userId1"));
		return "chat/bootchat";
	}
	

	@GetMapping("/chat/chatrooms/loadrooms/{userid}")
	@ResponseBody
	public ArrayList<ChatRoomDto> getChatRoomsForRecentAndLoad(@PathVariable String userid) {
		return chatRoomService.recentAndLoad(userid);
	}

	@GetMapping("/chat/chatrooms/loadrooms/search/{userid}")
	@ResponseBody
	public ArrayList<ChatRoomDto> getChatRoomsSearch(@PathVariable String userid, HttpSession session) {
		return chatRoomService.chatRoomsSearch(userid, session);		
	}

	@GetMapping("/chat/chatrooms/loadrooms/searchroom/{chatroomid}/{userId1}")
	@ResponseBody
	public ChatRoomDto getChatRoomsConnect(@PathVariable String chatroomid, @PathVariable String userId1) {
		return chatRoomService.chatRoomsConnect(chatroomid, userId1);
	}

	@GetMapping("/chat/chatrooms/out/{roomId}/{userid}")
	@ResponseBody
	public String getOutRooms(@PathVariable String roomId, @PathVariable String userid) {
		 return "http://localhost:8081/chat/chatrooms/" + userid;
	}
	
	@GetMapping("/chat/chatrooms/invite/{userid}/{chatroomid}")
	public String inviteChatRoom(@RequestParam List<String> userid, String chatroomid, HttpSession session) {
		chatRoomService.inviteChatRoomMethod(userid, chatroomid, session);
	    return "redirect:/chat/chatroom/" + chatroomid;
	}
	
	@PostMapping("/chat/chatrooms/edit")
	public String editRoomName(@RequestParam String chatroomid, String newRoomName, String userId1) {
		chatRoomService.editChatRoomName(chatroomid, newRoomName, userId1);
		return "chat/bootchat";
	}
}
