package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.model.MemoForm;
import com.tools.*;

public class MemoDAO {
	private ConnDB conn;
	private TimeUtils tu;
	private StringUtils su;

	public MemoDAO() {
		conn = new ConnDB();
		tu=new TimeUtils();
		su=new StringUtils();
	}
	public int insert(MemoForm f){
		int flag=0;
		String remindTime=f.getRemindTime();
		remindTime=String.valueOf(Integer.parseInt(remindTime.substring(0,remindTime.indexOf(":"))))+remindTime.substring(remindTime.indexOf(":"),remindTime.length());
		f.setRemindTime(remindTime);
		String sql1="SELECT * FROM tb_memo WHERE title='"+f.getTitle()+"' AND remindMode="+f.getRemindMode()+" AND remindTime='"+f.getRemindTime()+"'AND flag='"+f.getFlag()+"' AND creator='"+f.getCreator()+"'";
		ResultSet rs=conn.executeQuery(sql1);
		//System.out.println("判断是否添加SQL:"+sql1);

		try {
			if(rs.next()){
				flag=2;		//表示该备忘信息已经被添加
			}else{			//添加备忘信息
				String sql="";
				try {
					sql = "INSERT INTO tb_memo (title,remindMode,remindTime,content,creator,flag) VALUES('"+f.getTitle()+"',"+f.getRemindMode()+",'"+f.getRemindTime()+"','"+f.getContent()+"','"+f.getCreator()+"','"+f.getFlag()+"')";
					flag = conn.executeUpdate(sql);
					System.out.println("添加时的SQL："+sql);
				} catch (RuntimeException e) {
					e.printStackTrace();
					System.out.println("出错的SQL语句："+sql);
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		conn.close();		//关闭数据库连接
		return flag;
	}
	//判断是否有备忘信息
	public boolean isMemo(String user,int y,int m,int d ,int week){
		Boolean have=false;
		String sql="SELECT * FROM tb_memo WHERE creator='"+user+"' AND (remindMode=4 AND flag='"+m+"-"+d+"' or " +
		" remindMode=3 AND patindex('%"+su.formatNO(d,2)+",%',flag) <>0 or " +
				"remindMode=2 AND patindex('%"+week+",%',flag) <>0 or remindMode=0 AND flag='"+y+"-"+m+"-"+d+"')";
		ResultSet rs=conn.executeQuery(sql);
		try {
			if(rs.next()){
				have=true;		//表示已经添加备忘录
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		conn.close();		//关闭数据库连接
		return have;
	}
	//查询全部备忘信息
	public List<MemoForm> query(String user) {
		List<MemoForm> list = new ArrayList<MemoForm>();
		String sql ="SELECT * FROM tb_memo WHERE creator='"+user+"'";
		ResultSet rs = conn.executeQuery(sql);
		try {
			while (rs.next()) {
				MemoForm f = new MemoForm();
				f.setId(rs.getInt(1));
				f.setTitle(rs.getString(2));
				f.setContent(rs.getString(3));
				f.setRemindTime(rs.getString(4));
				f.setRemindMode(rs.getInt(5));
				f.setCreator(rs.getString(6));
				f.setFlag(rs.getString(7));
				f.setIsRead(rs.getInt(8));
				list.add(f);		 // 将备忘录信息保存到List集合中
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conn.close();
		return list;
	}
	//查询指定日期的备忘信息
	public List<MemoForm> query(String user,int y,int m,int d,int week) {
		List<MemoForm> list = new ArrayList<MemoForm>();
		//String sql = "SELECT * FROM tb_memo WHERE creator='"+user+"' AND remindMode=0 AND flag='"+y+"-"+m+"-"+d+"'";
		String sql="SELECT * FROM tb_memo WHERE creator='"+user+"' AND (remindMode=1 or remindMode=4 AND flag='"+m+"-"+d+"' or " +
				" remindMode=3 AND patindex('%"+su.formatNO(d,2)+",%',flag) <>0 or " +
				"remindMode=2 AND patindex('%"+week+",%',flag) <>0 or" +
				" remindMode=0 AND flag='"+y+"-"+m+"-"+d+"')";
		System.out.println("查询备忘录的SQL语句："+sql);
		ResultSet rs = conn.executeQuery(sql);
		try {
			while (rs.next()) {
				MemoForm f = new MemoForm();
				f.setId(rs.getInt(1));
				f.setTitle(rs.getString(2));
				f.setContent(rs.getString(3));
				f.setRemindTime(rs.getString(4));
				f.setRemindMode(rs.getInt(5));
				f.setCreator(rs.getString(6));
				f.setFlag(rs.getString(7));
				f.setIsRead(rs.getInt(8));
				list.add(f);		 // 将备忘录信息保存到List集合中
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conn.close();
		return list;
	}
	//指定的条件查询备忘信息
	public List<MemoForm> query(String user,String key) {
		List<MemoForm> list = new ArrayList<MemoForm>();
		String sql ="SELECT * FROM tb_memo WHERE creator='"+user+"' AND (title like '%"+key+"%' OR content like '%"+key+"%')";
		ResultSet rs = conn.executeQuery(sql);
		try {
			while (rs.next()) {
				MemoForm f = new MemoForm();
				f.setId(rs.getInt(1));
				f.setTitle(rs.getString(2));
				f.setContent(rs.getString(3));
				f.setRemindTime(rs.getString(4));
				f.setRemindMode(rs.getInt(5));
				f.setCreator(rs.getString(6));
				f.setFlag(rs.getString(7));
				f.setIsRead(rs.getInt(8));
				list.add(f);		 // 将备忘录信息保存到List集合中
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conn.close();
		return list;
	}
//删除备忘信息
	public int del(int id) {
		int rtn=0;
		try{
			String sql = "DELETE FROM tb_memo WHERE id=" + id + "";
			System.out.println("删除备忘信息的SQL语句："+sql);
			rtn=conn.executeUpdate(sql);
		}catch(Exception e){
			System.out.println("删除备忘信息的错误提示信息："+e.getMessage());
			rtn=0;
		}
		conn.close();
		return rtn;
	}
	//查询到期提醒的备忘录
	public String getRemind(String user) {
		//获取当前的年、月、日、星期、时、分
		Calendar c = Calendar.getInstance();
		int y=c.get(Calendar.YEAR);
		int m=c.get(Calendar.MONTH)+1;
		int d=c.get(Calendar.DAY_OF_MONTH);	
		int week=c.get(Calendar.DAY_OF_WEEK);
		int h=c.get(Calendar.HOUR_OF_DAY);
		String M=su.formatNO(c.get(Calendar.MINUTE),2);
		
		String sql="SELECT * FROM tb_memo WHERE isRead=0 AND creator='"+user+"' AND remindTime ='"+h+":"+M+"' AND (remindMode=1 or remindMode=4 AND flag='"+m+"-"+d+"' or " +
				" remindMode=3 AND patindex('%"+su.formatNO(d,2)+",%',flag) <>0 or " +
				"remindMode=2 AND patindex('%"+week+",%',flag) <>0 or" +
				" remindMode=0 AND flag='"+y+"-"+m+"-"+d+"')";
		//System.out.println("查询备忘录的SQL语句："+sql);
		ResultSet rs = conn.executeQuery(sql);
		String temp="";
		String id="";
		int i=1;
		try {
			while (rs.next()) {
				id+=String.valueOf(rs.getInt(1))+",";
				temp+="（"+i+"）"+rs.getString(2)+"<br>";
				i++;
			}
			if(!"".equals(temp) || temp!=""){
				//temp="<a href=\"#\" onclick=\"window.open('MemoServlet?action=showRemindMsg&id="+id+"','','width=400,height=300')\">"+temp+"11</a>";
				temp+="["+y+"-"+m+"-"+d+"&nbsp;"+h+":"+M+"]["+id.substring(0,id.length()-1)+"]";
			}
			//System.out.println("temp:"+temp);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conn.close();
		return temp;
	}
	//查询到期提醒的详细备忘信息
	public List<MemoForm> getRemindDetail(String id){
		List<MemoForm> list = new ArrayList<MemoForm>();
		String sql="SELECT * FROM tb_memo WHERE id IN ("+id+") AND isRead=0";
		//System.out.println("查询备忘录的SQL语句："+sql);
		ResultSet rs = conn.executeQuery(sql);
		try {
			while (rs.next()) {
				MemoForm f = new MemoForm();
				f.setId(rs.getInt(1));
				f.setTitle(rs.getString(2));
				f.setContent(rs.getString(3));
				f.setRemindTime(rs.getString(4));
				f.setRemindMode(rs.getInt(5));
				f.setCreator(rs.getString(6));
				f.setFlag(rs.getString(7));
				f.setIsRead(rs.getInt(8));
				list.add(f);		 // 将备忘录信息保存到List集合中
			}
			String sql_u="UPDATE tb_memo SET isRead=1 WHERE id IN("+id+")";
			conn.executeUpdate(sql_u);		//标记为已读
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conn.close();
		return list;		
	}
	//将isRead标记修改为0
	public int updateIsRead(String id){
		Calendar c = Calendar.getInstance();
		int h=c.get(Calendar.HOUR_OF_DAY);
		int M=c.get(Calendar.MINUTE);		
		String sql="UPDATE tb_memo SET isRead=0 WHERE RemindMode >0 AND id IN("+id+") AND remindTime <>'"+h+":"+M+"'";
		//System.out.println("将isRead标记修改为0的SQL语句："+sql);
		int r=conn.executeUpdate(sql);		//标记为已读
		return r;
	}
}
