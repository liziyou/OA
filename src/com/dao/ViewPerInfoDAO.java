package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.model.ProjectForm;
import com.model.ViewPerInfoForm;
import com.tools.*;


public class ViewPerInfoDAO {
	private static ConnDB conn;
	private TimeUtils tu;
	private StringUtils su;

	public ViewPerInfoDAO() {
		conn = new ConnDB();
		tu=new TimeUtils();
		su=new StringUtils();
	}
	
	//返回用户信息
	public static ArrayList<ViewPerInfoForm> getViewPerInfotList(){
		ArrayList<ViewPerInfoForm> list=new ArrayList<ViewPerInfoForm>();
		String sql="SELECT * FROM tb_perinfo";
		ResultSet rs=conn.executeQuery(sql);
		try{
			while(rs.next()){
				ViewPerInfoForm bi=new ViewPerInfoForm();
				bi.setPersonid(rs.getInt(1));
				bi.setPername(rs.getString(2));
				bi.setSex(rs.getString(3));
				bi.setDataofd(rs.getString(4));
				bi.setPhone(rs.getString(5));
				bi.setLandline(rs.getString(6));
				bi.setPosition(rs.getString(7));
				bi.setDuty(rs.getString(8));
				bi.setEntrytime(rs.getString(9));
				bi.setDepartime(rs.getString(10));
				bi.setPower(rs.getString(11));
				bi.setPpwd(rs.getString(12));
				list.add(bi);	
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
			conn.close();
			return list;
		}
	//指定条件用户信息
	public static List<ViewPerInfoForm> getpersontByPjid(String key){
		List<ViewPerInfoForm> list = new ArrayList<ViewPerInfoForm>();
		String sql="SELECT * FROM tb_perinfo WHERE (pername like '%"+key+"%' OR sex like '%"+key+"%' OR dataofd like '%"+key+"%' OR phone like '%"+key+"%' OR landline like '%"+key+"%' OR position like '%"+key+"%' OR duty like '%"+key+"%' OR entrytime like '%"+key+"%' OR departime like '%"+key+"%')";
		ResultSet rs = conn.executeQuery(sql);
		try {
			while (rs.next()) {
				ViewPerInfoForm bi=new ViewPerInfoForm();
				bi.setPersonid(rs.getInt(1));
				bi.setPername(rs.getString(2));
				bi.setSex(rs.getString(3));
				bi.setDataofd(rs.getString(4));
				bi.setPhone(rs.getString(5));
				bi.setLandline(rs.getString(6));
				bi.setPosition(rs.getString(7));
				bi.setDuty(rs.getString(8));
				bi.setEntrytime(rs.getString(9));
				list.add(bi);
				
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		conn.close();
		return list;
	}
	
	//修改某个用户记录的方法
	  public static int personupdate(int personid,String pername,String sex,String dataofd,String phone,String landline,String position,String duty,String entrytime,String departime,String power,String ppwd){
		  int result=0;
		  String sql="UPDATE tb_perinfo SET pername='"+pername+"',sex='"+sex+"',dataofd='"+dataofd+"',phone='"+phone+"',landline='"+landline+"',position='"+position+"',duty='"+duty+"',entrytime='"+entrytime+"',departime='"+departime+"',power='"+power+"',ppwd='"+ppwd+"' WHERE personid="+personid;  		
		  result=conn.executeUpdate(sql);
		  return result;  
	  }
	  

	//添加用户信息的方法
	  public static int addperson(String pername,String sex,String dataofd,String phone,String landline,String position,String duty,String entrytime,String departime,String power,String ppwd){
		  int result=0;
		  String sql="INSERT INTO tb_perinfo (pername,sex,dataofd,phone,landline,position,duty,entrytime,departime,power,ppwd) VALUES('"+pername+"','"+sex+"','"+dataofd+"','"+phone+"','"+landline+"','"+position+"','"+duty+"','"+entrytime+"','"+departime+"','"+power+"','"+ppwd+"')";
		  result=conn.executeUpdate(sql);
		  return result;
		  
	  }
	}




