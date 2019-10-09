package com.example.board.mapper;

import org.apache.ibatis.annotations.Param;

import com.example.board.domain.MemberVO;

public interface SignUpMapper {
	// 회원가입 함수 추가
	public int signUp(MemberVO signUp);
	
//	// 아이디 중복 체크
	public int idCheck(@Param ("user_id")String user_id);
}
