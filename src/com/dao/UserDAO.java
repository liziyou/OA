package com.dao;

import com.model.UserForm;

import com.tools.ConnDB;

import java.sql.*;

public class UserDAO {
	private ConnDB conn;

	public UserDAO() {
		conn = new ConnDB();
	}

	//��֤�û��ķ���������Ϊ1��ʾ��¼�ɹ��������ʾ��¼ʧ��
	public int login(UserForm m){
        int flag = 0;
        String sql = "SELECT * FROM tb_user where userName='" +
                     m.getUserName() + "'";
        ResultSet rs = conn.executeQuery(sql);
        try {
            if (rs.next()) {
                String pwd = m.getPwd();
                System.out.println("���룺"+pwd);
                if (pwd.equals(rs.getString(3))) {
                    flag = 1;
                    rs.last();
                    int rowSum = rs.getRow();
                    rs.first();
                    if (rowSum != 1) {
                        flag = 0;
                        System.out.print("��ȡ��row��ֵ��" + sql + rowSum);
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
