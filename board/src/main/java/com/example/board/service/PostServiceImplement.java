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

	//글 등록 함수 : postRegister
	@Override
	public int postRegister(PostVO postVO) {
		// TODO Auto-generated method stub
		return mapper.postRegister(postVO);
	}

	@Override
	public PostVO postDetail(int p_no) {
		// TODO Auto-generated method stub
		return mapper.postDetail(p_no);
	}

}
