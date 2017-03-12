package com.model;

public class AddrBookForm {
	public int addrid = 0;						// 通讯录编号
	public String addrname = "";				// 姓名
	public String addrsex = ""; 			// 性别
	public String e_mail="";			//邮箱
	public String address="";				//地址
	public String phone="";				//手机号码
	public String landine="";					//座机号
	public int getAddrid() {
		return addrid;
	}
	public void setAddrid(int addrid) {
		this.addrid = addrid;
	}
	public String getAddrname() {
		return addrname;
	}
	public void setAddrname(String addrname) {
		this.addrname = addrname;
	}
	public String getAddrsex() {
		return addrsex;
	}
	public void setAddrsex(String addrsex) {
		this.addrsex = addrsex;
	}
	public String getE_mail() {
		return e_mail;
	}
	public void setE_mail(String e_mail) {
		this.e_mail = e_mail;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getLandine() {
		return landine;
	}
	public void setLandine(String landine) {
		this.landine = landine;
	}
	
	
}
