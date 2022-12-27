package com.remoer.main;

public class Login {
	private String grade = "비회원";
	private String id = "";

	public String getGrade() {
		return this.grade;
	}

	public String getId() {
		return this.id;
	}

	public void memberLogin() {
		grade = "일반회원";
	}

	public void adminLogin() {
		grade = "관리자";
	}

	public void logout() {
		grade = "비회원";
		id = "";
	}

}
