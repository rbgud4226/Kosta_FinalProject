package com.example.demo.oracledb.chat.Room;

import com.example.demo.oracledb.chat.RoomUser.RoomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface ChatRoomDao extends JpaRepository<ChatRoom, String> {
	ChatRoom findByName(String name);
	ChatRoom findByChatroomid(String chatroomid);
	List<ChatRoom> findByRoomUsers(List<RoomUser> roomUsers);
	List<ChatRoom> findByParticipants(String participants);
}
