package com.example.board.service;

import com.example.board.domain.MemberVO;

public interface LogInService {
	//로그인 아이디, 패스워드
	public MemberVO logIn(String user_id, String password);

}
