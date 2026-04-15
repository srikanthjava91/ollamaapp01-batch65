package com.example.ollamaapp01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.ollamaapp01.repo.ChatMessageRepository;
import com.example.ollamaapp01.service.OllamaChatService;

import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
//@RequestMapping("api/v1/chat")
public class OllamaChatController {

//	@Autowired
//	OllamaChatService ollamaChatService;
//
//	@GetMapping
//	String chat(@RequestParam String message) {
//		String response = ollamaChatService.chat(message);
//		return response;
//	}

	@Autowired
	private OllamaChatService chatService;

	@Autowired
	private ChatMessageRepository repo;

	// Show chatbot page
	@GetMapping("/chatbot")
	public String chatbotPage(Model model, HttpSession session, ServletResponse response) {

		if (session.getAttribute("user") == null) {
			return "redirect:/login";
		}

		model.addAttribute("chatHistory", repo.findAll());
		return "chatbot";
	}

	// Send message
	@PostMapping("/sendMessage")
	public String sendMessage(@RequestParam String message) {
		chatService.chat(message);
		return "redirect:/chatbot";
	}

	// Clear history
	@GetMapping("/clearHistory")
	public String clearHistory(HttpSession session) {

		if (session.getAttribute("user") == null) {
			return "redirect:/login";
		}

		repo.deleteAll();
		return "redirect:/chatbot";
	}
}
