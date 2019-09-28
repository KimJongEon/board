package com.example.board.service;

import org.springframework.stereotype.Service;

import com.example.board.domain.MemberVO;
import com.example.board.mapper.LogInMapper;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class LogInServiceImplement implements LogInService {
	private LogInMapper mapper;
	
	@Override
	public MemberVO logIn(String user_id, String password) {
		// TODO Auto-generated method stub
		System.out.println("로그인 서비스 임플리먼트 오는지 확인 : "+user_id + password);
		return mapper.logIn(user_id, password);
	}

}
