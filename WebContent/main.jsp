<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*"%>  
<jsp:useBean class="com.tools.MyCalendar" id="calendar" scope="request"/> 
<jsp:useBean class="com.tools.TimeUtils" id="tu" scope="request"/> 
<jsp:useBean class="com.dao.MemoDAO" id="memo" scope="request"/> 
<%@ page import="java.util.*,java.io.*,java.text.*"%> 
<%@ page import="java.util.*" %> 
<%@ page import="java.text.*" %> 
<%@ include file="safe.jsp"%>
<%String user=session.getAttribute("userName").toString();%>
<%
String days[]=new String[42];		 //创建一个42个元素的一维数组，用于初始化万年历中的日期单元格
for(int i=0;i<42;i++){
	days[i]="";
}%>

<%
Calendar today1 = Calendar.getInstance();
SimpleDateFormat chineseDateFormat = new SimpleDateFormat("yyyy-MM-dd");	//指定日期格式为yyyy-MM-dd
int month=0;
int year=0;
GregorianCalendar currentDay = new GregorianCalendar();
//当参数year不为空，表示通过控制区对日历中显示的日期进行改变
if(request.getParameter("year")!=null && ! "".equals(request.getParameter("year"))){
	month=Integer.parseInt(request.getParameter("month"))-1;
	year= Integer.parseInt(request.getParameter("year"));	

}else{		//采用默认值
	month=currentDay.get(Calendar.MONTH);	//获取当前月
	year= currentDay.get(Calendar.YEAR);	//获取当前年
}
int today=currentDay.get(Calendar.DAY_OF_MONTH);	//获取当前日

