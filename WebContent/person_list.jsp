<%@ page contentType="text/html; charset=gb2312" language="java" import="java.util.*" errorPage="" %>
<%@ page import="com.model.ViewPerInfoForm"%>
<%List<ViewPerInfoForm> list=(List<ViewPerInfoForm>)request.getAttribute("personlist");
%>
<html>
  <head>
    <title>�鿴������Ϣ</title>
	<link href="CSS/style.css" rel="stylesheet"/>
  </head>

  <body>
<%@ include file="top.jsp"%>
<div id="main" style="padding-top:5px;">

  <table width="98%" height="48" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td height="30" align="center" > <form name="form1" method="post" action="ViewPerInfoServlet?action=personsearch">�������ѯ�ؼ��֣�
       
          <input name="key" type="text" size="40">
          &nbsp;
          <input name="Submit" type="submit" class="btn_bg" value="��ѯ">
      </form>
      </td>
    </tr>
    <tr>
      <td height="5px"></td>
    </tr>
  </table>
<table width="98%" height="60" border="0" cellpadding="0" cellspacing="1" bgcolor="#F2F2F0">
<tr>
<td width="7%" height="27" align="center" bgcolor="#EBDFC9">����</td>
<td width="8%" align="center" bgcolor="#EBDFC9">�Ա�</td>
<td width="7%" align="center" bgcolor="#EBDFC9">��������</td>
<td width="11%" align="center" bgcolor="#EBDFC9">�绰</td>
<td width="5%" align="center" bgcolor="#EBDFC9">����</td>
<td width="10%" align="center" bgcolor="#EBDFC9">ְλ</td>
<td width="17%" align="center" bgcolor="#EBDFC9">����ְ��</td>
<td width="5%" align="center" bgcolor="#EBDFC9">��ְ����</td>
<td width="5%" align="center" bgcolor="#EBDFC9">��ְ����</td>


</tr>
<%if(list.size()==0){%>
	<tr>
		<td colspan="7" align="center" bgcolor="#FFFFFF">�����û���Ϣ��</td>
	</tr>
<%}else{%>
	<%for(int i=0;i<list.size();i++){ 
	%>
	  <tr>
		<td height="27" bgcolor="#FFFFFF">&nbsp;<%=list.get(i).getPername() %></td>
		<td bgcolor="#FFFFFF">&nbsp;<%=list.get(i).getSex() %></td>
	    <td bgcolor="#FFFFFF">&nbsp;<%=list.get(i).getDataofd()%></td>
	    <td bgcolor="#FFFFFF">&nbsp;<%=list.get(i).getPhone() %></td>
	    <td bgcolor="#FFFFFF">&nbsp;<%=list.get(i).getLandline() %></td>
	    <td bgcolor="#FFFFFF">&nbsp;<%=list.get(i).getPosition() %></td>
	   <td bgcolor="#FFFFFF">&nbsp;<%=list.get(i).getDuty() %></td>
	   <td bgcolor="#FFFFFF">&nbsp;<%=list.get(i).getEntrytime() %></td>
	   <td bgcolor="#FFFFFF">&nbsp;<%=list.get(i).getDepartime() %></td>
	   
	    
	  </tr>     
	<%}
}%> 
</table>
</div>
<%@ include file="copyright.jsp"%>
  </body>
</html>
