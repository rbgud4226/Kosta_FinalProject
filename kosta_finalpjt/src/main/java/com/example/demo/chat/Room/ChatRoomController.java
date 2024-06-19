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

import com.example.demo.chat.Message.MessageService;
import com.example.demo.users.UsersService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ChatRoomController {
	@Autowired
	private ChatRoomService chatRoomService;

	@Autowired
	private ChatRoomNameService chatRoomNameService;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private UsersService usersService;

	@GetMapping("/chat/chatroom/{roomId}")
	public String getChatRoomByRoomId(@PathVariable String roomId, HttpSession session, ModelMap map) {
		String userId1 = (String) session.getAttribute("loginId");
		String partId = usersService.getById2(userId1).getUsernm();
		map.addAttribute("partId", partId);
		map.addAttribute("roomId", roomId);
		map.addAttribute("userId1", userId1);
		return "redirect:/chat/bootchat";
	}

	@GetMapping("/chat/chatroom")
	public String getChatRoom(@RequestParam(name = "userid") List<String> userid, HttpSession session, ModelMap map) {
		String userId1 = (String) session.getAttribute("loginId");
		if (!userid.contains(userId1)) {
			userid.add(userId1);
		}
		ChatRoomDto chatRoomDto = chatRoomService.createChatRoom(userid);
		String partId = usersService.getById2(userId1).getUsernm();
		map.addAttribute("partId", partId);
		map.addAttribute("roomId", chatRoomDto.getChatroomid());
		map.addAttribute("userId1", userId1);
		return "/chat/bootchat";
	}

	@GetMapping("/chat/chatrooms/{userid}")
	public String getChatRoomsByRoomId(@PathVariable String userid, HttpSession session, ModelMap map) {
		String userId1 = (String) session.getAttribute("loginId");
		ArrayList<ChatRoomDto> cr = chatRoomService.getChatRoomsListByName(userid, userId1);
		String partId = usersService.getById2(userId1).getUsernm();
		map.addAttribute("partId", partId);
		map.addAttribute("chatRooms", cr);
		map.addAttribute("userId1", userId1);
		return "/chat/bootchat";
	}

	@GetMapping("/chat/chatrooms/loadrooms/{userid}")
	@ResponseBody
	public ArrayList<ChatRoomDto> getPersonalChatRooms(@PathVariable String userid) {
		ArrayList<ChatRoomDto> cr = chatRoomService.getAllChatRooms(userid);
		for (ChatRoomDto chatRoom : cr) {
			String recentMsg = messageService.getRecentMessageByRoomId(chatRoom.getChatroomid());
			ArrayList<ChatRoomNameDto> roomNamesDto = chatRoomNameService.getChatRoomNames(chatRoom.getChatroomid());
			List<ChatRoomName> roomNames = new ArrayList<>();
			for (ChatRoomNameDto dto : roomNamesDto) {
				if (dto.getHost().equals(userid)) {
					roomNames.add(new ChatRoomName(dto.getId(), dto.getRoom(),dto.getHost(), dto.getRoomName(), dto.getEditableName()));   
	            }
			}
			chatRoom.setRecentMsg(recentMsg);
			chatRoom.setChatRoomNames(roomNames);
		}
		return cr;
	}

	@GetMapping("/chat/chatrooms/loadrooms/search/{userid}")
	@ResponseBody
	public ArrayList<ChatRoomDto> getChatRoomsSearch(@PathVariable String userid, HttpSession session) {
		String loginId = (String) session.getAttribute("loginId");
		ArrayList<ChatRoomDto> cr = chatRoomService.getChatRoomsListByName(userid, loginId);
		for (ChatRoomDto chatRoom : cr) {
			String recentMsg = messageService.getRecentMessageByRoomId(chatRoom.getChatroomid());
			ArrayList<ChatRoomNameDto> roomNamesDto = chatRoomNameService.getChatRoomNames(chatRoom.getChatroomid());
			List<ChatRoomName> roomNames = new ArrayList<>();
			for (ChatRoomNameDto dto : roomNamesDto) {
				if (dto.getHost().equals(loginId)) {
					roomNames.add(new ChatRoomName(dto.getId(), dto.getRoom(),dto.getHost(), dto.getRoomName(), dto.getEditableName()));   
	            }
			}
			chatRoom.setRecentMsg(recentMsg);
			chatRoom.setChatRoomNames(roomNames);
		}
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
		            roomNames.add(new ChatRoomName(dto.getId(), dto.getRoom(), dto.getHost(), dto.getRoomName(), dto.getEditableName()));
		        }
		    }
		    cr.setChatRoomNames(roomNames);
		    return cr;
	}

	@GetMapping("/chat/chatrooms/out/{roomId}/{userid}")
	public String getOutRooms(@PathVariable String roomId, @PathVariable String userid, ModelMap map) {
	    String mes = chatRoomService.getOutChatRoom(roomId, userid);
	    map.addAttribute("getOutMessage", mes);
	    return "redirect:/chat/chatrooms/" + userid;
	}

	@GetMapping("/chat/chatrooms/invite/{chatroomid}")
	@ResponseBody
	public String InviteChatRoom(@PathVariable String chatroomid, String newuserId, ModelMap map) {
		String mes = chatRoomService.inviteUserToChatRoom(chatroomid, newuserId);
		map.addAttribute("inviteMessage", mes);
		return "redirect:/chat/chatroom/" + chatroomid;
	}

	@PostMapping("/chat/chatrooms/edit")
	public String editRoomName(@RequestParam String chatroomid, String newRoomName, String userId1) {
		chatRoomService.editChatRoomName(chatroomid, newRoomName, userId1);
		return "redirect:/chat/bootchat";
	}
}
