package com.example.demo.chat.RoomUser;

import com.example.demo.chat.Room.ChatRoom;
import com.example.demo.users.Users;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoomUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roomuser_seq")
	@SequenceGenerator(name = "roomuser_seq", sequenceName = "roomuser_sequence", allocationSize = 1)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "chatroomid", referencedColumnName = "chatroomid" ,nullable = false)
	private ChatRoom chatRoom;
	
	@ManyToOne
	@JoinColumn(name = "userid", referencedColumnName = "id", nullable = false)
	private Users user;
}
