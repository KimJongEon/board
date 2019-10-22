package com.example.board.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.example.board.domain.PostVO;
import com.example.board.paging.Pagination;

public interface PostMapper {
	//DB에서 글 목록을 가져오는 함수 : postList
	public ArrayList<PostVO> postList(Pagination pagination);
	
	//글 등록 함수 : postRegister
	public int postRegister(PostVO postVO);
	
	//postDetail 정보 가져오는 함수
	public PostVO postDetail(int p_no);
	
	//글 삭제 함수
	public int postDel(int p_no);
	
	//글 수정 함수
	public int postEdit(@Param ("p_title") String p_title, @Param ("p_content")String p_content, @Param ("p_no")int p_no);
	
	//글 조회수 증가 함수
	public int upReadCount(int p_no);
	
	//총 게시글 개수 확인
	public int getBoardListCnt();
}
