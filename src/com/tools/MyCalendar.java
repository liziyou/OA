package com.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.tools.StringUtils;
public class MyCalendar {

	private int year;		//历年
	private int month;		//历月
	private int day;		//历日
	private boolean leap;	//闰年
	final static String chineseNumber[] = { "一", "二", "三", "四", "五", "六", "七",
			"八", "九", "十","十一","十二"};		//中文数字

	static SimpleDateFormat chineseDateFormat = new SimpleDateFormat(
			"yyyy年MM月dd日");		//日期格式
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
		0x049b0, 0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20, 0x0ada0};	//农历日期

	String[] solarTerm = {"小寒","大寒","立春","雨水","惊蛰","春分","清明","谷雨",
			"立夏","小满","芒种","夏至","小暑","大暑","立秋","处暑","白露","秋分",
			"寒露","霜降","立冬","小雪","大雪","冬至"};		//节气的中文名称
	int[] sTermInfo ={0,21208,42467,63836,85337,107014,128867,150921,173149,195551,
			218072,240693,263343,285989,308563,331033,353350,375494,397447,419210,
			440795,462224,483532,504758};		//各节气与小寒之间的时间差，单位为分钟
	 //返回某年的第n个节气为几日(从0小寒起算)
	public int sTerm(int y,int n) {
		Calendar offDate = Calendar.getInstance();
		offDate.set(1900, 0, 6, 2, 5, 0);
		long temp = offDate.getTime().getTime();
		offDate.setTime(new Date((long) ((31556925974.7 * (y - 1900) + sTermInfo[n] * 60000L) + temp)));
		return offDate.get(Calendar.DAY_OF_MONTH);
	}
	
	// ====== 返回农历 y年的总天数
	final private static int yearDays(int y) {
		int i, sum = 348;
		for (i = 0x8000; i > 0x8; i >>= 1) {
			if ((lunarInfo[y - 1900] & i) != 0)
				sum += 1;
		}
		return (sum + leapDays(y));
	}
	// ====== 返回农历 y年闰月的天数
	final private static int leapDays(int y) {
		if (leapMonth(y) != 0) {
			if ((lunarInfo[y - 1900] & 0x10000) != 0)
				return 30;
			else
				return 29;
		} else
			return 0;
	}

	// ====== 返回农历 y年哪个月是闰月（1-12中的值） , 没闰月返回 0
	final private static int leapMonth(int y) {
		return (int) (lunarInfo[y - 1900] & 0xf);
	}

	// ====== 返回农历 y年m月的总天数
	final private static int monthDays(int y, int m) {
		if ((lunarInfo[y - 1900] & (0x10000 >> m)) == 0)
			return 29;
		else
			return 30;
	}

	// ====== 返回农历 y年的生肖
	final public String animalsYear(int y) {
		final String[] Animals = new String[] { "鼠", "牛", "虎", "兔", "龙", "蛇",
				"马", "羊", "猴", "鸡", "狗", "猪" };
		return Animals[(y - 4) % 12];
	}

	// ====== 输入年份 返回干支, 0=甲子
	final public String cyclical(int y) {
		int num = y - 1900 + 36;
		final String[] Gan = new String[] { "甲", "乙", "丙", "丁", "戊", "己", "庚",
				"辛", "壬", "癸" };
		final String[] Zhi = new String[] { "子", "丑", "寅", "卯", "辰", "巳", "午",
				"未", "申", "酉", "戌", "亥" };

		return (Gan[num % 10] + Zhi[num % 12]);
	}
	//======= 公历节日	
	public String getSFeast(int m,int d){
		String[] sFeast = {"0101 元旦","0214 情人节","0308 妇女节","0312 植树节","0315 消费者权益日",
		"0401 愚人节","0501 劳动节","0504 青年节","0512 护士节","0601 儿童节","0701 建党节","0801 建军节",
		"0910 教师节","0928 孔子诞辰","1001 国庆节",	"1006 老人节","1024 联合国日",	"1224 平安夜","1225 圣诞节"};
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
	//====== 农历节日
	public String getLFeast(int m,int d){
		String[] lFeast ={"0101 春节","0115 元宵节","0505 端午节","0707 七夕情人节","0715 中元节",
				"0815 中秋节","0909 重阳节","1208 腊八节","1223 小年"};
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
	//===== 获取母亲节或父亲节所在的周
	public int getWeek(Calendar tempCal){
		//获取指定月的第一天的Calendar对象
		Calendar today1 = Calendar.getInstance();
		today1.set(Calendar.DAY_OF_MONTH,1 );
		today1.set(Calendar.MONTH,tempCal.get(Calendar.MONTH) );
		today1.set(Calendar.YEAR,tempCal.get(Calendar.YEAR));
		int week=today1.get(Calendar.DAY_OF_WEEK);  	//获取指定月的第一天是周几
		int tempWeek=0;
		if(week!=1){			//如果不是周日
			tempWeek=1;
		}else{
			tempWeek=0;
		}
		if(tempCal.get(Calendar.MONTH)==4){	//如果为5月，表示母亲节
			tempWeek+=2;
		}else{			//为父亲节
			tempWeek+=3;
		}
		return tempWeek;
	}
	//转换农历日期中的中文日期
	public static String getChinaDayString(int day) {
		String chineseTen[] = { "初", "十", "廿", "三" };
		int n = day % 10 == 0 ? 9 : day % 10 - 1;
		if (day > 30)
			return "";
		if (day == 10)
			return "初十";
		else
			return chineseTen[day / 10] + chineseNumber[n];
	}
	/**
	 * 返回y年m月d日对应的农历. yearCyl3:农历年与1864的相差数 ? monCyl4:从1900年1月31日以来,闰月数
	 * dayCyl5:与1900年1月31日相差的天数,再加40 ?
	 */
	public String myCalendar(Calendar cal) {
		int yearCyl, monCyl, dayCyl;
		int leapMonth = 0;
		Date baseDate = null;
		try {
			baseDate = chineseDateFormat.parse("1900年1月31日");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// 求出和1900年1月31日相差的天数
		int offset = (int) ((cal.getTime().getTime() - baseDate.getTime()) / 86400000L);
		dayCyl = offset + 40;
		monCyl = 14;

		// 用offset减去每农历年的天数
		// 计算当天是农历第几天
		// i最终结果是农历的年份
		// offset是当年的第几天
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
		// 农历年份
		year = iYear;

		yearCyl = iYear - 1864;
		leapMonth = leapMonth(iYear); // 闰哪个月,1-12
		leap = false;

		// 用当年的天数offset,逐个减去每月（农历）的天数，求出当天是本月的第几天
		int iMonth, daysOfMonth = 0;
		for (iMonth = 1; iMonth < 13 && offset > 0; iMonth++) {
			// 闰月
			if (leapMonth > 0 && iMonth == (leapMonth + 1) && !leap) {
				--iMonth;
				leap = true;
				daysOfMonth = leapDays(year);
			} else
				daysOfMonth = monthDays(year, iMonth);

			offset -= daysOfMonth;
			// 解除闰月
			if (leap && iMonth == (leapMonth + 1))
				leap = false;
			if (!leap)
				monCyl++;
		}
		// offset为0时，并且刚才计算的月份是闰月，要校正
		if (offset == 0 && leapMonth > 0 && iMonth == leapMonth + 1) {
			if (leap) {
				leap = false;
			} else {
				leap = true;
				--iMonth;
				--monCyl;
			}
		}
		// offset小于0时，也要校正
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

		String sFeast=getSFeast(m,d);				//获取公历节日
		String lFeast=getLFeast(month,day);			//获取农历节日
		String solarTerms="";
		//获取节气
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
		//判断是否为母亲节
		if(cal.get(Calendar.MONTH)==4){    
			if(cal.get(Calendar.WEEK_OF_MONTH)==getWeek(cal) && cal.get(Calendar.DAY_OF_WEEK)==1){
				if(!"".equals(tempStr)){
					tempStr=tempStr+"/"+"母亲节";
				}else{
					tempStr="母亲节";
				}
			}
		}
		//判断是否为父亲节
		if(cal.get(Calendar.MONTH)==5){
			if((cal.get(Calendar.WEEK_OF_MONTH)==getWeek(cal)) && (cal.get(Calendar.DAY_OF_WEEK)==1)){
			System.out.println("父："+getWeek(cal));
				if(!"".equals(tempStr)){
					tempStr=tempStr+"/"+"父亲节";
				}else{
					tempStr="父亲节";
				}
			}
		}
		//判断是否为闰月
		if(!"".equals(tempStr)){
			return "<font color='red'>"+tempStr+"</font>";
		}else{
			if(day==1){
				return  (leap ? "闰" : "") + chineseNumber[month - 1] + "月";
			}else{
				return getChinaDayString(day);
			}
		}
	}
}
