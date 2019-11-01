package com.example.board.service;

import java.util.ArrayList;
import java.util.UUID;

import com.example.board.domain.AttachVO;
import com.example.board.domain.PostVO;
import com.example.board.paging.Pagination;

public interface PostService {
	//DB에서 글 목록을 가져오는 함수 : postList
	public ArrayList<PostVO> postList(Pagination pagination);
	
	//글 등록 함수 : postRegister
	public int postRegister(PostVO postVO);
	
	//특정 글 선택 시 해당 post에 대한 정보를 가져오는 함수
	public PostVO postDetail(int p_no);

	//글 삭제 함수
	public int postDel(int p_no);

	//글 수정 함수
	public int postEdit(String p_title, String p_content, int p_no);
	
	//글 조회수 증가 함수
	public int upReadCount(int p_no);

	//페이징 처리 함수 ▼▼▼▼▼▼▼▼▼▼
	
	//총 게시글 개수 확인
	public int getBoardListCnt();
	
	//파일업로드의 내용을 DB에 넣기전에 작성한 글의 p_no를 가져오는 함수 
	public int findPostNo(String p_title, String p_content, String user_id);

	//파일업로드의 내용을 DB에 저장하는 함수
	public int postAttachRegi(int p_no, String ori_nm, String uuid, String save_nm);

	//글 상세내용을 불러올때 가져올 첨부파일 목록
	public ArrayList<AttachVO> attachList(int p_no);

	//글 수정에서 첨부파일 삭제 함수
	public int delAttach(String uuid);
}
