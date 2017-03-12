<%@ page contentType="text/html; charset=gb2312" language="java" import="java.util.*" errorPage="" %>
<%@ page import="com.model.ProjectForm"%>
<%List<ProjectForm> list=(List<ProjectForm>)request.getAttribute("myproList");
%>
<html>
  <head>
    <title>查看项目信息</title>
	<link href="CSS/style.css" rel="stylesheet"/>
  </head>
	<script language="javascript">
	function del(para){
		if(confirm("真的要删除该项目信息吗？")){
			window.location.href="ProjectServlet?action=delproject&pjid="+para+"&url=<%=request.getAttribute("url").toString()%>";
		}
	}
	</script>  
  <body>
<%@ include file="top.jsp"%>
<div id="main" style="padding-top:5px;">

  <table width="98%" height="48" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td height="30" align="center" > <form name="form1" method="post" action="ProjectServlet?action=searchpro">请输入查询关键字：
       
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
<td width="17%" height="27" align="center" bgcolor="#EBDFC9">项目名</td>
<td width="17%" align="center" bgcolor="#EBDFC9">负责人</td>
<td width="8%" align="center" bgcolor="#EBDFC9">开始时间</td>
<td width="7%" align="center" bgcolor="#EBDFC9">结束时间</td>
<td width="47%" align="center" bgcolor="#EBDFC9">备注</td>
<td width="5%" align="center" bgcolor="#EBDFC9">修改</td>
<td width="5%" align="center" bgcolor="#EBDFC9">删除</td>
</tr>
<%if(list.size()==0){%>
	<tr>
		<td colspan="7" align="center" bgcolor="#FFFFFF">暂无项目信息！</td>
	</tr>
<%}else{%>
	<%for(int i=0;i<list.size();i++){ 
	%>
	  <tr>
		<td height="27" bgcolor="#FFFFFF">&nbsp;<%=list.get(i).getPjname()%></td>
		<td bgcolor="#FFFFFF">&nbsp;<%=list.get(i).getPjleader()%></td>
	    <td bgcolor="#FFFFFF">&nbsp;<%=list.get(i).getPjstt()%></td>
	    <td bgcolor="#FFFFFF">&nbsp;<%=list.get(i).getPjtad() %></td>
	    <td bgcolor="#FFFFFF">&nbsp;<%=list.get(i).getRemark() %></td>
	    <td width="72%">&nbsp;&nbsp;<a href="#" onClick="window.open('pro_update.jsp','','width=950,height=519')"><img src="images/update.gif" border="0"></a></td>		  
	    <td width="5%" align="center" bgcolor="#FFFFFF">&nbsp;<a href="#" onClick="del(<%=list.get(i).getPjid()%>)"><img src="images/del.gif" border="0"></a></td>
	  </tr>     
	<%}
}%> 
</table>
</div>
<%@ include file="copyright.jsp"%>
  </body>
</html>
