package com.example.demo.chat.Room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomDao extends JpaRepository<ChatRoom, String> {
	ChatRoom findByName(String name);
	ChatRoom findByChatroomid(String chatroomid);
}
