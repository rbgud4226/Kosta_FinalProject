package com.example.demo.chat.Room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.example.demo.chat.RoomUser.RoomUser;


@Repository
public interface ChatRoomDao extends JpaRepository<ChatRoom, String> {
	ChatRoom findByName(String name);
	ChatRoom findByChatroomid(String chatroomid);
	List<ChatRoom> findByRoomUsers(List<RoomUser> roomUsers);
	List<ChatRoom> findByParticipants(String participants);
}
