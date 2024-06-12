package com.example.demo.chat.Message;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.example.demo.chat.Room.ChatRoom;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_seq")
	@SequenceGenerator(name = "chat_seq", sequenceName = "chat_sequence", allocationSize = 1)
	private Long id; 

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private ChatRoom room; 

	private String content; 
	private String sendDate;
	private String sender; 
	private String type;
	private String newuserId;
	private String fileName;
	private String fileId;
	private String fileRoot; 
	
	public Message(Long id, ChatRoom room, String sendDate, String sender, String type, String newuserId) {
		super();
		this.id = id;
		this.room = room;
		this.sendDate = sendDate;
		this.sender = sender;
		this.type = type;
		this.newuserId = newuserId;
	}
}
