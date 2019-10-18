package com.example.board.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.example.board.domain.ReplyVO;

public interface ReplyMapper {
	// 댓글 리스트를 가져오는 함수
	public ArrayList<ReplyVO> replyList(int p_no);

	public int replyInsert(@Param ("user_id") String user_id, @Param ("p_no") int p_no, @Param ("r_content") String r_content);
}
