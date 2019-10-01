package com.example.board.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.omg.CORBA.Request;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.board.domain.MemberVO;
import com.example.board.service.LogInService;

import lombok.AllArgsConstructor;

@Controller	
@AllArgsConstructor
public class LogInController {
	private LogInService logInService;
	
	// 로그인 페이지로 이동
	@RequestMapping(value = "/logInPage", method = RequestMethod.GET)
	public String logInPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		
		if(session.getAttribute("memberVO_logIn") == null) { //세션 없을 때
			return "logIn/logInPage";
		}else { //세션 있을 때
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('이미 로그인된 상태 입니다.'); </script>");
			out.flush();
			return "post/postList";	
		}
	}
	
	// 로그인
	@RequestMapping(value = "/logIn", method = RequestMethod.POST)
	public String logIn(MemberVO memberVO, HttpSession session, Model model, HttpServletResponse response) throws IOException {
		System.out.println("로그인 컨트롤러 오는지 확인");
		MemberVO memberVO_logIn = logInService.logIn(memberVO.getUser_id(), memberVO.getPassword());
		
		if(memberVO_logIn == null) {
			System.out.println("memberVO_logIn가 널입니다.");
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('아이디가 존재하지 않거나 비밀번호가 일치하지 않습니다.'); </script>");
			out.flush();
			return "logIn/logInPage";
		}else {
			session.setAttribute("memberVO_logIn", memberVO_logIn);
			return "redirect:/";
		}
		
	}
	
	// 로그아웃
	@RequestMapping(value = "/logOut", method = RequestMethod.GET)
	public String logOut(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

}
