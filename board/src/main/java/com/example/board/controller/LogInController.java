package com.example.board.controller;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.service.LogInService;

@Controller
public class LogInController {
	private LogInService logInService;
	
	// 로그인 페이지로 이동
	@RequestMapping(value = "/logInPage", method = RequestMethod.GET)
	public String logInPage(Locale locale, Model model) {

		return "logIn/logInPage";
	}
	
	// 로그인
	@RequestMapping(value = "/logIn", method = RequestMethod.POST)
	public String logIn(Locale locale, Model model) {
		
		
		return "post/postList";
	}
	
	

}
