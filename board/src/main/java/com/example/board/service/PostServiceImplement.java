package com.example.board.service;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.board.domain.AttachVO;
import com.example.board.domain.PostVO;
import com.example.board.mapper.PostMapper;
import com.example.board.paging.Pagination;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostServiceImplement implements PostService {
	private PostMapper mapper;
	
	//DB에서 글 목록을 가져오는 함수 : postList
	@Override
	public ArrayList<PostVO> postList(Pagination pagination) {
		// TODO Auto-generated method stub
		return mapper.postList(pagination);
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
	
	//글 조회수 증가 함수
	@Override
	public int upReadCount(int p_no) {
		// TODO Auto-generated method stub
		return mapper.upReadCount(p_no);
	}
	
	//총 게시글 개수 확인
	@Override
	public int getBoardListCnt(){
		// TODO Auto-generated method stub
		return mapper.getBoardListCnt();
	}

	//파일업로드의 내용을 DB에 넣기전에 작성한 글의 p_no를 가져오는 함수 
	@Override
	public int findPostNo(String p_title, String p_content, String user_id) {
		// TODO Auto-generated method stub
		return mapper.findPostNo(p_title, p_content, user_id);
	}

	//파일업로드의 내용을 DB에 저장하는 함수
	@Override
	public int postAttachRegi(int p_no, String ori_nm, String uuid, String save_nm) {
		// TODO Auto-generated method stub
		return mapper.postAttachRegi(p_no, ori_nm, uuid, save_nm);
	}

	//글 상세내용을 불러올때 가져올 첨부파일 목록
	@Override
	public ArrayList<AttachVO> attachList(int p_no) {
		// TODO Auto-generated method stub
		return mapper.attachList(p_no);
	}
	
	//글 수정에서 첨부파일 삭제 함수
	@Override
	public int delAttach(String uuid) {
		// TODO Auto-generated method stub
		return mapper.delAttach(uuid);
	}
}
