package com.example.board.service;

import java.util.ArrayList;

import com.example.board.domain.ReplyVO;

public interface ReplyService {
	//댓글 리스트를 가져오는 함수
	public ArrayList<ReplyVO> replyList(int p_no);

	public int replyInsert(String user_id, int p_no, String r_content);


}
