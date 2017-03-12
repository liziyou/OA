<%@ page contentType="text/html; charset=gb2312" language="java" import="java.util.*" errorPage="" %>
<%@page import="java.text.SimpleDateFormat"%>
<jsp:useBean id="su" class="com.tools.StringUtils" scope="request"/>
<%
String date=request.getParameter("date");
if("".equals(date) || date==null){
	date=new SimpleDateFormat("yyyy-M-d").format(new Date());
}
Date date1=new Date();
String time=date1.getHours()+":"+su.formatNO(date1.getMinutes(),2);

%>
<html>
  <head>
    <title>添加备忘信息</title>
	<link href="CSS/style.css" rel="stylesheet"/>
<script language="javascript"> 
function check(form){
   if(form.title.value==""){
     alert("请输入标题！");form.title.focus();return false;
   }
   	var temp="";
	for(i=0;i<form.remindMode.length;i++){
		if(form.remindMode[i].checked){
			temp=form.remindMode[i].value;
			break;				//跳出循环，减少不必要的循环次数
		}
	}
	switch(temp){
		case "0":			//单次
			break;
		case "1":			//每天
			break;
		case "2":			//每周
			var flag1 = false;
			for(i=0;i<form.flag.length;i++){
				if(form.flag[i].checked){
					flag1 = true;
					break;
				}
			}
			if(!flag1){
				alert("请选择要提醒的星期！");
				return false;
			}
			break;	
		case "3":			//每月
			var flag1 = false;
			for(i=0;i<form.flag.length;i++){
				if(form.flag[i].checked){
					flag1 = true;
					break;
				}
			}
			if(!flag1){
				alert("请选择要提醒的日期！");
				return false;
			}
			break;	
		case "4":			//每年
			if(form.flag_m.value==2 && form.flag_d.value>29){
				alert("您输入的日期不正确！");form.flag_d.focus();
				return false;
			}else if(form.flag_m.value%2==0 && form.flag_d.value>30){
				alert("您输入的日期不正确！");form.flag_d.focus();
				return false;
			}
			break;	
	}
   
   if(form.remindTime.value==""){
     alert("请输入提醒时间！");form.remindTime.focus();return false;
   }else{
	   if(!checkTime(form.remindTime.value)){
			alert("您输入的提醒时间格式不正确！");form.remindTime.focus();return false;
	   }
   }
}
</script> 
<script language="javascript">
//判断输入的日期是否正确
function CheckDate(INDate){
 if (INDate=="")			//如果输入日期为空
    {return true;}
	subYY=INDate.substr(0,4)	 //截取输入日期的前4位
	if(isNaN(subYY) || subYY<=0){	//如果输入的不是数字或者是负数
		return true;
	}
	//转换月份，从输入数字的第0位开始查找，判断是否有字符串"-"，如果有将separate的值设置为"-"
	if(INDate.indexOf('-',0)!=-1){	separate="-"}
	else{
		if(INDate.indexOf('/',0)!=-1){separate="/"}//如果没有字符串"-"，那么将separate的值设置为"/"
		else {return true;}//如果以上两者均查询不到，则返回true
		}
		area=INDate.indexOf(separate,0)//从字符串的第一个位置开始查找，返回特定字符的位置
		subMM=INDate.substr(area+1,INDate.indexOf(separate,area+1)-(area+1))
		if(isNaN(subMM) || subMM<=0){
		return true;
	}
		if(subMM.length<2){subMM="0"+subMM}//如果月份只有1位，那么在它的前面加"0"
	//转换日
	area=INDate.lastIndexOf(separate)//从字符串的最后一位开始查找，返回特定字符的位置
	subDD=INDate.substr(area+1,INDate.length-area-1)
	if(isNaN(subDD) || subDD<=0){
		return true;
	}
	if(eval(subDD)<10){subDD="0"+eval(subDD)}	//对作为数字表达式的字符串求值
	NewDate=subYY+"-"+subMM+"-"+subDD
	if(NewDate.length!=10){return true;}	//如果输入日期的长度小于10
    if(NewDate.substr(4,1)!="-"){return true;}	//如果输入日期的第5位不是"-"
    if(NewDate.substr(7,1)!="-"){return true;}	//如果输入日期的第8位不是"-"
	var MM=NewDate.substr(5,2);		//从第5位开始截取字符串，截取2为，即获得月份
	var DD=NewDate.substr(8,2);		//从第8位开始截取字符串，截取2为，即获得日
	//判断是否为闰年（能被4整除并且不能被100整除或者只能被400整除的年份就为闰年）
	if((subYY%4==0 && subYY%100!=0)||subYY%400==0){ //判断是否为闰年
		if(parseInt(MM)==2){	//将MM转化为整数
			if(DD>29){return true;}
		}
	}else{
		if(parseInt(MM)==2){
			if(DD>28){return true;}
		}	
	}
	var mm=new Array(1,3,5,7,8,10,12); //判断每月中的最大天数
	for(i=0;i< mm.length;i++){
		if (parseInt(MM) == mm[i]){	//判断月份是否为数组中的值
			if(parseInt(DD)>31){
				return true;
			}else{
				return false;
			}
		}
	}
   if(parseInt(DD)>30){return true;}
   if(parseInt(MM)>12){return true;}//如果月份大于12
   return false;	//如果以上情况都不是则返回false
}
function checkTime(str){
	 //在JavaScript中，正则表达式只能使用"/"开头和结束，不能使用双引号
	var Expression=/^(([0?0-9])|[01][0-9]|20|21|22|23):([0-5][0-9])$/; 
	var objExp=new RegExp(Expression);
	if(objExp.test(str)==true){
		return true;
	}else{
		return false;
	}
}   
function checkDay(str){
	 //在JavaScript中，正则表达式只能使用"/"开头和结束，不能使用双引号
	var Expression=/^([1-9]|[12][0-9]|30|31)$/; 		//只能输入1-31的数
	var objExp=new RegExp(Expression);
	if(objExp.test(str)==true){
		return true;
	}else{
		return false;
	}	
}
</script>
  </head>
  
  <body  style=" background-color:#FFFFFF;padding:3px; background-image:url(images/add_bottom.gif); background-repeat:repeat-x; background-position:bottom">
