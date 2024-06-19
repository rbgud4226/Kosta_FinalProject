package com.example.demo.chat.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.chat.RoomUser.RoomUserService;
import com.example.demo.users.Users;
import com.example.demo.users.UsersDao;
import com.example.demo.users.UsersService;

import jakarta.transaction.Transactional;

@Service
public class ChatRoomService {

	@Autowired
	private ChatRoomDao chatRoomDao;

	@Autowired
	private UsersDao usersDao;

	@Autowired
	private UsersService usersService;

	@Autowired
	private RoomUserService roomUserService;

	@Autowired
	private ChatRoomNameDao chatRoomNameDao;

	public ChatRoomDto createChatRoom(List<String> userIds) {
		String name = createChatRoomName(userIds);
		ChatRoom chatRoom = chatRoomDao.findByName(name);
		if (chatRoom != null) {
			if (!chatRoom.isStatus()) {
				chatRoom.setStatus(true);
				chatRoomDao.save(chatRoom);
			}
			if (!chatRoom.getRoomType().equals("PERSONAL") && !chatRoom.getRoomType().equals("PRIVATE")) {
				chatRoom = createNewChatRoom(userIds, name);
			}
			return new ChatRoomDto(chatRoom.getChatroomid(), chatRoom.getName(), chatRoom.getChatRoomNames(),
					chatRoom.getRoomType(), chatRoom.getChats(), chatRoom.getRoomUsers(), chatRoom.isStatus(), null, null);
		} else {
			chatRoom = createNewChatRoom(userIds, name);
		}
		return new ChatRoomDto(chatRoom.getChatroomid(), chatRoom.getName(), chatRoom.getChatRoomNames(),
				chatRoom.getRoomType(), chatRoom.getChats(), chatRoom.getRoomUsers(), chatRoom.isStatus(), null ,null);
	}

	private ChatRoom createNewChatRoom(List<String> userIds, String name) {
		List<String> participantsN = new ArrayList<>();
		for(String s : userIds) {
			participantsN.add(usersService.getById2(s).getUsernm());
		}
		String partN = createChatRoomName(participantsN);
		
		ChatRoom chatRoom = new ChatRoom();
		chatRoom.setChatroomid(UUID.randomUUID().toString());
		chatRoom.setName(name);
		chatRoom.setParticipants(partN);

		String[] l = chatRoom.getName().split("_");
		if (l.length > 2) {
			chatRoom.setRoomType("GROUP");
		} else if (l.length == 2 && l[0].equals(l[1])) {
			chatRoom.setRoomType("PRIVATE");
		} else {
			chatRoom.setRoomType("PERSONAL");
		}
		chatRoom.setStatus(true);
		chatRoomDao.save(chatRoom);

		for (String roomName : l) {
			ChatRoomName chatRoomName = new ChatRoomName();
			chatRoomName.setRoom(chatRoom);
			chatRoomName.setHost(roomName);
			chatRoomName.setRoomName(name);
			chatRoomName.setEditableName(partN);
			chatRoomNameDao.save(chatRoomName);
		}

		for (String userid : userIds) {
			Users user = usersService.getById2(userid);
			roomUserService.save(user, chatRoom);
		}
		return chatRoom;
	}
	

	public String getOutChatRoom(String chatroomid, String userid) {
	    String getOutMessage = "";
	    ChatRoom chatRoom = chatRoomDao.findByChatroomid(chatroomid);
	    if (chatRoom.getRoomType().equals("GROUP") || chatRoom.getRoomType().equals("PERSONAL")) {
	        String[] userIds = chatRoom.getName().split("_");
	        String[] partis = chatRoom.getParticipants().split("_");
	        String part = usersService.getById2(userid).getUsernm();
	        List<String> userIdList = new ArrayList<>(Arrays.asList(userIds));
	        List<String> partisList = new ArrayList<>(Arrays.asList(partis));

	        partisList.remove(part);
	        userIdList.remove(userid);

	        if (partisList.isEmpty()) {
	            chatRoom.setStatus(false);
	        } else {
	            String addUserIds = createChatRoomName(userIdList);
	            chatRoom.setName(addUserIds);
	            chatRoom.setParticipants(createChatRoomName(partisList));
	            getOutMessage = part + "님이 나갔습니다";
	        }
	        chatRoomDao.save(chatRoom);
	    }
	    return getOutMessage;
	}

