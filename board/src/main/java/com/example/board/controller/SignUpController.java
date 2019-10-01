package com.example.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SignUpController {
	// 회원가입 페이지로 이동
	@RequestMapping(value = "/signUpPage", method = RequestMethod.GET)
	public String signUpPage(Model model) {

		return "signUp/signUpPage";

	}
	
	// 회원가입 완료
	@RequestMapping(value = "/signUp", method = RequestMethod.POST)
	public String signUp(Model model){
		
		return "";
		
	}
}