Calendar thisMonth=Calendar.getInstance();
thisMonth.set(Calendar.MONTH, month );
thisMonth.set(Calendar.YEAR, year );
thisMonth.setFirstDayOfWeek(Calendar.SUNDAY);
thisMonth.set(Calendar.DAY_OF_MONTH,1);
int firstIndex=thisMonth.get(Calendar.DAY_OF_WEEK)-1;	//获取显示月历中的第一天所在的星期
int maxIndex=thisMonth.getActualMaximum(Calendar.DAY_OF_MONTH);		//获取显示月历的总天数
for(int i=0;i<maxIndex;i++){	//生成显示月历中的公历日期
	days[firstIndex+i]=String.valueOf(i+1);
}
%>  
<html>
<head>
<link href="CSS/style.css" rel="stylesheet">
<script language="javascript" src="JS/AjaxRequest.js"></script>
<script language="JavaScript" src="JS/remind.js"></script>
<script type="text/javascript">
<script language="javascript">
timer = window.setInterval("getRemindMessage();",10000); 
window.onload=function() {
	getRemindMessage();
}
function getRemindMessage(){
	var loader=new net.AjaxRequest("MemoServlet?action=getRemind"+new Date().getTime(),deal,onerror,"GET");
}
function onerror(){
	alert("您的操作有误");
	window.opener=null;
	window.close();
}
function deal(){
	var h=this.req.responseText;
	//var h="（1）测试<br>（2）每月的7号27号提醒<br>[2011-3-12&nbsp;10:05][1,7]";	
	
	h=h.replace(/\s/g,"");	//去除字符串中的Unicode空白符
	if(h!=""){
		foot1=h.substr(h.indexOf("[")+1,h.indexOf("]")-h.indexOf("[")-1);
		content=h.substr(0,h.indexOf("["));
	
		id=h.substr(h.lastIndexOf("[")+1,h.lastIndexOf("]")-(h.lastIndexOf("[")+1));
		var remindMessage = new PopBubble(200,120,"备忘录提醒：",content,foot1);  
		remindMessage.box(null,null,null,screen.height-30);	//设置弹出窗口的左边、右边、顶边和底边框的位置 
		remindMessage.speed    = 10; 		//设置窗口的弹出速度
		remindMessage.step    = 2; 			//设置窗口的弹出步幅
		remindMessage.show(); 				//弹出窗口
		PopBubble.prototype.oncommand = function(){  
			window.open("MemoServlet?action=showRemindMsg&id="+id,"","width=300,height=150,scrollbars=1");
			this.close = true;
			this.hide(); 			//收缩窗口
		}
 
	} 
}
</script>
<title>备忘录</title></head>
<body>
<%@ include file="top.jsp"%>
<div id="main">
<div id="left">
  <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  	<tr><td colspan="2" background="images/left_title.jpg" height="37"></td>    
  	<tr>
      <td width="11" bgcolor="#7ACCC8">&nbsp;</td>
      <td width="171" valign="top" bgcolor="#FFFFFF"><table width="100%" height="251" border="0" cellpadding="0" cellspacing="0">
      </tr> 
        <tr>
          <td width="28%" height="27" align="right"><img src="images/ico_father.gif" width="14" height="15"></td>
          <td width="72%" class="word_big">&nbsp;&nbsp;日常管理</td>
        </tr>
       <tr>
          <td width="28%" height="27" align="right"><img src="images/ico_child.gif" width="5" height="8" border="0"></td>
            <td width="72%">&nbsp;&nbsp;<a href="ProjectServlet?action=MyproAll" target="_blank">我的项目</a></td>
        </tr>
      <tr>
           <td width="28%" height="27" align="right"><img src="images/ico_child.gif" width="5" height="8" border="0"></td>
           <td width="72%">&nbsp;&nbsp;<a href="#" onClick="window.open('project_add.jsp','','width=950,height=519')">项目管理</a></td>		  </td>
        </tr>
        <tr>
          <td width="28%" height="27" align="right"><img src="images/ico_child.gif" width="5" height="8" border="0"></td>
            <td width="72%">&nbsp;&nbsp;<a href="LogServlet?action=logAll" target="_blank">工作日志</a></td>
        </tr>
        
        <tr>
		  <td width="28%" height="27" align="right"><img src="images/ico_father.gif" width="14" height="15"></td>
          <td width="72%" class="word_big">&nbsp;&nbsp;考勤管理</td>
        </tr>
        <tr>
          <td width="28%" height="27" align="right"><img src="images/ico_child.gif" width="5" height="8" border="0"></td>
            <td width="72%">&nbsp;&nbsp;<a href="PerAttendServlet?action=listAll" target="_blank">个人考勤</a></td>
        </tr> 
        <tr>
		  <td width="28%" height="27" align="right"><img src="images/ico_father.gif" width="14" height="15"></td>
          <td width="72%" class="word_big">&nbsp;&nbsp;员工管理</td>
        </tr>
        <tr>
          <td width="28%" height="27" align="right"><img src="images/ico_child.gif" width="5" height="8" border="0"></td>
            <td width="72%">&nbsp;&nbsp;<a href="ViewPerInfoServlet?action=personView" target="_blank">查看个人信息</a></td>
        </tr>
      <tr>
          <td width="28%" height="27" align="right"><img src="images/ico_child.gif" width="5" height="8" border="0"></td>
            <td width="72%">&nbsp;&nbsp;<a href="ModifyPerInfoServlet?action=listAll" target="_blank">修改个人信息</a></td>
        </tr>
        <tr>
		<td width="28%" height="27" align="right"><img src="images/ico_child.gif" width="5" height="8" border="0"></td>
           <td width="72%">&nbsp;&nbsp;<a href="ViewPerInfoServlet?action=personAdd" target="_blank">添加用户信息</a></td>
        <tr>
		  <td width="28%" height="27" align="right"><img src="images/ico_father.gif" width="14" height="15"></td>
          <td width="72%" class="word_big">&nbsp;&nbsp;通讯管理</td>
       </tr>
       <tr>
          <td width="28%" height="27" align="right"><img src="images/ico_child.gif" width="5" height="8" border="0"></td>
            <td width="72%">&nbsp;&nbsp;<a href="AddrBookServlet?action=BookAll" target="_blank">个人通讯录</a></td>
        </tr></li>
       <tr>
		<td width="28%" height="27" align="right"><img src="images/ico_child.gif" width="5" height="8" border="0"></td>
          <td width="72%">&nbsp;&nbsp;<a href="#" onClick="window.open('E_mail.jsp','','width=694,height=519')">电子邮箱</a></td>		  
        </tr>
       <tr>
		  <td width="28%" height="27" align="right"><img src="images/ico_father.gif" width="14" height="15"></td>
          <td width="72%" class="word_big">&nbsp;&nbsp;备忘录</td>
        </tr>
         <tr>
		<td width="28%" height="27" align="right"><img src="images/ico_child.gif" width="5" height="8" border="0"></td>
          <td width="72%">&nbsp;&nbsp;<a href="MemoServlet?action=listAll" target="_blank">查看全部备忘录</a></td>
        </tr>
        <tr>
		<td width="28%" height="27" align="right"><img src="images/ico_child.gif" width="5" height="8" border="0"></td>
          <td width="72%">&nbsp;&nbsp;<a href="#" onClick="window.open('memo_add.jsp','','width=694,height=519')">添加备忘录</a></td>		  
        </tr>

        <tr>
          <td height="35" align="right"><img src="images/ico_father.gif" width="14" height="15"></td>
          <td height="35"> &nbsp;&nbsp;<a href="logout.jsp" class="word_big">退出系统</a></td>
        </tr>
    </div>    
    </table>
  
  </table>
