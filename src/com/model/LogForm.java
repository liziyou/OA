package com.model;

public class LogForm {
	public int logid = 0;						// 编号
	public String logcreater = "";				// 作者
	public String logcon = ""; 			// 日志内容
	public String logtitle="";			//日志标题
	public String logtime="";				//创建时间
	public int getLogid() {
		return logid;
	}
	public void setLogid(int logid) {
		this.logid = logid;
	}
	public String getLogcreater() {
		return logcreater;
	}
	public void setLogcreater(String logcreater) {
		this.logcreater = logcreater;
	}
	public String getLogcon() {
		return logcon;
	}
	public void setLogcon(String logcon) {
		this.logcon = logcon;
	}
	public String getLogtitle() {
		return logtitle;
	}
	public void setLogtitle(String logtitle) {
		this.logtitle = logtitle;
	}
	public String getLogtime() {
		return logtime;
	}
	public void setLogtime(String logtime) {
		this.logtime = logtime;
	}
	
					

}
