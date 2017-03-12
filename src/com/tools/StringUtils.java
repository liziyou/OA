package com.tools;

import java.text.NumberFormat;

public class StringUtils {
	//将ISO-8859-1编码的字段串转换为GBK编码
	public String toGBK(String strvalue) {
		try {
			if (strvalue == null) {			//当变量strvalue为null时
				return "";					//返回空的字符串
			} else {
				strvalue = new String(strvalue.getBytes("ISO-8859-1"), "GBK");//将字符串转换为GBK编码
				return strvalue;			//返回转换后的输入变量strvalue
			}
		} catch (Exception e) {
			return "";
		}
	}

	// 对输入的字符串进行一次编码转换，防止SQL注入
	public String StringtoSql(String str) {
		if (str == null) {				//当变量str为null时
			return "";					//返回空的字符串
		} else {
			try {
				str = str.trim().replace('\'', (char) 32);	//将'号转换化为空格
			} catch (Exception e) {
				return "";
			}
		}
		return str;
	}
	//将整型数字格式为指定长度
	   public String formatNO(int str, int length) {
	        float ver = Float.parseFloat(System.getProperty(  //获取JDK的版本
	                "java.specification.version"));
	        String laststr = "";
	        if (ver < 1.5) {  //JDK1.5以下版本执行的语句
	            try {
	                NumberFormat formater = NumberFormat.getNumberInstance();
	                formater.setMinimumIntegerDigits(length);
	                laststr = formater.format(str).toString().replace(",", "");
	            } catch (Exception e) {
	                System.out.println("格式化字符串时的错误信息：" + e.getMessage());	//输出异常信息
	            }
	        } else {  //JDK1.5版本执行的语句
	            Integer[] arr=new Integer[1];
	            arr[0]=new Integer(str);
	        	laststr = String.format("%0" + length + "d", arr);
	        }
	        return laststr;
	    }
}
