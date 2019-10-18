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

	//글 상세 페이지 함수
	@Override
	public PostVO postDetail(int p_no) {
		// TODO Auto-generated method stub
		return mapper.postDetail(p_no);
	}
	
	//글 삭제 함수
	@Override
	public int postDel(int p_no) {
		// TODO Auto-generated method stub
		return mapper.postDel(p_no);
	}
	
	
	//글 수정 함수
	@Override
	public int postEdit(String p_title, String p_content, int p_no) {
		// TODO Auto-generated method stub
		return mapper.postEdit(p_title, p_content, p_no);
	}

}
