package com.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.tools.StringUtils;
public class MyCalendar {

	private int year;		//����
	private int month;		//����
	private int day;		//����
	private boolean leap;	//����
	final static String chineseNumber[] = { "һ", "��", "��", "��", "��", "��", "��",
			"��", "��", "ʮ","ʮһ","ʮ��"};		//��������

	static SimpleDateFormat chineseDateFormat = new SimpleDateFormat(
			"yyyy��MM��dd��");		//���ڸ�ʽ
	final static long[] lunarInfo = new long[] { 0x04bd8, 0x04ae0, 0x0a570,
		0x054d5, 0x0d260, 0x0d950, 0x16554, 0x056a0, 0x09ad0, 0x055d2,
		0x04ae0, 0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540, 0x0d6a0,
		0x0ada2, 0x095b0, 0x14977, 0x04970, 0x0a4b0, 0x0b4b5, 0x06a50,
		0x06d40, 0x1ab54, 0x02b60, 0x09570, 0x052f2, 0x04970, 0x06566,
		0x0d4a0, 0x0ea50, 0x06e95, 0x05ad0, 0x02b60, 0x186e3, 0x092e0,
		0x1c8d7, 0x0c950, 0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0, 0x1a5b4,
		0x025d0, 0x092d0, 0x0d2b2, 0x0a950, 0x0b557, 0x06ca0, 0x0b550,
		0x15355, 0x04da0, 0x0a5d0, 0x14573, 0x052d0, 0x0a9a8, 0x0e950,
		0x06aa0, 0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570, 0x05260,
		0x0f263, 0x0d950, 0x05b57, 0x056a0, 0x096d0, 0x04dd5, 0x04ad0,
		0x0a4d0, 0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b5a0, 0x195a6,
		0x095b0, 0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50, 0x06d40,
		0x0af46, 0x0ab60, 0x09570, 0x04af5, 0x04970, 0x064b0, 0x074a3,
		0x0ea50, 0x06b58, 0x055c0, 0x0ab60, 0x096d5, 0x092e0, 0x0c960,
		0x0d954, 0x0d4a0, 0x0da50, 0x07552, 0x056a0, 0x0abb7, 0x025d0,
		0x092d0, 0x0cab5, 0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9,
		0x04ba0, 0x0a5b0, 0x15176, 0x052b0, 0x0a930, 0x07954, 0x06aa0,
		0x0ad50, 0x05b52, 0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260, 0x0ea65,
		0x0d530, 0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0, 0x0a4d0,
		0x1d0b6, 0x0d250, 0x0d520, 0x0dd45, 0x0b5a0, 0x056d0, 0x055b2,
		0x049b0, 0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20, 0x0ada0};	//ũ������

	String[] solarTerm = {"С��","��","����","��ˮ","����","����","����","����",
			"����","С��","â��","����","С��","����","����","����","��¶","���",
			"��¶","˪��","����","Сѩ","��ѩ","����"};		//��������������
	int[] sTermInfo ={0,21208,42467,63836,85337,107014,128867,150921,173149,195551,
			218072,240693,263343,285989,308563,331033,353350,375494,397447,419210,
			440795,462224,483532,504758};		//��������С��֮���ʱ����λΪ����
	 //����ĳ��ĵ�n������Ϊ����(��0С������)
	public int sTerm(int y,int n) {
		Calendar offDate = Calendar.getInstance();
		offDate.set(1900, 0, 6, 2, 5, 0);
		long temp = offDate.getTime().getTime();
		offDate.setTime(new Date((long) ((31556925974.7 * (y - 1900) + sTermInfo[n] * 60000L) + temp)));
		return offDate.get(Calendar.DAY_OF_MONTH);
	}
	
	// ====== ����ũ�� y���������
	final private static int yearDays(int y) {
		int i, sum = 348;
		for (i = 0x8000; i > 0x8; i >>= 1) {
			if ((lunarInfo[y - 1900] & i) != 0)
				sum += 1;
		}
		return (sum + leapDays(y));
	}
	// ====== ����ũ�� y�����µ�����
	final private static int leapDays(int y) {
		if (leapMonth(y) != 0) {
			if ((lunarInfo[y - 1900] & 0x10000) != 0)
				return 30;
			else
				return 29;
		} else
			return 0;
	}

	// ====== ����ũ�� y���ĸ��������£�1-12�е�ֵ�� , û���·��� 0
	final private static int leapMonth(int y) {
		return (int) (lunarInfo[y - 1900] & 0xf);
	}

