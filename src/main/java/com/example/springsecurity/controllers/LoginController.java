package com.example.springsecurity.controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springsecurity.models.TokenResponse;

@RestController
public class LoginController {

	@PostMapping("/login")
	public TokenResponse login(HttpServletResponse response) {
		return new TokenResponse(response.getHeader("username"),response.getHeader("Authorization"));
	}
}
