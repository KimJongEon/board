package com.example.board.service;

import java.util.ArrayList;

import com.example.board.domain.PostVO;

public interface PostService {
	//DB에서 글 목록을 가져오는 함수 : postList
	public ArrayList<PostVO> postList();
	
	//글 등록 함수 : postRegister
	public int postRegister(PostVO postVO);
	
	//특정 글 선택 시 해당 post에 대한 정보를 가져오는 함수
	public PostVO postDetail(int p_no);

	//글 삭제 함수
	public int postDel(int p_no);

	//글 수정 함수
	public int postEdit(String p_title, String p_content, int p_no);

	
}
