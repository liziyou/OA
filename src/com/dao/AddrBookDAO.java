package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.model.AddrBookForm;
import com.model.MemoForm;
import com.model.ProjectForm;
import com.tools.*;

public class AddrBookDAO {
	private static ConnDB conn;
	private TimeUtils tu;
	private StringUtils su;

	public AddrBookDAO() {
		conn = new ConnDB();
		tu=new TimeUtils();
		su=new StringUtils();
	}
	//查询通讯录
	public static ArrayList<AddrBookForm> getBookList(String key){
		ArrayList<AddrBookForm> list=new ArrayList<AddrBookForm>();
		String sql="SELECT * FROM tb_addresses";
		ResultSet rs=conn.executeQuery(sql);
		try{
			while(rs.next()){
				AddrBookForm f=new AddrBookForm();
				f.setAddrid(rs.getInt(1));
				f.setAddrname(rs.getString(2));
				f.setAddrsex(rs.getString(3));
				f.setE_mail(rs.getString(4));
				f.setAddress(rs.getString(5));
				f.setPhone(rs.getString(6));
				f.setLandine(rs.getString(7));
				list.add(f);
			}		
		}catch(SQLException e){
			e.printStackTrace();
		}
		conn.close();
		return list;
	}


	
	//指定条件查询个人通讯录
		public List<AddrBookForm>searchbook(String key){
			List<AddrBookForm>list=new ArrayList<AddrBookForm>();
			String sql="SELECT * FROM tb_addresses WHERE (addrname like '%"+key+"%' OR addrsex like '%"+key+"%' OR e_mail like '%"+key+"%' OR address like '%"+key+"%' OR phone like '%"+key+"%' OR landline like '%"+key+"%')";
			ResultSet rs=conn.executeQuery(sql);
			try{
				while(rs.next()){
					AddrBookForm f=new AddrBookForm();
					f.setAddrid(rs.getInt(1));
					f.setAddrname(rs.getString(2));
					f.setAddrsex(rs.getString(3));
					f.setE_mail(rs.getString(4));
					f.setAddress(rs.getString(5));
					f.setPhone(rs.getString(6));
					f.setLandine(rs.getString(7));
					list.add(f);
				}
			}catch(SQLException e){
				e.printStackTrace();
			}
			conn.close();
			return list;
			
		}
}


