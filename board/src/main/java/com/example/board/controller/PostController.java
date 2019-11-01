package com.example.board.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import com.example.board.domain.AttachVO;
import com.example.board.domain.MemberVO;
import com.example.board.domain.PostVO;
import com.example.board.domain.ReplyVO;
import com.example.board.paging.Pagination;
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
	public String postList(Model model, @RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "1") int range) {

		// 전체 게시글 개수
		int listCnt = postService.getBoardListCnt();
//		System.out.println("1. 전체 게시글 개수 확인 : "+ listCnt);

		// Pagination 객체생성
		Pagination pagination = new Pagination();
		pagination.pageInfo(page, range, listCnt);
//		System.out.println("2. Pagination객체 확인 : "+pagination);
		model.addAttribute("pagination", pagination);
		model.addAttribute("postList", postService.postList(pagination));

		return "post/postList";
	}

	// 글 작성 페이지로 이동
	@RequestMapping(value = "/postRegisterPage", method = RequestMethod.GET)
	public String postRegisterPage(Model model) {

		return "post/postRegisterPage";

	}

	// 글 상세 페이지
	@RequestMapping(value = "/postDetail", method = RequestMethod.GET)
	public ModelAndView postDetail(@RequestParam(value = "p_no", required = false) int p_no, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();

		if (session.getAttribute("memberVO_logIn") == null) {
			response.sendRedirect("/logInPage");
		} else {
			String user_id = ((MemberVO) request.getSession().getAttribute("memberVO_logIn")).getUser_id();
			PostVO postDetail = postService.postDetail(p_no);
			ArrayList<ReplyVO> replyList = replyService.replyList(p_no);
			if (postDetail != null) {

				// 1. 조회수 쿠키 사용
				Cookie[] cookies = request.getCookies();
				// 2. 비교하기 위해 새로운 쿠키
				Cookie viewCookie = null;

				// 3.쿠키가 있을 경우
				if (cookies != null && cookies.length > 0) {
					for (int i = 0; i < cookies.length; i++) {
						// Cookie의 name이 cookie + reviewNo와 일치하는 쿠키를 viewCookie에 넣어줌
						if (cookies[i].getName().equals("cookie" + p_no + user_id)) {
							System.out.println("처음 쿠키가 생성한 뒤 들어옴.");
							viewCookie = cookies[i];
						}
					}
				}

				// 4. 만일 viewCookie가 null일 경우 쿠키를 생성해서 조회수 증가 로직을 처리함.
				if (viewCookie == null) {
					System.out.println("cookie 없음");

					// 쿠키 생성(이름, 값) - 쿠키 이름을 글번호와 세션에 저장된 user_id로 구분
					Cookie newCookie = new Cookie("cookie" + p_no + user_id, "|" + p_no + "|");

					// 쿠키 추가
					response.addCookie(newCookie);

					// 쿠키를 추가 시키고 조회수 증가시킴
					int result = postService.upReadCount(p_no);

					if (result > 0) {
						System.out.println("조회수 증가");
					} else {
						System.out.println("조회수 증가 에러");
					}
				}
				// 5. viewCookie가 null이 아닐경우 쿠키가 있으므로 조회수 증가 로직을 처리하지 않음.
				else {
					System.out.println("cookie 있음");

					// 쿠키 값 받아옴.
					String value = viewCookie.getValue();

					System.out.println("cookie 값 : " + value);

				}
				ModelAndView mv = new ModelAndView();
				mv.setViewName("post/postDetail"); // 뷰의 이름
				mv.addObject("postDetail", postDetail); // 뷰로 보낼 데이터 값 : 게시글 상세 내용
				return mv;
			} else {
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>alert('이미 삭제되거나 존재하지 않은 게시물 입니다.');" + " window.location.href='/'</script>");
				out.flush();
			}
		}
		return null;
	}
	// 글 삭제
	@ResponseBody
	@RequestMapping(value = "/postDelBtn", method = RequestMethod.GET)
	public String postDelBtn(@RequestParam int p_no) {
		// int로 형변환
		System.out.println(p_no);
		System.out.println("삭제 컨트롤러 오는지 확인");

		int postDel = postService.postDel(p_no);
		if (postDel == 1) {
			return "success";
		} else {
			return "fail";
		}

	}

	// 글 수정 페이지 이동
	@RequestMapping(value = "/postEditPage", method = RequestMethod.GET)
	public ModelAndView postEditPage(@RequestParam(value = "p_no", required = false) int p_no,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		PostVO postDetail = postService.postDetail(p_no);
		// 첨부파일 관련 VO 객체 데이터 추가
		HttpSession session = request.getSession();
		if (session.getAttribute("memberVO_logIn") != null) {
//			System.out.println("여기오는건가");

			ModelAndView mv = new ModelAndView();
			mv.setViewName("post/postEditPage"); // 뷰의 이름
			mv.addObject("postDetail", postDetail); // 뷰로 보낼 데이터 값 : 게시글 상세 내용
			return mv;
		} else {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>window.location.href='/'</script>");
			out.flush();
		}

		return null;
	}

	// 글 수정 완료
	@RequestMapping(value = "/postEdit", method = RequestMethod.POST)
	public String postEdit(PostVO postVO, HttpSession session, HttpServletRequest request
			,MultipartHttpServletRequest mtfRequest) {
		String session_user_id = ((MemberVO) request.getSession().getAttribute("memberVO_logIn")).getUser_id();
		String p_title = postVO.getP_title();
		String p_content = postVO.getP_content();
		String user_id = postVO.getUser_id();
		int p_no = postVO.getP_no(); // 수정할 글의 p_no
		//파일업로드에 필요한 변수들▼▼▼▼▼
		String filePath = "C:/upload"; // 파일 저장 경로
		String ori_nm = null; // 첨부 파일의 원래 이름
		String uuid = null; // 저장될 파일을 구분하기 위해 UUID 사용
		String save_nm = null; // 저장될 파일 이름 (uuid + org_nm)
		//파일업로드에 필요한 변수들▲▲▲▲▲
		
		int postEdit = postService.postEdit(p_title, p_content, p_no); //글 수정 서비스
		
		//파일 업로드▼▼▼▼▼▼
		List<MultipartFile> fileList = mtfRequest.getFiles("file");

		for (MultipartFile mf : fileList) {
			ori_nm = mf.getOriginalFilename();
			uuid = UUID.randomUUID().toString();

			save_nm = uuid + ori_nm; // 저장될 파일 이름은 UUID와 원래 파일이름을 같이 합쳐서 저장
			// DB에는 UUID와 원래 파일이름을 따로 저장

			String fileFullPath = filePath + "/" + save_nm; // 파일 전체 경로

			// DB에 uuid와 orinalFileName 저장하는 서비스 만들기
			// 첨부되는 파일이 여러 개 일수도 있기때문에 for을 사용해서 파일의 갯수만큼 DB에 저장
			int postAttachRegi = postService.postAttachRegi(p_no, ori_nm, uuid, save_nm);
			System.out.println("db에 저장 되었는지 확인 : " + postAttachRegi);
			try {
				// 파일 저장
				mf.transferTo(new File(fileFullPath)); // 파일저장 실제로는 service에서 처리

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		

		return "redirect:/postDetail/?p_no=" + p_no;
	}

	// 글등록 (파일업로드 포함)
	@RequestMapping(value = "/postRegister", method = RequestMethod.POST)
	public String postRegister(PostVO postVO, HttpSession session, HttpServletRequest request,
			MultipartHttpServletRequest mtfRequest) {
		String p_title = postVO.getP_title();
		String p_content = postVO.getP_content();
		String user_id = ((MemberVO) request.getSession().getAttribute("memberVO_logIn")).getUser_id();
		String filePath = "C:/upload"; // 파일 저장 경로
		String ori_nm = null; // 첨부 파일의 원래 이름
		String uuid = null; // 저장될 파일을 구분하기 위해 UUID 사용
		String save_nm = null; // 저장될 파일 이름 (uuid + org_nm)
		// session에서 가져온 user_id postVO객체의 user_id에 셋팅
		postVO.setUser_id(user_id);

		List<MultipartFile> fileList = mtfRequest.getFiles("file");

		// 유저아이디, 글제목, 내용으로 DB에 저장하는 서비스
		int postRegister = postService.postRegister(postVO);

		// DB에 글작성시 생성 된 p_no를 가져옴
		int p_no = postService.findPostNo(p_title, p_content, user_id);
		System.out.println("생성된 p_no 확인 : " + p_no);

		for (MultipartFile mf : fileList) {
			ori_nm = mf.getOriginalFilename();
			uuid = UUID.randomUUID().toString();

			save_nm = uuid + ori_nm; // 저장될 파일 이름은 UUID와 원래 파일이름을 같이 합쳐서 저장
			// DB에는 UUID와 원래 파일이름을 따로 저장

			String fileFullPath = filePath + "/" + save_nm; // 파일 전체 경로

//			long fileSize = mf.getSize();
//			System.out.println("파일 사이즈 확인 : " + fileSize);

			// DB에 uuid와 orinalFileName 저장하는 서비스 만들기
			// 첨부되는 파일이 여러 개 일수도 있기때문에 for을 사용해서 파일의 갯수만큼 DB에 저장
			int postAttachRegi = postService.postAttachRegi(p_no, ori_nm, uuid, save_nm);
			System.out.println("db에 저장 되었는지 확인 : " + postAttachRegi);
			try {
				// 파일 저장
				mf.transferTo(new File(fileFullPath)); // 파일저장 실제로는 service에서 처리

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (postRegister != 0) { // 글 등록 성공

			return "redirect:/";
		} else { // 글 등록 실패

			return "redirect:/";
		}
	}

	// 첨부파일 리스트 불러오기
	@RequestMapping(value = "/attachList", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<AttachVO> attachList(@RequestBody Map<String, Object> params) {
		int p_no = (int) params.get("p_no");
		System.out.println(p_no);
		ArrayList<AttachVO> attachVO = postService.attachList(p_no);
		System.out.println(attachVO);

		return attachVO;
	}

	// 첨부파일 삭제
	@ResponseBody
	@RequestMapping(value = "/delAttach", method = RequestMethod.POST)
	public int delAttach(@RequestBody Map<String, Object> params) {
		// 1. json 객체에서 uuid, save_nm를 가져와 변수에 담는다.
		String uuid = (String) params.get("uuid");
		String save_nm = (String) params.get("save_nm");
		String fullPath = "C:/upload/" + save_nm;
		int delAttach = 0;
		System.out.println(uuid);
		System.out.println(save_nm);
		
		// 2. 파일이 저장된 경로를 찾아가서 해당 파일을 삭제한다.
		File file = new File(fullPath);
		if (file.exists()) {
			if (file.delete()) {
				// 3. DB에서도 해당 파일에 대한 정보를 삭제한다.
				delAttach = postService.delAttach(uuid);
				if(delAttach !=0) {
					System.out.println("파일삭제 성공");
					return delAttach;
				}else {
					return delAttach;
				}
			} else {
				System.out.println("파일삭제 실패");
				return delAttach;
			}
		} else {
			System.out.println("파일이 존재하지 않습니다.");
			return delAttach;
		}
	}
}
