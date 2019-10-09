package com.example.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.board.domain.MemberVO;
import com.example.board.service.SignUpService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class SignUpController {
	private SignUpService signUpService;
	
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
	
	// 아이디 중복 체크
	@RequestMapping(value = "/idCheck", method = RequestMethod.GET)
	@ResponseBody
	public String idCheck(MemberVO memberVO) {
		String user_id = memberVO.getUser_id();
		System.out.println(user_id);
		int idCheck = signUpService.idCheck(user_id);
		System.out.println(idCheck);
		
		if(idCheck == 0) {// 중복이 아닐 때
			return "success";
		}else if(idCheck == 1) {// 중복일 때
			return "fail";
		}
		return user_id;
		
	}
}
