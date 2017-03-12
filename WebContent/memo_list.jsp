<%@ page contentType="text/html; charset=gb2312" language="java" import="java.util.*" errorPage="" %>
<%@ page import="com.model.MemoForm"%>
<%List<MemoForm> list=(List<MemoForm>)request.getAttribute("memoList");
String temp="";
%>
<html>
  <head>
    <title>查看备忘录信息</title>
	<link href="CSS/style.css" rel="stylesheet"/>
  </head>
	<script language="javascript">
	function del(para){
		if(confirm("真的要删除该备忘信息吗？")){
			window.location.href="MemoServlet?action=del&id="+para+"&url=<%=request.getAttribute("url").toString()%>";
		}
	}
	</script>  
  <body>
<%@ include file="top.jsp"%>
<div id="main" style="padding-top:5px;">

  <table width="98%" height="48" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td height="30" align="center" > <form name="form1" method="post" action="MemoServlet?action=search">请输入查询关键字：
       
          <input name="key" type="text" size="40">
          &nbsp;
          <input name="Submit" type="submit" class="btn_bg" value="查询">
      </form>
      </td>
    </tr>
    <tr>
      <td height="5px"></td>
    </tr>
  </table>
<table width="98%" height="60" border="0" cellpadding="0" cellspacing="1" bgcolor="#F2F2F0">
<tr>
<td width="17%" height="27" align="center" bgcolor="#EBDFC9">标题</td>
<td width="49%" align="center" bgcolor="#EBDFC9">内容</td>
<td width="8%" align="center" bgcolor="#EBDFC9">提醒时间</td>
<td width="7%" align="center" bgcolor="#EBDFC9">提醒方式</td>
<td width="15%" align="center" bgcolor="#EBDFC9">提醒日期</td>
<td width="4%" align="center" bgcolor="#EBDFC9">删除</td>
</tr>
<%if(list.size()==0){%>
	<tr>
		<td colspan="6" align="center" bgcolor="#FFFFFF">暂无备忘信息！</td>
	</tr>
<%}else{%>
	<%for(int i=0;i<list.size();i++){ 
		int remindMode=list.get(i).getRemindMode();
		switch (remindMode) {
			case 0:temp="单次";break;
			case 1:temp="每天";break;
			case 2:temp="每周";break;
			case 3:temp="每月";break;
			case 4:temp="每年";break;
		}
		String flag=list.get(i).getFlag();
		if(remindMode==2){		//将提醒方式为每周的提醒日期转换为“周日,周一……”的格式
			flag=flag.substring(0,flag.length()-1);
			String[] arrFlag=flag.split(",");
			int week=0;
			flag="";
			for(int j=0;j<arrFlag.length;j++){
				week=Integer.parseInt(arrFlag[j]);
				switch (week) {
					case 1:flag+="周日,";break;
					case 2:flag+="周一,";break;
					case 3:flag+="周二,";break;
					case 4:flag+="周三,";break;
					case 5:flag+="周四,";break;
					case 6:flag+="周五,";break;
					case 7:flag+="周六,";break;
				}				
			}
			flag=flag.substring(0,flag.length()-1);
		}else if(remindMode==3){
			flag=flag.substring(0,flag.length()-1);
		}
	%>
	  <tr>
		<td height="27" bgcolor="#FFFFFF">&nbsp;<%=list.get(i).getTitle()%></td>
		<td bgcolor="#FFFFFF">&nbsp;<%=list.get(i).getContent()%></td>
	    <td bgcolor="#FFFFFF">&nbsp;<%=list.get(i).getRemindTime()%></td>
	    <td bgcolor="#FFFFFF">&nbsp;<%=temp%></td>
	    <td bgcolor="#FFFFFF">&nbsp;<%=flag%></td>
	    <td align="center" bgcolor="#FFFFFF"><a href="#" onClick="del(<%=list.get(i).getId()%>)"><img src="images/del.gif" border="0"></a></td>
	  </tr>     
	<%}
}%> 
</table>
</div>
<%@ include file="copyright.jsp"%>
  </body>
</html>
