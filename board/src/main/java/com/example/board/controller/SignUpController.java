package com.example.board.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

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
	public String signUp(MemberVO memberVO, HttpServletResponse response) throws IOException{
//		String user_id = memberVO.getUser_id();
//		String password = memberVO.getPassword();
//		String ph_no = memberVO.getPh_no();
//		String user_nm = memberVO.getUser_nm();
//		
//		System.out.println(user_id + password + ph_no + user_nm);
		
		String user_id = memberVO.getUser_id();
		int idCheck = signUpService.idCheck(user_id);
		if(idCheck == 0) {// 아이디가 DB와 비교해서 중복이 아닐 때
			int signUp = signUpService.signUp(memberVO); //DB에 인서트
			if(signUp != 0) { //회원가입 성공
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>alert('회원가입에 성공하였습니다.'); </script>");
				out.flush();
				
				return "logIn/logInPage";
			}else { //회원가입 실패
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>alert('회원가입에 실패하였습니다.'); </script>");
				out.flush();
				
				return "signUp/signUpPage";
			}
		}else if(idCheck == 1) {// 아이디가 DB와 비교해서 중복일 때
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('중복된 아이디가 있거나 비밀번호가 일치하지 않습니다.'); </script>");
			out.flush();
			
			return "signUp/signUpPage";
		}
//		System.out.println("사인업 컨트롤러 : " + signUp);
		return "signUp/signUpPage";
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
