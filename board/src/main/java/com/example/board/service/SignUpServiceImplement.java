package com.example.board.service;

import org.springframework.stereotype.Service;

import com.example.board.domain.MemberVO;
import com.example.board.mapper.SignUpMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SignUpServiceImplement implements SignUpService {
	private SignUpMapper mapper;
	
	//회원가입
	@Override
	public int signUp(MemberVO memberVO) {
		// TODO Auto-generated method stub
		System.out.println("signUp 임플리먼트 넘어오는지 확인"+memberVO);
		return mapper.signUp(memberVO);
	}
	
	//아이디 중복체크
	@Override
	public int idCheck(String user_id) {
		// TODO Auto-generated method stub
//		System.out.println("사인업 임플리먼트 넘어오는지 확인 : " + user_id);
		return mapper.idCheck(user_id);
	}

}
