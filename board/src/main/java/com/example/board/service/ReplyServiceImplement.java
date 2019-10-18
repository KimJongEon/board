package com.example.board.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.example.board.domain.ReplyVO;
import com.example.board.mapper.ReplyMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReplyServiceImplement implements ReplyService {
	private ReplyMapper mapper;
	
	@Override
	public ArrayList<ReplyVO> replyList(int p_no) {
		// TODO Auto-generated method stub
		return mapper.replyList(p_no);
	}

	@Override
	public int replyInsert(String user_id, int p_no, String r_content) {
		// TODO Auto-generated method stub
		return mapper.replyInsert(user_id, p_no, r_content);
	}


}
