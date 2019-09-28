package com.example.board.mapper;

import org.apache.ibatis.annotations.Param;

import com.example.board.domain.MemberVO;

public interface LogInMapper {
	//로그인을 위한 메소드(함수) 추가
	public MemberVO logIn(@Param ("user_id")String user_id, @Param ("password")String password);
}
