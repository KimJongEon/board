package com.example.board.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.board.domain.MemberVO;
import com.example.board.domain.PostVO;
import com.example.board.service.PostService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class PostController {
	private PostService postService;
	
	// 글 목록 페이지로 이동 (홈)
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String postList(Model model) {
//		System.out.println(postService.postList());
		model.addAttribute("postList", postService.postList());
		System.out.println("postList모델 확인"+model);
		
		
		return "post/postList";
	}
	
	// 글 작성 페이지로 이동
	@RequestMapping(value = "/postRegisterPage", method = RequestMethod.GET)
	public String postRegisterPage(Model model) {

		return "post/postRegisterPage";

	}
	
	// 글 등록
	@RequestMapping(value = "/postRegister", method = RequestMethod.POST)
	public String postRegister(PostVO postVO, HttpSession session, HttpServletRequest request) {
		String p_title = postVO.getP_title();
		String p_content = postVO.getP_content();
		String user_id = ((MemberVO) request.getSession().getAttribute("memberVO_logIn")).getUser_id();
		
		System.out.println("컨트롤러 넘어오는지 테스트 : "+p_title + p_content + user_id);
		
		//session에서 가져온 user_id postVO객체의 user_id에 셋팅
		postVO.setUser_id(user_id);
		
		int postRegister = postService.postRegister(postVO);
		
		if(postRegister != 0) { //글 등록 성공
//			response.setContentType("text/html; charset=UTF-8");
//			PrintWriter out = response.getWriter();
//			out.println("<script>alert('글 등록 완료.'); </script>");
//			out.flush();
			
			return "redirect:/";
		}else { //글 등록 실패
//			response.setContentType("text/html; charset=UTF-8");
//			PrintWriter out = response.getWriter();
//			out.println("<script>alert('글 등록 실패.'); </script>");
//			out.flush();
			
			return "redirect:/";
		}
	
//		return "redirect:/";
	}
	
	@RequestMapping(value = "/postDetail", method = RequestMethod.GET)
	public ModelAndView postDetail(@RequestParam int p_no, HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("memberVO_logIn") == null) {
			response.sendRedirect("/logInPage");
		}else {
			System.out.println("postView 넘어오는지 확인");
			System.out.println(p_no);
			ModelAndView mv = new ModelAndView();
			PostVO postDetail = postService.postDetail(p_no); 
			
			mv.setViewName("post/postDetail"); // 뷰의 이름
			mv.addObject("postDetail", postDetail); // 뷰로 보낼 데이터 값

//			String p_no = Integer.toString(postVO.getP_no());
//			System.out.println(p_no);
			
			
			System.out.println(postDetail);
			return mv;
		}
		return null;

		
	}
}
	