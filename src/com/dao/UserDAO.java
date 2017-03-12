package com.dao;

import com.model.UserForm;

import com.tools.ConnDB;

import java.sql.*;

public class UserDAO {
	private ConnDB conn;

	public UserDAO() {
		conn = new ConnDB();
	}

	//验证用户的方法，返回为1表示登录成功，否则表示登录失败
	public int login(UserForm m){
        int flag = 0;
        String sql = "SELECT * FROM tb_user where userName='" +
                     m.getUserName() + "'";
        ResultSet rs = conn.executeQuery(sql);
        try {
            if (rs.next()) {
                String pwd = m.getPwd();
                System.out.println("密码："+pwd);
                if (pwd.equals(rs.getString(3))) {
                    flag = 1;
                    rs.last();
                    int rowSum = rs.getRow();
                    rs.first();
                    if (rowSum != 1) {
                        flag = 0;
                        System.out.print("获取的row的值：" + sql + rowSum);
                    }
                } else {
                    flag = 0;
                }
            }else{
                flag = 0;
            }
        } catch (SQLException ex) {
            flag = 0;
        }
        return flag;
	}
}
