package com.example.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SignUpController {
	// 회원가입 페이지로 이동
	@RequestMapping(value = "/signUp", method = RequestMethod.GET)
	public String register(Model model) {

		return "signUp/signUp";

	}
}
