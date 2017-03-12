package com.model;

public class UserForm {
	public int id = 0;						// ±àºÅ
	public String userName = "";			// ÓÃ»§Ãû
	public String pwd = ""; 				// ÃÜÂë
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}	
}
