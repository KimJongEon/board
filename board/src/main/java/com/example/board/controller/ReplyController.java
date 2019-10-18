package com.example.board.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.board.domain.ReplyVO;
import com.example.board.service.ReplyService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class ReplyController {
	private ReplyService replyService;
	
	//댓글 리스트 불러오기
	@RequestMapping(value = "/replyList", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<ReplyVO> replyList(@RequestBody Map<String, Object> params){
		int p_no = (int) params.get("p_no");
		System.out.println(p_no);
		ArrayList<ReplyVO> replyVO = replyService.replyList(p_no); 
		System.out.println(replyVO);
		
		return replyVO;
	} 
	
	
	//댓글 등록
	@ResponseBody
	@RequestMapping(value = "/replyRegi", method = RequestMethod.POST)
	public int replyRegi(@RequestBody Map<String, Object> params) {
		//	1. json 객체에서 user_id, p_no, r_content를 가져와 변수에 담는다.
		String user_id = (String) params.get("user_id");
		int p_no = (int) params.get("p_no");
		String r_content = (String) params.get("r_content");
		
		System.out.println(user_id + ", " + p_no + ", " + r_content);
		
		//	2. reply 테이블에 인서트
		int replyInsert = replyService.replyInsert(user_id, p_no, r_content);
		
		System.out.println("인서트 되었는지 확인 : "+replyInsert);

		
		return replyInsert;
	}
}