<img src="images/add_top.gif">
<script language="javascript">
//进行提醒方式选择时，执行的处理代码
function deal(form){
	var flag="";
	for(i=0;i<form.remindMode.length;i++){
		if(form.remindMode[i].checked){
			flag=form.remindMode[i].value;
			break;				//跳出循环，减少不必要的循环次数
		}
	}
	//设置每周显示的标记内容
	var temp="";
	var arrTemp=new Array("周日","周一","周二","周三","周四","周五","周六");
	for(i=1;i<8;i++){
		temp=temp+"<input name=\"flag\" type=\"checkbox\" class=\"noborder\" id=\"flag\" value=\""+i+"\">"+arrTemp[i-1]+"&nbsp;";
	}
	switch(flag){
		case "0":			//单次
			document.getElementById("remindFlag").innerHTML="公历日期：<input name=\"flag\" type=\"text\" id=\"flag\" value=\"<%=date%>\" size=\"10\" onblur=\"if(CheckDate(this.value)){alert('您输入的日期不正确或者为空，请重新输入');this.focus();}\">";
			break;
		case "1":			//每天
			document.getElementById("remindFlag").innerHTML="";document.getElementById("remindFlag").style.height="0px";
			break;
		case "2":			//每周
			document.getElementById("remindFlag").innerHTML=temp;
			break;	
		case "3":			//每月
			document.getElementById("remindFlag").innerHTML="<%for(int i=1;i<32;i++){%><input name=\"flag\" type=\"checkbox\" class=\"noborder\" id=\"flag\"  value=\"<%=su.formatNO(i,2)%>\"><%=su.formatNO(i,2)%><%if(i%9==0){out.print("<br>");}}%>";
			break;	
		case "4":			//每年
			document.getElementById("remindFlag").innerHTML="公历<select name=\"flag_m\" class=\"wenbenkuang\" id=\"flag_m\"><%for(int i=1;i<13;i++){%><option value=\"<%=i%>\"><%=i%></option><%}%></select>月<input type=\"text\" name=\"flag_d\" id=\"flag_d\" size=\"2\" value=\"1\" onblur=\"if(!checkDay(this.value)){alert('您输入的日期不正确！');this.focus();}\">日";
			break;	
	}
}
</script>

<form name="form1" method="post" action="MemoServlet?action=add" onSubmit="return check(this)">
  <table width="556" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td width="5%" height="30">&nbsp;</td>
      <td width="11%">标&nbsp;&nbsp;&nbsp;&nbsp;题：</td>
      <td width="84%">
        <input name="title" type="text" id="title" size="40">      </td>
    </tr>
    <tr>
      <td height="30">&nbsp;</td>
      <td>提醒方式：</td>
      <td><input name="remindMode" type="radio" class="noborder" value="0" checked onClick="deal(this.form)">
        单次&nbsp;
        <input name="remindMode" type="radio" class="noborder" value="1" onClick="deal(this.form)"> 
        每天&nbsp;
        <input name="remindMode" type="radio" class="noborder" value="2" onClick="deal(this.form)">
每周&nbsp;
<input name="remindMode" type="radio" class="noborder" value="3" onClick="deal(this.form)">
每月 
        &nbsp;
        <input name="remindMode" type="radio" class="noborder" value="4" onClick="deal(this.form)">
每年&nbsp;</td>
    </tr>
    <tr>
      <td height="30">&nbsp;</td>
      <td>&nbsp;</td>
      <td id="remindFlag">公历日期：
        <input name="flag" type="text" id="flag" value="<%=date%>" size="10" onBlur="if(CheckDate(this.value)){alert('您输入的日期不正确，请重新输入');this.focus();}"></td>
    </tr>
    <tr>
      <td height="30">&nbsp;</td>	
      <td height="30">提醒时间：</td>
      <td><input name="remindTime" type="text" id="remindTime" value="<%=time%>" size="10"></td>
    </tr>
	
    <tr>
      <td>&nbsp;</td>
      <td>内&nbsp;&nbsp;&nbsp;&nbsp;容：</td>
      <td><textarea name="content" cols="60" rows="10" class="wenbenkuang" id="content"></textarea></td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td height="40">&nbsp;</td>
      <td><input name="Submit" type="submit" class="btn_bg" value="保存">
      &nbsp;
      <input name="Reset" type="reset" class="btn_bg" value="重置"></td>
    </tr>
  </table>
</form>

  </body>
</html>
