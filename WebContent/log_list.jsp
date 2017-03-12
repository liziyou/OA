<%@ page contentType="text/html; charset=gb2312" language="java" import="java.util.*" errorPage="" %>
<%@ page import="com.model.LogForm"%>
<%List<LogForm> list=(List<LogForm>)request.getAttribute("logList");
String temp="";
%>
<html>
  <head>
    <title>查看日志信息</title>
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
      <td height="30" align="center" > <form name="form1" method="post" action="LogServlet?action=logsearch">请输入查询关键字：
       
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
<td width="17%" height="27" align="center" bgcolor="#EBDFC9">创建者</td>
<td width="49%" align="center" bgcolor="#EBDFC9">日志内容</td>
<td width="8%" align="center" bgcolor="#EBDFC9">日志标题</td>
<td width="7%" align="center" bgcolor="#EBDFC9">创建时间</td>
</tr>
<%if(list.size()==0){%>
	<tr>
		<td colspan="6" align="center" bgcolor="#FFFFFF">暂无备忘信息！</td>
	</tr>
<%}else{%>
	<%for(int i=0;i<list.size();i++){ 
	%>
	  <tr>
		<td height="27" bgcolor="#FFFFFF">&nbsp;<%=list.get(i).getLogcreater()%></td>
		<td bgcolor="#FFFFFF">&nbsp;<%=list.get(i).getLogcon()%></td>
	    <td bgcolor="#FFFFFF">&nbsp;<%=list.get(i).getLogtitle()%></td>
	    <td bgcolor="#FFFFFF">&nbsp;<%=list.get(i).getLogtime()%></td>
	  </tr>     
	<%}
}%> 
</table>
</div>
<%@ include file="copyright.jsp"%>
  </body>
</html>
