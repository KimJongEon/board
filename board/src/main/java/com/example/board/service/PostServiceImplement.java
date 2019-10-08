package com.example.board.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.example.board.domain.PostVO;
import com.example.board.mapper.PostMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostServiceImplement implements PostService {
	private PostMapper mapper;
	
	//DB에서 글 목록을 가져오는 함수 : postList
	@Override
	public ArrayList<PostVO> postList() {
		// TODO Auto-generated method stub
		return mapper.postList();
	}

}
