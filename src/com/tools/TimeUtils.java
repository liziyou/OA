package com.tools;

import java.util.Calendar;

public class TimeUtils {
	//获取指定日期是星期几
	public int getWeek(int y,int m,int d){
		Calendar today1 = Calendar.getInstance();	//创建并实例化Calendar对象
		today1.set(Calendar.DAY_OF_MONTH,d );		//重新设置Calendar对象中的日属性
		today1.set(Calendar.MONTH,m );				//重新设置Calendar对象中的月属性
		today1.set(Calendar.YEAR,y);				//重新设置Calendar对象中的年属性
		int week=today1.get(Calendar.DAY_OF_WEEK);	//获取指定日期的星期
		//System.out.println(y+"-"+m+"-"+d+"是星期"+week);
		return week;
	}
}
