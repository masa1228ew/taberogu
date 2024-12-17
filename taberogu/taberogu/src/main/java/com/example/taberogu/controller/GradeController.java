package com.example.taberogu.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.taberogu.entity.User;
import com.example.taberogu.repository.UserRepository;
import com.example.taberogu.security.UserDetailsImpl;

@Controller
@RequestMapping("/grade")
public class GradeController {
	 private final UserRepository userRepository;
	 
	 private GradeController(UserRepository userRepository) {
		 this.userRepository = userRepository;
	 }
	 
	  @GetMapping
	     public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {         
	         User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());  
	         
	         model.addAttribute("user", user);
	         
	         return "grade/index";
	     }
}
