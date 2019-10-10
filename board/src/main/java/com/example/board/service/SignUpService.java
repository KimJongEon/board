package com.example.board.service;

import com.example.board.domain.MemberVO;

public interface SignUpService {
	
	//회원가입 함수 추가
	public int signUp(MemberVO memberVO);
	
	//아이디 중복 체크
	public int idCheck(String user_id);
}
