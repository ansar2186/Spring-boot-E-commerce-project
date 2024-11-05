package com.shopme.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopme.admin.service.UserService;

@RestController
public class UserRestController {
	
	@Autowired
	private UserService service;
	
	@PostMapping("/users/{check-email}")
	public String checkDublicateEmail(@Param("id")Integer id, @Param("check-email")String email) {
		System.out.println("Controller called");
		
		return service.isEmailUnique(id,email) ? "OK" : "Duplicated";
		
	}

}
