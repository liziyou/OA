package com.tools;

import java.text.NumberFormat;

public class StringUtils {
	//��ISO-8859-1������ֶδ�ת��ΪGBK����
	public String toGBK(String strvalue) {
		try {
			if (strvalue == null) {			//������strvalueΪnullʱ
				return "";					//���ؿյ��ַ���
			} else {
				strvalue = new String(strvalue.getBytes("ISO-8859-1"), "GBK");//���ַ���ת��ΪGBK����
				return strvalue;			//����ת������������strvalue
			}
		} catch (Exception e) {
			return "";
		}
	}

	// ��������ַ�������һ�α���ת������ֹSQLע��
	public String StringtoSql(String str) {
		if (str == null) {				//������strΪnullʱ
			return "";					//���ؿյ��ַ���
		} else {
			try {
				str = str.trim().replace('\'', (char) 32);	//��'��ת����Ϊ�ո�
			} catch (Exception e) {
				return "";
			}
		}
		return str;
	}
	//���������ָ�ʽΪָ������
	   public String formatNO(int str, int length) {
	        float ver = Float.parseFloat(System.getProperty(  //��ȡJDK�İ汾
	                "java.specification.version"));
	        String laststr = "";
	        if (ver < 1.5) {  //JDK1.5���°汾ִ�е����
	            try {
	                NumberFormat formater = NumberFormat.getNumberInstance();
	                formater.setMinimumIntegerDigits(length);
	                laststr = formater.format(str).toString().replace(",", "");
	            } catch (Exception e) {
	                System.out.println("��ʽ���ַ���ʱ�Ĵ�����Ϣ��" + e.getMessage());	//����쳣��Ϣ
	            }
	        } else {  //JDK1.5�汾ִ�е����
	            Integer[] arr=new Integer[1];
	            arr[0]=new Integer(str);
	        	laststr = String.format("%0" + length + "d", arr);
	        }
	        return laststr;
	    }
}
