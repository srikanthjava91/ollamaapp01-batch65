package com.example.ollamaapp01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String loginPage() {

		return "login";
	}

	@PostMapping("/doLogin")
	public String doLogin(@RequestParam String username, @RequestParam String password, HttpSession session,
			ServletResponse response) {

		if (username.equals("admin") && password.equals("admin123")) {
			session.setAttribute("user", "admin");
			return "redirect:/chatbot";
		}

		return "redirect:/login?error=true";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
}