	// ====== ����ũ�� y��m�µ�������
	final private static int monthDays(int y, int m) {
		if ((lunarInfo[y - 1900] & (0x10000 >> m)) == 0)
			return 29;
		else
			return 30;
	}

	// ====== ����ũ�� y�����Ф
	final public String animalsYear(int y) {
		final String[] Animals = new String[] { "��", "ţ", "��", "��", "��", "��",
				"��", "��", "��", "��", "��", "��" };
		return Animals[(y - 4) % 12];
	}

	// ====== ������� ���ظ�֧, 0=����
	final public String cyclical(int y) {
		int num = y - 1900 + 36;
		final String[] Gan = new String[] { "��", "��", "��", "��", "��", "��", "��",
				"��", "��", "��" };
		final String[] Zhi = new String[] { "��", "��", "��", "î", "��", "��", "��",
				"δ", "��", "��", "��", "��" };

		return (Gan[num % 10] + Zhi[num % 12]);
	}
	//======= ��������	
	public String getSFeast(int m,int d){
		String[] sFeast = {"0101 Ԫ��","0214 ���˽�","0308 ��Ů��","0312 ֲ����","0315 ������Ȩ����",
		"0401 ���˽�","0501 �Ͷ���","0504 �����","0512 ��ʿ��","0601 ��ͯ��","0701 ������","0801 ������",
		"0910 ��ʦ��","0928 ���ӵ���","1001 �����",	"1006 ���˽�","1024 ���Ϲ���",	"1224 ƽ��ҹ","1225 ʥ����"};
		StringUtils su=new StringUtils();
		String tempDate=su.formatNO(m, 2)+su.formatNO(d, 2);
		String r="";
		for(int i=0;i<sFeast.length;i++){
			if(tempDate.equals(sFeast[i].substring(0,4))){
				r=sFeast[i].substring(5);
				break;
			}
		}
		return r;
	}
	//====== ũ������
	public String getLFeast(int m,int d){
		String[] lFeast ={"0101 ����","0115 Ԫ����","0505 �����","0707 ��Ϧ���˽�","0715 ��Ԫ��",
				"0815 �����","0909 ������","1208 ���˽�","1223 С��"};
		StringUtils su=new StringUtils();
		String tempDate=su.formatNO(m, 2)+su.formatNO(d, 2);
		String r="";
		for(int i=0;i<lFeast.length;i++){
			if(tempDate.equals(lFeast[i].substring(0,4))){
				r=lFeast[i].substring(5);
				break;
			}
		}
		return r;
	}
	//===== ��ȡĸ�׽ڻ��׽����ڵ���
	public int getWeek(Calendar tempCal){
		//��ȡָ���µĵ�һ���Calendar����
		Calendar today1 = Calendar.getInstance();
		today1.set(Calendar.DAY_OF_MONTH,1 );
		today1.set(Calendar.MONTH,tempCal.get(Calendar.MONTH) );
		today1.set(Calendar.YEAR,tempCal.get(Calendar.YEAR));
		int week=today1.get(Calendar.DAY_OF_WEEK);  	//��ȡָ���µĵ�һ�����ܼ�
		int tempWeek=0;
		if(week!=1){			//�����������
			tempWeek=1;
		}else{
			tempWeek=0;
		}
		if(tempCal.get(Calendar.MONTH)==4){	//���Ϊ5�£���ʾĸ�׽�
			tempWeek+=2;
		}else{			//Ϊ���׽�
			tempWeek+=3;
		}
		return tempWeek;
	}
	//ת��ũ�������е���������
	public static String getChinaDayString(int day) {
		String chineseTen[] = { "��", "ʮ", "إ", "��" };
		int n = day % 10 == 0 ? 9 : day % 10 - 1;
		if (day > 30)
			return "";
		if (day == 10)
			return "��ʮ";
		else
			return chineseTen[day / 10] + chineseNumber[n];
	}
	/**
	 * ����y��m��d�ն�Ӧ��ũ��. yearCyl3:ũ������1864������� ? monCyl4:��1900��1��31������,������
	 * dayCyl5:��1900��1��31����������,�ټ�40 ?
	 */
	public String myCalendar(Calendar cal) {
		int yearCyl, monCyl, dayCyl;
		int leapMonth = 0;
		Date baseDate = null;
		try {
			baseDate = chineseDateFormat.parse("1900��1��31��");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// �����1900��1��31����������
		int offset = (int) ((cal.getTime().getTime() - baseDate.getTime()) / 86400000L);
		dayCyl = offset + 40;
		monCyl = 14;

		// ��offset��ȥÿũ���������
		// ���㵱����ũ���ڼ���
		// i���ս����ũ�������
		// offset�ǵ���ĵڼ���
		int iYear, daysOfYear = 0;
		for (iYear = 1900; iYear < 2050 && offset > 0; iYear++) {
			daysOfYear = yearDays(iYear);
			offset -= daysOfYear;
			monCyl += 12;
		}
		if (offset < 0) {
			offset += daysOfYear;
			iYear--;
			monCyl -= 12;
		}
		// ũ�����
		year = iYear;

		yearCyl = iYear - 1864;
		leapMonth = leapMonth(iYear); // ���ĸ���,1-12
		leap = false;

		// �õ��������offset,�����ȥÿ�£�ũ��������������������Ǳ��µĵڼ���
		int iMonth, daysOfMonth = 0;
		for (iMonth = 1; iMonth < 13 && offset > 0; iMonth++) {
			// ����
			if (leapMonth > 0 && iMonth == (leapMonth + 1) && !leap) {
				--iMonth;
				leap = true;
				daysOfMonth = leapDays(year);
			} else
				daysOfMonth = monthDays(year, iMonth);

			offset -= daysOfMonth;
			// �������
			if (leap && iMonth == (leapMonth + 1))
				leap = false;
			if (!leap)
				monCyl++;
		}
		// offsetΪ0ʱ�����Ҹղż�����·������£�ҪУ��
		if (offset == 0 && leapMonth > 0 && iMonth == leapMonth + 1) {
			if (leap) {
				leap = false;
			} else {
				leap = true;
				--iMonth;
				--monCyl;
			}
		}
		// offsetС��0ʱ��ҲҪУ��
		if (offset < 0) {
			offset += daysOfMonth;
			--iMonth;
			--monCyl;
		}
		month = iMonth;
		day = offset + 1;
		int y=cal.get(Calendar.YEAR);
		int m=cal.get(Calendar.MONTH)+1;
		int d=cal.get(Calendar.DAY_OF_MONTH);	

		String sFeast=getSFeast(m,d);				//��ȡ��������
		String lFeast=getLFeast(month,day);			//��ȡũ������
		String solarTerms="";
		//��ȡ����
		if (d == sTerm(y, (m - 1) * 2))
			solarTerms = solarTerm[(m - 1) * 2];
		else if (d == sTerm(y, (m - 1) * 2 + 1))
			solarTerms = solarTerm[(m - 1) * 2 + 1];
		String tempStr="";
		   if(!"".equals(sFeast)){
			   tempStr=sFeast;
		   }
		   if(!"".equals(lFeast)){
			   if("".equals(tempStr)){
				   tempStr=lFeast;
			   }else{
				   tempStr=tempStr+"/"+lFeast;
			   }
		   }
		   if(!"".equals(solarTerms)){
			   if("".equals(tempStr)){
				   tempStr=solarTerms;
			   }else{
				   tempStr=tempStr+"/"+solarTerms;
			   }
		   }
		//�ж��Ƿ�Ϊĸ�׽�
		if(cal.get(Calendar.MONTH)==4){    
			if(cal.get(Calendar.WEEK_OF_MONTH)==getWeek(cal) && cal.get(Calendar.DAY_OF_WEEK)==1){
				if(!"".equals(tempStr)){
					tempStr=tempStr+"/"+"ĸ�׽�";
				}else{
					tempStr="ĸ�׽�";
				}
			}
		}
		//�ж��Ƿ�Ϊ���׽�
		if(cal.get(Calendar.MONTH)==5){
			if((cal.get(Calendar.WEEK_OF_MONTH)==getWeek(cal)) && (cal.get(Calendar.DAY_OF_WEEK)==1)){
			System.out.println("����"+getWeek(cal));
				if(!"".equals(tempStr)){
					tempStr=tempStr+"/"+"���׽�";
				}else{
					tempStr="���׽�";
				}
			}
		}
		//�ж��Ƿ�Ϊ����
		if(!"".equals(tempStr)){
			return "<font color='red'>"+tempStr+"</font>";
		}else{
			if(day==1){
				return  (leap ? "��" : "") + chineseNumber[month - 1] + "��";
			}else{
				return getChinaDayString(day);
			}
		}
	}
}
