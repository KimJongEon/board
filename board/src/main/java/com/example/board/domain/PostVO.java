package com.example.board.domain;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class PostVO {
	private int p_no; //포스트 번호 (DB에서 autoIncrement 설정)
	private String p_title; // 포스트 제목
	private String p_content; // 포스트 내용
	private Timestamp p_dt; //포스트 등록 일시
	private int p_readCount; // 포스트 조회수
	
	//Foreign Key
	private String user_id;
	
}
