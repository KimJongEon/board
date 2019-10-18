package com.example.board.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

//로그인 처리를 담당하는 인터셉터
public class LogInInterceptor extends HandlerInterceptorAdapter {

	// preHandle() : 컨트롤러보다 먼저 수행되는 메서드
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws IOException {

		// session 객체를 가져옴
		HttpSession session = request.getSession();

		// ajax 요청시 Headr 값 XMLHttpRequest 설정
		String ajaxHeader = "XMLHttpRequest";
		System.out.println("1 : ");

		if (session.getAttribute("memberVO_logIn") == null) {
			System.out.println("2 : ");
			if (ajaxHeader.equals(request.getHeader("x-requested-with"))) { // ajax를 사용했을 때
				System.out.println("3 : ");
				// 로그인이 안되어있는 상태이면 홈으로 돌려보냄(postList페이지)
//				response.sendRedirect("/logInPage"); // ajax를 사용하면 sendRedirect가 사용되지않고 ajax로 다시 리턴됨
				response.sendError(500);
				return false; // 더이상 컨트롤러로 요청이 가지않도록 false로 반환
			} else { // ajax를 사용하지 않았을 때
				System.out.println("4 : ");
				response.sendRedirect("/logInPage");
				return false; // 더이상 컨트롤러로 요청이 가지않도록 false로 반환
			}
		} else {
			System.out.println("5 : ");
			return true;
		}

	}

	/**
	 * Controller의 메소드가 수행이 완료되고, View 를 호출하기 전에 호출됩니다.
	 */
//	@Override
//	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mv)
//			throws Exception {
//
//		System.out.println("컨트롤러 실행 후 :Bye");
//		// 컨트롤러 실행 후 MV사용. 로그인 필터역할도 충분할 듯 합니다.
//		HttpSession session = request.getSession();
//		if (session.getAttribute("memberVO_logIn") == null) {
//			response.sendRedirect("/logInPage");
//		}
//
//	}
}
