package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.model.LogForm;
import com.model.MemoForm;
import com.model.ProjectForm;
import com.tools.*;

public class LogDAO {
	private static ConnDB conn;
	private TimeUtils tu;
	private StringUtils su;

	public LogDAO() {
		conn = new ConnDB();
		tu=new TimeUtils();
		su=new StringUtils();
	}
	
	//��ѯȫ��������־��Ϣ
	public static List<LogForm> loglist() {
		List<LogForm> list = new ArrayList<LogForm>();
		String sql ="SELECT * FROM tb_log";
		ResultSet rs = conn.executeQuery(sql);
		try {
			while (rs.next()) {
				LogForm f = new LogForm();
				f.setLogid(rs.getInt(1));
				f.setLogcreater(rs.getString(2));
				f.setLogcon(rs.getString(3));
				f.setLogtitle(rs.getString(4));
				f.setLogtime(rs.getString(5));
				list.add(f);		 // ��������־��Ϣ���浽List������
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conn.close();
		return list;
	}





	public static int insert(LogForm f) {
		// TODO Auto-generated method stub
		int flag=0;
		String sql1="SELECT * FROM tb_log WHERE logcreater='"+f.getLogcreater()+"' AND logcon='"+f.getLogcon()+"' AND logtitle='"+f.getLogtitle()+"' AND logtime='"+f.getLogtime()+"' ";
		ResultSet rs=conn.executeQuery(sql1);
		try {
			if(rs.next()){
				flag=2;		//��ʾ�ñ�����Ϣ�Ѿ������
			}else{			//��ӱ�����Ϣ
				String sql="";
				try {
					sql="INSERT INTO tb_log (logcreater,logcon,logtitle,logtime) VALUES('"+f.getLogcreater()+"','"+f.getLogcon()+"','"+f.getLogtitle()+"','"+f.getLogtime()+"')";
					flag = conn.executeUpdate(sql);
					System.out.println("���ʱ��SQL��"+sql);
				} catch (RuntimeException e) {
					e.printStackTrace();
					System.out.println("�����SQL��䣺"+sql);
				}
			}
		}catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		conn.close();		//�ر����ݿ�����
			return flag;
			
	}
	//ָ��������ѯ��־��Ϣ
	public static List<LogForm> searchlog(String key) {
		// TODO Auto-generated method stub
		List<LogForm> list = new ArrayList<LogForm>();
		String sql="SELECT * FROM tb_log WHERE (logcreater like '%"+key+"%' OR logcon like '%"+key+"%' OR logtitle like '%"+key+"%' OR logtime like '%"+key+"%')";
		ResultSet rs = conn.executeQuery(sql);
		try{
			while (rs.next()) {
				LogForm bi=new LogForm();
				bi.setLogid(rs.getInt(1));
				bi.setLogcreater(rs.getString(2));
				bi.setLogcon(rs.getString(3));
				bi.setLogtitle(rs.getString(4));
				bi.setLogtime(rs.getString(5));
				list.add(bi);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		conn.close();
		return list;
	}
}

