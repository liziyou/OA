package com.model;

public class MemoForm {
	public int id = 0;						// ���
	public String title = "";				// ����
	public String content = ""; 			// ����
	public String remindTime="";			//����ʱ��
	public int remindMode=0;				//���ѷ�ʽ
	public String creator="";				//������
	public String flag="";					//����ֶΣ���¼�����ÿ�ܡ�ÿ�º�ÿ������ʱ�ı������
	public int isRead=0;					//�Ƿ��Ķ�

	
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
