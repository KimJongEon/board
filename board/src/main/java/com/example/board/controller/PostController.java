package com.example.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PostController {
	// 글 목록 페이지로 이동 (홈)
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String postList(Model model) {

		return "post/postList";
	}
	
	// 글 작성 페이지로 이동
	@RequestMapping(value = "/postRegister", method = RequestMethod.GET)
	public String postRegister(Model model) {

		return "post/postRegister";

	}
	
	// 글 상세보기 페이지로 이동
	@RequestMapping(value = "/postDetail", method = RequestMethod.GET)
	public String postDetail(Model model) {

		return "post/postDetail";

	}
}
