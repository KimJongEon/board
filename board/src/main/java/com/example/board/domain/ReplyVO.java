package com.example.board.domain;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ReplyVO {
	private int r_no; //댓글 번호 (DB에서 autoIncrement 사용)
	private String r_content; // 댓글 내용
	private Timestamp r_dt; // 댓글 일시
	
	// Foreign Key
	private String user_id;
	private int p_no;
}
