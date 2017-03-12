package com.dao;
import com.tools.ConnDB;
import com.tools.StringUtils;
import com.tools.TimeUtils;

import java.sql.*;
import java.util.*;

import com.model.MemoForm;
import com.model.ProjectForm;

public class ProjectDAO {
	private static ConnDB conn;
	private TimeUtils tu;
	private StringUtils su;

	public ProjectDAO() {
		conn = new ConnDB();
		tu=new TimeUtils();
		su=new StringUtils();
	}

//�����ҵ���Ŀ�ķ���
public static ArrayList<ProjectForm> getProjectList(){
	ArrayList<ProjectForm> list=new ArrayList<ProjectForm>();
	String sql="SELECT * FROM tb_project";
	ResultSet rs=conn.executeQuery(sql);
	try{
		while(rs.next()){
			ProjectForm bi=new ProjectForm();
			bi.setPjid(rs.getInt(1));
			bi.setPjname(rs.getString(2));
			bi.setPjleader(rs.getString(3));
			bi.setPjstt(rs.getString(4));
			bi.setPjtad(rs.getString(5));
			bi.setRemark(rs.getString(6));
			list.add(bi);	
		}
		
	}catch(SQLException e){
		e.printStackTrace();
	}
		conn.close();
		return list;
	}



//ָ��������ѯ��Ŀ��Ϣ
public static List<ProjectForm> getProjectByPjid(String key){
	List<ProjectForm> list = new ArrayList<ProjectForm>();
	String sql="SELECT * FROM tb_project WHERE (pjname like '%"+key+"%' OR pjleader like '%"+key+"%' OR pjstt like '%"+key+"%' OR pjtad like '%"+key+"%' OR remark like '%"+key+"%')";
	ResultSet rs = conn.executeQuery(sql);
	try {
		while (rs.next()) {
			ProjectForm bi=new ProjectForm();
			bi.setPjid(rs.getInt(1));
			bi.setPjname(rs.getString(2));
			bi.setPjleader(rs.getString(3));
			bi.setPjstt(rs.getString(4));
			bi.setPjtad(rs.getString(5));
			bi.setRemark(rs.getString(6));
			list.add(bi);
			
		}
	}catch (SQLException e) {
		e.printStackTrace();
	}
	
	conn.close();
	return list;
}

	//�޸�ĳ����Ŀ��¼�ķ���
  public static int updateProject(int pjid,String pjname,String pjleader,String pjstt,String pjtad,String remark){
	  int result=0;
	  String sql="UPDATE tb_project SET pjname='"+pjname+"',pjleader='"+pjleader+"',pjstt='"+pjstt+"',pjtad='"+pjtad+"',remark='"+remark+"'WHERE pjid="+pjid;
	  result=conn.executeUpdate(sql);
	  return result;  
  }
  //ɾ��ĳ����Ŀ��Ϣ�ķ���
  public static int deleteProject(String pjid){
	  int r=0;
	 try{
		 String sql = "DELETE FROM tb_project WHERE pjid=" + pjid + "";
		 System.out.println("ɾ����Ŀ��Ϣ��SQL������"+sql);
		 r=conn.executeUpdate(sql);
	 }catch(Exception e){
		 System.out.println("ɾ����Ŀ��Ϣ�Ĵ�����ʾ��Ϣ��"+e.getMessage());
		 r=0;
	 }
	 conn.close();
	 return r;  
  }

  
  
  //�����Ŀ��Ϣ�ķ���
  public static int addProject(String pjname,String pjleader,String pjstt,String pjtad,String remark){
	  int result=0;
	  String sql="INSERT INTO tb_project (pjname,pjleader,pjstt,pjtad,remark) VALUES('"+pjname+"','"+pjleader+"','"+pjstt+"','"+pjtad+"','"+remark+"')";
	  result=conn.executeUpdate(sql);
	  return result;
	  
  }
}

