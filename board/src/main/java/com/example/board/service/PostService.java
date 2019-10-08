package com.example.board.service;

import java.util.ArrayList;

import com.example.board.domain.PostVO;

public interface PostService {
	//DB에서 글 목록을 가져오는 함수 : postList
	public ArrayList<PostVO> postList();
}
