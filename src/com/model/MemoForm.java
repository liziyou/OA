package com.model;

public class MemoForm {
	public int id = 0;						// 编号
	public String title = "";				// 标题
	public String content = ""; 			// 内容
	public String remindTime="";			//提醒时间
	public int remindMode=0;				//提醒方式
	public String creator="";				//创建者
	public String flag="";					//标记字段，记录如果是每周、每月和每年提醒时的标记内容
	public int isRead=0;					//是否被阅读

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	public String getRemindTime(){
		return remindTime;
	}
	public void setRemindTime(String remindTime){
		this.remindTime = remindTime;
	}
	
	public int getRemindMode(){
		return remindMode;
	}
	public void setRemindMode(int remindMode){
		this.remindMode = remindMode;
	}	
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	public int getIsRead() {
		return isRead;
	}

	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}
}