	public ArrayList<ChatRoomDto> getChatRoomsListByName(String name, String loginId) {
		List<ChatRoom> l = chatRoomDao.findAll();
		String partN = usersService.getById2(loginId).getUsernm();
		ArrayList<ChatRoomDto> list = new ArrayList<>();
		for (ChatRoom cr : l) {
			if (cr.getParticipants().contains(partN) && cr.getParticipants().contains(name) && cr.isStatus()) {
				list.add(new ChatRoomDto(cr.getChatroomid(), cr.getName(), cr.getChatRoomNames(), cr.getRoomType(),
						cr.getChats(), cr.getRoomUsers(), cr.isStatus(), null, cr.getParticipants()));
			}
		}
		return list;
	}

	public ArrayList<ChatRoomDto> getAllChatRooms(String loginId) {
		List<ChatRoom> l = chatRoomDao.findAll();
		ArrayList<ChatRoomDto> list = new ArrayList<>();
		for (ChatRoom cr : l) {
			if (cr.isStatus() && cr.getName().contains(loginId)) {
				list.add(new ChatRoomDto(cr.getChatroomid(), cr.getName(), cr.getChatRoomNames(), cr.getRoomType(),
						cr.getChats(), cr.getRoomUsers(), cr.isStatus(),null, cr.getParticipants()));
			}
		}
		return list;
	}

	public ChatRoomDto getChatRoomsByChatRoomId(String chatroomid) {
		ChatRoom c = chatRoomDao.findByChatroomid(chatroomid);
		ChatRoomDto cr = new ChatRoomDto(c.getChatroomid(), c.getName(), c.getChatRoomNames(), c.getRoomType(),
				c.getChats(), c.getRoomUsers(), c.isStatus(), null, c.getParticipants());
		return cr;
	}

	@Transactional
	public void editChatRoomName(String chatroomid, String newRoomName, String userId1) {
		ChatRoom chatRoom = chatRoomDao.findByChatroomid(chatroomid);
		if (chatRoom == null) {
			throw new NullPointerException("채팅방이 존재하지 않습니다.");
		}
		List<ChatRoomName> roomNames = chatRoom.getChatRoomNames();

		for (ChatRoomName crn : roomNames) {
			if (crn.getHost().equals(userId1)) {
				if (newRoomName == null || newRoomName.trim().isEmpty()) {
					crn.setEditableName("채팅방 이름 없음");
				} else {
					crn.setEditableName(newRoomName);
				}
			}
		}
		chatRoomDao.save(chatRoom);
	}

	public String inviteUserToChatRoom(String chatroomid, String newuserId) {
		String inviteMessage = "";
		if (usersDao.findByUsernm(newuserId) != null) {
			ChatRoom chatRoom = chatRoomDao.findByChatroomid(chatroomid);
			if (chatRoom == null) {
				throw new NullPointerException("채팅방이 존재하지 않습니다.");
			}
			if (chatRoom.getRoomType().equals("PRIVATE")) {
				inviteMessage = "PRIVATE 방은 사용자를 초대할수 없습니다";
				return inviteMessage;
			}
			String[] userIds = chatRoom.getName().split("_");
			ArrayList<String> userIdList = new ArrayList<>(Arrays.asList(userIds));
			if (userIdList.contains(newuserId)) {
				inviteMessage = newuserId + "는 이미 방에 있습니다";
				return inviteMessage;
			} else if (!userIdList.contains(newuserId)) {
				userIdList.add(newuserId);
				String addUserIds = createChatRoomName(userIdList);
				chatRoom.setRoomType("GROUP");
				chatRoom.setName(addUserIds);
				chatRoomDao.save(chatRoom);
				inviteMessage = newuserId + "님이 초대완료 되었습니다";
				return inviteMessage;
			} else {
				inviteMessage = "사용자 초대 실패";
				return inviteMessage;
			}
		} else {
			inviteMessage = newuserId + "는 사용자목록에 없는 사용자 입니다";
			return inviteMessage;
		}
	}

	public String createChatRoomName(List<String> userIds) {
		Collections.sort(userIds);
		return String.join("_", userIds);
	}

}
