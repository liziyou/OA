package com.tools;

import java.util.Calendar;

public class TimeUtils {
	//��ȡָ�����������ڼ�
	public int getWeek(int y,int m,int d){
		Calendar today1 = Calendar.getInstance();	//������ʵ����Calendar����
		today1.set(Calendar.DAY_OF_MONTH,d );		//��������Calendar�����е�������
		today1.set(Calendar.MONTH,m );				//��������Calendar�����е�������
		today1.set(Calendar.YEAR,y);				//��������Calendar�����е�������
		int week=today1.get(Calendar.DAY_OF_WEEK);	//��ȡָ�����ڵ�����
		//System.out.println(y+"-"+m+"-"+d+"������"+week);
		return week;
	}
}
