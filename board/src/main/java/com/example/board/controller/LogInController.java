package com.example.board.controller;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LogInController {
	// 로그인 페이지로 이동
	@RequestMapping(value = "/logIn", method = RequestMethod.GET)
	public String logIn(Locale locale, Model model) {

		return "logIn/logIn";
	}

}
