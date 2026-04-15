package com.example.ollamaapp01.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chatmessage61")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

	public ChatMessage(String userMessage, String botReply, LocalDateTime time) {
		super();
		this.userMessage = userMessage;
		this.botReply = botReply;
		this.time = time;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String userMessage;

	@Column(length = 2000)
	private String botReply;

	private LocalDateTime time;

}
