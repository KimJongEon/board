package com.example.board.domain;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class MemberVO {
	private String user_id;
	private String password;
	private String user_nm;
	private String ph_no;
	private Timestamp signUp_dt;
}
