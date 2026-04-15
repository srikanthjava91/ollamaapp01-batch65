package com.example.ollamaapp01.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.ollamaapp01.model.ChatMessage;
import com.example.ollamaapp01.model.OllamaRequest;
import com.example.ollamaapp01.model.OllamaResponse;
import com.example.ollamaapp01.repo.ChatMessageRepository;

@Service
public class OllamaChatService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ChatMessageRepository repo;

	private final String OLLAMA_URL = "http://localhost:11434/api/generate";

	// ✅ Primary + fallback models
	private final String PRIMARY_MODEL = "phi3:mini";
	private final String FALLBACK_MODEL = "tinyllama";

	public String chat(String message) {

		String replyMessage;

		try {
			replyMessage = callOllama(PRIMARY_MODEL, message);
		} catch (Exception e) {
			System.out.println("Primary model failed, switching to fallback...");
			replyMessage = callOllama(FALLBACK_MODEL, message);
		}

		// Save to DB
		ChatMessage chat = new ChatMessage(message, replyMessage, LocalDateTime.now());
		repo.save(chat);

		return replyMessage;
	}

	private String callOllama(String model, String message) {

		OllamaRequest request = new OllamaRequest(model, message, false);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<OllamaRequest> entity = new HttpEntity<>(request, headers);

		ResponseEntity<OllamaResponse> response = restTemplate.postForEntity(OLLAMA_URL, entity, OllamaResponse.class);

		return response.getBody().getResponse();
	}
}

//package com.example.ollamaapp01.service;
//
//import java.time.LocalDateTime;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import com.example.ollamaapp01.model.ChatMessage;
//import com.example.ollamaapp01.model.OllamaRequest;
//import com.example.ollamaapp01.model.OllamaResponse;
//import com.example.ollamaapp01.repo.ChatMessageRepository;
//
//@Service
//public class OllamaChatService {
//
//	@Autowired
//	private RestTemplate restTemplate;
//
//	@Autowired
//	private ChatMessageRepository repo;
//
//	private final String OLLAMA_URL = "http://localhost:11434/api/generate";
//
//	public String chat(String message) {
//
//		// ✅ Use lightweight model
////		OllamaRequest request = new OllamaRequest("tinyllama", message, false);
//		OllamaRequest request = new OllamaRequest("phi3", message, false);
//
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//
//		HttpEntity<OllamaRequest> entity = new HttpEntity<>(request, headers);
//
//		ResponseEntity<OllamaResponse> response = restTemplate.postForEntity(OLLAMA_URL, entity, OllamaResponse.class);
//
//		String replyMessage = response.getBody().getResponse();
//
//		// Save to DB
//		ChatMessage chat = new ChatMessage(message, replyMessage, LocalDateTime.now());
//		repo.save(chat);
//
//		return replyMessage;
//	}
//
////	public String chat(String message) {
////
////		OllamaRequest request = new OllamaRequest("tinyllama", message, false);
////		HttpHeaders headers = new HttpHeaders();
////		headers.setContentType(MediaType.APPLICATION_JSON);
////
////		HttpEntity<OllamaRequest> entity = new HttpEntity<>(request, headers);
////
////		ResponseEntity<OllamaResponse> response = restTemplate.postForEntity(OLLAMA_URL, entity, OllamaResponse.class);
////
////		String replyMessage = response.getBody().getResponse();
////
////		// Save to DB
////		ChatMessage chat = new ChatMessage(message, replyMessage, LocalDateTime.now());
////		repo.save(chat);
////
////		return replyMessage;
////	}
//}
