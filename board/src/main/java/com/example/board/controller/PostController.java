package com.example.board.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.board.domain.MemberVO;
import com.example.board.domain.PostVO;
import com.example.board.domain.ReplyVO;
import com.example.board.service.PostService;
import com.example.board.service.ReplyService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class PostController {
	private PostService postService;
	private ReplyService replyService;
	// 글 목록 페이지로 이동 (홈)
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String postList(Model model) {
//		System.out.println(postService.postList());
		model.addAttribute("postList", postService.postList());
//		System.out.println("postList모델 확인"+model);
		
		
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
		
//		System.out.println("컨트롤러 넘어오는지 테스트 : "+p_title + p_content + user_id);
		
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
	
	//글 상세 페이지
	@RequestMapping(value = "/postDetail", method = RequestMethod.GET)
	public ModelAndView postDetail(@RequestParam(value = "p_no",  required=false) int p_no, HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("memberVO_logIn") == null) {
			response.sendRedirect("/logInPage");
		}else {

			PostVO postDetail = postService.postDetail(p_no);
			ArrayList<ReplyVO> replyList = replyService.replyList(p_no);
			if(postDetail != null) {
				ModelAndView mv = new ModelAndView();
				mv.setViewName("post/postDetail"); // 뷰의 이름
				mv.addObject("postDetail", postDetail); // 뷰로 보낼 데이터 값 : 게시글 상세 내용
				return mv;
			}else {
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>alert('이미 삭제되거나 존재하지 않은 게시물 입니다.');"
						+ " window.location.href='/'</script>");
				out.flush();
			}
		}
		return null;
	}
	
	// 글 수정
	
	// 글 삭제
	@ResponseBody
	@RequestMapping (value = "/postDelBtn", method = RequestMethod.GET)
	public String postDelBtn(@RequestParam int p_no) {
		//int로 형변환
		System.out.println(p_no);
		System.out.println("삭제 컨트롤러 오는지 확인");
		
		int postDel = postService.postDel(p_no);
		if(postDel == 1) {
			return "success";
		}else {
			return "fail";
		}
		
	}
	
	// 글 수정 페이지 이동
	@RequestMapping (value = "/postEditPage", method = RequestMethod.GET)
	public ModelAndView postEditPage(@RequestParam(value = "p_no",  required=false) int p_no, HttpServletRequest request, HttpServletResponse response) throws IOException {
		PostVO postDetail = postService.postDetail(p_no);
		HttpSession session = request.getSession();	
		if(session.getAttribute("memberVO_logIn") != null) {
			System.out.println("여기오는건가");
			
			ModelAndView mv = new ModelAndView();
			mv.setViewName("post/postEditPage"); // 뷰의 이름
			mv.addObject("postDetail", postDetail); // 뷰로 보낼 데이터 값 : 게시글 상세 내용
			return mv;
		}else {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>window.location.href='/'</script>");
			out.flush();
		}
		
		return null;
	}
	
	// 글 수정 완료
	@RequestMapping(value = "/postEdit", method= RequestMethod.POST)
	public String postEdit(PostVO postVO, HttpSession session, HttpServletRequest request) {
		String session_user_id = ((MemberVO) request.getSession().getAttribute("memberVO_logIn")).getUser_id();
		String p_title = postVO.getP_title();
		String p_content = postVO.getP_content();
		String user_id = postVO.getUser_id();
		int p_no = postVO.getP_no();
		
		System.out.println("@1 : " + session_user_id);
		System.out.println("@2 : " + p_title);
		System.out.println("@3 : " + p_content);
		System.out.println("@4 : " + user_id);
		System.out.println("@5 : " + p_no);
		
		int postEdit = postService.postEdit(p_title, p_content, p_no);
		
		return "redirect:/postDetail/?p_no=" + p_no;
	}
}
	