</div>
<div id="calendar">
 <img src="images/main_top.jpg" width="681" height="35">
  <!---------------------------显示万年历开始-------------------------------------------->	
 <table width="443" border="0" align="left" style="float:left" cellpadding="0" cellspacing="1" bordercolor="#FFFFFF" bgcolor="#F2F1EF">
   <tr bgcolor="FFFCF1">
     <td height="36" colspan="7" align="center" valign="middle" bgcolor="#AC8F67"><b>
  <form   method="POST"   action="main.jsp">
    【<%=calendar.cyclical(year)+"("+calendar.animalsYear(year)+")"%>】
    <select   name="year"   onChange="this.form.submit();">   
      <%for(int i=2049;i>=1901;i--){ %>   
      <option   value="<%=i%>"   <%if(i==year)   out.println("selected");%>>   
        <%out.println(i);%>   
        </option>   
      <%}%>   
      </select>   
    年&nbsp;&nbsp;<select   name="month"   onChange="this.form.submit();">   
      <%   
      for(int i=1;i<=12;i++){   
      %>   
      <option   value="<%=i%>"   <%if(i==month+1)   out.println("selected");%>><%out.println(i);%></option>   
      <%   
      }   
%> </select>
    月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <%
  int ly,ny;   
  ly=year;   
  ny=year;   
  int   last=month;     
  if (last<1){   
	last=12;   
	ly--;   
  }   
  int next=month+1;   
  if (next>11){   
	next=0; 
	ny++;   
  } %>
  <%if (year>1900){%>
        <a href="main.jsp?year=<%=year-1%>&month=<%=month+1%>"><img src="images/shang-nian.gif" width="17" height="18" border="0" alt="上一年"></a>
<%}else{%>
		<img src="images/shang-nian.gif" width="17" height="18" border="0" alt="上一年">
 <%}
  if (ly>1900){   %>
 	 <a class="word_white"  href="main.jsp?year=<%=ly%>&month=<%=last%>"><img src="images/shang-yue.gif" width="18" height="18" border="0" alt="上一月"></a>&nbsp;
 <% } else{%>
  	<img src="images/shang-yue.gif" width="18" height="18" border="0" alt="上一月">
 <% } %>			  
    <a  class="word_white"   href="main.jsp"><img src="images/ben-yue.gif" width="13" height="18" border="0" alt="本月"></a>
  <%
  if(ny<2050){ %> 
 		<a  class="word_white"   href="main.jsp?year=<%=ny%>&month=<%=next+1%>"><img src="images/xia-yue.gif" width="18" height="18" border="0" alt="下一月"></a>   
 <% }else{%>
  		<img src="images/xia-yue.gif" width="18" height="18" border="0" alt="下一月">
 <% } %>
  <%if(year<2049){%>
		<a href="main.jsp?year=<%=year+1%>&month=<%=month+1%>"><img src="images/xia-nian.gif" width="17" height="18" border="0" alt="下一年"></a>&nbsp;	
	<%}else{%>
		<img src="images/xia-nian.gif" width="13" height="14" border="0">&nbsp;
 <%}%>
  </form></b>       </td>
    </tr>
   <tr bgcolor="C9B65A">
     <td width="44" height="40" align="center" bgcolor="#EBDFC9" class="word_red">日<br>
       SUN </font></td>
	  <td width="44" height="40" align="center" bgcolor="#EBDFC9" class="word_darkGray">一<br>
	    MON</td>
	  <td width="44" height="40" align="center" bgcolor="#EBDFC9" class="word_darkGray">二<br>
	    TUE</td>
	  <td width="44" height="40" align="center" bgcolor="#EBDFC9" class="word_darkGray">三<br>
	    WED</td>
	  <td width="44" height="40" align="center" bgcolor="#EBDFC9" class="word_darkGray">四<br>
	    THU</td>
	  <td width="44" height="40" align="center" bgcolor="#EBDFC9" class="word_darkGray">五<br>
	    FRI</td>
	  <td width="44" height="40" align="center" bgcolor="#EBDFC9" class="word_blue">六<br>
	    SAT</font></td>
    </tr>
   <%String color="#000000";
  String bgcolor="FFFFFF";
   for(int j=0;j<6;j++) { %>
   <tr bgcolor="FFFCF1">
     <% for(int i=j*7;i<(j+1)*7;i++) { 
	
			switch   ((i+1)%7){   
				  case   6:   
				  case   2:   
				  case   3:   
				  case   4:   
				  case   5:   
							color="#000000";   
							break;   
				  case   1:   
							color="#FF0000";   
							break;   
				  case   0:   
							color="#1B789D";   
							break;   
			  } 
			  if((i-firstIndex+1)==today){
					bgcolor="#C8E3F3";		//设置当天日期的背景颜色
				}else{
					bgcolor="FFFFFF"; 
				}	
	%>
     <td align="center" bgcolor="<%=bgcolor%>" valign="middle" <%if(!"".equals(days[i])){%>
	<%
	int d=Integer.parseInt(days[i]);
	int week=tu.getWeek(year, month, d);	//获取当前日期是星期几
	
	if(memo.isMemo(user,year,month+1,d,week)){
		out.println(" class=\"flag\""+"style=\"cursor:hand;\" title=\"单击灰色标记查看备忘录信息！\" onclick=\"window.open('MemoServlet?action=list&y="+year+"&m="+(month+1)+"&d="+d+"&week="+week+"')\"");
	}%>>
       <%
		out.println("<a href=\"#\" style=\"cursor:hand;\"  onMouseDown=\"window.open('memo_add.jsp?date="+year+"-"+(month+1)+"-"+d+"','','width=694,height=519');return false;\"><font color=\""+color+"\">"+d+"<br>");
		if(days[i]!=""){
			String wgh=year+"-"+(month+1)+"-"+d;
			today1.setTime(chineseDateFormat.parse(wgh));
			out.println(calendar.myCalendar(today1));		//获取农历日期或是节假日
		}
		out.println("</font></a>");%>
       <%}%>       </td>
	  <% } %>
     </tr>
   <% } %>
 </table> <!---------------------------显示万年历结束-------------------------------------------->
	<div id="right" ><img src="images/right.gif"></div>
  </div>
 
</div>
<%@ include file="copyright.jsp"%>
</body>   
</html>