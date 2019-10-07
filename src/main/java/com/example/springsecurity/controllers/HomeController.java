package com.example.springsecurity.controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springsecurity.models.HomePage;

@RestController
public class HomeController {

	@GetMapping("/home")
	public HomePage home(HttpServletResponse response) {
		return new HomePage("Lorem ipsum dolor sit amet consectetur adipisicing elit. Maxime repellat aperiam illo consectetur laborum corporis aliquid dolorum molestias. Architecto ut consectetur esse nisi neque aspernatur itaque quas vitae iste quod.",
				"Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...");
	}
}
