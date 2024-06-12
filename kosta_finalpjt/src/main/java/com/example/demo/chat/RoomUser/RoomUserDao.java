package com.example.demo.chat.RoomUser;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.chat.Room.ChatRoom;
import com.example.demo.users.Users;

@Repository
public interface RoomUserDao extends JpaRepository<RoomUser, Long> {
	List<RoomUser> findByChatRoom_Chatroomid(String chatroomid);

	List<RoomUser> findByRoomuser_Id(String id);

	Optional<RoomUser> findByChatRoomAndRoomuser(ChatRoom chatRoom, Users roomuser);
}
