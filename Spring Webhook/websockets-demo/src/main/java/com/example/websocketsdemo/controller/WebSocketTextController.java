package com.example.websocketsdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.websocketsdemo.dto.TextMessageDTO;

@RestController
public class WebSocketTextController {

	@Autowired
	SimpMessagingTemplate template;
	

	@PostMapping("/send")
	public ResponseEntity<Void> sendMessage(@RequestBody TextMessageDTO textMessageDTO) {
		template.convertAndSend("/topic/message", textMessageDTO);
		System.out.println("inside sendMessage");
	//	System.out.println("sha.getUser().getName():"+sha.getUser().getName());
	//	template.convertAndSendToUser(sha.getUser().getName(), "/topic/message", textMessageDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
//	@MessageMapping
//	public void Message(SimpMessageHeaderAccessor sha, @Payload TextMessageDTO textMessageDTO) {
//		System.out.println("sha.getUser().getName():"+sha.getUser().getName());
//		template.convertAndSendToUser(sha.getUser().getName(), "/topic/message", textMessageDTO);
//		
//	}

	@MessageMapping("/sendMessage")
	public void receiveMessage(@Payload TextMessageDTO textMessageDTO) {
		System.out.println("inside receiveMessage");
		// receive message from client
	}

	@SendTo("/topic/message")
	public TextMessageDTO broadcastMessage(@Payload TextMessageDTO textMessageDTO) {
		System.out.println("inside broadcastMessage");
		return textMessageDTO;
	}
	
}
