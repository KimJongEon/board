package com.example.board.domain;

import lombok.Data;

@Data
public class AttachVO {
	private int attach_no; // 첨부파일 번호 (DB에서 autoIncrement사용)
	private String ori_nm; // 파일 원본 이름
	private String save_nm; // 저장되는 파일 이름
	private String uuid; // 저장될 파일을 구분하기 위한 uui
//	private int file_size; // 파일 크기
	
	// Foreign Key
	private int p_no;
}
