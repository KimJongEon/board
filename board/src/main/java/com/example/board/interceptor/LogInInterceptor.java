package com.example.board.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

//로그인 처리를 담당하는 인터셉터
public class LogInInterceptor extends HandlerInterceptorAdapter{
	
	// preHandle() : 컨트롤러보다 먼저 수행되는 메서드
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws IOException {

        // session 객체를 가져옴
		HttpSession session = request.getSession();
		
		if(session.getAttribute("memberVO_logIn") == null) {
			//로그인이 안되어있는 상태이면 홈으로 돌려보냄(postList페이지)
			response.sendRedirect("/logInPage");
			return false; //더이상 컨트롤러로 요청이 가지않도록 false로 반환
		}else {
			return true;
		}
	}
}
