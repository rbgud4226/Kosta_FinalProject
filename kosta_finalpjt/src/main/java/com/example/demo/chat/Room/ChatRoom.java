package com.example.demo.chat.Room;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.chat.Message.Message;
import com.example.demo.chat.RoomUser.RoomUser;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {
	@Id
	private String chatroomid; // 채팅방 번호
	private String name; // 채팅방 이름, 변경 불가능(유저목록 확인용)
	private String roomName; // 수정 가능한 채팅방 이름
	private String roomType; // 개인, 단체

	@OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JsonIgnore
	private List<Message> chats = new ArrayList<>();

	@OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<RoomUser> roomUsers = new ArrayList<>();

	private boolean status; // 채팅방 상태
}
